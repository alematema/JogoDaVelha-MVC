package br.undra.calculadorproximajogada;

/**
 *
 * @author alexandre
 */
public class CalculadorProximaJogada {

    JogoDaVelhaVersaoHumanoVersusMaquina versusMaquina;

    public CalculadorProximaJogada() {

    }

    public void avancaUmaJogada(int posicao) {
        versusMaquina.avancaUmaJogada(posicao);
    }

    public int getProximaJogada() {
        return versusMaquina.getProximaJogada();
    }

    public static void main(String[] args) {
        new CalculadorProximaJogada().configura();
    }

    private void configura() {
        String[] args = {"-v"};
        versusMaquina = new JogoDaVelhaVersaoHumanoVersusMaquina(args);
        versusMaquina.configura();
    }

}
