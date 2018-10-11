package br.edu.undra.MVC;

import br.edu.undra.MVC.interfaces.Controller;
import br.edu.undra.MVC.interfaces.JogoVelhaView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe que simula uma implementação de uma View do jogo da velha.
 *
 * @author alexandre
 */
public class JogoDaVelhaMockView implements JogoVelhaView {

    boolean isConfigurado = false;
    String mensagem;
    String value;
    int posicao;
    List<Object> posicoesOndeVenceu;
    private String versaoJogo;
    private String versaoCalculador;
    private boolean isBotoesHabilitados;
    private boolean isItensMenuJogoSet;
    private boolean isHabilitadoVelocidadeENivelMenuJogo;
    private boolean isHabilitadoMenuJogo;
    private Controller controller;
    private final Map tabuleiroMock = new HashMap();

    public JogoDaVelhaMockView() {
        tabuleiroMock.put(1,0);
        tabuleiroMock.put(2,0);
        tabuleiroMock.put(3,0);
        tabuleiroMock.put(4,0);
        tabuleiroMock.put(5,0);
        tabuleiroMock.put(6,0);
        tabuleiroMock.put(7,0);
        tabuleiroMock.put(8,0);
        tabuleiroMock.put(9,0);
    }
    
    
    
    public Map getTabuleiroMock() {
        return tabuleiroMock;
    }

    @Override
    public void set(String value, int posicao) {
        
        tabuleiroMock.put(posicao, value);
        
        System.err.println("MOCK VIEW : set : value " + value + ", posicao "+ posicao);
        
    }

    public String getValue() {
        return value;
    }

    public int getPosicao() {
        return posicao;
    }

    @Override
    public void marcarOndeVenceu(List<Object> posicoes) {
        this.posicoesOndeVenceu = new ArrayList<>(posicoes);
        System.err.println("MOCK VIEW : marcarOndeVenceu  " + this.posicoesOndeVenceu);
    }

    public List<Object> getPosicoesOndeVenceu() {
        return posicoesOndeVenceu;
    }

    public void setIsConfigurado(boolean isConfigurado) {
        this.isConfigurado = isConfigurado;
    }

    @Override
    public void reconfigurar() {
        isConfigurado = true;
    }

    @Override
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
        System.err.println("MOCK VIEW : mensagem  " + mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public void setVersaoJogo(String versao) {
        Object[] args = {versao};
        controller.updateModel("setVersaoJogoVelha", args);
        this.versaoJogo = versao;
        System.err.println("MOCK VIEW : setVersaoJogo ");
    }

    public String getVersaoJogo() {
        return versaoJogo;
    }

    @Override
    public void setVersaoCalculador(String versao) {
        Object[] args = {versao};
        controller.updateModel("setCalculadorProximaJogada", args);
        this.versaoCalculador = versao;
        System.err.println("MOCK VIEW : setVersaoCalculador ");
    }

    public String getVersaoCalculador() {
        return versaoCalculador;
    }

    @Override
    public void desabilitarBotoes() {
        isBotoesHabilitados = false;
        System.err.println("MOCK VIEW : desabilitarBotoes ");
    }

    @Override
    public void habilitarBotoes() {
        isBotoesHabilitados = true;
        System.err.println("MOCK VIEW : habilitarBotoes ");
    }

    @Override
    public void setUpItensMenuJogo() {
        isItensMenuJogoSet = true;
        System.err.println("MOCK VIEW : setUpItensMenuJogo ");
    }

    public void setDownItensMenuJogo() {
        isItensMenuJogoSet = false;
    }

    @Override
    public void habilitarDesabilitarItensVelocidadeENivelMenuJogo() {
        isHabilitadoVelocidadeENivelMenuJogo = true;
         System.err.println("MOCK VIEW : habilitarDesabilitarItensVelocidadeENivelMenuJogo ");
    }

    public void desabilitarVelocidadeENivelMEnuJogo() {
        isHabilitadoVelocidadeENivelMenuJogo = false;
        System.err.println("MOCK VIEW : desabilitarVelocidadeENivelMEnuJogo ");
    }

    @Override
    public void desabilitarMenuJogos() {
        isHabilitadoMenuJogo = false;
        System.err.println("MOCK VIEW : desabilitarMenuJogos ");
    }

    @Override
    public void habilitarMenuJogos() {
        isHabilitadoMenuJogo = true;
        System.err.println("MOCK VIEW : habilitarMenuJogos ");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void joga(int posicao) {
        
        this.posicao = posicao;
        
        Object[] args = new Object[1];
        args[0] = ""+posicao;
        controller.updateModel("setPosicaoClicada", args);
        
        System.err.println("MOCK VIEW : joga em " +posicao);
        
    }

}
