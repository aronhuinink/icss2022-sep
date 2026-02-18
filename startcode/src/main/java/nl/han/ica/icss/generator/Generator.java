package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Stylerule;
import nl.han.ica.icss.ast.Stylesheet;

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



		return outcome;
	}
}
