package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.*;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.IdentifierNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.BodyStatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow.IteratorLoopNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow.WhileLoopNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;

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
                (ScrippleTypeNode) visit(ctx.type(0)),
                (ScrippleTypeNode) visit(ctx.type(1)));
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

    // TODO: statements
}
