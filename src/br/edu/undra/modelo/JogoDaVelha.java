package br.edu.undra.modelo;

import br.edu.undra.MVC.JogoVelhaController;
import br.edu.undra.MVC.JogoVelhaModel;
import br.edu.undra.interfaces.MVC.Controller;
import br.edu.undra.modelo.jogo.Jogador;
import br.edu.undra.modelo.jogo.Jogo;
import br.edu.undra.modelo.jogo.Tabuleiro;
import br.edu.undra.modelo.versoes.AbstracaoVersaoJogoVelha;
import br.edu.undra.modelo.versoes.VersaoComputadorVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusComputadorImpl;
import br.edu.undra.modelo.versoes.VersaoHumanoVersusHumanoImpl;
import br.undra.calculadorproximajogada.impl.CalculadorProximaJogadaIAParaJogoVelhaImpl;
import br.undra.calculadorproximajogada.impl.CalculadorProximaJogadaSimplesParaJogoVelhaImpl;
import br.undra.calculadorproximajogada.interfaces.CalculadorProximaJogada;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Um jogo da velha
 *
 * @author alexandre
 * @param <T>
 */
public class JogoDaVelha<T extends Jogador> extends Jogo implements JogoVelhaModel, Readable {

    private JogoVelhaController controller;

    private String id;

    private JogadorJogoDaVelha jogador1;
    private JogadorJogoDaVelha jogador2;
    private String ondeVenceu = "";
    private List<Object> posicoesOndeVenceu = new ArrayList<>();

    private Set<T> ultimosAJogar = new HashSet<>();

    private String posicaoClicada = "0";

    private Jogador ultimoAJogar;

    //as tres versoes do jogo da velhas
    private AbstracaoVersaoJogoVelha compVSComp = new VersaoComputadorVersusComputadorImpl();
    private AbstracaoVersaoJogoVelha humanoVSComp = new VersaoHumanoVersusComputadorImpl();
    private AbstracaoVersaoJogoVelha humanoVSHumano = new VersaoHumanoVersusHumanoImpl();
    private AbstracaoVersaoJogoVelha abstracaoVersaoJogoVelha;

    //as duas impleentacoes do calculador da proxima jogada
    private CalculadorProximaJogada<Jogador> calculadorProximaJogadaSimples = new CalculadorProximaJogadaSimplesParaJogoVelhaImpl();
    private CalculadorProximaJogada<Jogador> calculadorProximaJogadaIA;

    private CalculadorProximaJogada calculadorProximaJogada;

    public int numIns = 1;

    public JogoDaVelha(String nome) {

        super();

        setUp(nome);

        Tabuleiro tabuleiro = new Tabuleiro(3);
        setTabuleiro(tabuleiro);

    }

    private void setUp(String nome) {

        jogador1 = new JogadorJogoDaVelha("jogador 1");
        jogador2 = new JogadorJogoDaVelha("jogador 2");

        List<JogadorJogoDaVelha> jogadores = Arrays.asList(jogador1, jogador2);
        setNome(nome);
        setJogadores(jogadores);

        setUpJogadores();

        abstracaoVersaoJogoVelha = humanoVSComp;
        calculadorProximaJogada = calculadorProximaJogadaSimples;

        setUpAbstracaoVersaoJogo();

    }

    private void setUpAbstracaoVersaoJogo() {
        calculadorProximaJogadaIA = new CalculadorProximaJogadaIAParaJogoVelhaImpl(abstracaoVersaoJogoVelha, this);
    }

