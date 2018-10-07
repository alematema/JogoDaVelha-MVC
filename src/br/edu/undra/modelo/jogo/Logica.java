package br.edu.undra.modelo.jogo;

import java.util.Set;

/**
 * Modela a lógica de um jogo.
 * @author alexandre
 */
public interface Logica<T extends Jogador> {
    
    String getProximaJogadaParaJogador(T jogador);
    T getProximoAJogar();
    Set<T> getUltimosAJogar();
    
}
