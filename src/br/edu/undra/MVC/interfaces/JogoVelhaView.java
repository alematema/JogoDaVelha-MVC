package br.edu.undra.MVC.interfaces;

import br.edu.undra.MVC.interfaces.View;
import java.util.List;

/**
 * A view especializada do jogo velha
 * @author alexandre
 */
public interface JogoVelhaView extends View{
    void set(String value, int posicao);
    void marcarOndeVenceu(List<Object> posicoes);
    void reconfigurar();
    void setMensagem(String mensagem);
    void setVersaoJogo(String versao);
    void setVersaoCalculador(String versao);
    void desabilitarBotoes();
    void habilitarBotoes();
    void setUpItensMenuJogo();
    void habilitarDesabilitarItensVelocidadeENivelMenuJogo();
    void desabilitarMenuJogos();
    void habilitarMenuJogos();
}
