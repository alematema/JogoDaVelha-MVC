package br.undra.calculadorproximajogada.interfaces;

import br.edu.undra.modelo.jogo.Jogador;


/**
 * Abstracao da lógica de calculo de uma proxima jogada
 * @author alexandre
 */
public interface CalculadorProximaJogada<T extends Jogador> {
    
    int calcular( T jogador );
    int[] calcularLinhaEColuna( T jogador );
    String getNomeSimples();
    void reconfigurar();
    
}
