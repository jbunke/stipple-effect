package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.BodyStatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.*;

public final class ScrippleVisitor
        extends ScrippleParserBaseVisitor<ScrippleASTNode> {
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
                parameters, (ScrippleTypeNode) visit(ctx.type()));
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
                (ScrippleTypeNode) visit(ctx.type()),
                visitIdent(ctx.ident()));
    }

    @Override
    public IdentifierNode visitIdent(
            final ScrippleParser.IdentContext ctx
    ) {
        return new IdentifierNode(
                TextPosition.fromToken(ctx.IDENTIFIER().getSymbol()),
                ctx.IDENTIFIER().getText());
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
                (ScrippleTypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitSetType(
            final ScrippleParser.SetTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.SET,
                (ScrippleTypeNode) visit(ctx.type()));
    }

    @Override
    public CollectionTypeNode visitListType(
            final ScrippleParser.ListTypeContext ctx
    ) {
        return new CollectionTypeNode(CollectionTypeNode.Type.LIST,
                (ScrippleTypeNode) visit(ctx.type()));
    }

    @Override
    public MapTypeNode visitMapType(
            final ScrippleParser.MapTypeContext ctx
    ) {
        return new MapTypeNode(
                (ScrippleTypeNode) visit(ctx.key),
                (ScrippleTypeNode) visit(ctx.val));
    }

    @Override
    public StatementNode visitLoopStatement(
            final ScrippleParser.LoopStatementContext ctx
    ) {
        return (StatementNode) visit(ctx.loop_stat());
    }

    @Override
    public WhileLoopNode visitWhileLoop(
            ScrippleParser.WhileLoopContext ctx
    ) {
        final ExpressionNode loopCondition =
                (ExpressionNode) visit(ctx.while_def().expr());
        final StatementNode loopBody = (StatementNode) visit(ctx.body());

        return new WhileLoopNode(
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
        final ExpressionNode condition = (ExpressionNode) visit(ctx.expr());
        final StatementNode ifBody = (StatementNode) visit(ctx.ifBody);
        final IfStatementNode[] elseIfs = ctx.if_stat().stream()
                .map(this::visitIf_stat)
                .toArray(IfStatementNode[]::new);
        final StatementNode elseBody = ctx.elseBody != null
                ? (StatementNode) visit(ctx.elseBody) : null;

        return new IfStatementNode(TextPosition.fromToken(ctx.start),
                condition, ifBody, elseIfs, elseBody);
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
                (ScrippleTypeNode) visit(ctx.declaration().type()),
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
    public NativeAddCallNode visitAddToCollection(
            final ScrippleParser.AddToCollectionContext ctx
    ) {
        return new NativeAddCallNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.elem),
                ctx.index != null
                        ? (ExpressionNode) visit(ctx.index) : null);
    }

    @Override
    public NativeRemoveCallNode visitRemoveFromCollection(
            final ScrippleParser.RemoveFromCollectionContext ctx
    ) {
        return new NativeRemoveCallNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.col),
                (ExpressionNode) visit(ctx.arg));
    }

    @Override
    public NativeDefineCallNode visitDefineMapEntryStatement(
            final ScrippleParser.DefineMapEntryStatementContext ctx
    ) {
        return new NativeDefineCallNode(
                TextPosition.fromToken(ctx.start),
                (ExpressionNode) visit(ctx.map),
                (ExpressionNode) visit(ctx.key),
                (ExpressionNode) visit(ctx.val));
    }

    @Override
    public NativeDrawCallNode visitDrawOntoImageStatement(
            final ScrippleParser.DrawOntoImageStatementContext ctx
    ) {
        return new NativeDrawCallNode(
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
    public ExpressionNode visitNestedExpression(
            final ScrippleParser.NestedExpressionContext ctx
    ) {
        return (ExpressionNode) visit(ctx.expr());
    }

    // TODO: expressions, assignables, literals
}
