package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.HANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;

public class ASTListener extends ICSSBaseListener {

	private AST ast;
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}

	public AST getAST() {
		return ast;
	}

	/**
	 * Pop current node and attach to parent (peek) safely.
	 * Prevents null children, so the GUI won't crash on getChildren().
	 */
	private void attachSafe(String label) {
		ASTNode child = currentContainer.pop();

		if (child == null) {
			System.out.println(label + " -> pop() was NULL (stack mismatch / missing push)");
			return;
		}

		ASTNode parent = currentContainer.peek();
		if (parent == null) {
			System.out.println(label + " -> peek() was NULL (no parent)");
			return;
		}

		parent.addChild(child);
		System.out.println(label + " attach: "
				+ parent.getClass().getSimpleName() + " <- "
				+ child.getClass().getSimpleName());
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		System.out.println("enterStylesheet");
		currentContainer.push(new Stylesheet());
		super.enterStylesheet(ctx);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		System.out.println("exitStylesheet");
		ast.setRoot((Stylesheet) currentContainer.pop());
		super.exitStylesheet(ctx);
	}

	@Override
	public void enterLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("enterLineType");
		currentContainer.push(new Stylerule()); // TODO: namen aanpassen grammar
		super.enterLineType(ctx);
	}

	@Override
	public void exitLineType(ICSSParser.LineTypeContext ctx) {
		System.out.println("exitLineType");
		attachSafe("exitLineType");
		super.exitLineType(ctx);
	}

	@Override
	public void enterCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("enterCssRule");
		currentContainer.push(new Stylerule());
		super.enterCssRule(ctx);
	}

	@Override
	public void exitCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("exitCssRule");
		attachSafe("exitCssRule");
		super.exitCssRule(ctx);
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("enterSelector");
		currentContainer.push(new ClassSelector(ctx.getText()));
		super.enterSelector(ctx);
	}

	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("exitSelector");
		attachSafe("exitSelector");
		super.exitSelector(ctx);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("enterDeclaration");
		currentContainer.push(new Declaration());
		super.enterDeclaration(ctx);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		System.out.println("exitDeclaration");
		attachSafe("exitDeclaration");
		super.exitDeclaration(ctx);
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("enterIfStatement");
		currentContainer.push(new Stylerule());
		super.enterIfStatement(ctx);
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("exitIfStatement");
		attachSafe("exitIfStatement");
		super.exitIfStatement(ctx);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("enterProperty");
		currentContainer.push(new Stylerule());
		super.enterProperty(ctx);
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		System.out.println("exitProperty");
		attachSafe("exitProperty");
		super.exitProperty(ctx);
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		System.out.println("enterValue");
		currentContainer.push(new Stylerule());
		super.enterValue(ctx);
	}

	@Override
	public void exitValue(ICSSParser.ValueContext ctx) {
		System.out.println("exitValue");
		attachSafe("exitValue");
		super.exitValue(ctx);
	}

	@Override
	public void enterArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("enterArithmeticOperator");
		currentContainer.push(new Stylerule());
		super.enterArithmeticOperator(ctx);
	}

	@Override
	public void exitArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("exitArithmeticOperator");
		attachSafe("exitArithmeticOperator");
		super.exitArithmeticOperator(ctx);
	}

	@Override
	public void enterDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("enterDatatype");
		currentContainer.push(new Stylerule());
		super.enterDatatype(ctx);
	}

	@Override
	public void exitDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("exitDatatype");
		attachSafe("exitDatatype");
		super.exitDatatype(ctx);
	}

	@Override
	public void enterInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("enterInteger");
		currentContainer.push(new ScalarLiteral(ctx.getText()));
		super.enterInteger(ctx);
	}

	@Override
	public void exitInteger(ICSSParser.IntegerContext ctx) {
		System.out.println("exitInteger");
		attachSafe("exitInteger");
		super.exitInteger(ctx);
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("enterBoolLiteral");
		currentContainer.push(new BoolLiteral(ctx.getText()));
		super.enterBoolLiteral(ctx);
	}

	@Override
	public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		System.out.println("exitBoolLiteral");
		attachSafe("exitBoolLiteral");
		super.exitBoolLiteral(ctx);
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		System.out.println("enterVariable");
		currentContainer.push(new VariableReference(ctx.getText()));
		super.enterVariable(ctx);
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		System.out.println("exitVariable");
		attachSafe("exitVariable");
		super.exitVariable(ctx);
	}
}
