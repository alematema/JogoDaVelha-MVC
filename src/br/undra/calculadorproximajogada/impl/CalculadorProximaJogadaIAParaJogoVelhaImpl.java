package br.undra.calculadorproximajogada.impl;

import br.edu.undra.modelo.JogadorJogoDaVelha;
import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.undra.calculadorproximajogada.CalculadorProximaJogadaIA;
import br.undra.calculadorproximajogada.interfaces.CalculadorProximaJogada;

/**
 * Classe modela uma IA. <br>Calcula proxima jogada usando algoritmo de inteligencia
 * natural.<br>
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
    }

    @Override
    public int calcular(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Nessa implemantação, a logica de cálculo é determinada através das hook
     * classes:<br>
     * VersaoComputadorVersusComputadorImpl.<br>
     * VersaoHumanoVersusComputadorImpl.<br>
     * VersaoHumanoVersusHumanoImpl. <br>
     * Calcula proxima jogada baseando em análise combinatória.<br>
     * Todas evoluções possíveis do jogo são consideradas e, dai,<br>
     * calcula-se uma jogada com a maior probabilidade de se vencer o jogo.
     *
     * @param jogador o jogador
     * @return int[linha,coluna]
     */
    @Override
    public int[] calcularLinhaEColuna(Jogador jogador) {

        jogador = (JogadorJogoDaVelha) jogador;

        if (!calculadorProximaJogadaIA.isConfigurado()) {
            calculadorProximaJogadaIA.setJogoDaVelha((JogoDaVelha) jogador.getJogo());
            calculadorProximaJogadaIA.configura();
        }
        
        int linha ;
        int coluna ;
        int posicao = abstracaoVersaoJogoVelha.calularProximaJogada(jogador, calculadorProximaJogadaIA);

        linha = jogador.getJogo().getTabuleiro().transformarEmLinha(posicao);
        coluna = jogador.getJogo().getTabuleiro().transformarEmColuna(posicao);
        
        int[] proximaJogada = {linha,coluna};
        
        return proximaJogada;

    }

    public void setUltimaJogada(Integer[] ultimaJogada) {
        this.ultimaJogada = ultimaJogada;
    }

    public void setAbstracaoVersaoJogoVelha(AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha) {
        this.abstracaoVersaoJogoVelha = abstracaoVersaoJogoVelha;
    }

}
