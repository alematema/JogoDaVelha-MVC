package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus humano
 *
 * @author alexandre
 */
public class VersaoHumanoVersusHumanoImpl implements AbstracaoVersaoJogoVelha {

    Object[] args = new Object[1];
    
    @Override
    public void jogar(JogoDaVelha jogo) {

        Jogador jogador = jogo.getProximoAJogar();
        
        args[0] = ("Vez de "  + jogo.getProximoAJogar().getNome()+ " jogar.").toUpperCase();

        jogo.getController().updateView("setMensagem", args);

        try {

            int posicao = Integer.parseInt(jogo.getPosicaoClicada());

            while (!jogo.getProximoAJogar().joga(posicao)) {

                Thread.sleep(300);

                posicao = Integer.parseInt(jogo.getPosicaoClicada());

            }

            jogo.updateView(jogador);
            jogo.setPosicaoClicada("0");

        } catch (Exception e) {
            System.out.println(e.getCause());;
        }

    }

    @Override
    public String getVersao() {
        return "Versão Humano versus humano";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Joao");
        jogo.getJogador2().setNome("Kamila");

    }

}
