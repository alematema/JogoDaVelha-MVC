package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Computador versus computador
 *
 * @author alexandre
 */
public class VersaoComputadorVersusComputadorImpl implements AbstracaoVersaoJogoVelha {

    @Override
    public void jogar(JogoDaVelha jogo) {

        Jogador jogador = jogo.getProximoAJogar();

        jogo.getProximoAJogar().joga();
        jogo.updateView(jogador);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VersaoComputadorVersusComputadorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getVersao() {
        return "Jogo da Velha : Computador X computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {
        jogo.getJogador1().setNome("Comp1");
        jogo.getJogador2().setNome("Comp2");
    }

    @Override
    public void liberarJogada() {

    }

}
