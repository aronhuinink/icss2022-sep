package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;


public class Checker {

    private HANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    private int scopeDepth = 0;



    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        scopeDepth = 0;
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet root) {
        enterScope(); // globale scope

        for (ASTNode child : root.getChildren()) {
            if(child instanceof VariableAssignment){
                checkVariableAssignment((VariableAssignment) child);
            }

            if(child instanceof Stylerule){
                checkStylerule(child);
            }
        }

        exitScope();
    }

    private void checkVariableAssignment(VariableAssignment node) {
        String variableName = "";
        Expression expression = null;

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableReference){
                variableName = ((VariableReference) child).name;
            }

            if(child instanceof Expression){
                expression = (Expression) child;
            }
        }

        if(variableName.equals("") || expression == null){
            node.setError("Invalid variable assignment");
            return;
        }

        ExpressionType type = getExpressionType(expression);

        if(type == ExpressionType.UNDEFINED){
            node.setError("Variable assignment has invalid type");
            return;
        }

        addVariable(variableName, type);
    }

    private void checkStylerule(ASTNode node) {
        enterScope(); // scope van stylerule

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment){
                checkVariableAssignment((VariableAssignment) child);
            }

            if(child instanceof IfClause){
                checkIfClause((IfClause) child);
            }

            if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }
        }

        exitScope();
    }


    private void checkDeclaration(Declaration node) {
        for (ASTNode child : node.getChildren()) {
            if (child instanceof VariableReference) {
                checkVariableReference((VariableReference) child);
            }

            else if(child instanceof AddOperation ){
                checkAddOperation((AddOperation) child);
            } else if (child instanceof MultiplyOperation){
                checkMultiplyOperation((MultiplyOperation) child);
            }else if (child instanceof SubtractOperation){
                checkSubtractOperation((SubtractOperation) child);
            }
        }

        ExpressionType valueType = getExpressionType(node.expression);

        switch (node.property.name) {
            case "color":
            case "background-color":
                if (valueType != ExpressionType.COLOR) {
                    node.setError("Property expects color");
                }
                break;

            case "width":
            case "height":
                if (valueType != ExpressionType.PIXEL) {
                    node.setError("Property expects pixels");
                }
                break;
        }

    }


    private void checkVariableReference(VariableReference node) {
        if(findVariableType(node.name) == ExpressionType.UNDEFINED){
            node.setError("Variable not found");
        }
    }

    private void checkIfClause(IfClause node){
        enterScope(); // scope van deze if

        for (ASTNode child : node.getChildren()) {

            if(child instanceof VariableReference){
                ExpressionType type = getExpressionType(child);

                if(type == ExpressionType.UNDEFINED){
                    child.setError("Variable not found");
                }
                else if(type != ExpressionType.BOOL){
                    child.setError("If clause condition must be boolean");
                }
            }

            else if(child instanceof BoolLiteral){
                // geldig
            }

            else if(child instanceof ColorLiteral
                    || child instanceof PercentageLiteral
                    || child instanceof PixelLiteral
                    || child instanceof ScalarLiteral){
                child.setError("If clause condition must be boolean");
            }

            else if(child instanceof VariableAssignment){
                checkVariableAssignment((VariableAssignment) child);
            }

            else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }

            else if(child instanceof IfClause){
                checkIfClause((IfClause) child);
            }
        }

        exitScope();
    }

    private void checkAddOperation(AddOperation node){
        ExpressionType left = getExpressionType(node.getChildren().get(0));
        ExpressionType right = getExpressionType(node.getChildren().get(1));

        if(left == ExpressionType.UNDEFINED || right == ExpressionType.UNDEFINED){
            node.setError("Unknown variable in operation");
            return;
        }

        if(left != right){
            node.setError("Operation variables not of same type!");
            return;
        }

        if(left == ExpressionType.COLOR){
            node.setError("Can't calculate with colors");
        }
    }

    private void checkMultiplyOperation(MultiplyOperation node){
        ExpressionType left = getExpressionType(node.getChildren().get(0));
        ExpressionType right = getExpressionType(node.getChildren().get(1));

        if(left == ExpressionType.UNDEFINED || right == ExpressionType.UNDEFINED){
            node.setError("Unknown variable in operation");
            return;
        }

        if(left == ExpressionType.COLOR || right == ExpressionType.COLOR){
            node.setError("Can't calculate with colors");
            return;
        }

        if(left != ExpressionType.SCALAR && right != ExpressionType.SCALAR){
            node.setError("At least one operand needs to be scalar");
        }
    }

    private void checkSubtractOperation(SubtractOperation node){
        ExpressionType left = getExpressionType(node.getChildren().get(0));
        ExpressionType right = getExpressionType(node.getChildren().get(1));

        if(left == ExpressionType.UNDEFINED || right == ExpressionType.UNDEFINED){
            node.setError("Unknown variable in operation");
            return;
        }

        if(left != right){
            node.setError("Operation variables not of same type!");
            return;
        }

        if(left == ExpressionType.COLOR){
            node.setError("Can't calculate with colors");
        }
    }

    private ExpressionType getExpressionType(ASTNode node) {

        if(node instanceof PixelLiteral)
            return ExpressionType.PIXEL;

        if(node instanceof PercentageLiteral)
            return ExpressionType.PERCENTAGE;

        if(node instanceof ScalarLiteral)
            return ExpressionType.SCALAR;

        if(node instanceof ColorLiteral)
            return ExpressionType.COLOR;

        if(node instanceof BoolLiteral)
            return ExpressionType.BOOL;

        if(node instanceof VariableReference) {
            return findVariableType(((VariableReference) node).name);
        }

        if(node instanceof AddOperation || node instanceof SubtractOperation) {
            ExpressionType left = getExpressionType(node.getChildren().get(0));
            ExpressionType right = getExpressionType(node.getChildren().get(1));

            if(left != right) {
                node.setError("Operands must be same type");
                return ExpressionType.UNDEFINED;
            }

            if(left == ExpressionType.COLOR) {
                node.setError("Cannot calculate with colors");
                return ExpressionType.UNDEFINED;
            }

            return left;
        }

        if(node instanceof MultiplyOperation) {
            ExpressionType left = getExpressionType(node.getChildren().get(0));
            ExpressionType right = getExpressionType(node.getChildren().get(1));

            if(left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
                node.setError("Cannot calculate with colors");
                return ExpressionType.UNDEFINED;
            }

            if(left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
                node.setError("At least one operand must be scalar");
                return ExpressionType.UNDEFINED;
            }

            if(left == ExpressionType.SCALAR) {
                return right;
            }

            return left;
        }

        return ExpressionType.UNDEFINED;
    }

    private void enterScope() {
        variableTypes.addFirst(new HashMap<>());
        scopeDepth++;
    }

    private void exitScope() {
        variableTypes.removeFirst();
        scopeDepth--;
    }

    private void addVariable(String name, ExpressionType type) {
        variableTypes.getFirst().put(name, type);
    }

    private ExpressionType findVariableType(String name) {
        for (int i = 0; i < scopeDepth; i++) {
            HashMap<String, ExpressionType> scope = variableTypes.get(i);

            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }

        return ExpressionType.UNDEFINED;
    }
}
