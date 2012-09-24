/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp.classifier;

import org.neuroph.nnet.MultiLayerPerceptron;

/**
 *
 * @author TeMPOraL
 */
public class NNClassifier_MLP implements Classifier {
    
    public final static int INPUTS = 3;
    public final static int OUTPUTS = 20;
    public final static int HIDDEN = 5;
    
    private MultiLayerPerceptron nnet;
    
    NNClassifier_MLP() {
        nnet = new MultiLayerPerceptron(INPUTS, HIDDEN, OUTPUTS);
    }

    @Override
    public void learn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unlearn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forget() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ask() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void askForMultiple() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
