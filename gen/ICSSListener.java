// Generated from C:/Users/aronh/Documents/Github/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#cssRules}.
	 * @param ctx the parse tree
	 */
	void enterCssRules(ICSSParser.CssRulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#cssRules}.
	 * @param ctx the parse tree
	 */
	void exitCssRules(ICSSParser.CssRulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#lineType}.
	 * @param ctx the parse tree
	 */
	void enterLineType(ICSSParser.LineTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#lineType}.
	 * @param ctx the parse tree
	 */
	void exitLineType(ICSSParser.LineTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#cssRule}.
	 * @param ctx the parse tree
	 */
	void enterCssRule(ICSSParser.CssRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#cssRule}.
	 * @param ctx the parse tree
	 */
	void exitCssRule(ICSSParser.CssRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#datatype}.
	 * @param ctx the parse tree
	 */
	void enterDatatype(ICSSParser.DatatypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#datatype}.
	 * @param ctx the parse tree
	 */
	void exitDatatype(ICSSParser.DatatypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(ICSSParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(ICSSParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ICSSParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ICSSParser.VariableContext ctx);
}