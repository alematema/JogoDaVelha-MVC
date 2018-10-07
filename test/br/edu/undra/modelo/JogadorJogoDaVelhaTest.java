package br.edu.undra.modelo;

import br.edu.undra.modelo.jogo.Tabuleiro;
import java.util.ArrayList;
import java.util.List;
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
public class JogadorJogoDaVelhaTest {

    JogadorJogoDaVelha jogador1;
    JogadorJogoDaVelha jogador2;
    Tabuleiro tabuleiro;
    JogoDaVelha jogoDaVelha;

    public JogadorJogoDaVelhaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        jogador1 = new JogadorJogoDaVelha("Armando");
        jogador2 = new JogadorJogoDaVelha("Claudio");

        List<JogadorJogoDaVelha> jogadores = new ArrayList<>();
        jogadores.add(jogador2);
        jogadores.add(jogador1);

        tabuleiro = new Tabuleiro(3);

        jogoDaVelha = new JogoDaVelha("Jogo da Velha", jogadores, tabuleiro);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testJoga() {

        System.err.println("testJoga");

        setUp();

        jogador1.setPrimeiroAJogar(true);

        assertEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertTrue(!jogador1.jogou());

        assertEquals(0, jogador1.getJogadas().size());
        assertEquals(0, jogador2.getJogadas().size());

        jogoDaVelha.getProximoAJogar().joga();
        jogoDaVelha.getProximoAJogar().joga();

        assertEquals(1, jogador1.getJogadas().size());
        assertEquals(1, jogador2.getJogadas().size());

        jogoDaVelha.getProximoAJogar().joga();
        assertEquals(2, jogador1.getJogadas().size());
        assertEquals(1, jogador2.getJogadas().size());

        System.out.println(jogador1.getJogadas());
        System.out.println(jogador2.getJogadas());
    }

