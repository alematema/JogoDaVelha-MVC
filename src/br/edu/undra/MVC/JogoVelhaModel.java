package br.edu.undra.MVC;

import br.edu.undra.interfaces.MVC.Model;

/**
 * A interface do modelo do jogo da velha.
 * @author alexandre
 */
public interface JogoVelhaModel extends Model {
    
    void setVelocity(int novoValor);
    void play(Object[] args);
    void setPosicaoClicada(String posicao);
    
}
