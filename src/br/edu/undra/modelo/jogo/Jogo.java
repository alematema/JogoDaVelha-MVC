package br.edu.undra.modelo.jogo;

import br.edu.undra.modelo.JogadorJogoDaVelha;
import java.util.List;
import java.util.Set;

/**
 * Modela um jogo.
 * @author alexandre
 * @param <T>
 */
public abstract class Jogo<T extends Jogador> {
    
    private String nome;
    private List<T> jogadores;
    private Tabuleiro tabuleiro;
    
    

    public Jogo() {
    }

    public Jogo(List<T> jogadores) {
        this.jogadores = jogadores;
    }

    public Jogo(List<T> jogadores, Tabuleiro tabuleiro) {
        
        this.jogadores = jogadores;
        this.tabuleiro = tabuleiro;
        
        setUpJogadores();
    }

    public Jogo(String nome, List<T> jogadores, Tabuleiro tabuleiro) {
        this(jogadores, tabuleiro);
        this.nome = nome;
    }
    
    public abstract Set<T> getUltimosAJogar();
    public abstract String getProximaJogadaParaJogador(Jogador jogador);
    public abstract JogadorJogoDaVelha getProximoAJogar();
    public abstract void iniciar();
    public abstract boolean terminou();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<T> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<T> jogadores) {
        this.jogadores = jogadores;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void setUpJogadores() {
        for(T j :jogadores) j.setJogo(this);
    }  

}