    @Test
    public void testDesfazerUltimaJogada() {

        System.err.println("testDesfazerUltimaJogada");

        setUp();

        jogador1.setPrimeiroAJogar(true);

        assertEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertTrue(!jogador1.jogou());

        assertEquals(0, jogador1.getJogadas().size());
        assertEquals(0, jogador2.getJogadas().size());

        jogoDaVelha.getProximoAJogar().joga();
        jogoDaVelha.getProximoAJogar().joga();

        assertEquals(1, jogador1.getJogadas().size());
        assertEquals(1, jogador2.getJogadas().size());

        //desfaz ultima jogada do jogador 2
        String ultimaJogada = (String) jogador2.getJogadas().get(jogador2.getJogadas().size() - 1);

        //recupero da ultima jogada a posicao ou linhaXcoluna
        int linha = Integer.parseInt(ultimaJogada.split(",")[0]);
        int coluna = Integer.parseInt(ultimaJogada.split(",")[1]);
        //jogador 2 escreveu valor 2 em linha e coluna e logico a posicao NAO esta livre.
        assertNotEquals(Tabuleiro.POSICAO_LIVRE, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(2, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertNotEquals(jogador2, jogoDaVelha.getProximoAJogar());

        //verifica estados dos jogadores
        assertEquals(2, jogador2.getElemento());
        assertEquals(4, jogador2.getAtual());

        //estados updatados do jogador pois foi atualizado estado dele em getProximoAJogar()
        assertEquals(3, jogador1.getElemento());
        assertEquals(5, jogador1.getAtual());

        //verificar ultimo estado APOS jogar do jogador1
        int atualAposJogar = Integer.parseInt(jogador1.getUltimoEstadoAposJogar().split(",")[0]);
        int elementoAposJogar = Integer.parseInt(jogador1.getUltimoEstadoAposJogar().split(",")[1]);
        assertEquals(3, atualAposJogar);
        assertEquals(1, elementoAposJogar);

        //desfaz jogada do jogador 2
        jogador2.desfazerUltimaJogada();

        //estados updatados do jogador1 pois foi feito roll back no seu estado apos desfazerUltimaJogada()
        assertEquals(1, jogador1.getElemento());
        assertEquals(3, jogador1.getAtual());

        //jogador 2 APAGOU valor 2 em linha e coluna e logico a posicao ESTA livre.
        assertEquals(Tabuleiro.POSICAO_LIVRE, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertNotEquals(2, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(0, jogador2.getJogadas().size());
        assertNotEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertEquals(jogador2, jogoDaVelha.getProximoAJogar());

        jogoDaVelha.getProximoAJogar().joga();

        assertEquals(1, jogador1.getJogadas().size());
        assertEquals(1, jogador2.getJogadas().size());

        //reccupera ultima jogada do jogador 2
        ultimaJogada = (String) jogador2.getJogadas().get(jogador2.getJogadas().size() - 1);

        //recupero da ultima jogada a posicao ou linhaXcoluna
        linha = Integer.parseInt(ultimaJogada.split(",")[0]);
        coluna = Integer.parseInt(ultimaJogada.split(",")[1]);
        //jogador 2 escreveu valor 2 em linha e coluna e logico a posicao NAO esta livre.
        assertNotEquals(Tabuleiro.POSICAO_LIVRE, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(2, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertNotEquals(jogador2, jogoDaVelha.getProximoAJogar());

        jogoDaVelha.getProximoAJogar().joga();//jogador1
        jogoDaVelha.getProximoAJogar().joga();//jogador2
        jogoDaVelha.getProximoAJogar().joga();//jogador1

        //verifica estados dos jogadores
        assertEquals(4, jogador2.getElemento());
        assertEquals(6, jogador2.getAtual());
        assertEquals(5, jogador1.getElemento());
        assertEquals(7, jogador1.getAtual());

        assertEquals(3, jogador1.getJogadas().size());
        assertEquals(2, jogador2.getJogadas().size());

        //reccupera ultima jogada do jogador 1
        ultimaJogada = (String) jogador1.getJogadas().get(jogador1.getJogadas().size() - 1);

        //recupero da ultima jogada a posicao ou linhaXcoluna
        linha = Integer.parseInt(ultimaJogada.split(",")[0]);
        coluna = Integer.parseInt(ultimaJogada.split(",")[1]);
        //jogador 1 escreveu valor 5 em linha e coluna e logico a posicao NAO esta livre.
        assertNotEquals(Tabuleiro.POSICAO_LIVRE, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(5, jogoDaVelha.getTabuleiro().get(linha, coluna));
        assertEquals(jogador2, jogoDaVelha.getProximoAJogar());
        assertNotEquals(jogador1, jogoDaVelha.getProximoAJogar());

        //verificar ultimo estado APOS jogar do jogador1
        atualAposJogar = Integer.parseInt(jogador1.getUltimoEstadoAposJogar().split(",")[0]);
        elementoAposJogar = Integer.parseInt(jogador1.getUltimoEstadoAposJogar().split(",")[1]);
        assertEquals(7, atualAposJogar);
        assertEquals(5, elementoAposJogar);

        //verificar ultimo estado APOS jogar do jogador2
        atualAposJogar = Integer.parseInt(jogador2.getUltimoEstadoAposJogar().split(",")[0]);
        elementoAposJogar = Integer.parseInt(jogador2.getUltimoEstadoAposJogar().split(",")[1]);
        assertEquals(6, atualAposJogar);
        assertEquals(4, elementoAposJogar);

        jogador1.desfazerUltimaJogada();

        assertEquals(2, jogador1.getJogadas().size());
        assertEquals(jogador1, jogoDaVelha.getProximoAJogar());
        assertNotEquals(jogador2, jogoDaVelha.getProximoAJogar());

        //verificar ultimo estado APOS jogar do jogador2
        atualAposJogar = Integer.parseInt(jogador2.getUltimoEstadoAposJogar().split(",")[0]);
        elementoAposJogar = Integer.parseInt(jogador2.getUltimoEstadoAposJogar().split(",")[1]);
        assertEquals(6, atualAposJogar);
        assertEquals(4, elementoAposJogar);

        //verifica estados dos jogadores
        assertEquals(4, jogador2.getElemento());
        assertEquals(6, jogador2.getAtual());
        assertEquals(5, jogador1.getElemento());
        assertEquals(7, jogador1.getAtual());

        jogoDaVelha.getProximoAJogar().joga();//jogador1
        jogoDaVelha.getProximoAJogar().joga();//jogador2

        System.out.println(jogador1.getJogadas());
        System.out.println(jogador2.getJogadas());
    }

       @Test
    public void testJoga_int_int() {

        System.out.println("testJoga_int_int");

        setUp();

        jogador1.setPrimeiroAJogar(true);

        //Tabuleiro 3X3
        //testando posicoes invalidas
        int linha = 0;//linha INVALIDA
        int coluna = 2;//COLUNA VALIDA

        assertFalse(jogador1.joga(linha, coluna));

        linha = 0;//linha INVALIDA
        coluna = 4;//COLUNA INVALIDA

        assertFalse(jogador1.joga(linha, coluna));

        linha = 4;//linha INVALIDA
        coluna = 3;//COLUNA VALIDA

        assertFalse(jogador1.joga(linha, coluna));

        linha = 3;//linha VALIDA
        coluna = 0;//COLUNA INVALIDA

        assertFalse(jogador1.joga(linha, coluna));

        //testando posicoes VALIDAS
        linha = 1;//linha VALIDA
        coluna = 1;//COLUNA IALIDA

        assertTrue(jogador1.joga(linha, coluna));

        assertEquals(jogador1.getJogo().getTabuleiro().get(linha, coluna), 0);

    }

    @Test
    public void testJoga_int_int_int() {

        System.out.println("testJoga_int_int_int");

        setUp();

        jogador1.setPrimeiroAJogar(true);

        //Tabuleiro 3X3
        int linha = 0;
        int coluna = 0;

        //Tabuleiro 3X3
        //testando posicoes invalidas
        linha = 0;//linha INVALIDA
        coluna = 2;//COLUNA VALIDA

        assertFalse(jogador1.joga(linha, coluna));

        //testando posicoes VALIDAS
        linha = 1;//linha VALIDA
        coluna = 1;//COLUNA IALIDA

        assertTrue(jogador1.joga(10, linha, coluna));

        assertEquals(jogador1.getJogo().getTabuleiro().get(linha, coluna), 10);

    }

    @Test
    public void testJoga_int() {

        System.out.println("testJoga_int");

        setUp();

        jogador1.setPrimeiroAJogar(true);
        System.out.println(jogador1.joga(2));

        System.out.println(jogoDaVelha);

    }

    @Test
    public void testJogaNaPosicao_int_int() {

        System.out.println("testJogaNaPosicao_int_int");

        setUp();

        jogador1.setPrimeiroAJogar(true);
        System.out.println(jogador1.jogaNaPosicao(3, -1));

        System.out.println(jogoDaVelha);

    }

}
