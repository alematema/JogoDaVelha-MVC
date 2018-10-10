package br.edu.undra.servicos.calculadorproximajogada.analisador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import com.rits.cloning.Cloner;
public class Jogador {

    private JogoDaVelhaWrapped jogoDaVelha;
    private int proxima;
    private int anterior;
    private JogadorComecaOJogo jogadorComecaOJogo;
    private boolean isVezDeJogar;
    private int ultimaPosicao;
    private int ultimoValor;
    private Jogador oponente;
    private List<Integer> posicoesLivres = new ArrayList<>();
    private List<Integer> posicoesEmQuePerde = new ArrayList<>();
    private List<Integer> posicoesEmQueNaoPerde = new ArrayList<>();
    private Map<Integer, Double> probabilidades = new HashMap<>();
    private boolean isPrimeiraJogada = Boolean.TRUE;
    private Analisador analisador;
    private List<JogoDaVelhaWrapped> espaco = new ArrayList<>();
    private Boolean verbose = Boolean.FALSE;
    private CalculadorProximaJogadaIA calculadorProximaJogada;

    /**
     * @return the posicoesLivres
     */
    public List<Integer> getPosicoesLivres() {
        posicoesLivres.clear();
        for (int i = 0; i < this.getJogoDaVelha().getBaseCorrente().size(); i++) {
            if (this.getJogoDaVelha().getBaseCorrente().get(i) == 0) {
                posicoesLivres.add(i);
            }
        }
        return posicoesLivres;
    }

    /**
     * @param posicoesLivres the posicoesLivres to set
     */
    public void setPosicoesLivres(List<Integer> posicoesLivres) {
        this.posicoesLivres = posicoesLivres;
    }

    /**
     * @return the espaco
     */
    public List<JogoDaVelhaWrapped> getEspaco() {
        return espaco;
    }

    /**
     * @param espaco the espaco to set
     */
    public void setEspaco(List<JogoDaVelhaWrapped> espaco) {
        this.espaco = espaco;
    }

    public Jogador(JogoDaVelhaWrapped jogoDaVelha, JogadorComecaOJogo comecaOJogo, Analisador analisador) {
        this(jogoDaVelha, comecaOJogo);
        this.analisador = analisador;
    }

    public Jogador(JogoDaVelhaWrapped jogoDaVelha, JogadorComecaOJogo comecaOJogo, Analisador analisador, CalculadorProximaJogadaIA calculadorProximaJogada) {
        this(jogoDaVelha, comecaOJogo, analisador);
        this.calculadorProximaJogada = calculadorProximaJogada;
    }

    public Jogador(JogoDaVelhaWrapped jogoDaVelha, JogadorComecaOJogo comecaOJogo) {

        this.jogoDaVelha = jogoDaVelha;

        if (JogadorComecaOJogo.SIM.equals(comecaOJogo)) {
            proxima = 1;
            isVezDeJogar = Boolean.TRUE;
        } else {
            isVezDeJogar = Boolean.FALSE;
            proxima = 2;
        }

        this.jogadorComecaOJogo = comecaOJogo;

        proxima -= 2;

    }

    /**
     * @return the oponente
     */
    public Jogador getOponente() {
        return oponente;
    }

    /**
     * @param oponente the oponente to set
     */
    public void setOponente(Jogador oponente) {
        this.oponente = oponente;
    }

    /**
     * @return the jogoDaVelha
     */
    public JogoDaVelhaWrapped getJogoDaVelha() {
        return jogoDaVelha;
    }

    /**
     * Setter para logica de clone em Analisador
     */
    public void getJogoDaVelha(JogoDaVelhaWrapped jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
    }

    /**
     * @return the ultimoValor
     */
    public int getUltimoValor() {
        return ultimoValor;
    }

    /**
     * @param ultimoValor the ultimoValor to set
     */
    public void setUltimoValor(int ultimoValor) {
        this.ultimoValor = ultimoValor;
    }

    /**
     * @return the ultimaPosicao
     */
    public int getUltimaPosicao() {

        int indexOf = 0;
        for (int i = 0; i < this.getJogoDaVelha().getBaseCorrente().size(); i++) {
            if (this.getJogoDaVelha().getBaseCorrente().get(i) == getAnterior()) {
                ultimaPosicao = i;
            }
        }

        return ultimaPosicao;
    }

    /**
     * @param ultimaPosicao the ultimaPosicao to set
     */
    public void setUltimaPosicao(int ultimaPosicao) {
        this.ultimaPosicao = ultimaPosicao;
    }

