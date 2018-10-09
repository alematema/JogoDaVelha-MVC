package br.undra.calculadorproximajogada.impl;

import br.edu.undra.modelo.JogadorJogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.undra.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.security.SecureRandom;
import java.util.List;

/**
 * Calcula proxima jogada apenas buscando posicoes livres.
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaSimplesParaJogoVelhaImpl implements CalculadorProximaJogada<Jogador> {

    /**
     * Calcula a próxima jogada SEMPRE escolhendo ALEATORIAMENTE<br>
     * uma das posicoes LIVRES do jogo.<br>
     * INDEPENDENTEMENTE se for VERSAO CompVSComp OU VERSAOHumanoVSComp.
     *
     * @param jogador
     * @return Um int , representando uma posição LIVRE escolhida
     * ALEATÓRIAMENTE.
     */
    @Override
    public int calcular(Jogador jogador) {

        jogador = (JogadorJogoDaVelha) jogador;

        List<Object> posicoesLivres = jogador.getJogo().getTabuleiro().getPosicoesLivres();

        byte[] seed = new byte[6];
        seed[0] = 2;
        seed[1] = -1;
        seed[2] = 12;
        seed[3] = 11;
        seed[4] = -25;
        seed[5] = 30;

        SecureRandom r = new SecureRandom(seed);

        int posicao = r.nextInt(posicoesLivres.size() >= 1 ? posicoesLivres.size() : 1);

        String posicaoLivre = (String) posicoesLivres.get(posicao);

        String[] p = posicaoLivre.split(",");

        return Integer.parseInt(p[2]);

    }

    /**
     * Calcula a próxima jogada SEMPRE escolhendo ALEATORIAMENTE<br>
     * uma das posicoes LIVRES do jogo.<br>
     * INDEPENDENTEMENTE se for VERSAO CompVSComp OU VERSAO HumanoVSComp.
     *
     * @param jogador
     * @return Um int[linha,coluna], representando posição LIVRE escolhida
     * ALEATÓRIAMENTE, na forma de LINHAxCOLUNA
     */
    @Override
    public int[] calcularLinhaEColuna(Jogador jogador) {

        int posicao = calcular(jogador);

        jogador = (JogadorJogoDaVelha) jogador;

        int linha = jogador.getJogo().getTabuleiro().transformarEmLinha(posicao);
        int coluna = jogador.getJogo().getTabuleiro().transformarEmColuna(posicao);

        int[] posicaoLivre = {linha, coluna};

        System.out.println(getClass().getName() + ".calcularLinhaEColuna(Object)");

        return posicaoLivre;

    }
}
