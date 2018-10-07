package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Scanner;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus computador
 *
 * @author alexandre
 */
public class VersaoHumanoVersusComputadorImpl implements AbstracaoVersaoJogoVelha {

    Object[] args = new Object[1];

    @Override
    public void jogar(JogoDaVelha jogo) {

        Jogador jogador = jogo.getProximoAJogar();

        jogo.getProximoAJogar().joga();

        if (jogo.terminou()) {
            jogo.updateView(jogador);
            return;
        }

        jogo.updateView(jogador);

        jogador = jogo.getProximoAJogar();

        try {

            int posicao = Integer.parseInt(getPosicao(jogo));

            while (!jogo.getProximoAJogar().joga(posicao)) {

                // System.err.println("\n" + posicao + " É POSIÇÃO INVÁLIDA! VÁLIDAS SÃO ENTRE 1 e 9 E DESOCUPADAS...");
                //System.out.println("\nSUA VEZ DE JOGAR... digite a posicao (1 a 9) ");
                args[0] = "É a sua vez de jogar !!! ".toUpperCase();

                jogo.getController().updateView("setMensagem", args);

                Thread.sleep(300);

                posicao = Integer.parseInt(getPosicao(jogo));

            }

            jogo.updateView(jogador);
            jogo.setPosicaoClicada("0");

        } catch (Exception e) {
            System.out.println(e.getCause());;
        }

    }

    @Override
    public String getVersao() {
        return "Versão Humano versus computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Computador");
        jogo.getJogador2().setNome("Humano");

    }

    private String getPosicao(JogoDaVelha jogo) {
        return jogo.getPosicaoClicada();
    }

}
