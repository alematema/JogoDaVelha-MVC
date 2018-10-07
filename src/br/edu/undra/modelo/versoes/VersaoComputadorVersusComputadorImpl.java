package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
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
        
        jogo.getProximoAJogar().joga();

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(VersaoComputadorVersusComputadorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getVersao() {
        return "Versão Computador versus computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {
        jogo.getJogador1().setNome("Comp1");
        jogo.getJogador2().setNome("Comp2");
    }

}
