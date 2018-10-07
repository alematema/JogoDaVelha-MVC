package br.edu.undra.modelo;

import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.jogo.Jogo;
import br.edu.undra.modelo.jogo.Tabuleiro;
import java.util.List;

/**
 * Modela um jogador de jogo da velha.
 *
 * @author alexandre
 * @param <J> algo que extenda de Jogo
 */
public class JogadorJogoDaVelha<J extends Jogo> extends Jogador {

    private int atual = 2;
    private int elemento = 0;
    private String ultimoEstadoAposJogar;
    private String bolaOuX = "O";

    public JogadorJogoDaVelha(String nome) {
        super(nome);
    }

    public JogadorJogoDaVelha(J jogo) {
        super(jogo);
    }

    public JogadorJogoDaVelha(String nome, J jogo) {
        super(nome, jogo);
    }

    public int getAtual() {
        return atual;
    }

    public void setAtual(int atual) {
        this.atual = atual;
    }

    public int getElemento() {
        return elemento;
    }

    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    public String getUltimoEstadoAposJogar() {
        return ultimoEstadoAposJogar;
    }

    public void setUltimoEstadoAposJogar(String ultimoEstadoAposJogar) {
        this.ultimoEstadoAposJogar = ultimoEstadoAposJogar;
    }

    public String getBolaOuX() {
        return bolaOuX;
    }

    @Override
    public void setPrimeiroAJogar(boolean primeiroAJogar) {
        super.setPrimeiroAJogar(primeiroAJogar);
        atual = 1;
        bolaOuX = "X";
    }

    @Override
    public void desfazerUltimaJogada() {

        if (getJogadas().size() >= 1) {

            String ultimaJogada = (String) getJogadas().get(getJogadas().size() - 1);

            //recupera da ultima jogada a posicao ou linhaXcoluna
            int linha = Integer.parseInt(ultimaJogada.split(",")[0]);
            int coluna = Integer.parseInt(ultimaJogada.split(",")[1]);

            //escreve o valor de POSICAO_LIVRE em linhaXcoluna
            getJogo().getTabuleiro().set(Tabuleiro.POSICAO_LIVRE, linha, coluna);

            getJogadas().remove(getJogadas().size() - 1);

            getJogo().setProximoAJogar(this);

            for (JogadorJogoDaVelha j : (List<JogadorJogoDaVelha>) getJogo().getJogadores()) {

                if (!j.equals(this)) {

                    j.retornarAoUltimoEstadoAposJogar();

                    break;

                }
            }

            setJogou(false);

        } else {
            return;
        }

    }

    public void retornarAoUltimoEstadoAposJogar() {

        if (getJogadas().size() < 1) {
            return;
        }

        int atualAposJogar = Integer.parseInt(ultimoEstadoAposJogar.split(",")[0]);
        int elementoAposJogar = Integer.parseInt(ultimoEstadoAposJogar.split(",")[1]);

        atual = atualAposJogar;
        elemento = elementoAposJogar;

    }

    @Override
    public JogoDaVelha getJogo() {
        return (JogoDaVelha) super.getJogo();
    }

    public boolean venceu() {
        return getJogo().jogadorVenceu(this);
    }

    @Override
    public void joga() {

        //logica de jogar vai aqui
        String posicao = getJogo().getProximaJogadaParaJogador(this);

        int linha = Integer.parseInt(posicao.split(",")[0]);
        int coluna = Integer.parseInt(posicao.split(",")[1]);

        getJogo().getTabuleiro().set(elemento, linha, coluna);

        getJogadas().add(posicao + "," + elemento);

        getJogo().getUltimosAJogar().add(this);

//        System.out.println(getNome() + " jogou");

        setJogou(true);

        ultimoEstadoAposJogar = atual + "," + elemento;

    }

    @Override
    public boolean joga(int linha, int coluna) {

        boolean posicoesValidas = getJogo().getTabuleiro().validar(linha, coluna);
        if (!posicoesValidas) {
            return false;
        }

        if (!getJogo().getTabuleiro().isPosicaoLivre(linha, coluna)) {
            return false;
        }

        getJogo().getTabuleiro().set(elemento, linha, coluna);

        getJogadas().add(linha + "," + coluna + "," + elemento);

        getJogo().getUltimosAJogar().add(this);

//        System.out.println(getNome() + " jogou " + elemento + " na posicao " + linha + "," + coluna);
        System.out.println(getNome() + " jogou");

        setJogou(true);

        ultimoEstadoAposJogar = atual + "," + elemento;

        return true;

    }

    @Override
    public boolean joga(int elemento, int linha, int coluna) {

        setElemento(elemento);
        return joga(linha, coluna);

    }

    @Override
    public boolean joga(int posicao) {

        int linha = getJogo().getTabuleiro().transformarEmLinha(posicao);
        int coluna = getJogo().getTabuleiro().transformarEmColuna(posicao);

        return joga(linha, coluna);

    }

    @Override
    public boolean jogaNaPosicao(int elemento, int posicao) {

        setElemento(elemento);
        return joga(posicao);

    }

}
