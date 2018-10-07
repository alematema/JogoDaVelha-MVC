package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;

/**
 * Uma abstração das versoes do jogo da velha. <br>
 * Versões podem ser : <br>
 * Computador versus computador<br>
 * Humano versus computador.<br>
 * Humano versus humano.
 * @author alexandre
 */
public interface AbstracaoVersaoJogoVelha {
    void jogar(JogoDaVelha jogo);
    String getVersao();
    void SetUp(JogoDaVelha jogo);
}
