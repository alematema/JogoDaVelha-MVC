package br.edu.undra.interfaces.MVC;

/**
 * A interface do controller.
 * @author alexandre
 */
public interface Controller {
    
    boolean updateModel(String methodName, Object[] args);
    boolean updateView(String methodName, Object[] args);
    
}
