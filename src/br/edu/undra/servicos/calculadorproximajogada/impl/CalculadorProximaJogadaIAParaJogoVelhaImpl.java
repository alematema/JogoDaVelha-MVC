package br.edu.undra.servicos.calculadorproximajogada.impl;

import br.edu.undra.MVC.modelo.impl.JogadorJogoDaVelha;
import br.edu.undra.MVC.modelo.impl.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.servicos.calculadorproximajogada.analisador.CalculadorProximaJogadaIA;
import br.edu.undra.servicos.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe modela uma IA. Torna o jogo mais difícil jogar contra o computador.<br>Calcula proxima jogada usando algoritmo de
 * inteligencia natural.<br>
 * O algoritmo é baseado em análise combinatória.<br>
 * Todas evoluções possíveis do jogo são consideradas e, dai,<br>
 * calcula-se uma jogada com a maior probabilidade de se vencer o jogo.<br>
 
 *
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaIAParaJogoVelhaImpl implements CalculadorProximaJogada {

    private Integer[] ultimaJogada;
    private CalculadorProximaJogadaIA calculadorProximaJogadaIA = new CalculadorProximaJogadaIA();
    private AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha;
    
    public volatile boolean isConfigurado = false;

    public CalculadorProximaJogadaIAParaJogoVelhaImpl() {
    }

    public CalculadorProximaJogadaIAParaJogoVelhaImpl(AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha) {
        
        this.abstracaoVersaoJogoVelha = abstracaoVersaoJogoVelha;
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                
                String[] args = {"-v"};
//                String[] args = {};
                calculadorProximaJogadaIA.configura(args);
                isConfigurado = true;
            }
        }).start();
    }
    
    public CalculadorProximaJogadaIAParaJogoVelhaImpl(AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha, JogoDaVelha jogoDaVelha) {
        
        this.abstracaoVersaoJogoVelha = abstracaoVersaoJogoVelha;
        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean w = false;
                while(!w){
                    
                    try {
                        
                        jogoDaVelha.getController().updateView("desabilitarMenuJogos", null);
                        
                    } catch (Exception e) {
                        continue;
                    }
                    
                    w = true;
                }
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CalculadorProximaJogadaIAParaJogoVelhaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                jogoDaVelha.getController().updateView("desabilitarMenuJogos", null);
                
                //String[] args = {"-v"};
                String[] args = {};
                calculadorProximaJogadaIA.configura(args);
                jogoDaVelha.getController().updateView("habilitarMenuJogos", null);
                
                isConfigurado = true;
                
            }
        }).start();
    }

    @Override
    public int calcular(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Torna o jogo mais difícil de se jogar contra o computador.<br>
     * Nessa implementação, a logica de cálculo é determinada através das hook
     * classes:<br>
     * VersaoComputadorVersusComputadorImpl.<br>
     * VersaoHumanoVersusComputadorImpl.<br>
     * VersaoHumanoVersusHumanoImpl. <br>
     * Calcula-se proxima jogada baseando em análise combinatória.<br>
     * Todas evoluções possíveis do jogo são consideradas e, dai,<br>
     * calcula-se uma jogada com a maior probabilidade de se vencer o jogo.
     *
     * @param jogador o jogador
     * @return int[linha,coluna] representando a melhor posicao pra se jogar.
     */
    @Override
    public int[] calcularLinhaEColuna(Jogador jogador) {

        jogador = (JogadorJogoDaVelha) jogador;

        if (!calculadorProximaJogadaIA.isConfigurado()) {

//            String[] args = {"-v"};
            String[] args = {};
            calculadorProximaJogadaIA.configura(args);

        }
        //É NECESSÁRIO ESSE SET AQUI, DEPOIS DE SE CONFIGURAR O CALCULADOR,
        //SE SE QUISER QUE NAO SEJAM FEITOS LOGS NO START UP DO JOGO.
        calculadorProximaJogadaIA.setJogoDaVelha((JogoDaVelha) jogador.getJogo());

        if (!calculadorProximaJogadaIA.isCalculadorSincronizado(jogador)) {
            calculadorProximaJogadaIA.sincronizarCalculador(jogador);
        } else {
        }

        int linha;
        int coluna;
        int posicao = abstracaoVersaoJogoVelha.calularProximaJogada(jogador, calculadorProximaJogadaIA);

        linha = (int)jogador.getJogo().getTabuleiro().transformarEmLinha(posicao);
        coluna = (int)jogador.getJogo().getTabuleiro().transformarEmColuna(posicao);

        int[] proximaJogada = {linha, coluna};

        return proximaJogada;

    }

    public void setUltimaJogada(Integer[] ultimaJogada) {
        this.ultimaJogada = ultimaJogada;
    }

    public void setAbstracaoVersaoJogoVelha(AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha) {
        this.abstracaoVersaoJogoVelha = abstracaoVersaoJogoVelha;
    }

    public AbstracaoVersaoJogoVelha getAbstracaoVersaoJogoVelha() {
        return abstracaoVersaoJogoVelha;
    }
    
    @Override
    public String getNomeSimples() {
        return "calculadorIA";
    }

    @Override
    public void reconfigurar() {
        boolean verbose = false;
        calculadorProximaJogadaIA.reconfigurar(verbose);
    }
   

}