    /**
     * @return the isVezDeJogar
     */
    public boolean isVezDeJogar() {
        return isVezDeJogar;
    }

    /**
     * @param isVezDeJogar the isVezDeJogar to set
     */
    public void setVezDeJogar(boolean isVezDeJogar) {
        this.isVezDeJogar = isVezDeJogar;
    }

//	public boolean getMelhorPosicaoEJogaNela(int posicao, int valor){
//		//do range check ... nao implementado por motivos de se querer generalizar o tabuleiro para NxN
//		if(!isPosicaoLivre(posicao)) return false;
//		return this.jogoDaVelha.naPosicao(posicao).setValor(valor).jogar();
//	}
    public int getMelhorPosicaoEJogaNela() {

        if (this.venceu()) {
            return 1;
        }

        int melhorPosicao = -1;
        double maiorProbabilidade = 0.0;

        if (isPrimeiraJogada()) {

            Random rdn = new Random(System.currentTimeMillis());
            melhorPosicao = rdn.nextInt(9);

        } else {

            //conhece todas posicoes livres
            updatePosicoesLivres();

            if (verbose) {
                System.out.print("\tPOSICOES LIVRES ");
                this.posicoesLivres.forEach(p -> System.out.print((p + 1) + ","));
                String posicoesLivres = "";

                calculadorProximaJogada.setMensagemConfigurador("POSICOES LIVRES ");
                System.out.println("");
                System.out.println("");
            }

            //conhece posicoes em que perde
            atualizaPosicoesEmQuePerderiaSeJogasseNelas();

            if (verbose) {
                System.out.print("\tPOSICOES EM QUE PERDERIA SE JOGASSE ");
                calculadorProximaJogada.setMensagemConfigurador("POSICOES EM QUE PERDERIA SE JOGASSE ");
                if (this.posicoesEmQuePerde.isEmpty()) {
                    System.out.print(": NENHUMA");
                    calculadorProximaJogada.setMensagemConfigurador(": NENHUMA ");
                } else {
                    this.posicoesEmQuePerde.forEach(p -> System.out.print((p + 1) + ","));
                }
                System.out.println("");
                System.out.println("");
            }

            //conhece posicoes em que NAO perde E PODE ganhar
            atualizaPosicoesLivresNasQuaisNaoPerderiaSeJogasseNelas();

            if (verbose) {
                System.out.print("\tPOSICOES LIVRES EM QUE NÃO PERDE E PODE GANHAR SE JOGAR ");
                this.posicoesLivres.forEach(p -> System.out.print((p + 1) + ","));
                System.out.println("");
                System.out.println("");
            }

            maiorProbabilidade = 0.0;

            if (!this.posicoesLivres.isEmpty()) {

                if (verbose) {
                    System.out.print("\tCALCULANDO PROBALIDADES (tamanho do espaco # " + getEspaco().size() + " )");
                    System.out.println("");
                    System.out.println("");
                    calculadorProximaJogada.setMensagemConfigurador("CALCULANDO PROBALIDADES (tamanho do espaco # " + getEspaco().size() + " ) ");
                }

                for (int i = 0; i < this.posicoesLivres.size(); i++) {

                    if (jogasseNessaPosicaoGanharia(this.posicoesLivres.get(i))) {
                        melhorPosicao = this.posicoesLivres.get(i);
                        maiorProbabilidade = 1.0;
                        break;
                    } else {// calcula melhor posicao ... a que tera MAIOR PROBABILIDADE DE VENCER SE JOGAR NELA

                        double proba = analisador.noEspaco(getEspaco()).depoisQueJogador(this).jogarNaPosicao(this.posicoesLivres.get(i)).doJogo(getJogoDaVelha()).getProbabilidadeDeVencer();
                        
                        if (verbose) {
                            calculadorProximaJogada.setMensagemConfigurador("CALCULANDO PROBALIDADES (tamanho do espaco # " + getEspaco().size() + " ) ");

                            String probaDeGanharEmI = "PROBABILIDADE  " + (100 * proba) + " DE GANHAR, JOGANDO NA POSIÇAO " + (this.posicoesLivres.get(i) + 1);
                            calculadorProximaJogada.setMensagemConfigurador(probaDeGanharEmI);
                            System.out.printf("\tPROBABILIDADE  %.2f  DE GANHAR, JOGANDO NA POSIÇAO %d \n", 100 * proba, this.posicoesLivres.get(i) + 1);
                        }

                        if (proba >= maiorProbabilidade) {
                            maiorProbabilidade = proba;
                            melhorPosicao = this.posicoesLivres.get(i);
                        }
                        
                    }
                }
            } else {
                melhorPosicao = posicoesEmQuePerde.get(0);
            }

        }

        if (verbose) {
            System.out.println("\n\n\tMELHOR POSICAO  " + (melhorPosicao + 1));
            System.out.printf("\tMAIOR PROBABILIDADE %.2f \n", 100 * maiorProbabilidade);
            System.out.println("\n\n--------------------------------------------------------");

            calculadorProximaJogada.setMensagemConfigurador("MELHOR POSICAO  " +  (melhorPosicao + 1));

            String probaDeGanharEmI = "MAIOR PROBABILIDADE  " + (100 * maiorProbabilidade);
            calculadorProximaJogada.setMensagemConfigurador(probaDeGanharEmI);

        }

        setEspaco(analisador.noEspaco(getEspaco()).depoisQueJogador(this).jogarNaPosicao(melhorPosicao).doJogo(getJogoDaVelha()).recalcularEspaco());

        getOponente().setEspaco(getEspaco());

        getJogoDaVelha().getTabuleiro().comMascara().descreve();

        setVezDeJogar(Boolean.FALSE);
        getOponente().setVezDeJogar(Boolean.TRUE);

        return melhorPosicao;
    }

