package br.edu.undra.MVC;

import br.edu.undra.interfaces.MVC.Model;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.servicos.calculadorproximajogada.interfaces.CalculadorProximaJogada;

/**
 * A interface do modelo do jogo da velha.
 * @author alexandre
 */
public interface JogoVelhaModel extends Model {
    
    void setVelocity(int novoValor);
    void play(Object[] args);
    void setPosicaoClicada(String posicao);
    void setVersaoJogoVelha(String versao);
    void setCalculadorProximaJogada(String nomeCalculadorProximaJogada);
    void liberarJogada();
}
