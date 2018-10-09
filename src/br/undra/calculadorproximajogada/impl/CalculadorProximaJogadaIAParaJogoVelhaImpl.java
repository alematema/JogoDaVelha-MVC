package br.undra.calculadorproximajogada.impl;

import br.edu.undra.modelo.JogadorJogoDaVelha;
import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.undra.calculadorproximajogada.CalculadorProximaJogadaIA;
import br.undra.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.util.List;

/**
 * Classe modela uma IA. <br>Calcula proxima jogada usando algoritmo de
 * inteligencia natural.<br>
 * O algoritmo é baseado em análise combinatória.<br>
 * Todas evoluções possíveis do jogo são consideradas e, dai,<br>
 * calcula-se uma jogada com a maior probabilidade de se vencer o jogo.
 *
 *
 * @author alexandre
 */
public class CalculadorProximaJogadaIAParaJogoVelhaImpl implements CalculadorProximaJogada {

    private Integer[] ultimaJogada;
    private CalculadorProximaJogadaIA calculadorProximaJogadaIA = new CalculadorProximaJogadaIA();
    private AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha;

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
            }
        }).start();
    }

    @Override
    public int calcular(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
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

        System.out.println(getClass().getName() + ".calcularLinhaEColuna(Object)");

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

        linha = jogador.getJogo().getTabuleiro().transformarEmLinha(posicao);
        coluna = jogador.getJogo().getTabuleiro().transformarEmColuna(posicao);

        int[] proximaJogada = {linha, coluna};

        return proximaJogada;

    }

    public void setUltimaJogada(Integer[] ultimaJogada) {
        this.ultimaJogada = ultimaJogada;
    }

    public void setAbstracaoVersaoJogoVelha(AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha) {
        this.abstracaoVersaoJogoVelha = abstracaoVersaoJogoVelha;
    }

    @Override
    public String getNomeSimples() {
        return "calculadorIA";
    }
   

}
