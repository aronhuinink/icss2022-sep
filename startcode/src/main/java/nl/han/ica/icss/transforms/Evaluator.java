package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> values;

    public Evaluator() {
        values = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        values = new HANLinkedList<>();

        applyStylesheet(ast.root);
    }

    private void applyStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
            }
            else if(child instanceof Stylerule){
                applyStylerule((Stylerule) child);
            }
        }
    }

    private void applyStylerule(Stylerule stylerule) {
        ArrayList<ASTNode> newChildren = new ArrayList<>();

        for (ASTNode child : stylerule.getChildren()) {

            if(child instanceof IfClause && isIfClauseTrue((IfClause) child)) {
                System.out.println("if clause true");
                newChildren.add(((IfClause)child).body);
            }
            else if(child instanceof ElseClause){
                newChildren.add((ElseClause)child).body;
            }
            else{
                newChildren.add(child);
            }
        }
        stylerule.body = newChildren;
    }

    private boolean isIfClauseTrue(IfClause ifClause) {//door de linkedlist gaan om op te zoeken zodat je uitvindt of hij bestaat.

        String variableNameToCheck = "";

        for (ASTNode child : ifClause.getChildren()) {
            if(child instanceof VariableReference) {
                variableNameToCheck = ((VariableReference)child).name;
            }
        }

        for (int i = 0; i < values.getSize(); i++) {
            HashMap<String, Literal> map = values.get(i);


            if(map.get(variableNameToCheck) != null ){
                if(((BoolLiteral) map.get(variableNameToCheck)).value) return true;
            }
        }
        return false;
    }


    private void applyVariableAssignment(VariableAssignment assignment) {
        if (assignment.expression instanceof AddOperation) {
            assignment.expression = applyAddOperation((AddOperation) assignment.expression);
        } else if (assignment.expression instanceof SubtractOperation) {
            assignment.expression = applySubtractOperation((SubtractOperation) assignment.expression);
        } else if (assignment.expression instanceof MultiplyOperation) {
            assignment.expression = applyMultiplyOperation((MultiplyOperation) assignment.expression);
        }

        if (assignment.expression instanceof BoolLiteral) {
            BoolLiteral boolLiteral = (BoolLiteral) assignment.expression;

            HashMap<String, Literal> map = new HashMap<>();
            map.put(assignment.name.name, boolLiteral);

            values.addFirst(map);
        }

        printValuesDebug();
    }

    private Expression applyMultiplyOperation(MultiplyOperation multiplyOperation) {
        if(multiplyOperation.lhs instanceof ScalarLiteral && multiplyOperation.rhs instanceof ScalarLiteral) {
            ScalarLiteral left = (ScalarLiteral) multiplyOperation.lhs;
            ScalarLiteral right = (ScalarLiteral) multiplyOperation.rhs;

            return new ScalarLiteral(left.value * right.value);
        }
        else if(multiplyOperation.lhs instanceof PercentageLiteral) {
            PercentageLiteral left = (PercentageLiteral) multiplyOperation.lhs;
            ScalarLiteral right = (ScalarLiteral) multiplyOperation.rhs;

            return new ScalarLiteral(left.value/100 * right.value);
        }
        else if(multiplyOperation.rhs instanceof PercentageLiteral) {
            ScalarLiteral left = (ScalarLiteral) multiplyOperation.lhs;
            PercentageLiteral right = (PercentageLiteral) multiplyOperation.rhs;

            return new ScalarLiteral(left.value * right.value/100);
        }
        else if (multiplyOperation.lhs instanceof PixelLiteral){
            PixelLiteral left = (PixelLiteral) multiplyOperation.lhs;
            ScalarLiteral right = (ScalarLiteral) multiplyOperation.rhs;

            return new PixelLiteral(left.value * right.value);
        }
        else if(multiplyOperation.rhs instanceof PixelLiteral) {
            ScalarLiteral left = (ScalarLiteral) multiplyOperation.lhs;
            PixelLiteral right = (PixelLiteral) multiplyOperation.rhs;

            return new PixelLiteral(left.value * right.value);
        }

        throw new RuntimeException("Error, parser let through code it shouldn't");
    }

    //bijna dublicate aan addOperation
    private Expression applySubtractOperation(SubtractOperation subtractOperation) {
        //ervanuitgaand dat de juiste waarden doorkomen i.e. de parser zorgt er twee px, percentages of pixelwaarde is

        if (subtractOperation.lhs instanceof ScalarLiteral
                && subtractOperation.rhs instanceof ScalarLiteral) {

            ScalarLiteral left = (ScalarLiteral) subtractOperation.lhs;
            ScalarLiteral right = (ScalarLiteral) subtractOperation.rhs;

            return new ScalarLiteral(left.value - right.value);
        }
        //percentage and percentage
        else if(subtractOperation.lhs instanceof PercentageLiteral && subtractOperation.rhs instanceof PercentageLiteral) {
            PercentageLiteral left = (PercentageLiteral) subtractOperation.lhs;
            PercentageLiteral right = (PercentageLiteral) subtractOperation.rhs;

            return new PercentageLiteral(left.value - right.value);
        }

        //px en px
        else if(subtractOperation.lhs instanceof PixelLiteral && subtractOperation.rhs instanceof PixelLiteral) {
            PixelLiteral left = (PixelLiteral) subtractOperation.lhs;
            PixelLiteral right = (PixelLiteral) subtractOperation.rhs;

            return new PixelLiteral(left.value - right.value);
        }


        throw new RuntimeException("Error, parser let through code it shouldn't");
    }

    private Literal applyAddOperation(AddOperation addOperation) {
        //ervanuitgaand dat de juiste waarden doorkomen i.e. de parser zorgt er twee px, percentages of pixelwaarde is

        if (addOperation.lhs instanceof ScalarLiteral
                && addOperation.rhs instanceof ScalarLiteral) {

            ScalarLiteral left = (ScalarLiteral) addOperation.lhs;
            ScalarLiteral right = (ScalarLiteral) addOperation.rhs;

            return new ScalarLiteral(left.value + right.value);
        }
        //percentage and percentage
        else if(addOperation.lhs instanceof PercentageLiteral && addOperation.rhs instanceof PercentageLiteral) {
            PercentageLiteral left = (PercentageLiteral) addOperation.lhs;
            PercentageLiteral right = (PercentageLiteral) addOperation.rhs;

            return new PercentageLiteral(left.value + right.value);
        }

        //px en px
        else if(addOperation.lhs instanceof PixelLiteral && addOperation.rhs instanceof PixelLiteral) {
            PixelLiteral left = (PixelLiteral) addOperation.lhs;
            PixelLiteral right = (PixelLiteral) addOperation.rhs;

            return new PixelLiteral(left.value + right.value);
        }


        throw new RuntimeException("Error, parser let through code it shouldn't");
    }
    private void printValuesDebug() {
        for (int i = 0; i < values.getSize(); i++) {
            HashMap<String, Literal> mapDebug = values.get(i);

            for (String key : mapDebug.keySet()) {
                Literal literal = mapDebug.get(key);

                if (literal instanceof BoolLiteral) {
                    BoolLiteral boolLiteral = (BoolLiteral) literal;

                    System.out.println("String: " + key);
                    System.out.println("Literal: " + (boolLiteral.value ? "TRUE" : "FALSE"));
                }
            }
        }
    }
}


//TODO maak een hashmap om de data op te slaan
/*


AdjustColor := TRUE;


p {
	if[AdjustColor] {
	    color: #124532;
	}
	height: 20px;
}
 */