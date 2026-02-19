package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.operations.AddOperation;

public class Generator {

	public String generate(AST ast) {
		return generateStylesheet(ast.root);
	}

	private String generateStylesheet(Stylesheet stylesheet){
		String result = "";

		for (ASTNode node : stylesheet.getChildren()) {
			if (node instanceof Stylerule) {
				result += generateStylerule((Stylerule) node);
			}
		}
		return result;
	}

	private String generateStylerule(Stylerule node){
		String outcome = "";

		for (ASTNode child : node.getChildren()) {
			if (child instanceof Declaration) {
				outcome += generateDeclaration((Declaration) child);
			}
		}

		System.out.println(outcome);
		return outcome;
	}

	private String generateDeclaration(Declaration node) {
		String outcome = "";

		for (ASTNode child : node.getChildren()) {
			if (child instanceof AddOperation) {
				outcome += generateAddOperation((AddOperation) child);
			}
		}

		return outcome;
	}

	private String generateAddOperation(AddOperation node) {
		String outcome = "";

		for (ASTNode child : node.getChildren()) {
			

		}


		return outcome;
	}
}
