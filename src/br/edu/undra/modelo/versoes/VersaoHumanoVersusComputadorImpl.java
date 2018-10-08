package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus computador
 *
 * @author alexandre
 */
public class VersaoHumanoVersusComputadorImpl implements AbstracaoVersaoJogoVelha {

    private boolean liberarJogada = false;

    Object[] args = new Object[1];

    @Override
    public void jogar(JogoDaVelha jogo) {

        Jogador jogador = jogo.getProximoAJogar();

        if (jogador.isPrimeiroAJogar()) {

            jogo.getProximoAJogar().joga();

            if (jogo.terminou()) {
                jogo.updateView(jogador);
                return;
            }

            jogo.updateView(jogador);

        }

        try {

            int posicao = Integer.parseInt(getPosicao(jogo));

            jogador = jogo.getProximoAJogar();

            while (!jogo.getProximoAJogar().joga(posicao)) {

                if (liberarJogada) {
                    liberarJogada = false;
                    return;
                }

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
        return "Jogo da Velha : Humano X computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Computador");
        jogo.getJogador2().setNome("Humano");

        jogo.getJogador1().setPrimeiroAJogar(true);

    }

    private String getPosicao(JogoDaVelha jogo) {
        return jogo.getPosicaoClicada();
    }

    @Override
    public void liberarJogada() {
        liberarJogada = true;
    }

    @Override
    public void setVelocity(int newValeu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
