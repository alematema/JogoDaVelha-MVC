package br.edu.undra.servicos.calculadorproximajogada.impl;

import br.edu.undra.MVC.modelo.impl.JogadorJogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.servicos.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.security.SecureRandom;
import java.util.List;

/**
 * Calcula proxima jogada apenas buscando posicoes livres.
 * Torna o jogo mais FACIL de se jogar contra o computador.<br>
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaSimplesParaJogoVelhaImpl implements CalculadorProximaJogada<Jogador> {

    /**
     * Torna o jogo mais FACIL de se jogar contra o computador.<br>
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
     * Torna o jogo mais FACIL de se jogar contra o computador.<br>
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

        int linha = (int)jogador.getJogo().getTabuleiro().transformarEmLinha(posicao);
        int coluna =(int) jogador.getJogo().getTabuleiro().transformarEmColuna(posicao);

        int[] posicaoLivre = {linha, coluna};

        return posicaoLivre;

    }

    @Override
    public String getNomeSimples() {
       return "calculadorSimples";
    }

    @Override
    public void reconfigurar() {
        
    }
}
