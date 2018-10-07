package br.edu.undra.modelo.jogo;

import java.util.ArrayList;
import java.util.List;

/**
 * Modela um jogador.
 *
 * @author alexandre
 * @param <T> T extends Jogo
 */
public abstract class Jogador<T extends Jogo> {

    private String nome;

    private boolean primeiroAJogar = false;
    private boolean jogou = false;
    private String proximaJogada;
    private List<String> jogadas = new ArrayList<>();

    private T jogo;

    public Jogador() {
    }

    public Jogador(String nome) {
        this.nome = nome;
    }

    public Jogador(T jogo) {
        this.jogo = jogo;
    }

    public Jogador(String nome, T jogo) {
        this.nome = nome;
        this.jogo = jogo;
    }

    abstract public void joga();

    abstract public boolean joga(int linha, int coluna);

    abstract public boolean joga(int elemento, int linha, int coluna);

    abstract public boolean joga(int posicao);

    abstract public boolean jogaNaPosicao(int elemento, int posicao);

    abstract public void desfazerUltimaJogada();

    public boolean isPrimeiroAJogar() {
        return primeiroAJogar;
    }

    public void setPrimeiroAJogar(boolean primeiroAJogar) {
        this.primeiroAJogar = primeiroAJogar;
    }

    public boolean jogou() {
        return jogou;
    }

    public void setJogou(boolean jogou) {
        this.jogou = jogou;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public T getJogo() {
        return jogo;
    }

    public void setJogo(T jogo) {
        this.jogo = jogo;
    }

    public String getProximaJogada() {
        return proximaJogada;
    }

    public void setProximaJogada(String proximaJogada) {
        this.proximaJogada = proximaJogada;
    }

    public List<String> getJogadas() {
        return jogadas;
    }

}
