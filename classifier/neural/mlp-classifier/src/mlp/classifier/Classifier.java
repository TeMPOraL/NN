/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp.classifier;

/**
 *
 * @author TeMPOraL
 */
public interface Classifier {
    
    abstract public void learn(double[] inputs, int output);
    
    abstract public void unlearn(double[] inputs, int output);
    
    abstract public void forget(int output);
    
    abstract public int ask(double[] inputs);
    
    abstract public int[] askForMultiple(double[] inputs);
    
    abstract public double[] debugAskForAll(double[] inputs);
    
}