    public JogoDaVelha(String nome, List<T> jogadores, Tabuleiro tabuleiro) {
        super(nome, jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        setUpJogadores();
    }

    public JogoDaVelha(List<T> jogadores, Tabuleiro tabuleiro) {
        super(jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        setUpJogadores();
    }

    public JogoDaVelha(String nome, String id, List<T> jogadores, Tabuleiro tabuleiro) {
        super(nome, jogadores, tabuleiro);
        if (jogadores.size() != 2) {
            throw new IllegalArgumentException("Devem haver EXATAMENTE 2 jogadores para o jogo da velha ok.");
        }
        this.id = id;
        setUpJogadores();
    }

    @Override
    public void setUpJogadores() {
        for (JogadorJogoDaVelha jogador : (List<JogadorJogoDaVelha>) getJogadores()) {
            jogador.setJogo(this);
        }
    }

    public JogadorJogoDaVelha getJogador1() {
        return jogador1;
    }

    public JogadorJogoDaVelha getJogador2() {
        return jogador2;
    }

    public String getOndeVenceu() {
        return ondeVenceu;
    }

    public List<Object> getPosicoesOndeVenceu() {
        return posicoesOndeVenceu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {

        String toString = "";

        toString += getNome();
        toString += "\n\n";

        String[] estados = getTabuleiro().getEstado().split((String) Tabuleiro.SEPARADOR);

        int coluna = 1;

        for (String estado : estados) {

            int valor = Integer.parseInt(estado.split(",")[2]);

            if (valor == 0) {
                toString += ". ";
            } else if (valor % 2 == 0) {
                toString += "o ";
            } else {
                toString += "x ";
            }

            if (coluna % getTabuleiro().getDimensao() == 0) {
                toString += "\n";
            }

            coluna++;

        }

        return toString;
    }

    public String getEstado() {

        String comoEstaOJogo = "";

        comoEstaOJogo += "\n";

        String[] estados = getTabuleiro().getEstado().split((String) Tabuleiro.SEPARADOR);

        int coluna = 1;

        for (String estado : estados) {

            int valor = Integer.parseInt(estado.split(",")[2]);

            if (valor == 0) {
                comoEstaOJogo += ". ";
            } else if (valor % 2 == 0) {
                comoEstaOJogo += "o ";
            } else {
                comoEstaOJogo += "x ";
            }

            if (coluna % getTabuleiro().getDimensao() == 0) {
                comoEstaOJogo += "\n";
            }

            coluna++;

        }

        return comoEstaOJogo;

    }

    JogadorJogoDaVelha proximoAJogar = null;

    public void setProximoAJogar(JogadorJogoDaVelha proximoAJogar) {
        this.proximoAJogar = proximoAJogar;
    }

    @Override
    public JogadorJogoDaVelha getProximoAJogar() {

        if (proximoAJogar != null) {

            if (!proximoAJogar.jogou()) {
                return proximoAJogar;
            }

            getUltimosAJogar().clear();

            for (JogadorJogoDaVelha j : (List<JogadorJogoDaVelha>) getJogadores()) {

                if (!j.equals(proximoAJogar)) {

                    proximoAJogar = j;
                    break;

                }
            }

        } else {

            for (JogadorJogoDaVelha j : (List<JogadorJogoDaVelha>) getJogadores()) {

                if (j.isPrimeiroAJogar()) {

                    proximoAJogar = j;

                }

            }

        }

        proximoAJogar.setElemento(proximoAJogar.getAtual());
        proximoAJogar.setAtual(proximoAJogar.getAtual() + 2);

        proximoAJogar.setJogou(false);

        return proximoAJogar;

    }

    /**
     * Devolve proxima jogada para um jogador.<br>
     * A String devolvida está no formato linha,coluna<br>
     * Por exemplo, uma proxima jogada pode ser a string "2,1". Nela,
     * <br>o primeiro valor, 2, corresponde à linha;
     * <br>o segundo valor, 1, corresponde à coluna;
     *
     * @param jogador o jogador
     * @return Uma string representando uma posição LIVRE do tabuleiro.
     */
    @Override
    public String getProximaJogadaParaJogador(Jogador jogador) {

        if (jogador.jogou()) {
            return null;
        }

        int[] proximaJogada = getCalculadorProximaJogada().calcularLinhaEColuna(jogador);

        return Integer.toString(proximaJogada[0]) + "," + Integer.toString(proximaJogada[1]);

    }

    @Override
    public Set getUltimosAJogar() {
        return this.ultimosAJogar;
    }

    public boolean jogadorVenceu(JogadorJogoDaVelha jogador) {

        boolean venceu = false;

        List<Object> elementos;

        //varre colunas procurando trinca
        for (int coluna = 1; coluna <= getTabuleiro().getDimensao(); coluna++) {

            elementos = getTabuleiro().getColuna(coluna);

            if (aoMenosUmaTrinca(elementos, jogador)) {
                venceu = true;
                ondeVenceu = "coluna " + coluna;
                posicoesOndeVenceu = getTabuleiro().getPosicoesDestesValores(elementos);
                break;
            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre linhas procurando trinca
            for (int linha = 1; linha <= getTabuleiro().getDimensao(); linha++) {

                elementos = getTabuleiro().getLinha(linha);

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "linha " + linha;
                    posicoesOndeVenceu = getTabuleiro().getPosicoesDestesValores(elementos);
                    break;
                }

            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre diagonal principal procurando trinca
            for (int i = 1; i <= getTabuleiro().getDimensao(); i++) {

                elementos = getTabuleiro().getDiagonalPrincipal();

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "diagonal principal";
                    posicoesOndeVenceu = getTabuleiro().getPosicoesDestesValores(elementos);
                    break;
                }

            }

        }

        if (!venceu) {//CONTINUA PROCURANDO TRINCA ...

            //varre diagonal secundaria procurando trinca
            for (int i = 1; i <= getTabuleiro().getDimensao(); i++) {

                elementos = getTabuleiro().getDiagonalSecundaria();

                if (aoMenosUmaTrinca(elementos, jogador)) {
                    venceu = true;
                    ondeVenceu = "diagonal secundaria";
                    posicoesOndeVenceu = getTabuleiro().getPosicoesDestesValores(elementos);
                    break;
                }

            }

        }

        return venceu;
    }

    public boolean aoMenosUmaTrinca(List<Object> elementos, JogadorJogoDaVelha jogador) {

        boolean aoMenosUmaTrinca = true;

        if (jogador.isPrimeiroAJogar()) {
            //procura por apenas IMPARES
            for (Object e : elementos) {
                if (((Integer) e) % 2 == 0) {
                    aoMenosUmaTrinca = false;
                    break;
                }
            }

        } else {

            //procura por apenas PARES, DIFERENTES DE MARCADOR POSICAO_LIVRE
            for (Object e : elementos) {
                if (Objects.equals((Integer) e, (Integer) Tabuleiro.POSICAO_LIVRE) || (((Integer) e) % 2 != 0)) {
                    aoMenosUmaTrinca = false;
                    break;
                }
            }

        }

        return aoMenosUmaTrinca;
    }

    @Override
    public void iniciar() {

        System.err.println(getAbstracaoVersaoJogoVelha().getVersao());

        Object[] args = new Object[1];

        args[0] = getAbstracaoVersaoJogoVelha().getVersao().toUpperCase();

        controller.updateView("setMensagem", args);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }

        getJogador1().setPrimeiroAJogar(true);

        getAbstracaoVersaoJogoVelha().SetUp(this);

        while (!terminou()) {

            getAbstracaoVersaoJogoVelha().jogar(this);

        }

        args = new Object[2];

        Object[] args2 = new Object[1];

        if (getJogador1().venceu()) {

            args[0] = getPosicoesOndeVenceu();

            controller.updateView("marcarOndeVenceu", args);

            args2[0] = (getJogador1().getNome() + " VENCEU na " + getOndeVenceu()).toUpperCase();

        } else if (getJogador2().venceu()) {

            args[0] = getPosicoesOndeVenceu();

            controller.updateView("marcarOndeVenceu", args);

            args2[0] = (getJogador2().getNome() + " VENCEU na " + getOndeVenceu()).toUpperCase();

        } else {

            args2[0] = " >>>>>>>>>>>>>> EMPATOU <<<<<<<<<<<<<<<";

        }

        controller.updateView("setMensagem", args2);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }

        reiniciar();

    }

    public Jogador getUltimoAJogar() {
        return ultimoAJogar;
    }

    public void updateView(Jogador jogador) {

        ultimoAJogar = jogador;

        Object[] args = new Object[2];

        Object[] args2 = new Object[1];

        args2[0] = (jogador.getNome() + " jogou").toUpperCase();
        controller.updateView("setMensagem", args2);

        for (int posicao = 1; posicao <= getTabuleiro().getDimensao() * getTabuleiro().getDimensao(); posicao++) {

            args[0] = getTabuleiro().get(posicao);
            args[1] = posicao;

            controller.updateView("set", args);

        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }

        args2[0] = ("-------------------------").toUpperCase();
        controller.updateView("setMensagem", args2);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reiniciar() {

        calculadorProximaJogadaIA.reconfigurar();

        ultimoAJogar = null;
        proximoAJogar = null;

        Object[] args = new Object[1];

        args[0] = "REINICIANDO O JOGO... " + new Date();

        controller.updateView("setMensagem", args);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }
        ondeVenceu = "";
        posicoesOndeVenceu = new ArrayList<>();
        ultimosAJogar = new HashSet<>();

        jogador1.reconfigurar();

        args[0] = jogador1.getNome() + " reconficurado";
        controller.updateView("setMensagem", args);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }

        jogador2.reconfigurar();
        args[0] = jogador2.getNome() + " reconficurado";
        controller.updateView("setMensagem", args);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
        }