    /**
     *
     */
    private void atualizaPosicoesLivresNasQuaisNaoPerderiaSeJogasseNelas() {
        for (int i = 0; i < this.posicoesEmQuePerde.size(); i++) {
            posicoesLivres.remove(posicoesEmQuePerde.get(i));
        }//fim
    }

    /**
     *
     */
    private void atualizaPosicoesEmQuePerderiaSeJogasseNelas() {
        posicoesEmQuePerde.clear();
        for (int i = 0; i < this.getPosicoesLivres().size(); i++) {
            if (analisador.jogadorPerderiaSeJogasseAqui(getPosicoesLivres().get(i), this, this.getOponente())) {
                posicoesEmQuePerde.add(getPosicoesLivres().get(i));
            }
        }//fim
    }

    /**
     *
     */
    private void updatePosicoesLivres() {
        this.posicoesLivres.clear();
        for (int i = 0; i < getJogoDaVelha().getBaseCorrente().size(); i++) {
            if (getJogoDaVelha().getBaseCorrente().get(i) == 0) {
                posicoesLivres.add(i);
            }
        }//fim
    }

    private boolean jogasseNessaPosicaoGanharia(int posicao) {

        boolean ganha = false;

        this.jogaNaPosicao(posicao);

        if (this.venceu()) {
            ganha = Boolean.TRUE;
        }

        this.desfazerJogada();

        return ganha;
    }

    private boolean isPrimeiraJogada() {

        this.isPrimeiraJogada = Boolean.TRUE;

        for (int i = 0; i < getJogoDaVelha().getBaseCorrente().size(); i++) {
            if (getJogoDaVelha().getBaseCorrente().get(i) != 0) {
                isPrimeiraJogada = Boolean.FALSE;
                break;
            }
        }

        return isPrimeiraJogada;
    }

    private boolean isSegundaJogada() {

        int isSegunda = 0;

        for (int i = 0; i < getJogoDaVelha().getBaseCorrente().size(); i++) {
            if (getJogoDaVelha().getBaseCorrente().get(i) != 0) {
                isSegunda++;
            }
        }
        return isSegunda == 2;
    }

    public boolean jogaContraComputador(int posicao) {

        List<JogoDaVelhaWrapped> espacoRecalc = analisador.noEspaco(getEspaco()).depoisQueJogador(this).jogarNaPosicao(posicao).doJogo(getJogoDaVelha()).recalcularEspaco();

        if (espacoRecalc == null) {
            return false;
        } else {
            setEspaco(espacoRecalc);

            getOponente().setEspaco(getEspaco());

            setVezDeJogar(Boolean.FALSE);
            getOponente().setVezDeJogar(Boolean.TRUE);

            return true;
        }

    }
    
    public boolean joga(int posicao) {

        List<JogoDaVelhaWrapped> espacoRecalc = analisador.noEspaco(getEspaco()).depoisQueJogador(this).jogarNaPosicao(posicao).doJogo(getJogoDaVelha()).recalcularEspaco();

        if (espacoRecalc == null) {
            return false;
        } else {
            setEspaco(espacoRecalc);

            getOponente().setEspaco(getEspaco());

            setVezDeJogar(Boolean.FALSE);
            getOponente().setVezDeJogar(Boolean.TRUE);

            return true;
        }

    }

