package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.scripting.ScriptParser;
import com.jordanbunke.stipple_effect.scripting.ScriptParserBaseVisitor;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function.FuncCallNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function.HOFuncCallNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function.HOFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.color_def.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.img_gen.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.rng.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.tex_lookup.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.property.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.*;

import java.util.List;

public class ScriptVisitor
        extends ScriptParserBaseVisitor<ASTNode> {
    @Override
    public HeadFuncNode visitHead_rule(
            final ScriptParser.Head_ruleContext ctx
    ) {
        return new HeadFuncNode(
                TextPosition.fromToken(ctx.start),
                (FuncSignatureNode) visit(ctx.signature()),
                (StatementNode) visit(ctx.func_body()),
                ctx.helper().stream().map(this::visitHelper)
                        .toArray(HelperFuncNode[]::new));
    }

    @Override
    public HelperFuncNode visitHelper(
            final ScriptParser.HelperContext ctx
    ) {
        return new HelperFuncNode(
                TextPosition.fromToken(ctx.start),
                (FuncSignatureNode) visit(ctx.signature()),
                (StatementNode) visit(ctx.func_body()),
                ctx.ident().getText());
    }

    @Override
    public StatementNode visitStandardFuncBody(
            final ScriptParser.StandardFuncBodyContext ctx
    ) {
        return (StatementNode) visit(ctx.body());
    }

    @Override
    public ReturnStatementNode visitFunctionalFuncBody(
            final ScriptParser.FunctionalFuncBodyContext ctx
    ) {
        return ReturnStatementNode.forTyped(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public FuncSignatureNode visitVoidReturnSignature(
            final ScriptParser.VoidReturnSignatureContext ctx
    ) {
        final boolean hasParams = ctx.param_list() != null;

        final ParametersNode parameters = hasParams
                ? visitParam_list(ctx.param_list())
                : new ParametersNode(
                        TextPosition.fromToken(ctx.RPAREN().getSymbol()),
                new DeclarationNode[] {});

        return new FuncSignatureNode(
                TextPosition.fromToken(ctx.LPAREN().getSymbol()), parameters);
    }

    @Override
    public FuncSignatureNode visitTypeReturnSignature(
            final ScriptParser.TypeReturnSignatureContext ctx
    ) {
        final boolean hasParams = ctx.param_list() != null;

        final ParametersNode parameters = hasParams
                ? visitParam_list(ctx.param_list())
                : new ParametersNode(
                TextPosition.fromToken(ctx.RPAREN().getSymbol()),
                new DeclarationNode[] {});

        return new FuncSignatureNode(
                TextPosition.fromToken(ctx.LPAREN().getSymbol()),
                parameters, (TypeNode) visit(ctx.type()));
    }

    @Override
    public ParametersNode visitParam_list(
            final ScriptParser.Param_listContext ctx
    ) {
        return new ParametersNode(
                TextPosition.fromToken(ctx.start),
                ctx.declaration().stream()
                        .map(this::visitDeclaration)
                        .toArray(DeclarationNode[]::new));
    }

    @Override
    public DeclarationNode visitDeclaration(
            final ScriptParser.DeclarationContext ctx
    ) {
        return new DeclarationNode(
                TextPosition.fromToken(ctx.start),
                ctx.FINAL() == null,
                (TypeNode) visit(ctx.type()),
                visitIdent(ctx.ident()));
    }

    @Override
    public IdentifierNode visitIdent(
            final ScriptParser.IdentContext ctx
    ) {
        return new IdentifierNode(
                TextPosition.fromToken(ctx.start),
                ctx.IDENTIFIER().getSymbol().getText());
    }

    @Override
    public BaseTypeNode visitBoolType(
            final ScriptParser.BoolTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.BOOL);
    }

    @Override
    public BaseTypeNode visitIntType(
            final ScriptParser.IntTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.INT);
    }

    @Override
    public BaseTypeNode visitFloatType(
            final ScriptParser.FloatTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.FLOAT);
    }

    @Override
    public BaseTypeNode visitCharType(
            final ScriptParser.CharTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.CHAR);
    }

    @Override
    public BaseTypeNode visitStringType(
            final ScriptParser.StringTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.STRING);
    }

    @Override
    public BaseTypeNode visitImageType(
            final ScriptParser.ImageTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.IMAGE);
    }

    @Override
    public BaseTypeNode visitColorType(
            final ScriptParser.ColorTypeContext ctx
    ) {
        return new BaseTypeNode(TextPosition.fromToken(ctx.start),
                BaseTypeNode.Type.COLOR);
    }

    @Override
    public CollectionTypeNode visitArrayType(
            final ScriptParser.ArrayTypeContext ctx
    ) {
        return new CollectionTypeNode(
                TextPosition.fromToken(ctx.start),
                CollectionTypeNode.Type.ARRAY,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitSetType(
            final ScriptParser.SetTypeContext ctx
    ) {
        return new CollectionTypeNode(
                TextPosition.fromToken(ctx.start),
                CollectionTypeNode.Type.SET,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitListType(
            final ScriptParser.ListTypeContext ctx
    ) {
        return new CollectionTypeNode(
                TextPosition.fromToken(ctx.start),
                CollectionTypeNode.Type.LIST,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public MapTypeNode visitMapType(
            final ScriptParser.MapTypeContext ctx
    ) {
        return new MapTypeNode(
                TextPosition.fromToken(ctx.start),
                (TypeNode) visit(ctx.key),
                (TypeNode) visit(ctx.val));
    }

    @Override
    public FuncTypeNode visitFunctionType(
            final ScriptParser.FunctionTypeContext ctx
    ) {
        final TypeNode[] paramTypes = ctx.func_type().param_types().type()
                .stream().map(p -> (TypeNode) visit(p))
                .toArray(TypeNode[]::new);

        return new FuncTypeNode(TextPosition.fromToken(ctx.start),
                paramTypes, (TypeNode) visit(ctx.func_type().ret));
    }

    @Override
    public StatementNode visitLoopStatement(
            final ScriptParser.LoopStatementContext ctx
    ) {
        return (StatementNode) visit(ctx.loop_stat());
    }

    @Override
    public WhileLoopNode visitWhileLoop(
            final ScriptParser.WhileLoopContext ctx
    ) {
        final ExpressionNode loopCondition =
                (ExpressionNode) visit(ctx.while_def().expr());
        final StatementNode loopBody = (StatementNode) visit(ctx.body());

        return new WhileLoopNode(
                TextPosition.fromToken(ctx.start),
                loopCondition, loopBody);
    }

    @Override
    public DoWhileLoopNode visitDoWhileLoop(
            final ScriptParser.DoWhileLoopContext ctx
    ) {
        final ExpressionNode loopCondition =
                (ExpressionNode) visit(ctx.while_def().expr());
        final StatementNode loopBody = (StatementNode) visit(ctx.body());

        return new DoWhileLoopNode(
                TextPosition.fromToken(ctx.start),
                loopCondition, loopBody);
    }

    @Override
    public IteratorLoopNode visitIteratorLoop(
            final ScriptParser.IteratorLoopContext ctx
    ) {
        final DeclarationNode declaration =
                visitDeclaration(ctx.iteration_def().declaration());
        final ExpressionNode collection =
                (ExpressionNode) visit(ctx.iteration_def().expr());
        final StatementNode loopBody = (StatementNode) visit(ctx.body());

        return new IteratorLoopNode(
                TextPosition.fromToken(ctx.start),
                declaration, collection, loopBody);
    }

    @Override
    public ForLoopNode visitForLoop(
            final ScriptParser.ForLoopContext ctx
    ) {
        final InitializationNode initialization =
                visitVar_init(ctx.for_def().var_init());
        final ExpressionNode loopCondition =
                (ExpressionNode) visit(ctx.for_def().expr());
        final AssignmentNode incrementation =
                (AssignmentNode) visit(ctx.for_def().assignment());
        final StatementNode loopBody = (StatementNode) visit(ctx.body());

        return new ForLoopNode(TextPosition.fromToken(ctx.start),
                initialization, loopCondition, incrementation, loopBody);
    }

    @Override
    public IfStatementNode visitIfStatement(
            final ScriptParser.IfStatementContext ctx
    ) {
        return visitIf_stat(ctx.if_stat());
    }

    @Override
    public IfStatementNode visitIf_stat(
            final ScriptParser.If_statContext ctx
    ) {
        final ExpressionNode[] conditions = ctx.if_def().stream()
                .map(i -> (ExpressionNode) visit(i.cond))
                .toArray(ExpressionNode[]::new);
        final StatementNode[] bodies = ctx.if_def().stream()
                .map(i -> (StatementNode) visit(i.body()))
                .toArray(StatementNode[]::new);
        final StatementNode elseBody = ctx.elseBody != null
                ? (StatementNode) visit(ctx.elseBody) : null;

        return new IfStatementNode(TextPosition.fromToken(ctx.start),
                conditions, bodies, elseBody);
    }

    @Override
    public DeclarationNode visitVarDefStatement(
            final ScriptParser.VarDefStatementContext ctx
    ) {
        return (DeclarationNode) visit(ctx.var_def());
    }

    @Override
    public InitializationNode visitVar_init(
            final ScriptParser.Var_initContext ctx
    ) {
        return new InitializationNode(
                TextPosition.fromToken(ctx.start),
                ctx.declaration().FINAL() == null,
                (TypeNode) visit(ctx.declaration().type()),
                visitIdent(ctx.declaration().ident()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public AssignmentNode visitAssignmentStatement(
            final ScriptParser.AssignmentStatementContext ctx
    ) {
        return (AssignmentNode) visit(ctx.assignment());
    }

    @Override
    public ReturnStatementNode visitReturnStatement(
            final ScriptParser.ReturnStatementContext ctx
    ) {
        final TextPosition position =
                TextPosition.fromToken(ctx.return_stat().start);

        if (ctx.return_stat().expr() == null)
            return ReturnStatementNode.forVoid(position);

        return ReturnStatementNode.forTyped(position,
                (ExpressionNode) visit(ctx.return_stat().expr()));
    }

    @Override
    public AddNode visitAddToCollection(
            final ScriptParser.AddToCollectionContext ctx
    ) {
        return new AddNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.elem),
                ctx.index != null
                        ? (ExpressionNode) visit(ctx.index) : null);
    }

    @Override
    public RemoveNode visitRemoveFromCollection(
            final ScriptParser.RemoveFromCollectionContext ctx
    ) {
        return new RemoveNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.arg));
    }

    @Override
    public MapDefineNode visitDefineMapEntryStatement(
            final ScriptParser.DefineMapEntryStatementContext ctx
    ) {
        return new MapDefineNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map),
                (ExpressionNode) visit(ctx.key),
                (ExpressionNode) visit(ctx.val));
    }

    @Override
    public DrawNode visitDrawOntoImageStatement(
            final ScriptParser.DrawOntoImageStatementContext ctx
    ) {
        return new DrawNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.canvas),
                (ExpressionNode) visit(ctx.img),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y));
    }

    @Override
    public DotNode visitDotStatement(
            final ScriptParser.DotStatementContext ctx
    ) {
        return new DotNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.canvas),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y));
    }

    @Override
    public DrawLineNode visitDrawLineStatement(
            final ScriptParser.DrawLineStatementContext ctx
    ) {
        return new DrawLineNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.canvas),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.breadth),
                (ExpressionNode) visit(ctx.x1),
                (ExpressionNode) visit(ctx.y1),
                (ExpressionNode) visit(ctx.x2),
                (ExpressionNode) visit(ctx.y2));
    }

    @Override
    public FillNode visitFillStatement(
            final ScriptParser.FillStatementContext ctx
    ) {
        return new FillNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.canvas),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y),
                (ExpressionNode) visit(ctx.w),
                (ExpressionNode) visit(ctx.h));
    }

    @Override
    public StatementNode visitSingleStatBody(
            final ScriptParser.SingleStatBodyContext ctx
    ) {
        return (StatementNode) visit(ctx.stat());
    }

    @Override
    public BodyStatementNode visitComplexBody(
            final ScriptParser.ComplexBodyContext ctx
    ) {
        return new BodyStatementNode(
                TextPosition.fromToken(ctx.LCURLY().getSymbol()),
                ctx.stat().stream()
                        .map(s -> (StatementNode) visit(s))
                        .toArray(StatementNode[]::new));
    }

    @Override
    public StandardAssignmentNode visitStandardAssignment(
            final ScriptParser.StandardAssignmentContext ctx
    ) {
        return new StandardAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public NoOperandAssignmentNode visitIncrementAssignment(
            final ScriptParser.IncrementAssignmentContext ctx
    ) {
        return new NoOperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                true);
    }

    @Override
    public NoOperandAssignmentNode visitDecrementAssignment(
            final ScriptParser.DecrementAssignmentContext ctx
    ) {
        return new NoOperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                false);
    }

    @Override
    public OperandAssignmentNode visitAddAssignment(
            final ScriptParser.AddAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.ADD,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitSubAssignment(
            final ScriptParser.SubAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.SUBTRACT,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitMultAssignment(
            final ScriptParser.MultAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.MULTIPLY,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitDivAssignmnet(
            final ScriptParser.DivAssignmnetContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.DIVIDE,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitModAssignment(
            final ScriptParser.ModAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.MODULO,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitAndAssignment(
            final ScriptParser.AndAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.AND,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitOrAssignment(
            final ScriptParser.OrAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.OR,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public BoolLiteralNode visitBoolLiteral(
            final ScriptParser.BoolLiteralContext ctx
    ) {
        return (BoolLiteralNode) visit(ctx.bool_lit());
    }

    @Override
    public BoolLiteralNode visitTrue(final ScriptParser.TrueContext ctx) {
        return new BoolLiteralNode(
                TextPosition.fromToken(ctx.TRUE().getSymbol()), true);
    }

    @Override
    public BoolLiteralNode visitFalse(final ScriptParser.FalseContext ctx) {
        return new BoolLiteralNode(
                TextPosition.fromToken(ctx.FALSE().getSymbol()), false);
    }

    @Override
    public FloatLiteralNode visitFloatLiteral(
            final ScriptParser.FloatLiteralContext ctx
    ) {
        return new FloatLiteralNode(
                TextPosition.fromToken(ctx.FLOAT_LIT().getSymbol()),
                Float.parseFloat(ctx.FLOAT_LIT().getSymbol().getText()));
    }

    @Override
    public IntLiteralNode visitIntLiteral(
            final ScriptParser.IntLiteralContext ctx
    ) {
        return new IntLiteralNode(
                TextPosition.fromToken(ctx.int_lit().start),
                Integer.parseInt(ctx.int_lit().getText()));
    }

    @Override
    public ColorHexCodeLiteralNode visitColorLiteral(
            final ScriptParser.ColorLiteralContext ctx
    ) {
        return new ColorHexCodeLiteralNode(
                TextPosition.fromToken(ctx.start),
                ctx.getText());
    }

    @Override
    public CharLiteralNode visitCharLiteral(
            final ScriptParser.CharLiteralContext ctx
    ) {
        return new CharLiteralNode(
                TextPosition.fromToken(ctx.CHAR_LIT().getSymbol()),
                ctx.CHAR_LIT().getSymbol().getText().charAt(1));
    }

    @Override
    public StringLiteralNode visitStringLiteral(
            final ScriptParser.StringLiteralContext ctx
    ) {
        final String withQuotes = ctx.STRING_LIT().getSymbol().getText();

        return new StringLiteralNode(
                TextPosition.fromToken(ctx.STRING_LIT().getSymbol()),
                withQuotes.substring(1, withQuotes.length() - 1));
    }

    @Override
    public ExpressionNode visitNestedExpression(
            final ScriptParser.NestedExpressionContext ctx
    ) {
        return (ExpressionNode) visit(ctx.expr());
    }

    @Override
    public CastNode visitCastExpression(
            final ScriptParser.CastExpressionContext ctx
    ) {
        return new CastNode(TextPosition.fromToken(ctx.start),
                (TypeNode) visit(ctx.type()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public FuncCallNode visitFunctionCallExpression(
            final ScriptParser.FunctionCallExpressionContext ctx
    ) {
        final ExpressionNode[] args = ctx.args().expr().stream()
                .map(arg -> (ExpressionNode) visit(arg))
                .toArray(ExpressionNode[]::new);

        return new FuncCallNode(TextPosition.fromToken(ctx.start),
                ctx.ident().getText(), args);
    }

    @Override
    public HOFuncCallNode visitHOFuncCallExpression(
            final ScriptParser.HOFuncCallExpressionContext ctx
    ) {
        final ExpressionNode[] args = ctx.args().expr().stream()
                .map(arg -> (ExpressionNode) visit(arg))
                .toArray(ExpressionNode[]::new);

        return new HOFuncCallNode(TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.func), args);
    }

    @Override
    public HOFuncNode visitHOFuncExpression(
            final ScriptParser.HOFuncExpressionContext ctx
    ) {
        return new HOFuncNode(TextPosition.fromToken(ctx.start),
                ctx.ident().getText());
    }

    @Override
    public AssignableNode visitAssignableExpression(
            final ScriptParser.AssignableExpressionContext ctx
    ) {
        return (AssignableNode) visit(ctx.assignable());
    }

    @Override
    public IdentifierNode visitSimpleAssignable(
            final ScriptParser.SimpleAssignableContext ctx
    ) {
        return visitIdent(ctx.ident());
    }

    @Override
    public ListAssignableNode visitListAssignable(
            final ScriptParser.ListAssignableContext ctx
    ) {
        return new ListAssignableNode(
                TextPosition.fromToken(ctx.start),
                ctx.ident().IDENTIFIER().getSymbol().getText(),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public ArrayAssignableNode visitArrayAssignable(
            final ScriptParser.ArrayAssignableContext ctx
    ) {
        return new ArrayAssignableNode(
                TextPosition.fromToken(ctx.start),
                ctx.ident().IDENTIFIER().getSymbol().getText(),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public LiteralNode visitLiteralExpression(
            final ScriptParser.LiteralExpressionContext ctx
    ) {
        return (LiteralNode) visit(ctx.literal());
    }

    @Override
    public UnaryOperationNode visitUnaryExpression(
            final ScriptParser.UnaryExpressionContext ctx
    ) {
        return new UnaryOperationNode(TextPosition.fromToken(ctx.op),
                ctx.op.getText(), (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public BinaryOperationNode visitArithmeticBinExpression(
            final ScriptParser.ArithmeticBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitMultBinExpression(
            final ScriptParser.MultBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitPowerBinExpression(
            final ScriptParser.PowerBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start),
                ctx.RAISE().getSymbol().getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitComparisonBinExpression(
            final ScriptParser.ComparisonBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitLogicBinExpression(
            final ScriptParser.LogicBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public TernaryOperationNode visitTernaryExpression(
            final ScriptParser.TernaryExpressionContext ctx
    ) {
        return new TernaryOperationNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.cond),
                (ExpressionNode) visit(ctx.if_),
                (ExpressionNode) visit(ctx.else_));
    }

    @Override
    public ContainsNode visitContainsExpression(
            final ScriptParser.ContainsExpressionContext ctx
    ) {
        return new ContainsNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.elem));
    }

    @Override
    public MapLookupNode visitMapLookupExpression(
            final ScriptParser.MapLookupExpressionContext ctx
    ) {
        return new MapLookupNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map),
                (ExpressionNode) visit(ctx.elem));
    }

    @Override
    public MapKeysetNode visitMapKeysetExpression(
            final ScriptParser.MapKeysetExpressionContext ctx
    ) {
        return new MapKeysetNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map));
    }

    @Override
    public ColorChannelNode visitColorChannelExpression(
            final ScriptParser.ColorChannelExpressionContext ctx
    ) {
        return new ColorChannelNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.expr()),
                ctx.op.getText().substring(1).toLowerCase());
    }

    @Override
    public AbsoluteNode visitAbsoluteExpression(
            final ScriptParser.AbsoluteExpressionContext ctx
    ) {
        return new AbsoluteNode(
                TextPosition.fromToken(ctx.ABS().getSymbol()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public MinMaxCollectionNode visitMinCollectionExpression(
            final ScriptParser.MinCollectionExpressionContext ctx
    ) {
        return new MinMaxCollectionNode(
                TextPosition.fromToken(ctx.MIN().getSymbol()),
                true, (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public MinMaxTwoArgNode visitMinTwoArgExpression(
            final ScriptParser.MinTwoArgExpressionContext ctx
    ) {
        return new MinMaxTwoArgNode(
                TextPosition.fromToken(ctx.MIN().getSymbol()),
                false, (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public MinMaxCollectionNode visitMaxCollectionExpression(
            final ScriptParser.MaxCollectionExpressionContext ctx
    ) {
        return new MinMaxCollectionNode(
                TextPosition.fromToken(ctx.MAX().getSymbol()),
                true, (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public MinMaxTwoArgNode visitMaxTwoArgExpression(
            final ScriptParser.MaxTwoArgExpressionContext ctx
    ) {
        return new MinMaxTwoArgNode(
                TextPosition.fromToken(ctx.MAX().getSymbol()),
                true, (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public ClampNode visitClampExpression(
            final ScriptParser.ClampExpressionContext ctx
    ) {
        return new ClampNode(
                TextPosition.fromToken(ctx.CLAMP().getSymbol()),
                (ExpressionNode) visit(ctx.min),
                (ExpressionNode) visit(ctx.val),
                (ExpressionNode) visit(ctx.max));
    }

    @Override
    public RandNode visitRandomExpression(
            final ScriptParser.RandomExpressionContext ctx
    ) {
        return new RandNode(
                TextPosition.fromToken(ctx.RAND().getSymbol()));
    }

    @Override
    public RandTwoArgNode visitRandomTwoArgExpression(
            final ScriptParser.RandomTwoArgExpressionContext ctx
    ) {
        return new RandTwoArgNode(
                TextPosition.fromToken(ctx.RAND().getSymbol()),
                (ExpressionNode) visit(ctx.min),
                (ExpressionNode) visit(ctx.max));
    }

    @Override
    public ProbabilityNode visitProbabilityExpression(
            final ScriptParser.ProbabilityExpressionContext ctx
    ) {
        return new ProbabilityNode(
                TextPosition.fromToken(ctx.PROB().getSymbol()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public FlipCoinNode visitFlipCoinBoolExpression(
            ScriptParser.FlipCoinBoolExpressionContext ctx
    ) {
        return new FlipCoinNode(TextPosition.fromToken(
                ctx.FLIP_COIN().getSymbol()));
    }

    @Override
    public TernaryOperationNode visitFlipCoinArgExpression(
            ScriptParser.FlipCoinArgExpressionContext ctx
    ) {
        final TextPosition pos = TextPosition.fromToken(
                ctx.FLIP_COIN().getSymbol());
        return new TernaryOperationNode(pos, new FlipCoinNode(pos),
                (ExpressionNode) visit(ctx.t),
                (ExpressionNode) visit(ctx.f));
    }

    @Override
    public ImageFromPathNode visitImageFromPathExpression(
            final ScriptParser.ImageFromPathExpressionContext ctx
    ) {
        return new ImageFromPathNode(
                TextPosition.fromToken(ctx.expr().start),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public ImageOfBoundsNode visitImageOfBoundsExpression(
            final ScriptParser.ImageOfBoundsExpressionContext ctx
    ) {
        return new ImageOfBoundsNode(
                TextPosition.fromToken(ctx.BLANK().getSymbol()),
                (ExpressionNode) visit(ctx.width),
                (ExpressionNode) visit(ctx.height));
    }

    @Override
    public ColorAtPixelNode visitColorAtPixelExpression(
            final ScriptParser.ColorAtPixelExpressionContext ctx
    ) {
        return new ColorAtPixelNode(
                TextPosition.fromToken(ctx.img.start),
                (ExpressionNode) visit(ctx.img),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y));
    }

    @Override
    public ImageBoundNode visitImageBoundExpression(
            final ScriptParser.ImageBoundExpressionContext ctx
    ) {
        final String text = ctx.op.getText().substring(1);

        return new ImageBoundNode(TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.expr()),
                text.toLowerCase().startsWith("w"));
    }

    @Override
    public TextureColorReplaceNode visitTextureColorReplaceExpression(
            final ScriptParser.TextureColorReplaceExpressionContext ctx
    ) {
        return new TextureColorReplaceNode(
                TextPosition.fromToken(ctx.TEX_COL_REPL().getSymbol()),
                (ExpressionNode) visit(ctx.texture),
                (ExpressionNode) visit(ctx.lookup),
                (ExpressionNode) visit(ctx.replace));
    }

    @Override
    public GenLookupNode visitGenLookupExpression(
            final ScriptParser.GenLookupExpressionContext ctx
    ) {
        return new GenLookupNode(
                TextPosition.fromToken(ctx.GEN_LOOKUP().getSymbol()),
                (ExpressionNode) visit(ctx.source),
                (ExpressionNode) visit(ctx.vert));
    }

    @Override
    public ImageSectionNode visitImageSectionExpression(
            final ScriptParser.ImageSectionExpressionContext ctx
    ) {
        return new ImageSectionNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.img),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y),
                (ExpressionNode) visit(ctx.w),
                (ExpressionNode) visit(ctx.h));
    }

    @Override
    public CharAtNode visitCharAtExpression(
            final ScriptParser.CharAtExpressionContext ctx
    ) {
        return new CharAtNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.string),
                (ExpressionNode) visit(ctx.index));
    }

    @Override
    public SubstringNode visitSubstringExpression(
            final ScriptParser.SubstringExpressionContext ctx
    ) {
        return new SubstringNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.string),
                (ExpressionNode) visit(ctx.beg),
                (ExpressionNode) visit(ctx.end));
    }

    @Override
    public RGBColorNode visitRGBColorExpression(
            final ScriptParser.RGBColorExpressionContext ctx
    ) {
        return new RGBColorNode(
                TextPosition.fromToken(ctx.RGB().getSymbol()),
                (ExpressionNode) visit(ctx.r),
                (ExpressionNode) visit(ctx.g),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public RGBAColorNode visitRGBAColorExpression(
            final ScriptParser.RGBAColorExpressionContext ctx
    ) {
        return new RGBAColorNode(
                TextPosition.fromToken(ctx.RGBA().getSymbol()),
                (ExpressionNode) visit(ctx.r),
                (ExpressionNode) visit(ctx.g),
                (ExpressionNode) visit(ctx.b),
                (ExpressionNode) visit(ctx.a));
    }

    @Override
    public ExplicitMapInitNode visitExplicitMapExpression(
            final ScriptParser.ExplicitMapExpressionContext ctx
    ) {
        final List<ScriptParser.K_v_pairContext> keyValPairs =
                ctx.k_v_pairs().k_v_pair();
        final int n = keyValPairs.size();

        final ExpressionNode[] keys = new ExpressionNode[n];
        final ExpressionNode[] vals = new ExpressionNode[n];

        for (int i = 0; i < n; i++) {
            keys[i] = (ExpressionNode) visit(keyValPairs.get(i).key);
            vals[i] = (ExpressionNode) visit(keyValPairs.get(i).val);
        }

        return new ExplicitMapInitNode(
                TextPosition.fromToken(ctx.LCURLY().getSymbol()),
                keys, vals);
    }

    @Override
    public ExplicitCollectionInitNode visitExplicitArrayExpression(
            final ScriptParser.ExplicitArrayExpressionContext ctx
    ) {
        return new ExplicitCollectionInitNode(
                TextPosition.fromToken(ctx.LBRACKET().getSymbol()),
                CollectionTypeNode.Type.ARRAY,
                ctx.expr().stream()
                        .map(e -> (ExpressionNode) visit(e))
                        .toArray(ExpressionNode[]::new));
    }

    @Override
    public ExplicitCollectionInitNode visitExplicitListExpression(
            final ScriptParser.ExplicitListExpressionContext ctx
    ) {
        return new ExplicitCollectionInitNode(
                TextPosition.fromToken(ctx.LT().getSymbol()),
                CollectionTypeNode.Type.LIST,
                ctx.expr().stream()
                        .map(e -> (ExpressionNode) visit(e))
                        .toArray(ExpressionNode[]::new));
    }

    @Override
    public ExplicitCollectionInitNode visitExplicitSetExpression(
            final ScriptParser.ExplicitSetExpressionContext ctx
    ) {
        return new ExplicitCollectionInitNode(
                TextPosition.fromToken(ctx.LCURLY().getSymbol()),
                CollectionTypeNode.Type.SET,
                ctx.expr().stream()
                        .map(e -> (ExpressionNode) visit(e))
                        .toArray(ExpressionNode[]::new));
    }

    @Override
    public NewArrayNode visitNewArrayExpression(
            final ScriptParser.NewArrayExpressionContext ctx
    ) {
        return new NewArrayNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                (TypeNode) visit(ctx.type()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public NewCollectionNode visitNewListExpression(
            final ScriptParser.NewListExpressionContext ctx
    ) {
        return new NewCollectionNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                CollectionTypeNode.Type.LIST,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public NewCollectionNode visitNewSetExpression(
            final ScriptParser.NewSetExpressionContext ctx
    ) {
        return new NewCollectionNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                CollectionTypeNode.Type.SET,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public NewMapNode visitNewMapExpression(
            final ScriptParser.NewMapExpressionContext ctx
    ) {
        return new NewMapNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                (TypeNode) visit(ctx.kt), (TypeNode) visit(ctx.vt));
    }
}
