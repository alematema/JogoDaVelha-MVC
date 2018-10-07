package br.edu.undra.MVC;

import br.edu.undra.interfaces.MVC.Controller;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.view.DisplayJogoVelha;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * O controller especializado do jogo velha.
 *
 * @author alexandre
 */
public class JogoVelhaController implements Controller {

    private final JogoVelhaModel model;
    private final JogoVelhaView view;

    public JogoVelhaController(JogoVelhaModel model, JogoVelhaView view) {
        this.model = model;
        this.view = view;
        model.setController(this);
        view.setController(this);
    }

    public DisplayJogoVelha getView() {
        return (DisplayJogoVelha) view;
    }

    @Override
    public boolean updateModel(String methodName, Object[] args) {

        boolean updated = true;

        //System.out.println("updating model ... " + methodName + " args "+ args[0]);
        try {
            Class<?>[] paramTypes = {String.class};
            model.getClass().getMethod(methodName, paramTypes).invoke(model, args[0]);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

        try {
            Class<?>[] paramTypes = {AbstracaoVersaoJogoVelha.class};
            model.getClass().getMethod(methodName, paramTypes).invoke(model, args[0]);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }
        
        
        return updated;
    }

    @Override
    /**
     * A performance não fica afetada, nesse contexto. por causa desta
     * implementação. <br>
     * Os condicionais ficam expressos nos multiplos try...catch.<br>
     *
     */
    public boolean updateView(String methodName, Object[] args) {

        boolean updated = true;

        try {
            Class<?>[] paramTypes = {String.class, int.class};
            view.getClass().getMethod(methodName, paramTypes).invoke(view, Integer.toString((Integer) args[0]), (int) args[1]);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

        try {
            Class<?>[] paramTypes = {List.class};
            view.getClass().getMethod(methodName, paramTypes).invoke(view, args[0]);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

        try {
            Class<?>[] paramTypes = {};
            view.getClass().getMethod(methodName, paramTypes).invoke(view);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

        try {
            Class<?>[] paramTypes = {String.class};
            view.getClass().getMethod(methodName, paramTypes).invoke(view, args[0]);
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }


        return updated;
    }

}
