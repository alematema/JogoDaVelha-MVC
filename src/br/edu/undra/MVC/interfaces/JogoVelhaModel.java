package br.edu.undra.MVC.interfaces;

/**
 * A interface do modelo do jogo da velha.
 * @author alexandre
 */
public interface JogoVelhaModel extends Model {
    
    void setVelocity(int novoValor);
    int getVelocity();
    void play(Object[] args);
    void setPosicaoClicada(String posicao);
    void setVersaoJogoVelha(String versao);
    void setCalculadorProximaJogada(String nomeCalculadorProximaJogada);
    void liberarJogada();
}
