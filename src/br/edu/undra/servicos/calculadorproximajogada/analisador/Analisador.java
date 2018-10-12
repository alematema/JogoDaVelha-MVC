package br.edu.undra.servicos.calculadorproximajogada.analisador;

import br.edu.undra.servicos.caching.JogadasJogoVelhaCache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Analisador {

    private GeradorDeJogosDaVelha geradorDeJogosDaVelha;
    private List<String> bases;
    List<Integer> ultimasJogadasAnalisadas = new ArrayList<>();
    List<JogoDaVelhaWrapped> possibilidades;
    List<Integer> ultimasJogadassAnalisadasFake = new ArrayList<>();
    List<Integer> baseCorrenteFake = new ArrayList<>();
    private int jogandoNaPosicao;
    private int proximaJogada;
    private List<JogoDaVelhaWrapped> possibilidadesRemanescentes;
    private List<JogoDaVelhaWrapped> espaco;
    private List<JogoDaVelhaWrapped> espacoTotal;
    private Jogador jogador;
    private int posicao;
    private JogoDaVelhaWrapped jogoDaVelha;
    private double maiorProbabilidade = 0.0;
    private int melhorPosicao = 0;
    private Boolean verbose = Boolean.FALSE;
    private List<Integer> ondePerderia = new ArrayList<>();
    private List<Integer> ondeNaoPerderia = new ArrayList<>();

    private JogadasJogoVelhaCache cache = new JogadasJogoVelhaCache();

    /**
     * @return the espacoTotal
     */
    public List<JogoDaVelhaWrapped> getEspacoTotal() {
        return espacoTotal;
    }

    public Analisador(boolean verbose, CalculadorProximaJogadaIA calculadorProximaJogada) {
        geradorDeJogosDaVelha = new GeradorDeJogosDaVelha(calculadorProximaJogada);
        setVerbose(verbose);
        geradorDeJogosDaVelha.setVerbose(verbose);
        geradorDeJogosDaVelha.gerarTodosJogos();
        System.err.println("Banco de jogos criado [ok]");
        System.err.println("Ordenando o Banco de jogos ...");
        if (calculadorProximaJogada != null) {
            calculadorProximaJogada.setMensagemConfigurador("Banco de jogos criado [ok]");
        }
        if (calculadorProximaJogada != null) {
            calculadorProximaJogada.setMensagemConfigurador("Ordenando o Banco de jogos ...");
        }
        bases = new ArrayList<>(geradorDeJogosDaVelha.getJogos().keySet());
        Collections.sort(bases);
        System.err.println("Banco de jogos ordenado [ok]");
        if (calculadorProximaJogada != null) {
            calculadorProximaJogada.setMensagemConfigurador("Banco de jogos ordenado [ok]");
        }

        this.espacoTotal = new ArrayList<>(geradorDeJogosDaVelha.getJogos().values());

    }

    public void clean() {
        ultimasJogadasAnalisadas.clear();
        indiceUltimasJogadas.clear();
    }

    private void zerar() {
        possibilidades = null;
        ultimasJogadassAnalisadasFake = new ArrayList<>();
        baseCorrenteFake = new ArrayList<>();
    }

    public boolean jogadorPerderiaSeJogasseAqui(int posicao, Jogador jogador, Jogador oponente) {

        boolean jogou = jogador.jogaNaPosicao(posicao);// simula jogar na
        // posicao

        if (!jogou) {
            System.out.println("Não foi possível jogar na posição " + posicao + "/ " + jogador.getClass().getName());
            //throw new RuntimeException("Não foi possível jogar na posição " + posicao);
        }

        boolean perderia = false;

        if (jogador.venceu()) {
            jogador.desfazerJogada();
            return false;
        }

        boolean oponenteGanha = false;

        for (int i = 0; i < oponente.getJogoDaVelha().getBaseCorrente().size(); i++) {

            if (oponente.podeJogarNessaPosicao(i)) {

                oponente.jogaNaPosicao(i);

                if (oponente.venceu()) {
//					System.out.print(" quarta " + oponente.getJogoDaVelha().getBaseCorrente().get(i));
                    oponente.desfazerJogada();
                    oponenteGanha = Boolean.TRUE;
                    break;
                }

                oponente.desfazerJogada();

            }
        }

        perderia = oponenteGanha;

        jogador.desfazerJogada();

        return perderia;
    }

    public boolean oponenteVenceriaNaProximaJogada(Jogador atual, Jogador oponente, JogoDaVelhaWrapped jogoDaVelha,
            int jogandoNaPosicao, int proximaJogada, List<JogoDaVelhaWrapped> espaco) {

        boolean venceria = false;

//		Jogador atualClone = atual.getClone();// deep copy
//		Jogador oponenteClone = oponente.getClone();// deep copy
        return venceria;

    }

    public double getProbabilidadeDeVencer(JogoDaVelhaWrapped jogoDaVelha, int jogandoNaPosicao, int daProximaJogada,
            List<JogoDaVelhaWrapped> espaco) {

        long init = System.currentTimeMillis();
        
        this.setJogandoNaPosicao(jogandoNaPosicao);
        this.setProximaJogada(daProximaJogada);

        this.zerar();

        setEspaco(espaco);
        setPossibilidadesRemanescentes(
                this.getPossibilidades(jogoDaVelha, getEspaco(), getJogandoNaPosicao(), getProximaJogada()));

        int quantosJogosGanhamNoEspacoRemanascente = 0;
        for (JogoDaVelhaWrapped jogo : getPossibilidadesRemanescentes()) {
            if (jogo.jogadorVenceriaComEssaJogada(getProximaJogada())) {
                quantosJogosGanhamNoEspacoRemanascente++;
            }
        }

        double probabilidade = (double) quantosJogosGanhamNoEspacoRemanascente
                / (double) getPossibilidadesRemanescentes().size();

//        System.out.println("Analisador.getProbabilidadeDeVencer levou " + (System.currentTimeMillis() - init) + " ms para analisar "+ getPossibilidadesRemanescentes().size() +" jogadas");
        
        return probabilidade;
    }

    public List<JogoDaVelhaWrapped> getPossibilidades(JogoDaVelhaWrapped jogoDaVelha, List<JogoDaVelhaWrapped> jogos, int posicao,
            int daProximaJogada) {

        setUpParaPrevisao(jogoDaVelha, jogos, posicao, daProximaJogada);

        List<Integer> baseCorrente = baseCorrenteFake;
        int i = 0;
        i = getPosicaoUltimaJogadaNaoAnalisadaParaPrevisao(baseCorrente);

        if (isPrimeiraJogada(possibilidades)) {

            selecionarPossibilidades(possibilidades, baseCorrente, i);

        } else {// ha pelo menos uma jogada analisada

            List<JogoDaVelhaWrapped> jogosQueNaoSaoCaminho = new ArrayList<>();

            for (JogoDaVelhaWrapped jogo : possibilidades) {

                selecionarImpossibilidadesParaPrevisao(baseCorrente, i, jogosQueNaoSaoCaminho, jogo);

            }

            selecionarPossibilidades(possibilidades, jogosQueNaoSaoCaminho);

        }

        return possibilidades;
    }

    /**
     * @param jogoDaVelha
     * @param jogos
     * @param posicao
     * @param daProximaJogada
     */
    private void setUpParaPrevisao(JogoDaVelhaWrapped jogoDaVelha, List<JogoDaVelhaWrapped> jogos, int posicao, int daProximaJogada) {

//        if (verbose) {
//            System.out.println("\n" + daProximaJogada);
//            System.out.printf("\n\t%d JOGADO NA POSICAO  %d\n", daProximaJogada, posicao);
//            // System.out.println("\tVALOR JOGADO " + daProximaJogada);
//        }
        if (possibilidades == null) {
            possibilidades = new ArrayList<>(jogos);
        }
        for (int i = 0; i < ultimasJogadasAnalisadas.size(); i++) {
            ultimasJogadassAnalisadasFake.add(i, ultimasJogadasAnalisadas.get(i));
        }
        for (int i = 0; i < jogoDaVelha.getBaseCorrente().size(); i++) {
            baseCorrenteFake.add(i, jogoDaVelha.getBaseCorrente().get(i));
        }
        baseCorrenteFake.set(posicao, daProximaJogada);
    }

    /**
     * @param baseCorrente
     * @param i
     * @param osQueNaoFazemMatch
     * @param jogo
     */
    private void selecionarImpossibilidadesParaPrevisao(List<Integer> baseCorrente, int i,
            List<JogoDaVelhaWrapped> osQueNaoFazemMatch, JogoDaVelhaWrapped jogo) {
        boolean matches = true;
        int j = 0;
        for (j = 0; j < ultimasJogadassAnalisadasFake.size(); j++) {

            int index = baseCorrente.indexOf(ultimasJogadassAnalisadasFake.get(j));
            matches = matches && jogo.getTabuleiro().getBase().get(index) == baseCorrente.get(index);

        }

        if (isJogoUmCaminho(baseCorrente, i, jogo, matches)) {
        } else {
            osQueNaoFazemMatch.add(jogo);
        }
    }

    /**
     * @param baseCorrente
     * @return
     */
    private int getPosicaoUltimaJogadaNaoAnalisadaParaPrevisao(List<Integer> baseCorrente) {
        int i;
        for (i = 0; i < baseCorrente.size(); i++) {
            if (isUltimaJogadaNaoAnalisadaParaPrevisao(baseCorrente, i)) {
                break;
            }
        }
        return i;
    }

    /**
     * @param baseCorrente
     * @param i
     * @return
     */
    private boolean isUltimaJogadaNaoAnalisadaParaPrevisao(List<Integer> baseCorrente, int i) {
        return baseCorrente.get(i) != 0 && !ultimasJogadassAnalisadasFake.contains(baseCorrente.get(i));
    }

    private final List<Integer> indiceUltimasJogadas = new ArrayList<>();

    public List<Integer> getIndiceUltimasJogadas() {
        return indiceUltimasJogadas;
    }

    /**
     * Este método recalcula o espaço de jogos possíveis,<br> a partir do estado
     * corrente do jogo.
     * <br>Para um jogo, e apenas para um desses jogos, o jogo atual evoluirá.
     *
     * @param jogoAtual o jogo atual
     *
     * @return Lista de jogos da velha, cujos estados são os únicos estados
     * futuros POSSÍVEIS, <br>
     * ou as únicas possibilidades para as quais o jogo EVOLUIRÁ.
     */
    private List<JogoDaVelhaWrapped> getEspacoRecalculado(JogoDaVelhaWrapped jogoAtual) {

        if (isPrimeiraJogada(espaco)) {

            if (cache.isCached(posicao + "," + 1)) {

                return cache.get(posicao + "," + 1);

            } else {

                selecionarPossibilidades(espaco, jogoAtual.getBaseCorrente(), posicao);

                if (verbose) {
                    System.out.print("\tCALCULANDO ESPAÇO INICIAL (tamanho do espaco # " + getEspaco().size() + " )");
                    System.out.println("");
                }

                cache.put(posicao + "," + 1, new ArrayList<>(espaco));

                return espaco;

            }

        }

        //posicao,valor,posicao,valor,...,posicao,valor
        String posicaoValorKey = "";
        for (int i = 0; i < indiceUltimasJogadas.size(); i++) {

            posicaoValorKey += indiceUltimasJogadas.get(i);
            posicaoValorKey += ",";
            posicaoValorKey += jogoAtual.getBaseCorrente().get(indiceUltimasJogadas.get(i));
            posicaoValorKey += ",";

        }

        posicaoValorKey = posicaoValorKey.substring(0, posicaoValorKey.length() - 1);

        if (cache.isCached(posicaoValorKey)) {
            
            return cache.get(posicaoValorKey);
            
        } else {

            int tamanhoEspacoAtual = espaco.size();

            if (verbose) {
                System.out.print("\tRECALCULADO ESPAÇO : #" + tamanhoEspacoAtual);
            }
            List<JogoDaVelhaWrapped> jogosFuturosPossiveis = new ArrayList<>();

            for (JogoDaVelhaWrapped jogo : espaco) {

                boolean isJogoPossivel = true;

                for (int i = 0; i < indiceUltimasJogadas.size(); i++) {

                    int valorNoJogoAtual = jogoAtual.getBaseCorrente().get(indiceUltimasJogadas.get(i));
                    int valorNumJogo = jogo.getTabuleiro().getBase().get(indiceUltimasJogadas.get(i));
                    isJogoPossivel = isJogoPossivel && (valorNoJogoAtual == valorNumJogo);
                    if (!isJogoPossivel) {
                        break;
                    }

                }

                if (isJogoPossivel) {
                    jogosFuturosPossiveis.add(jogo);
                }

            }

            if (verbose) {

                System.out.print("  ->  #" + jogosFuturosPossiveis.size());
                System.out.println("");

            }

            cache.put(posicaoValorKey, new ArrayList<>(jogosFuturosPossiveis));
            
            return jogosFuturosPossiveis;

        }

    }

    public List<JogoDaVelhaWrapped> getPossibilidades(JogoDaVelhaWrapped jogoDaVelha, List<JogoDaVelhaWrapped> possibilidades) {

        List<Integer> baseCorrente = jogoDaVelha.getBaseCorrente();
        int i = 0;

        i = getPosicaoUltimaJogadaNaoAnalisada(baseCorrente);

        if (isPrimeiraJogada(possibilidades)) {

            selecionarPossibilidades(possibilidades, baseCorrente, i);

        } else {// ha pelo menos uma jogada analisada

            List<JogoDaVelhaWrapped> jogosQueNaoSaoCaminho = new ArrayList<>();

            for (JogoDaVelhaWrapped jogo : possibilidades) {

                selecionarImpossibilidades(baseCorrente, i, jogosQueNaoSaoCaminho, jogo);

            }

            selecionarPossibilidades(possibilidades, jogosQueNaoSaoCaminho);

        }

        //System.out.println("posicao ultima nao analisada " + i + " valor " + baseCorrente.get(i));
        ultimasJogadasAnalisadas.add(baseCorrente.get(i));

        return possibilidades;
    }

    /**
     * @param possibilidades
     * @param jogosQueNaoSaoCaminho
     */
    private void selecionarPossibilidades(List<JogoDaVelhaWrapped> possibilidades, List<JogoDaVelhaWrapped> jogosQueNaoSaoCaminho) {
        for (JogoDaVelhaWrapped jogo : jogosQueNaoSaoCaminho) {
            possibilidades.remove(jogo);
            //System.out.println(" jogo removido "+ jogo.getTabuleiro().getBase().toString());
        }
    }

    /**
     * @param baseCorrente
     * @param i
     * @param jogosQueNaoSaoCaminho
     * @param jogo
     */
    private void selecionarImpossibilidades(List<Integer> baseCorrente, int i, List<JogoDaVelhaWrapped> jogosQueNaoSaoCaminho,
            JogoDaVelhaWrapped jogo) {
        boolean matches = true;

        matches = isJogoUmCaminho(baseCorrente, jogo, matches);

        if (isJogoUmCaminho(baseCorrente, i, jogo, matches)) {
        } else {
            jogosQueNaoSaoCaminho.add(jogo);
        }
    }

    /**
     * @param possibilidades
     * @param baseCorrente
     * @param i
     */
    private void selecionarPossibilidades(List<JogoDaVelhaWrapped> possibilidades, List<Integer> baseCorrente, int i) {
        for (String base : bases) {

            if (isUmCaminhoPossivel(baseCorrente, i, base)) {
                possibilidades.add(geradorDeJogosDaVelha.getJogos().get(base));
                // System.out.println(j++ + " jogo "+ base);
            }

        }
    }

    /**
     * @param possibilidades
     * @return
     */
    private boolean isPrimeiraJogada(List<JogoDaVelhaWrapped> possibilidades) {
        return possibilidades.isEmpty();
    }

    /**
     * @param baseCorrente
     * @param i
     * @param jogo
     * @param matches
     * @return
     */
    private boolean isJogoUmCaminho(List<Integer> baseCorrente, int i, JogoDaVelhaWrapped jogo, boolean matches) {
        return jogo.getTabuleiro().getBase().get(i) == baseCorrente.get(i) && matches;
    }

    /**
     * @param baseCorrente
     * @param jogo
     * @param matches
     * @return
     */
    private boolean isJogoUmCaminho(List<Integer> baseCorrente, JogoDaVelhaWrapped jogo, boolean matches) {
        int j;
        //System.err.println("jogo? "+ jogo.getTabuleiro().getBase() + " base corrente do jogo? " + jogo.getTabuleiro().getBaseCorrente()+ " base corrente " + baseCorrente);
        for (j = 0; j < ultimasJogadasAnalisadas.size(); j++) {

            int index = baseCorrente.indexOf(ultimasJogadasAnalisadas.get(j));
            matches = matches && jogo.getTabuleiro().getBase().get(index) == baseCorrente.get(index);

        }
        return matches;
    }

    /**
     * @param baseCorrente
     * @param i
     * @param base
     * @return
     */
    private boolean isUmCaminhoPossivel(List<Integer> baseCorrente, int i, String base) {
        return geradorDeJogosDaVelha.getJogos().get(base).getTabuleiro().getBase().get(i) == baseCorrente.get(i);
    }

    /**
     * @param baseCorrente
     * @return
     */
    private int getPosicaoUltimaJogadaNaoAnalisada(List<Integer> baseCorrente) {
        int i;
        for (i = 0; i < baseCorrente.size(); i++) {
            if (isUltimaJogadaNaoAnalisada(baseCorrente, i)) {
                break;
            }
        }
        return i;
    }

    /**
     * @param baseCorrente
     * @param i
     * @return
     */
    private boolean isUltimaJogadaNaoAnalisada(List<Integer> baseCorrente, int i) {
        return baseCorrente.get(i) != 0 && !ultimasJogadasAnalisadas.contains(baseCorrente.get(i));
    }

    public Analisador noEspaco(List<JogoDaVelhaWrapped> espaco) {
        this.espaco = espaco;
        return this;
    }

    public Analisador depoisQueJogador(Jogador jogador) {
        this.jogador = jogador;
        return this;
    }

    public Analisador jogarNaPosicao(int posicao) {
        this.posicao = posicao;
        return this;
    }

    public double getProbabilidadeDeVencer() {
        return this.getProbabilidadeDeVencer(this.jogoDaVelha, this.posicao, this.jogador.getProximoValorASerJogado(),
                this.espaco);
    }

    public Analisador doJogo(JogoDaVelhaWrapped jogoDaVelha) {
        this.jogoDaVelha = jogoDaVelha;
        return this;
    }

    public List<JogoDaVelhaWrapped> recalcularEspaco() {

        if (this.jogador.jogaNaPosicao(this.posicao)) {

            this.indiceUltimasJogadas.add(posicao);
            return getEspacoRecalculado(jogoDaVelha);

        }

        //return this.getPossibilidades(this.jogoDaVelha, this.espaco);
        return null;
    }

    public Analisador paraJogador(Jogador jogador) {
        this.jogador = jogador;
        return this;
    }

    public void preverMelhorJogada() {

        maiorProbabilidade = 0.0;
        melhorPosicao = 0;

        for (int i = 0; i < jogador.getJogoDaVelha().getBaseCorrente().size(); i++) {

            if (jogador.podeJogarNessaPosicao(i)) {// posicao livre
                // analisa probabilidade de gannhar

                this.posicao = i;

                double proba = this.getProbabilidadeDeVencer(this.jogoDaVelha, this.posicao,
                        this.jogador.getProximoValorASerJogado(), this.espaco);
                System.out.println("\tPROBABILIDADE  " + proba);

                if (proba >= maiorProbabilidade) {
                    maiorProbabilidade = proba;
                    melhorPosicao = i;
                }
            }
        }

    }

    public List<JogoDaVelhaWrapped> getEspaco() {
        return espaco;
    }

    public void setEspaco(List<JogoDaVelhaWrapped> espaco) {
        this.espaco = espaco;
    }

    public List<JogoDaVelhaWrapped> getPossibilidadesRemanescentes() {
        return possibilidadesRemanescentes;
    }

    public void setPossibilidadesRemanescentes(List<JogoDaVelhaWrapped> possibilidadesRemanescentes) {
        this.possibilidadesRemanescentes = possibilidadesRemanescentes;
    }

    public int getProximaJogada() {
        return proximaJogada;
    }

    public void setProximaJogada(int proximaJogada) {
        this.proximaJogada = proximaJogada;
    }

    public int getJogandoNaPosicao() {
        return jogandoNaPosicao;
    }

    public void setJogandoNaPosicao(int jogandoNaPosicao) {
        this.jogandoNaPosicao = jogandoNaPosicao;
    }

    /**
     * @return the melhorPosicao
     */
    public int getMelhorPosicao() {
        return melhorPosicao;
    }

    /**
     * @return the maiorProbabilidade
     */
    public double getMaiorProbabilidade() {
        return maiorProbabilidade;
    }

    public void verboseOff() {
        this.verbose = Boolean.FALSE;
    }

    public void verboseOn() {
        this.verbose = Boolean.TRUE;
    }

    private List<JogoDaVelhaWrapped> getEspaco(int posicao, int valor) {

        // if(getEspaco()!=null&&getEspaco().isEmpty())setEspaco(espacoTotal);
        List<JogoDaVelhaWrapped> espacoRestrito = new ArrayList<>();

        for (JogoDaVelhaWrapped jogo : getEspacoTotal()) {
            if (jogo.getBaseCorrente().get(posicao) == valor) {
                espacoRestrito.add(jogo);
            }
        }

        return espacoRestrito;
    }

    public List<JogoDaVelhaWrapped> getSubEspaco(int posicao, int valor) {

        // if(getEspaco()!=null&&getEspaco().isEmpty())setEspaco(espacoTotal);
        List<JogoDaVelhaWrapped> espacoRestrito = new ArrayList<>();

        for (JogoDaVelhaWrapped jogo : getEspaco()) {
            if (jogo.getBaseCorrente().get(posicao) == valor) {
                espacoRestrito.add(jogo);
            }
        }

        return espacoRestrito;
    }

    public List<Integer> getPosicoesOndePerderia() {

        ondePerderia.clear();

        for (Integer posicao : this.jogador.getPosicoesLivres()) {

            if (jogadorPerderiaSeJogasseAqui(posicao, jogador, jogador.getOponente())) {
                ondePerderia.add(posicao);
            }

        }

        return ondePerderia;

    }

    public List<Integer> getPosicoesOndeNaoPerderia() {

        ondeNaoPerderia = new ArrayList<>(this.jogador.getPosicoesLivres());

        for (Integer posicaoEmQuePerderia : getPosicoesOndePerderia()) {

            ondeNaoPerderia.remove(posicaoEmQuePerderia);

        }

        return ondeNaoPerderia;
    }

    public List<Integer> getPosicoesOndePerderiaDaquiADuasJogadas() {

        List<Integer> ondePerderiaNaProximaDaProxima = new ArrayList<>();

        List<Integer> ondeNaoPerderiaNaProxima = this.getPosicoesOndeNaoPerderia();

//		jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
        for (int i = 0; i < ondeNaoPerderiaNaProxima.size(); i++) {

            boolean perderia = false;

            if (this.jogador.jogaNaPosicao(ondeNaoPerderiaNaProxima.get(i))) {// nao perde em p(i)

//				jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
                List<Integer> posicoesLivresAposPrimeiraDoAtual = this.jogador.getPosicoesLivres();
                for (int j = 0; j < posicoesLivresAposPrimeiraDoAtual.size(); j++) {

                    if (jogador.getOponente().jogaNaPosicao(posicoesLivresAposPrimeiraDoAtual.get(j))) {//ateh aki nao perde qndo oponente getMelhorPosicaoEJogaNela em j

//						jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
                        List<Integer> posicoesLivres = jogador.getPosicoesLivres();
                        for (int ii = 0; ii < posicoesLivres.size(); ii++) {

                            boolean loose = jogadorPerderiaSeJogasseAqui(posicoesLivres.get(ii), jogador, jogador.getOponente());

//							if(loose){
//								jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
//								System.out.println(" primeira " + ondeNaoPerderiaNaProxima.get(i) + " segunda " + posicoesLivresAposPrimeiraDoAtual.get(j) + " terceira " + posicoesLivres.get(ii) );
//							}
                            perderia = perderia || loose;
                        }

                        jogador.getOponente().desfazerJogada();

//						jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
                    }

                }

                jogador.desfazerJogada();

//				jogador.getJogoDaVelha().getTabuleiro().semMascara().descreve();
            }

            if (perderia) {
                ondePerderiaNaProximaDaProxima.add(ondeNaoPerderiaNaProxima.get(i));
            }

        }

        return ondePerderiaNaProximaDaProxima;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
        geradorDeJogosDaVelha.setVerbose(verbose);
    }

}
