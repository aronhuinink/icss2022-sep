package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private HANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet root) {
        variableTypes.addFirst(new HashMap<>());
        for (ASTNode child : root.getChildren()) {
            if(child instanceof Stylesheet) {}
        }
    }

    private void checkVariableAssignment(VariableAssignment child) {
    }



}
