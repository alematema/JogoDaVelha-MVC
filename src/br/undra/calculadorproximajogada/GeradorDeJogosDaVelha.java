package br.undra.calculadorproximajogada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeradorDeJogosDaVelha {

    private Map<String, List<Integer>> bases;
    private List<Integer> base;
    private Map<String, JogoDaVelha> jogos;
    private boolean verbose = false;

    /**
     * @return the jogos
     */
    public Map<String, JogoDaVelha> getJogos() {
        return jogos;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
     public JogoDaVelha getJogoById(String base) {
        if (getJogos() == null) {
            throw new RuntimeException("Erro! Crie os jogos antes de recuperar algum !!!");
        }
        return getJogos().get(base);
    }

    public List<Integer> getBase() {

        if (base == null) {
            base = new ArrayList<Integer>();
            base.add(1);
            base.add(2);
            base.add(3);
            base.add(4);
            base.add(5);
            base.add(6);
            base.add(7);
            base.add(8);
            base.add(9);
        }

        return base;
    }

    private Map<String, List<Integer>> getBases() {
        if (bases == null) {
            bases = new HashMap<>();
        }
        return bases;
    }

    public Map<String, List<Integer>> gerarBases() {

        if(verbose)System.out.println("\t\tCriando bases");

        while (true) {

            Collections.shuffle(getBase());
            //getBases().put(getBase().toString(), new ArrayList<>(getBase()));

            if (getBases().put(getBase().toString(), new ArrayList<>(getBase())) == null) {
                if (getBases().keySet().size() == 362880 / 4) {
                    if(verbose)System.out.println("\t\t\t25% bases criadas");
                }
                if (getBases().keySet().size() == 362880 / 2) {
                   if(verbose) System.out.println("\t\t\t50% bases criadas");
                }
                if (getBases().keySet().size() == 3 * 362880 / 4) {
                    if(verbose)System.out.println("\t\t\t75% bases criadas");
                }
                //System.out.println(getBase() + ","+getBases().keySet().size());
            }
            

            if (getBases().keySet().size() == 362880) {
                break;
            }

        }

        if(verbose)System.out.println("\t\t100% bases criadas");

        return getBases();

    }

    public void gerarTodosJogos() {

        if (jogos == null) {
            int centos = 1;
            int i = 0;
            jogos = new HashMap<>();
            gerarBases();

            for (String base : getBases().keySet()) {
                jogos.put(base, new JogoDaVelha(new Tabuleiro(getBases().get(base))));
                if (i++ % (getBases().keySet().size() / 10) == 0) {

                    if (centos % 3 == 0) {
                        if(verbose)System.err.println("\tCriados " + (centos * 1f / 10f) * 100 + " % do banco de jogos ");
                    }
                    centos++;
                }
            }
        }

    }

    public static void main(String[] args) {

        GeradorDeJogosDaVelha geradorDeJogosDaVelha = new GeradorDeJogosDaVelha();

        long init = System.currentTimeMillis();

        geradorDeJogosDaVelha.gerarTodosJogos();

        String msg = " generating all games took " + (System.currentTimeMillis() - init) / 1000 + " secs";

        init = System.currentTimeMillis();

        List<String> bases = new ArrayList<>(geradorDeJogosDaVelha.getBases().keySet());

        //Collections.sort(bases);
        for (String base : bases) {
            System.err.println(geradorDeJogosDaVelha.getJogoById(base).getId());
        }

        System.err.println(msg);
        System.err.println(" took " + (System.currentTimeMillis() - init) / 1000 + " secs for printing " + bases.size() + " games");

    }

}
