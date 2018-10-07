package br.edu.undra.modelo.versoes;

import br.edu.undra.modelo.JogoDaVelha;
import java.util.Scanner;

/**
 * Uma implementação das versoes do jogo da velha. <br>
 * Versão Humano versus computador
 *
 * @author alexandre
 */
public class VersaoHumanoVersusComputadorImpl implements AbstracaoVersaoJogoVelha {

    Scanner s;

    @Override
    public void jogar(JogoDaVelha jogo) {
        
        jogo.getProximoAJogar().joga();
        
        if (jogo.terminou()) {
            return;
        }

        System.err.println(jogo.getEstado());

        System.err.println("Digite uma posicao (1 a 9)");

        int posicao = s.nextInt();

        while (!jogo.getProximoAJogar().joga(posicao)) {

            System.err.println("\n" + posicao + " É POSIÇÃO INVÁLIDA! VÁLIDAS SÃO ENTRE 1 e 9 E DESOCUPADAS...");
            System.out.println("\nSUA VEZ DE JOGAR... digite a posicao (1 a 9) ");

            posicao = s.nextInt();

        }

    }

    @Override
    public String getVersao() {
        return "Versão Humano versus computador";
    }

    @Override
    public void SetUp(JogoDaVelha jogo) {

        jogo.getJogador1().setNome("Computador");
        jogo.getJogador2().setNome("Humano");

        s = new Scanner(System.in);

    }

}
