package br.edu.undra.MVC;

import br.edu.undra.MVC.interfaces.JogoVelhaController;
import br.edu.undra.MVC.modelo.impl.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.modelo.versoes.VersaoComputadorVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusHumanoImpl;
import br.edu.undra.servicos.calculadorproximajogada.impl.CalculadorProximaJogadaIAParaJogoVelhaImpl;
import br.edu.undra.servicos.calculadorproximajogada.impl.CalculadorProximaJogadaSimplesParaJogoVelhaImpl;
import br.edu.undra.servicos.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandre
 */
public class JogoDaVelhaMVCTest {

    JogoDaVelha model;
    JogoDaVelhaMockView view;
    JogoVelhaController controller;

    //as tres versoes do jogo da velhas
    private AbstracaoVersaoJogoVelha compVSComp;
    private AbstracaoVersaoJogoVelha humanoVSComp;
    private AbstracaoVersaoJogoVelha humanoVSHumano;

    //as duas impleentacoes do calculador da proxima jogada
    private CalculadorProximaJogada<Jogador> calculadorProximaJogadaSimples;
    private CalculadorProximaJogada<Jogador> calculadorProximaJogadaIA;

    public JogoDaVelhaMVCTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        //as tres versoes do jogo da velhas
        compVSComp = new VersaoComputadorVersusComputadorImpl();
        humanoVSComp = new VersaoHumanoVersusComputadorImpl();
        humanoVSHumano = new VersaoHumanoVersusHumanoImpl();

        //as duas impleentacoes do calculador da proxima jogada
        calculadorProximaJogadaSimples = new CalculadorProximaJogadaSimplesParaJogoVelhaImpl();
        calculadorProximaJogadaIA = new CalculadorProximaJogadaIAParaJogoVelhaImpl();

        model = new JogoDaVelha("Jogo da Velha");

        view = new JogoDaVelhaMockView();

        controller = new JogoVelhaController(model, view);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void viewMudaVersaoEmModelTest() {

        Object[] args = {compVSComp.getNomeResumido()};
        view.getController().updateModel("setVersaoJogoVelha", args);
        assertEquals(model.getVersaoJogoVelha().getNomeResumido(), args[0]);

        System.out.println(model.getVersaoJogoVelha().getVersao());

        args[0] = humanoVSComp.getNomeResumido();
        view.getController().updateModel("setVersaoJogoVelha", args);
        assertEquals(model.getVersaoJogoVelha().getNomeResumido(), args[0]);

        System.out.println(model.getVersaoJogoVelha().getVersao());

        args[0] = humanoVSHumano.getNomeResumido();
        view.getController().updateModel("setVersaoJogoVelha", args);
        assertEquals(model.getVersaoJogoVelha().getNomeResumido(), args[0]);

        System.out.println(model.getVersaoJogoVelha().getVersao());

    }

    @Test
    public void viewMudaVCalculadorProximaJogadaEmModelTest() {

        Object[] args = {calculadorProximaJogadaSimples.getNomeSimples()};
        view.getController().updateModel("setCalculadorProximaJogada", args);
        assertEquals(model.getCalculadorProximaJogada().getNomeSimples(), args[0]);

        System.out.println(model.getCalculadorProximaJogada().getNomeSimples());

        args[0] = calculadorProximaJogadaIA.getNomeSimples();
        view.getController().updateModel("setCalculadorProximaJogada", args);
        assertEquals(model.getCalculadorProximaJogada().getNomeSimples(), args[0]);

        System.out.println(model.getCalculadorProximaJogada().getNomeSimples());

    }

    @Test
    public void viewMudaVelocidadeJogoComputadorVSComputadorTest() {

        Object[] args = new Object[1];

        args[0] = compVSComp.getNomeResumido();
        view.getController().updateModel("setVersaoJogoVelha", args);

        args[0] = 10;
        view.getController().updateModel("setVelocity", args);

        assertEquals(model.getVelocity(), args[0]);

    }

    @Test
    public void viewNAOMudaVelocidadeJogoQuandoJogandoHumanoVSComputadorOuJogandoHumanoVSHumanoTest() {

        Object[] args = new Object[1];

        args[0] = humanoVSComp.getNomeResumido();
        view.getController().updateModel("setVersaoJogoVelha", args);

        args[0] = 10;
        view.getController().updateModel("setVelocity", args);
        int velocidade = model.getVelocity();
        args[0] = 20;
        view.getController().updateModel("setVelocity", args);
        assertEquals(velocidade, model.getVelocity());

        args[0] = humanoVSHumano.getNomeResumido();
        view.getController().updateModel("setVersaoJogoVelha", args);

        args[0] = 10;
        view.getController().updateModel("setVelocity", args);
        velocidade = model.getVelocity();

        args[0] = 20;
        view.getController().updateModel("setVelocity", args);
        assertEquals(velocidade, model.getVelocity());

    }


}
