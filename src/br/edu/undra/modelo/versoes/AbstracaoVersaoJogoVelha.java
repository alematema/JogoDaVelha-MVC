package br.edu.undra.modelo.versoes;

import br.edu.undra.MVC.modelo.impl.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.servicos.calculadorproximajogada.analisador.CalculadorProximaJogadaIA;

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
    String getNomeResumido();
    void SetUp(JogoDaVelha jogo);
    void liberarJogada();
    void setVelocity(int newValeu);
    int calularProximaJogada(Jogador jogador, CalculadorProximaJogadaIA calculadorProximaJogadaIA);
}
