package br.edu.undra.MVC.interfaces;

/**
 * A interface do controller.
 * @author alexandre
 */
public interface Controller {
    
    boolean updateModel(String methodName, Object[] args);
    boolean updateView(String methodName, Object[] args);
    
}
