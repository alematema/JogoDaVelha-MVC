package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.undra.calculadorproximajogada.CalculadorProximaJogadaIA;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus humano
 *
 * @author alexandre
 */
public class VersaoHumanoVersusHumanoImpl implements AbstracaoVersaoJogoVelha {

    private boolean liberarJogada = false;
    
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
                
                if(liberarJogada){
                    liberarJogada = false;
                    return;
                }

            }

            jogo.updateView(jogador);
            jogo.setPosicaoClicada("0");

        } catch (Exception e) {
            System.out.println(e.getCause());;
        }

    }

    @Override
    public String getVersao() {
        return "Jogo da Velha : Humano X humano";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Joao");
        jogo.getJogador2().setNome("Kamila");

    }

    @Override
    public void liberarJogada() {
        liberarJogada = true;
    }

    @Override
    public void setVelocity(int newValeu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int calularProximaJogada(Jogador jogador, CalculadorProximaJogadaIA calculadorProximaJogadaIA) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