        jogador1.setPrimeiroAJogar(true);

        args = new Object[3];

        Object[] args2 = new Object[1];

        for (int i = 1; i <= getTabuleiro().getDimensao() * getTabuleiro().getDimensao(); i++) {

            getTabuleiro().set(Tabuleiro.POSICAO_LIVRE, i);
            args[0] = getTabuleiro().get(i);
            args[1] = i;
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDaVelha.class.getName()).log(Level.SEVERE, null, ex);
            }
            controller.updateView("set", args);
            args2[0] = ("setting " + args[0] + " at " + args[1]).toUpperCase();
            controller.updateView("setMensagem", args2);

        }

        controller.updateView("reconfigurar", null);

        iniciar();

    }

    @Override
    public boolean terminou() {
        return jogador1.venceu() || jogador2.venceu() || getTabuleiro().getPosicoesLivres().isEmpty();
    }

    /**
     * Recupera uma implementacao da lógica de calculo de uma proxima
     * jogada.<br>
     * Ou se calcula aleatoriamente a proxima jogada, ou se usa analise
     * combinatoria.<br>
     * Podem ser uma das implementações:<br>
     * CalculadorProximaJogadaIAParaJogoVelhaImpl<br>
     * CalculadorProximaJogadaSimplesParaJogoVelhaImpl
     *
     * @return Uma implementacao da lógica de calculo de uma proxima jogada
     */
    public CalculadorProximaJogada getCalculadorProximaJogada() {
        return calculadorProximaJogada;
    }

    /**
     * Seta uma implementacao da lógica de calculo de uma proxima jogada.<br>
     * Ou se calcula aleatoriamente a proxima jogada, ou se usa analise
     * combinatoria.<br>
     * Podem ser uma das implementações:<br>
     * CalculadorProximaJogadaIAParaJogoVelhaImpl<br>
     * CalculadorProximaJogadaSimplesParaJogoVelhaImpl
     *
     * @param nomeCalculadorProximaJogada uma string representando instancia de
     * CalculadorProximaJogada
     */
    @Override
    public void setCalculadorProximaJogada(String nomeCalculadorProximaJogada) {

        if (nomeCalculadorProximaJogada.equals(calculadorProximaJogadaSimples.getNomeSimples())) {

            lastCalculadorSetado = calculadorProximaJogadaSimples;

        } else if (nomeCalculadorProximaJogada.equals(calculadorProximaJogadaIA.getNomeSimples())) {

            lastCalculadorSetado = calculadorProximaJogadaIA;

        }

        if (this.calculadorProximaJogada != lastCalculadorSetado) {

            this.calculadorProximaJogada = lastCalculadorSetado;

        } else {

        }

    }

    private CalculadorProximaJogada lastCalculadorSetado = null;

    public AbstracaoVersaoJogoVelha getAbstracaoVersaoJogoVelha() {
        return abstracaoVersaoJogoVelha;
    }

    /**
     * Uma implementacao da abstração das versoes do jogo da velha. <br>
     * Versões podem ser : <br>
     * Computador versus computador<br>
     * Humano versus computador.<br>
     * Humano versus humano.
     *
     * @param versao
     */
    @Override
    public void setVersaoJogoVelha(String versao) {

        if (versao.equals(compVSComp.getNomeResumido())) {
            abstracaoVersaoJogoVelha = compVSComp;
        } else if (versao.equals(humanoVSComp.getNomeResumido())) {
            abstracaoVersaoJogoVelha = humanoVSComp;
        } else if (versao.equals(humanoVSHumano.getNomeResumido())) {
            abstracaoVersaoJogoVelha = humanoVSHumano;
        }

        try {
            if (!(abstracaoVersaoJogoVelha instanceof VersaoHumanoVersusHumanoImpl)) {
                ((CalculadorProximaJogadaIAParaJogoVelhaImpl) calculadorProximaJogadaIA).setAbstracaoVersaoJogoVelha(abstracaoVersaoJogoVelha);
            }
        } catch (Exception e) {
        }

        abstracaoVersaoJogoVelha.SetUp(this);
//        System.err.println("\n\nMUDANÇA DE VERSÃO PARA " + abstracaoVersaoJogoVelha.getVersao());
    }

    @Override
    public void setVelocity(int novoValor) {
        abstracaoVersaoJogoVelha.setVelocity(novoValor);
    }

    @Override
    public void play(Object[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (JogoVelhaController) controller;
    }

    @Override
    public Collection<Method> getMethods() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int read(CharBuffer cb) throws IOException {
        cb.put(getPosicaoClicada());
        return cb.length();
    }

    public String getPosicaoClicada() {
        return posicaoClicada;
    }

    @Override
    public void setPosicaoClicada(String posicao) {
        this.posicaoClicada = posicao;
    }

    @Override
    public void liberarJogada() {
        abstracaoVersaoJogoVelha.liberarJogada();
    }

    public boolean isConficurado() {
        return ((CalculadorProximaJogadaIAParaJogoVelhaImpl) calculadorProximaJogadaIA).isConfigurado;
    }

}
