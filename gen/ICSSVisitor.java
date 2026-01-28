// Generated from C:/Users/aronh/Documents/Github/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#cssRules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssRules(ICSSParser.CssRulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#lineType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineType(ICSSParser.LineTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#cssRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssRule(ICSSParser.CssRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ICSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#datatype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatatype(ICSSParser.DatatypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(ICSSParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#boolLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ICSSParser.VariableContext ctx);
}