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
public class JogoDaVelhaMVCTJogadasTest {

    public JogoDaVelhaMVCTJogadasTest() {
    }

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
    public void viewRecebePosicoesOndeVenceuOJogoTest() {

        setUp();

        new Thread(new Runnable() {
            @Override
            public void run() {
                model.iniciar();
            }
        }).start();

        Object[] args = {humanoVSHumano.getNomeResumido()};
        view.getController().updateModel("setVersaoJogoVelha", args);

        while (!model.isConficurado()) {
        }

        try {

            int sleep = 2000;

            // o primeiro que jogar vai ganhar em 1,2,3
            view.joga(getProximaJogadaParaJogador1());//primeiro jogador . joga 1
            Thread.sleep(sleep);
            view.joga(getProximaJogadaParaJogador2());//segundo jogador
            Thread.sleep(sleep);
            view.joga(getProximaJogadaParaJogador1());//primeiro jogador. joga 2
            Thread.sleep(sleep);
            view.joga(getProximaJogadaParaJogador2());//segundo jogador
            Thread.sleep(sleep);
            view.joga(getProximaJogadaParaJogador1());//primeiro jogador. joga 3
            Thread.sleep(sleep * 2);

            System.out.println(view.getPosicoesOndeVenceu());
            assertEquals(view.getPosicoesOndeVenceu().size(), 3);
            assertTrue(view.getPosicoesOndeVenceu().contains(1));
            assertTrue(view.getPosicoesOndeVenceu().contains(2));
            assertTrue(view.getPosicoesOndeVenceu().contains(3));

        } catch (Exception e) {
        }

    }

    @Test
    public void viewRecebeSetingDasPosicoesEValoresDoTabuleiroAposUmaJogadaTest() {

        setUp();
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.iniciar();
            }
        }).start();

        Object[] args = {humanoVSHumano.getNomeResumido()};
        view.getController().updateModel("setVersaoJogoVelha", args);

        while (!model.isConficurado()) {
        }

        try {

            int sleep = 2000;

            // como ninguem jogou, o tabuleiro da view deve estar vazio, com 0 em todas posicoes.
            for (Object pos : view.getTabuleiroMock().keySet()) {
                assertEquals(view.getTabuleiroMock().get(pos), 0);
            }

            // joga na posicao em 1.
            // O modelo vai setar o tabuleiro da view com 1 na posicao 1 e zero nas demais.
            view.joga(1);//primeiro jogador . joga 1
            Thread.sleep(sleep);

            // O modelo vai setar o tabuleiro da view com 1 na posicao 1 e zero nas demais.
            assertEquals("1", view.getTabuleiroMock().get(1));
            for (int i = 2; i <= 9; i++) {
                assertEquals("0", view.getTabuleiroMock().get(i));
            }

            // joga na posicao em 9.
            // O modelo vai setar o tabuleiro da view com 1 na posicao 1, 2 na posicao 9 e zero nas demais.
            view.joga(9);//primeiro jogador . joga 1
            Thread.sleep(sleep);

            // O modelo vai setar o tabuleiro da view com 1 na posicao 1, 2 na posicao 9 e zero nas demais.
            assertEquals("1", view.getTabuleiroMock().get(1));
            assertEquals("2", view.getTabuleiroMock().get(9));
            for (int i = 2; i < 9; i++) {
                assertEquals("0", view.getTabuleiroMock().get(i));
            }

        } catch (Exception e) {
        }

    }

    int i = 0;

    private int getProximaJogadaParaJogador1() {
        i = i + 1;
        return i;
    }

    int j = 6;

    private int getProximaJogadaParaJogador2() {
        j = j + 1;
        return j;
    }
}
