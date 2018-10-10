package br.edu.undra.servicos.calculadorproximajogada.analisador;

import br.edu.undra.modelo.JogadorJogoDaVelha;
import br.edu.undra.modelo.JogoDaVelha;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * CalculadorProximaJogadaIA para computadorXhumano E PARA
 * computadorXcomputador<br>
 * Usa analise combinatoria para inferir melhor jogada.
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaIA {

    JogoDaVelhaVersaoHumanoVersusMaquina versusMaquina;
    public String mensagemConfigurador;
    public JogoDaVelha jogoDaVelha;
    private volatile boolean isConfigurado = false;

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
     *
     * @param posicao uma posicao válida : maior ou igual que 1 e menor ou igual
     * dimensao*dimensao;
     */
    public void avancaUmaJogada(int posicao) {
        versusMaquina.avancaUmaJogada(posicao);
    }

    /**
     * Joga na linha e coluna. <br>
     * Faz corresponder ao estado atual do jogo da velha.
     *
     * @param linha uma linha válida : maior ou igual que 1 e menor ou igual
     * dimensao
     * @param coluna uma coluna válida : maior ou igual que 1 e menor ou igual
     * dimensao
     */
    public void avancaUmaJogada(int linha, int coluna) {

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
        calculadorProximaJogada.configura(null);
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

    public void configura(String[] args) {

        if(args == null ){
            args = new String[0];
            args[0] = "";
        }
        
        if (!isConfigurado()) {

            versusMaquina = new JogoDaVelhaVersaoHumanoVersusMaquina(args, this);
            try {
                versusMaquina.configura(args[0].toUpperCase().equals("-V"));
            } catch (Exception e) {
                boolean verbose = true;
                versusMaquina.configura(verbose);
            }
            

        }

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

    public List<Integer> getPosicoesLivres() {

        List<Integer> posicoesLivres = new ArrayList<>();

        for (int i = 0; i < versusMaquina.getJogoDaVelha().getTabuleiro().getBaseCorrente().size(); i++) {
            if (versusMaquina.getJogoDaVelha().getTabuleiro().getBaseCorrente().get(i).equals(0)) {
                posicoesLivres.add(i);
            }
        }

        return posicoesLivres;
    }

    public int getPosicaoLivre() {
        return versusMaquina.getPosicaoLivre();
    }

    public boolean jogoTerminou() {
        return versusMaquina.jogoTerminou();
    }

    public void reconfigurar(boolean verbose) {
        versusMaquina.reconfigurar(verbose);
    }

    public boolean isOcupada(int posOcupada) {
        return !versusMaquina.getJogoDaVelha().isPosicaoLivre(posOcupada - 1);
    }

    public Jogador getJogador2() {
        return versusMaquina.getJogador2();
    }

    public Jogador getJogador1() {
        return versusMaquina.getJogador1();
    }

    public void descreverTabuleiro() {
        versusMaquina.getJogoDaVelha().getTabuleiro().comMascara().descreve();
    }
    
     /**
     * Verifica se o tabuleiro deste jogo da velha está SINCRONIZADO<br>
     * com o tabuleiro do calculador da proxima jogada.
     *
     * @param jogador
     * @return true , se sync.<br>
     * false, caso contrario.
     */
    public boolean isCalculadorSincronizado(br.edu.undra.modelo.jogo.Jogador jogador) {
        
        
        if (jogoTerminou()) {
            
            boolean verbose = true;
             reconfigurar(true);
        }

        jogador = (JogadorJogoDaVelha) jogador;

        return jogador.getJogo().getTabuleiro().getPosicoesLivres().size() == getPosicoesLivres().size();

    }
    
     /**
     * Sincroniza o tabuleiro deste jogo com o tabuleiro <br>
     * do calculador de proxima jogada.
     *
     * @param jogador
     */
    public void sincronizarCalculador(br.edu.undra.modelo.jogo.Jogador jogador) {

        jogador = (JogadorJogoDaVelha) jogador;

        List<Object> posicoesOcupadas = jogador.getJogo().getTabuleiro().getPosicoeOcupadas();

        List<Integer> listValoresNasPosicoesOcupadas = new ArrayList<>();
        
        for(Object o : posicoesOcupadas){
            
            int valor = Integer.parseInt(((String)o).split(",")[3]);
            listValoresNasPosicoesOcupadas.add(valor);
            
        }
        
        Collections.sort(listValoresNasPosicoesOcupadas);
        
//        System.out.println("Posicoes Ocupadas " + posicoesOcupadas);
//        System.out.println("Valore posicoes Ocupadas ordenados " + listValoresNasPosicoesOcupadas);

        posicoesOcupadas = jogador.getJogo().getTabuleiro().getPosicoesDestesValoresInteiros(listValoresNasPosicoesOcupadas);
//        System.out.println("Posicoes Ocupadas ordenadas " + posicoesOcupadas);
        
        
        for (Object pos : posicoesOcupadas) {

            int posicao = (Integer)pos;

            if (isOcupada(posicao)) {
            } else {
                
                descreverTabuleiro();
                
                int valor = (Integer) jogador.getJogo().getTabuleiro().get(posicao);

                if (valor % 2 == 1) {
                    //jogador1 tera que jogar
                    getJogador1().joga(posicao - 1);
                } else {
                    //jogaodr2 tera que jogar
                    getJogador2().joga(posicao - 1);
                }
                //jogador2.getJogoDaVelha().getTabuleiro().comMascara().descreve();
                descreverTabuleiro();

            }

        }

    }
    

}
