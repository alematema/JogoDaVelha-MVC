package br.edu.undra.modelo.versoes;

import br.edu.undra.MVC.modelo.impl.JogoDaVelha;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.servicos.calculadorproximajogada.analisador.CalculadorProximaJogadaIA;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus computador
 *
 * @author alexandre
 */
public class VersaoHumanoVersusComputadorImpl implements AbstracaoVersaoJogoVelha {

    private boolean liberarJogada = false;

    Object[] args = new Object[1];

    @Override
    public void jogar(JogoDaVelha jogo) {

        Jogador jogador = jogo.getProximoAJogar();

        if (jogador.isPrimeiroAJogar()) {

            jogo.getProximoAJogar().joga();

            if (jogo.terminou()) {
                jogo.updateView(jogador);
                return;
            }

            jogo.updateView(jogador);

        }

        try {

            int posicao = Integer.parseInt(getPosicao(jogo));

            jogador = jogo.getProximoAJogar();

            while (!jogo.getProximoAJogar().joga(posicao)) {

                if (liberarJogada) {
                    liberarJogada = false;
                    return;
                }

                args[0] = "É a sua vez de jogar !!! ".toUpperCase();

                jogo.getController().updateView("setMensagem", args);

                Thread.sleep(300);

                posicao = Integer.parseInt(getPosicao(jogo));

            }

            jogo.updateView(jogador);
            jogo.setPosicaoClicada("0");

        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }

    @Override
    public String getVersao() {
        return "Jogo da Velha : Humano X computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Computador");
        jogo.getJogador2().setNome("Você");

        jogo.getJogador1().setPrimeiroAJogar(true);

    }

    private String getPosicao(JogoDaVelha jogo) {
        return jogo.getPosicaoClicada();
    }

    @Override
    public void liberarJogada() {
        liberarJogada = true;
    }

    @Override
    public void setVelocity(int newValeu)  {
    }

    /**
     * Calcula-se proxima jogada baseando em análise combinatória.<br>
     * Todas evoluções possíveis do jogo são consideradas e, dai,<br>
     * calcula-se uma jogada com a maior probabilidade de se vencer o jogo.
     *
     * @param jogador o jogador
     * @return int representando a melhor posicao para se jogar.
     */
    @Override
    public int calularProximaJogada(Jogador jogador, CalculadorProximaJogadaIA calculadorProximaJogadaIA) {

        int linha;
        int coluna;
        int posicao = 0;

        //é primeira jogada
        if (((JogoDaVelha) jogador.getJogo()).getUltimoAJogar() == null) {

            posicao = calculadorProximaJogadaIA.getMelhorJogada();

        } else {

            Jogador ultimoAJogar = ((JogoDaVelha) jogador.getJogo()).getUltimoAJogar();
            String ultimaJogada = (String) ultimoAJogar.getJogadas().get(ultimoAJogar.getJogadas().size() - 1);

            linha = Integer.parseInt(ultimaJogada.split(",")[0]);
            coluna = Integer.parseInt(ultimaJogada.split(",")[1]);

            posicao = jogador.getJogo().getTabuleiro().transformarEmPosicao(linha, coluna);
            calculadorProximaJogadaIA.avancaUmaJogada(posicao);

            posicao = calculadorProximaJogadaIA.getMelhorJogada();

        }

        return posicao;

    }

    @Override
    public String getNomeResumido() {
        return "humanoVSComp";
    }

    @Override
    public int getVelocity(){
        return Integer.MIN_VALUE;
    }

}
