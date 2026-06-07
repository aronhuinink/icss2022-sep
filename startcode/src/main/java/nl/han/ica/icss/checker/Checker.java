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

    private HANLinkedList<HashMap<String, ExpressionType>> variableTypes;//hier wordt de scope opgeslagen, de variableHelperList moeten hierin verandert worden maar voor nu lui
    ArrayList<String> variableNames = new ArrayList<>();
    ArrayList<VariableHelper> variableHelperList = new ArrayList<>();



    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet root) {
        System.out.println("--------------------------------------------------------");
        variableTypes.addFirst(new HashMap<>());
        for (ASTNode child : root.getChildren()) {
            addVariableToList(child, false);

            if(child instanceof Stylerule){
                checkStylerule(child);
            }

            if(child instanceof VariableAssignment){
                checkVariableAssignment(child);
            }
        }

        for (int i = 0; i < variableNames.size(); i++) {
            System.out.println(variableNames.get(i));
        }

    }

    private void checkVariableAssignment(ASTNode child) {
        if(child instanceof AddOperation ){
            checkAddOperation((AddOperation) child);
        } else if (child instanceof MultiplyOperation){
            checkMultiplyOperation((MultiplyOperation) child);
        }else if (child instanceof SubtractOperation){
            checkSubtractOperation((SubtractOperation) child);
        }
    }

    private void checkStylerule(ASTNode node) {
        for (ASTNode Child : node.getChildren()) {
            if(Child instanceof IfClause){

                checkIfClause((IfClause) Child);
            }

            if(Child instanceof Declaration){
                checkDeclaration((Declaration) Child);
            }

            if(Child instanceof VariableAssignment){
                addVariableToList(Child, true);
            }
        }

        removeLocalVariablesFromList();
    }

    private void addVariableToList(ASTNode child, boolean isLocal) {
        if(child instanceof VariableAssignment) {
            String variableName = "";
            String variableDatatype = "";
            for (ASTNode grandChild : child.getChildren()) {

                if(grandChild instanceof VariableReference){
                    variableName = ((VariableReference) grandChild).name;
                }

                else if(grandChild instanceof ColorLiteral){
                    variableDatatype = "ColorLiteral";
                }
                else if(grandChild instanceof PixelLiteral){
                    variableDatatype = "PixelLiteral";
                }
                else if(grandChild instanceof ScalarLiteral){
                    variableDatatype = "ScalarLiteral";
                }
                else if(grandChild instanceof BoolLiteral){
                    variableDatatype = "BoolLiteral";
                }
            }

            if(Objects.equals(variableName, "") || Objects.equals(variableDatatype, "")) System.out.println("Aron warning: empty name or datatype");
            addVariableToList(variableName, variableDatatype, isLocal);
        }
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

    private void addVariableToList(String variableName, String variableDatatype, boolean isLocal) {
        //variableNames.add(variableName);

        //klasselijst maken die de naam en de datatype opslaat voor elke variabele
        VariableHelper variableHelper = new VariableHelper();
        variableHelper.setName(variableName);
        variableHelper.setDatatype(variableDatatype);
        variableHelper.setIsLocal(isLocal);
        variableHelperList.add(variableHelper);

        System.out.println(variableHelper.getName() + " " + variableHelper.getDatatype() + " " + variableHelper.getIsLocal());
    }

    private void checkVariableReference(VariableReference node) {

        for(VariableHelper instance: variableHelperList){
            if (Objects.equals(instance.getName(), node.name)) {
                return;
            }
        }

        node.setError("Variable not found");
    }

    private void checkIfClause(IfClause node){
        for (ASTNode child : node.getChildren()) {

            if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);

                ExpressionType type = getExpressionType(child);
                if(type != ExpressionType.BOOL){
                    child.setError("If clause variable not boolean");
                }
            }

            if(child instanceof BoolLiteral){
                // mag dus niks doen
            }

            if(child instanceof ColorLiteral
                    || child instanceof PercentageLiteral
                    || child instanceof PixelLiteral
                    || child instanceof ScalarLiteral){
                child.setError("If clause expression not boolean");
            }

            if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }

            if(child instanceof VariableAssignment){
                addVariableToList(child, true);
            }

            if(child instanceof IfClause){
                checkIfClause((IfClause) child);
            }
        }
    }

    private void checkAddOperation(AddOperation node){

        ArrayList<String> types = new ArrayList<>();

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }

            if(!(child instanceof VariableReference))
                types.add(child.getClass().getSimpleName());
            else{
                types.add(checkVariableReferenceType((VariableReference) child));
            }
        }

        for(String type : types){
            System.out.println(type);
        }

        if(!Objects.equals(types.get(0), types.get(1))){
            node.setError("Operation variables not of same type!");
        }


        if(Objects.equals(types.get(0), "ColorLiteral") || Objects.equals(types.get(1), "ColorLiteral")){
            node.setError("Can't calculate with colors");
        }
    }

    private void checkMultiplyOperation(MultiplyOperation node){
        ArrayList<String> types = new ArrayList<>();

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }

            if(!(child instanceof VariableReference))
                types.add(child.getClass().getSimpleName());
            else{
                types.add(checkVariableReferenceType((VariableReference) child));
            }
        }

        for(String type : types){
            System.out.println(type);
        }

        if(!Objects.equals(types.get(0), "ScalarLiteral") && !Objects.equals(types.get(1), "ScalarLiteral")){
            node.setError("at least one operand needs to be scalar");
        }
        if(Objects.equals(types.get(0), "ColorLiteral") || Objects.equals(types.get(1), "ColorLiteral")){
            node.setError("Can't calculate with colors");
        }
    }

    private void checkSubtractOperation(SubtractOperation node){
        ArrayList<String> types = new ArrayList<>();

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }

            if(!(child instanceof VariableReference))
                types.add(child.getClass().getSimpleName());
            else{
                types.add(checkVariableReferenceType((VariableReference) child));
            }
        }

        for(String type : types){
            System.out.println(type);
        }

        if(!Objects.equals(types.get(0), types.get(1))){
            node.setError("Operation variables not of same type!");
        }
        if(Objects.equals(types.get(0), "ColorLiteral") || Objects.equals(types.get(1), "ColorLiteral")){
            node.setError("Can't calculate with colors");
        }
    }

    private String checkVariableReferenceType(VariableReference node){
        String type = null;

        for(VariableHelper instance: variableHelperList){
            if(Objects.equals(instance.getName(), node.name)){//de variabele naam vinden in de arraylist, na vinden checken of de types gelijk aan elkaar zijn
                type = instance.getDatatype();
            }
        }
        return type;
    }


    private void removeLocalVariablesFromList(){
        variableHelperList.removeIf(variable -> variable.getIsLocal());
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
            String type = checkVariableReferenceType((VariableReference) node);

            if(type == null) {
                return ExpressionType.UNDEFINED;
            }

            switch(type) {
                case "PixelLiteral":
                    return ExpressionType.PIXEL;
                case "PercentageLiteral":
                    return ExpressionType.PERCENTAGE;
                case "ScalarLiteral":
                    return ExpressionType.SCALAR;
                case "ColorLiteral":
                    return ExpressionType.COLOR;
                case "BoolLiteral":
                    return ExpressionType.BOOL;
            }
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
}
