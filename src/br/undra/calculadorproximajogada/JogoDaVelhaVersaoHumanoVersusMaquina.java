package br.undra.calculadorproximajogada;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class JogoDaVelhaVersaoHumanoVersusMaquina {

    private static int MAX_JOGOS = 10;
    private JogoDaVelhaWrapped jogoDaVelha = new JogoDaVelhaWrapped(new Tabuleiro());
    private Jogador jogador1;
    private Jogador jogador2;
    private Analisador analisador;
    private boolean verbose = false;
    SecureRandom secureRandom;
    CalculadorProximaJogadaIA calculadorProximaJogada;

    public JogoDaVelhaVersaoHumanoVersusMaquina(String[] args, CalculadorProximaJogadaIA calculadorProximaJogada) {

        verbose = false;
        try {
            if (args[0].toUpperCase().equals("-V")) {
                verbose = true;
            }
        } catch (Exception e) {
        }

        this.calculadorProximaJogada = calculadorProximaJogada;

        analisador = new Analisador(verbose, calculadorProximaJogada);

        jogador1 = new Jogador(jogoDaVelha, JogadorComecaOJogo.SIM, analisador, calculadorProximaJogada);
        jogador2 = new Jogador(jogoDaVelha, JogadorComecaOJogo.NAO, analisador, calculadorProximaJogada);
        jogador1.setOponente(jogador2);
        jogador2.setOponente(jogador1);
        jogador1.setVezDeJogar(Boolean.TRUE);
        jogador2.setVezDeJogar(Boolean.FALSE);

        byte[] seed = new byte[6];
        seed[0] = 2;
        seed[1] = -1;
        seed[2] = 12;
        seed[3] = 11;
        seed[4] = -25;
        seed[5] = 30;

        secureRandom = new SecureRandom(seed);

    }

    public int getMelhorJogada() {
        return jogador1.getMelhorPosicaoEJogaNela() + 1;
    }

    public int getMelhorJogadaAlternandoEntreJogadores() {

        if (jogador1.isVezDeJogar()) {
            return jogador1.getMelhorPosicaoEJogaNela() + 1;
        } else {
            return jogador2.getMelhorPosicaoEJogaNela() + 1;
        }

    }

    public void avancaUmaJogada(int posicao) {
        jogador2.jogaContraComputador(posicao - 1);
        jogador2.getJogoDaVelha().getTabuleiro().comMascara().descreve();
    }

    public void configura() {
        try {
            jogoDaVelha = new JogoDaVelhaWrapped(new Tabuleiro());
            jogador1 = new Jogador(jogoDaVelha, JogadorComecaOJogo.SIM, analisador, calculadorProximaJogada);
            jogador2 = new Jogador(jogoDaVelha, JogadorComecaOJogo.NAO, analisador, calculadorProximaJogada);
            jogador1.setOponente(jogador2);
            jogador2.setOponente(jogador1);
            analisador.clean();
            analisador.verboseOff();
            jogador1.setVezDeJogar(Boolean.TRUE);
            jogador2.setVezDeJogar(Boolean.FALSE);
            if (verbose) {
                jogador1.verboseOn();
                jogador2.verboseOn();
                analisador.setVerbose(true);
            } else {
                jogador1.verboseOff();
                jogador2.verboseOff();
                analisador.setVerbose(false);
            }
        } catch (Exception e) {
            System.out.println("EXCESAO EM JogoDaVelhaVersaoHumanoVersusMaquina.configura() " + e.getCause());
        }

    }

    public int getPosicaoLivre() {

        List<Integer> posicoesLivres = new ArrayList<>();

        for (int i = 0; i < jogoDaVelha.getBaseCorrente().size(); i++) {
            if (jogoDaVelha.getBaseCorrente().get(i) == 0) {
                posicoesLivres.add(i);
            }
        }//fim

        int posicaoLivre = secureRandom.nextInt(posicoesLivres.size() >= 1 ? posicoesLivres.size() : 1);

        return posicaoLivre + 1;
    }

    boolean jogoTerminou() {
        return jogoDaVelha.terminou();
    }

    public JogoDaVelhaWrapped getJogoDaVelha() {
        return jogoDaVelha;
    }

    void reconfigurar() {
        configura();
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

}
