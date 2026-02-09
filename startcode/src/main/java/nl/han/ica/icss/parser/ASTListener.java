package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.HANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

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
	public void enterCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("enterCssRule");
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitCssRule(ICSSParser.CssRuleContext ctx) {
		System.out.println("exitCssRule");
		attachSafe("exitCssRule"); // Stylerule wordt child van Stylesheet
	}

	@Override
	public void enterClass_selector(ICSSParser.Class_selectorContext ctx) {
		System.out.println("enterClass_selector");
		currentContainer.push(new ClassSelector(ctx.getText()));
		super.enterClass_selector(ctx);
	}

	@Override
	public void exitClass_selector(ICSSParser.Class_selectorContext ctx) {
		System.out.println("exitClass_selector");
		attachSafe("exitClass_selector");
		super.exitClass_selector(ctx);
	}

	@Override
	public void enterId_selector(ICSSParser.Id_selectorContext ctx) {
		System.out.println("enterId_selector");
		currentContainer.push(new IdSelector(ctx.getText()));
		super.enterId_selector(ctx);
	}

	@Override
	public void exitId_selector(ICSSParser.Id_selectorContext ctx) {
		System.out.println("exitId_selector");
		attachSafe("exitId_selector");
		super.exitId_selector(ctx);
	}

	@Override
	public void enterTag_selector(ICSSParser.Tag_selectorContext ctx) {
		System.out.println("enterTag_selector");
		currentContainer.push(new TagSelector(ctx.getText()));
		super.enterTag_selector(ctx);
	}

	@Override
	public void exitTag_selector(ICSSParser.Tag_selectorContext ctx) {
		System.out.println("exitTag_selector");
		attachSafe("exitTag_selector");
		super.exitTag_selector(ctx);
	}


	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		if (ctx.declarationName() != null) {
			System.out.println("enterDeclaration (property)");
			currentContainer.push(new Declaration());
		}
		super.enterDeclaration(ctx);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		if (ctx.declarationName() != null) {
			System.out.println("exitDeclaration (property)");
			attachSafe("exitDeclaration");
		}
		super.exitDeclaration(ctx);
	}

	@Override
	public void enterDeclarationName(ICSSParser.DeclarationNameContext ctx) {
		System.out.println("enterDeclarationName");
		currentContainer.push(new PropertyName(ctx.getText()));
		super.enterDeclarationName(ctx);
	}

	@Override
	public void exitDeclarationName(ICSSParser.DeclarationNameContext ctx) {
		System.out.println("exitDeclarationName");
		attachSafe("exitDeclarationName"); // -> parent is nu Declaration
		super.exitDeclarationName(ctx);
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("enterIfStatement");

		IfClause ifClause = new IfClause();
		currentContainer.push(ifClause);

		// child(2) is het item tussen [ en ]: IF '[' X ']'
		String condText = ctx.getChild(2).getText();

		ASTNode conditionNode;
		if ("TRUE".equals(condText) || "FALSE".equals(condText)) {
			conditionNode = new BoolLiteral(condText);
		} else {
			// Zowel LOWER_IDENT als CAPITAL_IDENT hier afvangen
			conditionNode = new VariableReference(condText);
		}

		// direct attachen aan de IfClause (zonder stack-pop)
		ifClause.addChild(conditionNode);

		super.enterIfStatement(ctx);
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		System.out.println("exitIfStatement");
		attachSafe("exitIfStatement");
		super.exitIfStatement(ctx);
	}

	@Override public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
		System.out.println("enterElseClause");
		currentContainer.push(new ElseClause());
		super.enterElseClause(ctx);
	}

	@Override public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
		System.out.println("exitElseClause");
		attachSafe("exitElseClause");
		super.exitElseClause(ctx);
	}

//	@Override
//	public void enterProperty(ICSSParser.PropertyContext ctx) {
//		System.out.println("enterProperty");
//		currentContainer.push(new Stylerule());
//		super.enterProperty(ctx);
//	}
//
//	@Override
//	public void exitProperty(ICSSParser.PropertyContext ctx) {
//		System.out.println("exitProperty");
//		attachSafe("exitProperty");
//		super.exitProperty(ctx);
//	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		if (ctx.getChildCount() == 1) {
			return;
		}
		if (ctx.getChild(1).getText().equals("+")) {
			currentContainer.push(new AddOperation());
		} else if (ctx.getChild(1).getText().equals("-")) {
			currentContainer.push(new SubtractOperation());
		} else if (ctx.getChild(1).getText().equals("*")) {
			currentContainer.push(new MultiplyOperation());
		}
	}

	@Override
	public void exitValue(ICSSParser.ValueContext ctx) {
		if (ctx.getChildCount() == 1) {
			return;
		}
		ASTNode current = currentContainer.pop();
		currentContainer.peek().addChild(current);
	}

	@Override
	public void enterArithmeticOperator(ICSSParser.ArithmeticOperatorContext ctx) {
		System.out.println("enterArithmeticOperator");

		if(ctx.getText().equals("*")){
			currentContainer.push(new MultiplyOperation());
		}
		else if(ctx.getText().equals("+")){
			currentContainer.push(new AddOperation());
		}
		else if(ctx.getText().equals("-")){
			currentContainer.push(new SubtractOperation());
		}
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
		super.enterDatatype(ctx);
	}
	@Override
	public void exitDatatype(ICSSParser.DatatypeContext ctx) {
		System.out.println("exitDatatype");
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
		currentContainer.push(new VariableAssignment());
		super.enterVariable(ctx);
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		System.out.println("exitVariable");
		attachSafe("exitVariable");
		super.exitVariable(ctx);
	}

	@Override public void enterColor(ICSSParser.ColorContext ctx) {
		System.out.println("enterColor");
		currentContainer.push(new ColorLiteral(ctx.getText()));
		super.enterColor(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitColor(ICSSParser.ColorContext ctx) {
		System.out.println("exitColor");
		attachSafe("exitColor");
		super.exitColor(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override
	public void enterPixelSize(ICSSParser.PixelSizeContext ctx) {
		currentContainer.push(new PixelLiteral(ctx.getText()));
	}
	@Override
	public void exitPixelSize(ICSSParser.PixelSizeContext ctx) {
		attachSafe("exitPixelSize");
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterPercentage(ICSSParser.PercentageContext ctx)
	{
		System.out.println("enterPercentage");
		currentContainer.push(new PercentageLiteral(ctx.getText()));
		super.enterPercentage(ctx);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitPercentage(ICSSParser.PercentageContext ctx) {
		System.out.println("exitPercentage");
		attachSafe("exitPercentage");
		super.exitPercentage(ctx);
	}

	@Override
	public void enterVariableName(ICSSParser.VariableNameContext ctx) {
		System.out.println("enterVariableName");
		currentContainer.push(new VariableReference(ctx.getText()));
		super.enterVariableName(ctx);
	}

	@Override
	public void exitVariableName(ICSSParser.VariableNameContext ctx) {
		System.out.println("exitVariableName");
		attachSafe("exitVariableName");
		super.exitVariableName(ctx);
	}

	@Override
	public void enterLiteral(ICSSParser.LiteralContext ctx) {
		System.out.println("enterLiteral");
		currentContainer.push(new VariableReference(ctx.getText()));
		super.enterLiteral(ctx);
	}

	@Override
	public void exitLiteral(ICSSParser.LiteralContext ctx) {
		System.out.println("exitLiteral");
		attachSafe("exitLiteral"); // -> parent is Declaration (via datatype/value)
		super.exitLiteral(ctx);
	}



}

