package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.scripting.ScriptParser;
import com.jordanbunke.stipple_effect.scripting.ScriptParserBaseVisitor;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.*;

public final class ScriptVisitor
        extends ScriptParserBaseVisitor<ASTNode> {
    @Override
    public HeadFuncNode visitHead_rule(
            final ScriptParser.Head_ruleContext ctx
    ) {
        // signature
        final MethodSignatureNode signature =
                (MethodSignatureNode) visit(ctx.signature());
        // body
        final StatementNode[] statements = ctx.stat().stream()
                .map(s -> (StatementNode) visit(s))
                .toArray(StatementNode[]::new);
        return new HeadFuncNode(
                TextPosition.fromToken(ctx.FUNC().getSymbol()),
                signature, statements);
    }

    @Override
    public MethodSignatureNode visitVoidReturnSignature(
            final ScriptParser.VoidReturnSignatureContext ctx
    ) {
        final boolean hasParams = ctx.param_list() != null;

        final ParametersNode parameters = hasParams
                ? visitParam_list(ctx.param_list())
                : new ParametersNode(
                        TextPosition.fromToken(ctx.RPAREN().getSymbol()),
                new DeclarationNode[] {});

        return new MethodSignatureNode(
                TextPosition.fromToken(ctx.LPAREN().getSymbol()), parameters);
    }

    @Override
    public MethodSignatureNode visitTypeReturnSignature(
            final ScriptParser.TypeReturnSignatureContext ctx
    ) {
        final boolean hasParams = ctx.param_list() != null;

        final ParametersNode parameters = hasParams
                ? visitParam_list(ctx.param_list())
                : new ParametersNode(
                TextPosition.fromToken(ctx.RPAREN().getSymbol()),
                new DeclarationNode[] {});

        return new MethodSignatureNode(
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
    public SimpleTypeNode visitBoolType(
            final ScriptParser.BoolTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
    }

    @Override
    public SimpleTypeNode visitIntType(
            final ScriptParser.IntTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }

    @Override
    public SimpleTypeNode visitFloatType(
            final ScriptParser.FloatTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);
    }

    @Override
    public SimpleTypeNode visitCharType(
            final ScriptParser.CharTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.CHAR);
    }

    @Override
    public SimpleTypeNode visitStringType(
            final ScriptParser.StringTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.STRING);
    }

    @Override
    public SimpleTypeNode visitImageType(
            final ScriptParser.ImageTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }

    @Override
    public SimpleTypeNode visitColorType(
            final ScriptParser.ColorTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.COLOR);
    }

    @Override
    public CollectionTypeNode visitArrayType(
            final ScriptParser.ArrayTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.ARRAY,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitSetType(
            final ScriptParser.SetTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.SET,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitListType(
            final ScriptParser.ListTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.LIST,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public MapTypeNode visitMapType(
            final ScriptParser.MapTypeContext ctx
    ) {
        return new MapTypeNode(
                (TypeNode) visit(ctx.key),
                (TypeNode) visit(ctx.val));
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
    public StatementNode visitComplexBody(
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
        return new BoolLiteralNode(
                TextPosition.fromToken(ctx.BOOL_LIT().getSymbol()),
                Boolean.parseBoolean(ctx.BOOL_LIT().getSymbol().getText()));
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
                CollectionTypeNode.Type.LIST);
    }

    @Override
    public NewCollectionNode visitNewSetExpression(
            final ScriptParser.NewSetExpressionContext ctx
    ) {
        return new NewCollectionNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                CollectionTypeNode.Type.SET);
    }

    @Override
    public NewMapNode visitNewMapExpression(
            final ScriptParser.NewMapExpressionContext ctx
    ) {
        return new NewMapNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()));
    }
}
