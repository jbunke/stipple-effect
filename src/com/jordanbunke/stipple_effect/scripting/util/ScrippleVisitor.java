package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.stipple_effect.scripting.ScrippleParser;
import com.jordanbunke.stipple_effect.scripting.ScrippleParserBaseVisitor;
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

public final class ScrippleVisitor
        extends ScrippleParserBaseVisitor<ASTNode> {
    @Override
    public ScriptFunctionNode visitHead_rule(
            final ScrippleParser.Head_ruleContext ctx
    ) {
        // signature
        final MethodSignatureNode signature =
                (MethodSignatureNode) visit(ctx.signature());
        // body
        final StatementNode[] statements = ctx.stat().stream()
                .map(s -> (StatementNode) visit(s))
                .toArray(StatementNode[]::new);
        return new ScriptFunctionNode(
                TextPosition.fromToken(ctx.FUNC().getSymbol()),
                signature, statements);
    }

    @Override
    public MethodSignatureNode visitVoidReturnSignature(
            final ScrippleParser.VoidReturnSignatureContext ctx
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
            final ScrippleParser.TypeReturnSignatureContext ctx
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
            final ScrippleParser.Param_listContext ctx
    ) {
        return new ParametersNode(
                TextPosition.fromToken(ctx.start),
                ctx.declaration().stream()
                        .map(this::visitDeclaration)
                        .toArray(DeclarationNode[]::new));
    }

    @Override
    public DeclarationNode visitDeclaration(
            final ScrippleParser.DeclarationContext ctx
    ) {
        return new DeclarationNode(
                TextPosition.fromToken(ctx.start),
                ctx.FINAL() == null,
                (TypeNode) visit(ctx.type()),
                visitIdent(ctx.ident()));
    }

    @Override
    public IdentifierNode visitIdent(
            final ScrippleParser.IdentContext ctx
    ) {
        return new IdentifierNode(
                TextPosition.fromToken(ctx.start),
                ctx.IDENTIFIER().getSymbol().getText());
    }

    @Override
    public SimpleTypeNode visitBoolType(
            final ScrippleParser.BoolTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
    }

    @Override
    public SimpleTypeNode visitIntType(
            final ScrippleParser.IntTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }

    @Override
    public SimpleTypeNode visitFloatType(
            final ScrippleParser.FloatTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);
    }

    @Override
    public SimpleTypeNode visitCharType(
            final ScrippleParser.CharTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.CHAR);
    }

    @Override
    public SimpleTypeNode visitStringType(
            final ScrippleParser.StringTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.STRING);
    }

    @Override
    public SimpleTypeNode visitImageType(
            final ScrippleParser.ImageTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }

    @Override
    public SimpleTypeNode visitColorType(
            final ScrippleParser.ColorTypeContext ctx
    ) {
        return new SimpleTypeNode(SimpleTypeNode.Type.COLOR);
    }

    @Override
    public CollectionTypeNode visitArrayType(
            final ScrippleParser.ArrayTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.ARRAY,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitSetType(
            final ScrippleParser.SetTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.SET,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitListType(
            final ScrippleParser.ListTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.LIST,
                (TypeNode) visit(ctx.type()));
    }

    @Override
    public MapTypeNode visitMapType(
            final ScrippleParser.MapTypeContext ctx
    ) {
        return new MapTypeNode(
                (TypeNode) visit(ctx.key),
                (TypeNode) visit(ctx.val));
    }

    @Override
    public StatementNode visitLoopStatement(
            final ScrippleParser.LoopStatementContext ctx
    ) {
        return (StatementNode) visit(ctx.loop_stat());
    }

    @Override
    public WhileLoopNode visitWhileLoop(
            final ScrippleParser.WhileLoopContext ctx
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
            final ScrippleParser.DoWhileLoopContext ctx
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
            final ScrippleParser.IteratorLoopContext ctx
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
            final ScrippleParser.ForLoopContext ctx
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
            final ScrippleParser.IfStatementContext ctx
    ) {
        return visitIf_stat(ctx.if_stat());
    }

    @Override
    public IfStatementNode visitIf_stat(
            final ScrippleParser.If_statContext ctx
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
            final ScrippleParser.VarDefStatementContext ctx
    ) {
        return (DeclarationNode) visit(ctx.var_def());
    }

    @Override
    public InitializationNode visitVar_init(
            final ScrippleParser.Var_initContext ctx
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
            final ScrippleParser.AssignmentStatementContext ctx
    ) {
        return (AssignmentNode) visit(ctx.assignment());
    }

    @Override
    public ReturnStatementNode visitReturnStatement(
            final ScrippleParser.ReturnStatementContext ctx
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
            final ScrippleParser.AddToCollectionContext ctx
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
            final ScrippleParser.RemoveFromCollectionContext ctx
    ) {
        return new RemoveNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.arg));
    }

    @Override
    public MapDefineNode visitDefineMapEntryStatement(
            final ScrippleParser.DefineMapEntryStatementContext ctx
    ) {
        return new MapDefineNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map),
                (ExpressionNode) visit(ctx.key),
                (ExpressionNode) visit(ctx.val));
    }

    @Override
    public DrawNode visitDrawOntoImageStatement(
            final ScrippleParser.DrawOntoImageStatementContext ctx
    ) {
        return new DrawNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.canvas),
                (ExpressionNode) visit(ctx.img),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y));
    }

    @Override
    public StatementNode visitSingleStatBody(
            final ScrippleParser.SingleStatBodyContext ctx
    ) {
        return (StatementNode) visit(ctx.stat());
    }

    @Override
    public StatementNode visitComplexBody(
            final ScrippleParser.ComplexBodyContext ctx
    ) {
        return new BodyStatementNode(
                TextPosition.fromToken(ctx.LCURLY().getSymbol()),
                ctx.stat().stream()
                        .map(s -> (StatementNode) visit(s))
                        .toArray(StatementNode[]::new));
    }

    @Override
    public StandardAssignmentNode visitStandardAssignment(
            final ScrippleParser.StandardAssignmentContext ctx
    ) {
        return new StandardAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public NoOperandAssignmentNode visitIncrementAssignment(
            final ScrippleParser.IncrementAssignmentContext ctx
    ) {
        return new NoOperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                true);
    }

    @Override
    public NoOperandAssignmentNode visitDecrementAssignment(
            final ScrippleParser.DecrementAssignmentContext ctx
    ) {
        return new NoOperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                false);
    }

    @Override
    public OperandAssignmentNode visitAddAssignment(
            final ScrippleParser.AddAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.ADD,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitSubAssignment(
            final ScrippleParser.SubAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.SUBTRACT,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitMultAssignment(
            final ScrippleParser.MultAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.MULTIPLY,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitDivAssignmnet(
            final ScrippleParser.DivAssignmnetContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.DIVIDE,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitModAssignment(
            final ScrippleParser.ModAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.MODULO,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitAndAssignment(
            final ScrippleParser.AndAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.AND,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public OperandAssignmentNode visitOrAssignment(
            final ScrippleParser.OrAssignmentContext ctx
    ) {
        return new OperandAssignmentNode(
                TextPosition.fromToken(ctx.start),
                (AssignableNode) visit(ctx.assignable()),
                OperandAssignmentNode.Operator.OR,
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public BoolLiteralNode visitBoolLiteral(
            final ScrippleParser.BoolLiteralContext ctx
    ) {
        return new BoolLiteralNode(
                TextPosition.fromToken(ctx.BOOL_LIT().getSymbol()),
                Boolean.parseBoolean(ctx.BOOL_LIT().getSymbol().getText()));
    }

    @Override
    public FloatLiteralNode visitFloatLiteral(
            final ScrippleParser.FloatLiteralContext ctx
    ) {
        return new FloatLiteralNode(
                TextPosition.fromToken(ctx.FLOAT_LIT().getSymbol()),
                Float.parseFloat(ctx.FLOAT_LIT().getSymbol().getText()));
    }

    @Override
    public IntLiteralNode visitIntLiteral(
            final ScrippleParser.IntLiteralContext ctx
    ) {
        return new IntLiteralNode(
                TextPosition.fromToken(ctx.int_lit().start),
                Integer.parseInt(ctx.int_lit().getText()));
    }

    @Override
    public CharLiteralNode visitCharLiteral(
            final ScrippleParser.CharLiteralContext ctx
    ) {
        return new CharLiteralNode(
                TextPosition.fromToken(ctx.CHAR_LIT().getSymbol()),
                ctx.CHAR_LIT().getSymbol().getText().charAt(1));
    }

    @Override
    public StringLiteralNode visitStringLiteral(
            final ScrippleParser.StringLiteralContext ctx
    ) {
        final String withQuotes = ctx.STRING_LIT().getSymbol().getText();

        return new StringLiteralNode(
                TextPosition.fromToken(ctx.STRING_LIT().getSymbol()),
                withQuotes.substring(1, withQuotes.length() - 1));
    }

    @Override
    public ExpressionNode visitNestedExpression(
            final ScrippleParser.NestedExpressionContext ctx
    ) {
        return (ExpressionNode) visit(ctx.expr());
    }

    @Override
    public AssignableNode visitAssignableExpression(
            final ScrippleParser.AssignableExpressionContext ctx
    ) {
        return (AssignableNode) visit(ctx.assignable());
    }

    @Override
    public IdentifierNode visitSimpleAssignable(
            final ScrippleParser.SimpleAssignableContext ctx
    ) {
        return visitIdent(ctx.ident());
    }

    @Override
    public ListAssignableNode visitListAssignable(
            final ScrippleParser.ListAssignableContext ctx
    ) {
        return new ListAssignableNode(
                TextPosition.fromToken(ctx.start),
                ctx.ident().IDENTIFIER().getSymbol().getText(),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public ArrayAssignableNode visitArrayAssignable(
            final ScrippleParser.ArrayAssignableContext ctx
    ) {
        return new ArrayAssignableNode(
                TextPosition.fromToken(ctx.start),
                ctx.ident().IDENTIFIER().getSymbol().getText(),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public LiteralNode visitLiteralExpression(
            final ScrippleParser.LiteralExpressionContext ctx
    ) {
        return (LiteralNode) visit(ctx.literal());
    }

    @Override
    public UnaryOperationNode visitUnaryExpression(
            final ScrippleParser.UnaryExpressionContext ctx
    ) {
        return new UnaryOperationNode(TextPosition.fromToken(ctx.op),
                ctx.op.getText(), (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public BinaryOperationNode visitArithmeticBinExpression(
            final ScrippleParser.ArithmeticBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitMultBinExpression(
            final ScrippleParser.MultBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitPowerBinExpression(
            final ScrippleParser.PowerBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start),
                ctx.RAISE().getSymbol().getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitComparisonBinExpression(
            final ScrippleParser.ComparisonBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public BinaryOperationNode visitLogicBinExpression(
            final ScrippleParser.LogicBinExpressionContext ctx
    ) {
        return new BinaryOperationNode(
                TextPosition.fromToken(ctx.a.start), ctx.op.getText(),
                (ExpressionNode) visit(ctx.a),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public TernaryOperationNode visitTernaryExpression(
            final ScrippleParser.TernaryExpressionContext ctx
    ) {
        return new TernaryOperationNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.cond),
                (ExpressionNode) visit(ctx.if_),
                (ExpressionNode) visit(ctx.else_));
    }

    @Override
    public ContainsNode visitContainsExpression(
            final ScrippleParser.ContainsExpressionContext ctx
    ) {
        return new ContainsNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.elem));
    }

    @Override
    public MapLookupNode visitMapLookupExpression(
            final ScrippleParser.MapLookupExpressionContext ctx
    ) {
        return new MapLookupNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map),
                (ExpressionNode) visit(ctx.elem));
    }

    @Override
    public MapKeysetNode visitMapKeysetExpression(
            final ScrippleParser.MapKeysetExpressionContext ctx
    ) {
        return new MapKeysetNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map));
    }

    @Override
    public ColorChannelNode visitColorChannelExpression(
            final ScrippleParser.ColorChannelExpressionContext ctx
    ) {
        return new ColorChannelNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.expr()),
                ctx.op.getText().substring(1).toLowerCase());
    }

    @Override
    public ImageFromPathNode visitImageFromPathExpression(
            final ScrippleParser.ImageFromPathExpressionContext ctx
    ) {
        return new ImageFromPathNode(
                TextPosition.fromToken(ctx.expr().start),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public ImageOfBoundsNode visitImageOfBoundsExpression(
            final ScrippleParser.ImageOfBoundsExpressionContext ctx
    ) {
        return new ImageOfBoundsNode(
                TextPosition.fromToken(ctx.BLANK().getSymbol()),
                (ExpressionNode) visit(ctx.width),
                (ExpressionNode) visit(ctx.height));
    }

    @Override
    public ColorAtPixelNode visitColorAtPixelExpression(
            final ScrippleParser.ColorAtPixelExpressionContext ctx
    ) {
        return new ColorAtPixelNode(
                TextPosition.fromToken(ctx.img.start),
                (ExpressionNode) visit(ctx.img),
                (ExpressionNode) visit(ctx.x),
                (ExpressionNode) visit(ctx.y));
    }

    @Override
    public ImageBoundNode visitImageBoundExpression(
            final ScrippleParser.ImageBoundExpressionContext ctx
    ) {
        final String text = ctx.op.getText().substring(1);

        return new ImageBoundNode(TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.expr()),
                text.toLowerCase().startsWith("w"));
    }

    @Override
    public TextureColorReplaceNode visitTextureColorReplaceExpression(
            final ScrippleParser.TextureColorReplaceExpressionContext ctx
    ) {
        return new TextureColorReplaceNode(
                TextPosition.fromToken(ctx.TEX_COL_REPL().getSymbol()),
                (ExpressionNode) visit(ctx.texture),
                (ExpressionNode) visit(ctx.lookup),
                (ExpressionNode) visit(ctx.replace));
    }

    @Override
    public RGBColorNode visitRGBColorExpression(
            final ScrippleParser.RGBColorExpressionContext ctx
    ) {
        return new RGBColorNode(
                TextPosition.fromToken(ctx.RGB().getSymbol()),
                (ExpressionNode) visit(ctx.r),
                (ExpressionNode) visit(ctx.g),
                (ExpressionNode) visit(ctx.b));
    }

    @Override
    public RGBAColorNode visitRGBAColorExpression(
            final ScrippleParser.RGBAColorExpressionContext ctx
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
            final ScrippleParser.ExplicitArrayExpressionContext ctx
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
            final ScrippleParser.ExplicitListExpressionContext ctx
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
            final ScrippleParser.ExplicitSetExpressionContext ctx
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
            final ScrippleParser.NewArrayExpressionContext ctx
    ) {
        return new NewArrayNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                (TypeNode) visit(ctx.type()),
                (ExpressionNode) visit(ctx.expr()));
    }

    @Override
    public NewCollectionNode visitNewListExpression(
            final ScrippleParser.NewListExpressionContext ctx
    ) {
        return new NewCollectionNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                CollectionTypeNode.Type.LIST);
    }

    @Override
    public NewCollectionNode visitNewSetExpression(
            final ScrippleParser.NewSetExpressionContext ctx
    ) {
        return new NewCollectionNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()),
                CollectionTypeNode.Type.SET);
    }

    @Override
    public NewMapNode visitNewMapExpression(
            final ScrippleParser.NewMapExpressionContext ctx
    ) {
        return new NewMapNode(
                TextPosition.fromToken(ctx.NEW().getSymbol()));
    }
}