    public boolean jogaNaPosicao(int posicao) {
        //do range check ... nao implementado por motivos de se querer generalizar o tabuleiro para NxN
        if (!(0 <= posicao && posicao <= 8)) {
            return false;
        }
        if (!isPosicaoLivre(posicao)) {
            return false;
        }
        if (!isJogandoCerto()) {
            return false;
        }
        boolean jogou = this.jogoDaVelha.naPosicao(posicao).setValor(getProxima()).jogar();
        if (jogou) {
            setVezDeJogar(Boolean.FALSE);
            //setting para logica do desfazer jogada
            setUltimaPosicao(this.jogoDaVelha.getUltimaPosicaoJogada());
            setUltimoValor(this.jogoDaVelha.getPenultimoValorJogado());
        }
        return jogou;
    }

    public boolean isJogandoCerto() {

        boolean isJogandoCerto = Boolean.TRUE;

        //1,3,5,7,9
        //2,4,6,8
        // se eh impar : 0,0,0,0
        // se eh impar : 1,0,0,0
        // se eh impar : 1,2,3,4,5
        if (this.jogadorComecaOJogo.equals(JogadorComecaOJogo.SIM)) {

            int maiorPar = 0;
            for (int i = 0; i < this.getJogoDaVelha().getBaseCorrente().size(); i++) {
                if (this.getJogoDaVelha().getBaseCorrente().get(i) % 2 == 0 && this.getJogoDaVelha().getBaseCorrente().get(i) > maiorPar) {
                    maiorPar = this.getJogoDaVelha().getBaseCorrente().get(i);
                }
            }
            int proximo = getProximoValorASerJogado();
            proximo--;
            isJogandoCerto = (maiorPar == proximo);

        } else {

            //1,3,5,7,9
            //2,4,6,8
            // se eh par : 0,0,0,0
            // se eh par : 1,0,0,0
            // se eh par : 1,2,0,0
            int maiorImpar = -1;
            for (int i = 0; i < this.getJogoDaVelha().getBaseCorrente().size(); i++) {
                if (this.getJogoDaVelha().getBaseCorrente().get(i) % 2 == 1 && this.getJogoDaVelha().getBaseCorrente().get(i) > maiorImpar) {
                    maiorImpar = this.getJogoDaVelha().getBaseCorrente().get(i);
                }
            }

            int proximo = getProximoValorASerJogado();
            proximo--;
            isJogandoCerto = (maiorImpar == proximo);

        }

        return isJogandoCerto;
    }

    public boolean desfazerJogada() {
        if (this.jogoDaVelha.desfazerJogada(getUltimaPosicao(), 0)) {
            this.proxima -= 2;//volta
            //setUltimoValor(this.proxima);
            return true;
        }
        return false;
    }

    public boolean podeJogarNessaPosicao(int posicao) {
        return this.getJogoDaVelha().isPosicaoLivre(posicao);
    }

    public boolean isPosicaoLivre(int posicao) {
        return this.getJogoDaVelha().isPosicaoLivre(posicao);
    }

    /**
     * @return the proxima
     */
    private int getProxima() {
        proxima += 2;
        return proxima;
    }

    /**
     * @return the proxima
     */
    private int getAnterior() {
        anterior = proxima;
        return anterior;
    }

    public int getProximoValorASerJogado() {
        int proxima = this.proxima + 2;
        return proxima;
    }

    public int getAnteriorValorJogado() {
        return getAnterior();
    }

    public Analisador getAnalisador() {
        return analisador;
    }

//	public Jogador getClone(){
//		
//		Cloner cloner = new Cloner();
//		return cloner.deepClone(this);
//		
//		
//	}
    public boolean venceu() {

        boolean venceu = false;

        if (this.jogadorComecaOJogo.equals(JogadorComecaOJogo.SIM)) {
            venceu = this.getJogoDaVelha().getTabuleiro().primeiroJogadorFezAlgumaTrinca();
        } else {
            venceu = this.getJogoDaVelha().getTabuleiro().segundoJogadorFezAlgumaTrinca();
        }

        return venceu;
    }

    public void verboseOff() {
        this.verbose = Boolean.FALSE;
    }

    public void verboseOn() {
        this.verbose = Boolean.TRUE;
    }

    public Jogador() {
    }

}
