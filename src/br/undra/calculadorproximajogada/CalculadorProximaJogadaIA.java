package br.undra.calculadorproximajogada;

import br.edu.undra.modelo.JogoDaVelha;

/**
 * CalculadorProximaJogadaIA para computadorXhumano.<br>
 * Usa analise combinatoria para inferir melhor jogada.
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaIA {

    JogoDaVelhaVersaoHumanoVersusMaquina versusMaquina;
    public String mensagemConfigurador;
    public JogoDaVelha jogoDaVelha;

    public JogoDaVelha getJogoDaVelha() {
        return jogoDaVelha;
    }

    public void setJogoDaVelha(JogoDaVelha jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
    }

    public CalculadorProximaJogadaIA() {

    }

    public CalculadorProximaJogadaIA(JogoDaVelha jogoDaVelha) {
        this();
        this.jogoDaVelha = jogoDaVelha;
    }
    
    /**
     * Joga na posicao. 
     * @param posicao uma posicao válida : maior ou igual que 1 e menor ou igual dimensao*dimensao;
     */
    public void avancaUmaJogada(int posicao) {
        versusMaquina.avancaUmaJogada(posicao);
    }

    /**
     * Joga na linha e coluna. <br>
     * Faz corresponder ao estado atual do jogo da velha.
     * @param linha uma linha válida : maior ou igual que 1 e menor ou igual dimensao
     * @param coluna uma coluna válida : maior ou igual que 1 e menor ou igual dimensao
     */
    public void avancaUmaJogada( int linha, int coluna ) {
        
        int posicao = jogoDaVelha.getTabuleiro().transformarEmPosicao(linha, coluna);
        versusMaquina.avancaUmaJogada(posicao);
    }

    public int getMelhorJogada() {
        
        return versusMaquina.getMelhorJogada();
    }

    public int getMelhorJogadaAlternandoEntreJogadores() {
        
        return versusMaquina.getMelhorJogadaAlternandoEntreJogadores();
    }
    
    public static void main(String[] args) {
        CalculadorProximaJogadaIA calculadorProximaJogada = new CalculadorProximaJogadaIA();
        calculadorProximaJogada.configura();
        int melhorJogada = calculadorProximaJogada.getMelhorJogada();
        System.out.println(melhorJogada);
        
        int posicaoLivre = calculadorProximaJogada.getPosicaoLivre();
        if (posicaoLivre != 0 && posicaoLivre != melhorJogada) {
            calculadorProximaJogada.avancaUmaJogada(posicaoLivre);
        }

         //segunda jogada
        melhorJogada = calculadorProximaJogada.getMelhorJogada();
        System.out.println(melhorJogada);
        
        posicaoLivre = calculadorProximaJogada.getPosicaoLivre();
        if (posicaoLivre != 0 && posicaoLivre != melhorJogada) {
            calculadorProximaJogada.avancaUmaJogada(posicaoLivre);
        }

        //terceira jogada
        melhorJogada = calculadorProximaJogada.getMelhorJogada();

        System.out.println(melhorJogada);
        
        posicaoLivre = calculadorProximaJogada.getPosicaoLivre();
        if (posicaoLivre != 0 && posicaoLivre != melhorJogada) {
            calculadorProximaJogada.avancaUmaJogada(posicaoLivre);
        }
        
    }

    public void configura() {
        String[] args = {"-v"};
        versusMaquina = new JogoDaVelhaVersaoHumanoVersusMaquina(args, this);
        versusMaquina.configura();
    }

    public boolean isConfigurado() {
        return versusMaquina != null;
    }

    public void setMensagemConfigurador(String mensagemConfigurador) {
        this.mensagemConfigurador = mensagemConfigurador;

        try {
            Object[] args = {mensagemConfigurador};
            this.jogoDaVelha.getController().updateView("setMensagem", args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public int getPosicaoLivre() {
        return versusMaquina.getPosicaoLivre();
    }
    
    public boolean jogoTerminou(){
        return versusMaquina.jogoTerminou();
    }

    public void reconfigurar() {
        versusMaquina.reconfigurar();
    }

}
