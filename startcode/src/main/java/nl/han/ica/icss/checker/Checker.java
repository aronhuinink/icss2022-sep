package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Checker {

    private HANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    ArrayList<String> variableNames = new ArrayList<>();

    /*
    TODO
    Check of variables daadwerkelijk gedefinieerd zijn
    stap 1: sla de namen van de variabele op in een lijst V
    stap 2: vergelijk de namen van de variabelen op de plekken waar ze gebruikt worden
     */


    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet root) {
        System.out.println("--------------------------------------------------------");
        variableTypes.addFirst(new HashMap<>());
        for (ASTNode child : root.getChildren()) {
            if(child instanceof VariableAssignment) {
                for (ASTNode grandChild : child.getChildren()) {
                    if(grandChild instanceof VariableReference){
                        addVariableToList((VariableReference) grandChild);
                    }
                }
            }

            if(child instanceof Stylerule){
                for (ASTNode grandChild : child.getChildren()) {
                    if(grandChild instanceof IfClause){

                        checkIfClause((IfClause) grandChild);
                    }
                }
            }
        }

        for (int i = 0; i < variableNames.size(); i++) {
            System.out.println(variableNames.get(i));
        }

    }

    private void addVariableToList(VariableReference node) {
        String variableName = node.name;
        variableNames.add(variableName);
    }

    private void checkVariableReference(VariableReference node) {
        for (String name : variableNames) {
            if (Objects.equals(name, node.name)) {
                return;
            }
        }
        node.setError("Variable not found");
    }

    private void checkIfClause(IfClause node){
        System.out.println("checkIfClause");
        for (ASTNode child : node.getChildren()) {
            if(child instanceof IfClause){
                checkIfClause((IfClause) child);
            }

            else if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }
        }

    }

}
