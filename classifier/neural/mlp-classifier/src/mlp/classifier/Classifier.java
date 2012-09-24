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
    
    abstract public void learn();
    
    abstract public void unlearn();
    
    abstract public void forget();
    
    abstract public void ask();
    
    abstract public void askForMultiple();
    
}
