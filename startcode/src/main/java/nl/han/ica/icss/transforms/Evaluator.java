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
            if (child instanceof VariableReference) {
                applyVariableReference((VariableReference) child);
            }
        }
    }

    private void applyVariableReference(VariableReference variableReference) {
        
//        for (ASTNode child : variableReference.getChildren()) {
//            if (child instanceof AddOperation || child instanceof SubtractOperation || child instanceof MultiplyOperation) {
//                getLiterals((AddOperation) child);
//            }
//
//        }

        HANQueue queue = new HANQueue();
        String value;

        for (ASTNode child : variableReference.getChildren()) {
            if(child instanceof AddOperation) {
                queue.enqueue(applyAddOperation((AddOperation) child));
            }
        }
    }


    private int applyAddOperation(AddOperation addOperation) {
        for (ASTNode child : addOperation.getChildren()) {
            if (child instanceof ScalarLiteral) {
                return ((ScalarLiteral) child).value;
            }
        }
        return 0;
    }
}
