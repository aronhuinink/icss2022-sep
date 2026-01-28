package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.datastructures.HANStack;


/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

	//https://chatgpt.com/c/6978b50e-1280-832f-82e2-669993932a8f
	//Uitleg


	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		System.out.println("enterStylesheet");
		currentContainer.push(new Stylesheet());
		super.enterStylesheet(ctx);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		System.out.println("exitStylesheet");
	}

	@Override
	public void enterCssRules(ICSSParser.CssRulesContext ctx) {
		System.out.println("enterCssRules");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
		super.enterCssRules(ctx);
	}

	@Override
	public void exitCssRules(ICSSParser.CssRulesContext ctx) {
		System.out.println("exitCssRules");
	}

	@Override
	public void enterLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("enterLineType");
	}

	@Override
	public void exitLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("exitLineType");
	}

	@Override
	public void enterCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("enterCssRule");
	}

	@Override
	public void exitCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("exitCssRule");
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("enterSelector");
	}

	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("exitSelector");
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("enterDeclaration");
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("exitDeclaration");
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("enterIfStatement");
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("exitIfStatement");
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("enterProperty");
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("exitProperty");
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		System.out.println("enterValue");
	}

	@Override
	public void exitValue(ICSSParser.ValueContext ctx) {
		System.out.println("exitValue");
	}

	@Override
	public void enterArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("enterArithmeticOperator");
	}

	@Override
	public void exitArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("exitArithmeticOperator");
	}

	@Override
	public void enterDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("enterDatatype");
	}

	@Override
	public void exitDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("exitDatatype");
	}

	@Override
	public void enterInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("enterInteger");
	}

	@Override
	public void exitInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("exitInteger");
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("enterBoolLiteral");
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("exitBoolLiteral");
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		System.out.println("enterVariable");
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		System.out.println("exitVariable");
	}

}
