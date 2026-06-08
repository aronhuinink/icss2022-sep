package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.HANQueue;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();

        applyStylesheet(ast.root);
    }

    private void applyStylesheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
            }
        }
    }

    private void applyVariableAssignment(VariableAssignment assignment) {
        if (assignment.expression instanceof AddOperation) {
            assignment.expression = applyAddOperation((AddOperation) assignment.expression);
        }
        else if (assignment.expression instanceof SubtractOperation) {
            assignment.expression = applySubtractOperation((SubtractOperation) assignment.expression);
        }
        else if (assignment.expression instanceof MultiplyOperation) {
            assignment.expression = applyMultiplyOperation((MultiplyOperation) assignment.expression);
        }


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
}
