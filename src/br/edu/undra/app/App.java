package br.edu.undra.app;

import br.edu.undra.MVC.JogoVelhaController;
import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.view.DisplayJogoVelha;
import br.edu.undra.view.JogoVelhaWindow;
import javax.swing.JFrame;

/**
 *
 * @author alexandre
 */
public class App {
    
    public static void main(String[] args) {
        
        new Thread(() -> {
            new App().start(args);
        }).start();

        
    }
    
    public void start(String[]args){
        
        JogoDaVelha model = new JogoDaVelha("Jogo da Velha");
        
        DisplayJogoVelha view = new DisplayJogoVelha();
        
        JogoVelhaController controller = new JogoVelhaController(model, view);
        
        JogoVelhaWindow window = new JogoVelhaWindow(view);
        
        window.configureAndShow();
        
        model.setAbstracaoVersaoJogoVelha(window.getAbstracaoVersaoJogoVelha());
        model.setCalculadorProximaJogada(window.getCalculadorProximaJogada());
        
        model.iniciar();
        
    }
    
}
