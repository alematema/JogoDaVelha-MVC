package br.undra.calculadorproximajogada;

import java.util.Scanner;


public class JogoDaVelhaVersaoHumanoVersusMaquina {

    private static int MAX_JOGOS = 10;
    private JogoDaVelha jogoDaVelha = new JogoDaVelha(new Tabuleiro());
    private Jogador jogador1;
    private Jogador jogador2;
    private Analisador analisador;
    private boolean verbose = false;

    public JogoDaVelhaVersaoHumanoVersusMaquina(String[] args) {
        System.err.println("MENU\n-1 -> liga VERBOSE\n 0 -> desliga VERBOSE\n10 -> SAI do jogo\n-v na linha de comando, ativa verbose\n-----------------------------------");
        System.err.println("Criando banco de jogos ...");
        try {
            if (args[0].toUpperCase().equals("-V")) {
                verbose = true;
                System.err.println("VERBOSE ON");
            }
        } catch (Exception e) {
            System.err.println("NO VERBOSE");
        }
        analisador = new Analisador(verbose);

        jogador1 = new Jogador(jogoDaVelha, JogadorComecaOJogo.SIM, analisador);
        jogador2 = new Jogador(jogoDaVelha, JogadorComecaOJogo.NAO, analisador);
        jogador1.setOponente(jogador2);
        jogador2.setOponente(jogador1);
        jogador1.setVezDeJogar(Boolean.TRUE);
        jogador2.setVezDeJogar(Boolean.FALSE);

    }

    public int getProximaJogada(){
        return jogador1.getMelhorPosicaoEJogaNela();
    }
    
    public void avancaUmaJogada(int posicao){
        jogador2.jogaContraComputador(posicao - 1);
    }
    
     public void configura() {
        jogoDaVelha = new JogoDaVelha(new Tabuleiro());
        jogador1 = new Jogador(jogoDaVelha, JogadorComecaOJogo.SIM, analisador);
        jogador2 = new Jogador(jogoDaVelha, JogadorComecaOJogo.NAO, analisador);
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
    }

}
