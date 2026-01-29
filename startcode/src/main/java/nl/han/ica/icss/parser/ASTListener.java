package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.datastructures.HANStack;
import nl.han.ica.icss.ast.selectors.ClassSelector;


/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */

//wat dit vgm inhoudt: De structuur van de tree van wijzigen naar zoals die eruit ziet van de ast


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
		ast.setRoot((Stylesheet) currentContainer.pop());
		super.exitStylesheet(ctx);
		System.out.println("exitStylesheet");
	}

//	@Override
//	public void enterCssRules(ICSSParser.CssRulesContext ctx) {
//		System.out.println("enterCssRules");
//		currentContainer.push(new Stylerule());
//		super.enterCssRules(ctx);
//	}
//
//	@Override
//	public void exitCssRules(ICSSParser.CssRulesContext ctx) {
//		System.out.println("exitCssRules");
//		ASTNode current = currentContainer.pop();
//		currentContainer.peek().addChild(current);
//	}

	@Override
	public void enterLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("enterLineType");
		currentContainer.push(new IfClause());//TODO: namen aanpassen grammar
	}

	@Override
	public void exitLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("exitLineType");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("enterCssRule");
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("exitCssRule");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("enterSelector");
		currentContainer.push(new ClassSelector(ctx.getText()));
	}

	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("exitSelector");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("enterDeclaration");
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("exitDeclaration");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("enterIfStatement");
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("exitIfStatement");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("enterProperty");
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("exitProperty");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		System.out.println("enterValue");
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitValue(ICSSParser.ValueContext ctx) {
		System.out.println("exitValue");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("enterArithmeticOperator");
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("exitArithmeticOperator");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("enterDatatype");
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("exitDatatype");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("enterInteger");
		currentContainer.push(new ScalarLiteral(ctx.getText()));
	}

	@Override
	public void exitInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("exitInteger");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("enterBoolLiteral");
		currentContainer.push(new BoolLiteral(ctx.getText()));
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("exitBoolLiteral");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		System.out.println("enterVariable");
		currentContainer.push(new VariableReference(ctx.getText()));
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		System.out.println("exitVariable");
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}


}
