package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Checker {

    private HANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    ArrayList<String> variableNames = new ArrayList<>();
    ArrayList<VariableHelper> variableHelperList = new ArrayList<>();

    /*
    TODO
    Controleer of de operanden van de operaties plus en min van gelijk type zijn. Je mag geen pixels bij percentages optellen bijvoorbeeld. Controleer dat bij vermenigvuldigen minimaal een operand een scalaire waarde is. Zo mag 20% * 3 en 4 * 5 wel, maar mag 2px * 3px niet.
    V stap 1: maak de arraylist 2d zodat de datatype opgeslagen kan worden
    V 2: sla de datatype op bij het maken van de functie
     3: controleer bij gebruik

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
                addVariableToList(variableName, variableDatatype);
            }

            if(child instanceof Stylerule){
                for (ASTNode grandChild : child.getChildren()) {
                    if(grandChild instanceof IfClause){

                        checkIfClause((IfClause) grandChild);
                    }

                    if(grandChild instanceof Declaration){
                        checkDeclaration((Declaration) grandChild);
                    }
                }
            }
        }

        for (int i = 0; i < variableNames.size(); i++) {
            System.out.println(variableNames.get(i));
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


    }

    private void addVariableToList(String variableName, String variableDatatype) {
        //variableNames.add(variableName);

        //klasselijst maken die de naam en de datatype opslaat voor elke variabele
        VariableHelper variableHelper = new VariableHelper();
        variableHelper.setName(variableName);
        variableHelper.setDatatype(variableDatatype);
        variableHelperList.add(variableHelper);

        System.out.println(variableHelper.getName() + " " + variableHelper.getDatatype());
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
            if(child instanceof IfClause){
                checkIfClause((IfClause) child);
            }

            else if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }

            else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }


        }

    }

    private void checkAddOperation(AddOperation node){

        ArrayList<String> types = new ArrayList<>();

        for (ASTNode child : node.getChildren()) {
            if(child instanceof VariableReference){
                checkVariableReference((VariableReference) child);
            }

//            if(child instanceof ColorLiteral) {
//                node.setError("Colors can't be used in operations!");
//            }

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
    }

    private void checkMultiplyOperation(MultiplyOperation node){
        System.out.println("checking mul");
    }

    private void checkSubtractOperation(SubtractOperation node){
        System.out.println("checking sub");
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


}
