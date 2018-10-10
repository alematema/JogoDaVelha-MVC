package br.edu.undra.MVC.interfaces;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * A interface do modelo.
 * @author alexandre
 */
public interface Model {
    
    void setController(Controller controller);
    Controller getController();
    Collection<Method> getMethods();
    
}
