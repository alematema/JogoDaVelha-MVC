package br.edu.undra.app;

import br.edu.undra.MVC.interfaces.JogoVelhaController;
import br.edu.undra.MVC.modelo.impl.JogoDaVelha;
import br.edu.undra.MVC.view.impl.DisplayJogoVelha;
import br.edu.undra.MVC.view.impl.JogoVelhaWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void start(String[] args) {

        JogoDaVelha model = new JogoDaVelha("Jogo da Velha");

        DisplayJogoVelha view = new DisplayJogoVelha();

        JogoVelhaController controller = new JogoVelhaController(model, view);

        JogoVelhaWindow window = new JogoVelhaWindow(view);

        window.configureAndShow(model.getAbstracaoVersaoJogoVelha().getVersao());

        new Thread(new Runnable() {
            @Override
            public void run() {
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                while(!model.isConficurado()){};
                
                Object[] args = {model.getAbstracaoVersaoJogoVelha().getNomeResumido()};
                model.getController().updateView("setVersaoJogo", args);
                        
                Object[] args1 = {model.getCalculadorProximaJogada().getNomeSimples()};
                model.getController().updateView("setVersaoCalculador", args1);        
                
                model.getController().updateView("setUpItensMenuJogo", null);
                model.getController().updateView("habilitarDesabilitarItensVelocidadeENivelMenuJogo", null);
                
            }
        }).start();

        model.iniciar();

    }

}
