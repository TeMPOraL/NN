/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp.classifier;

import java.util.HashSet;
import java.util.Set;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author TeMPOraL
 */
public class NNClassifier_MLP implements Classifier {
    
    public final static int INPUTS = 4;
    public final static int OUTPUTS = 20;
    public final static int HIDDEN = 5;
    
    public final static float THRESHOLD = 0.7f;
    public final static double MOMENTUM = 0.25;
    
    private MultiLayerPerceptron nnet = new MultiLayerPerceptron(INPUTS, HIDDEN, OUTPUTS);
    
    TrainingSet<SupervisedTrainingElement> trainingSet;
    
    Set<Request> requestsSoFar = new HashSet<Request>();
    
    NNClassifier_MLP() {

    }

    @Override
    public void learn(double[] inputs, int output) {
        requestsSoFar.add(new Request(inputs, output));
        retrain();
    }

    @Override
    public void unlearn(double[] inputs, int output) {
        //TODO
        //1) find the guy in the set
        //2) remove him
        //3) retrain network
    }

    @Override
    public void forget(int output) {
        //TODO
        //1) unhook node
    }

    @Override
    public int ask(double[] inputs) {
        nnet.setInput(inputs);
        nnet.calculate();
        double[] results = nnet.getOutput();
        
        int i = 1;
        int retVal = 0;
        double max = results[0];
        for( ; i < results.length ; ++i) {
            if(results[i] > max) {
                max = results[i];
                retVal = i;
            }
        }
        return retVal;
    }

    @Override
    public int[] askForMultiple(double[] inputs) {
        //TODO
        return null;
    }
    
    private void retrain() {
        rebuildTrainingSet();
        nnet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, INPUTS,HIDDEN,OUTPUTS);
        MomentumBackpropagation mb =(MomentumBackpropagation)nnet.getLearningRule();
        mb.setMomentum(MOMENTUM);
        mb.setLearningRate(0.1);
        nnet.randomizeWeights(-1, 1);
        mb.setMaxIterations(10000);
        nnet.learn(trainingSet);
    }
    
    private void rebuildTrainingSet() {
        trainingSet = new TrainingSet<SupervisedTrainingElement>(INPUTS, OUTPUTS);
        for(Request req : requestsSoFar) {
            trainingSet.addElement(new SupervisedTrainingElement(req.inputs, buildOutput(req.output)));
        }
    }

    private double[] buildOutput(int output) {
        double[] retArray = new double[OUTPUTS];
        for(int i = 0 ; i < retArray.length ; ++i) {
            retArray[i] = 0;
        }
        retArray[output] = 1.0f;
        return retArray;
    }
    
    class Request {
        double[] inputs;
        int output;

        private Request(double[] inputs, int output) {
            this.inputs = inputs;
            this.output = output;
        }
    }
}
