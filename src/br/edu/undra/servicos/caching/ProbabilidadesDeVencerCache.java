package br.edu.undra.servicos.caching;

import br.edu.undra.servicos.calculadorproximajogada.analisador.JogoDaVelhaWrapped;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cache das Probabilidades de se vencer jogo da velha.
 *
 * @author alexandre
 */
public class ProbabilidadesDeVencerCache {

    private String PROPERTIES_FILE_NAME = "probabilidades.properties";

    private FileInputStream propertiesFile;

    private Properties properties = new Properties();

    List<Integer> indiceUltimasPosicoesJogadas = new ArrayList<>();
    List<Integer> valoresNosIndiceUltimasPosicoesJogadas = new ArrayList<>();

    public ProbabilidadesDeVencerCache() {

        try {

            propertiesFile = new FileInputStream("src/resources/" + PROPERTIES_FILE_NAME);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ProbabilidadesDeVencerCache(String propertiesFileName) {

        PROPERTIES_FILE_NAME = propertiesFileName;

        File f = new File("src/resources/" + propertiesFileName);

        try {

            if (!f.exists()) {
                
                Files.createFile(Paths.get("src/resources/" + PROPERTIES_FILE_NAME));
            }

            propertiesFile = new FileInputStream("src/resources/" + PROPERTIES_FILE_NAME);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Verifica se uma sequencia de jogadas está no cache.<br>
     * A String passada está no formato
     * posicao,valor,posicao,valor,...,posicao,valor<br>
     * Por exemplo,uma possivel string é da forma 2,1,6,3
     * <br>onde<br>
     * o 2, corresponde à posicao 2; o 1 , ao valor nessa posicao 2.
     * <br>
     * o 6, corresponde à posicao 6; o 3 , ao valor nessa posicao 6.
     *
     *
     * @param valoresPosicoesKey String na forma
     * posicao,valor,posicao,valor,...,posicao,valor
     * @return true se no cache <br> false, caso contrario.
     */
    public boolean isCached(String valoresPosicoesKey) {

        try {

            properties.load(propertiesFile);

        } catch (IOException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties.containsKey(valoresPosicoesKey);

    }

    /**
     * Retorna Sring correspondentes à chave valoresPosicoes<br>
     * A chave passada está no formato
     * posicao,valor,posicao,valor,...,posicao,valor<br>
     * Por exemplo,uma possivel string é da forma 2,1,6,3
     * <br>onde<br>
     * o 2, corresponde à posicao 2; o 1 , ao valor nessa posicao 2.
     * <br>
     * o 6, corresponde à posicao 6; o 3 , ao valor nessa posicao 6.
     *
     *
     * @param valoresPosicoesKey String na forma
     * posicao,valor,posicao,valor,...,posicao,valor
     *
     * @return Exemplo string 10.23-2;24.23-5;1.23-3;5,24.23 que representa
     * probabilidades de se vencer o jogo<br> nas posicoes 2, 5 e 3
     * respectivamente. ou <br>
     * String vazia, caso nao tenha alguma no cache.
     */
    public String get(String valoresPosicoesKey) {

        if (!isCached(valoresPosicoesKey)) {
            System.out.println("CACHING SERVICE : SEM probabilidades para key " + valoresPosicoesKey);
            return "";
        }

        try {

            properties.load(propertiesFile);

        } catch (IOException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        }

        String probabilidades = properties.getProperty(valoresPosicoesKey);

        System.out.println("CACHING SERVICE : get : key " + valoresPosicoesKey + " probabilidades " + probabilidades);

        return probabilidades;
    }

    /**
     * Cacheia String correspondentes à chave valoresPosicoes<br>
     * A chave passada está no formato
     * posicao,valor,posicao,valor,...,posicao,valor<br>
     * Por exemplo,uma possivel string é da forma 2,1,6,3
     * <br>onde<br>
     * o 2, corresponde à posicao 2; o 1 , ao valor nessa posicao 2.
     * <br>
     * o 6, corresponde à posicao 6; o 3 , ao valor nessa posicao 6.
     *
     *
     * @param valoresPosicoesKey String na forma
     * posicao,valor,posicao,valor,...,posicao,valor
     *
     * @param probabilidades a sequencia de probabilidades e posicoes
     * respectivas<br>
     * no seguinte formato<br>
     * string 10.23-2;24.23-5;1.23-3;5,24.23 que representa probabilidades de se vencer
     * o jogo<br> nas posicoes 2, 5 e 3 respectivamente.
     */
    public void put(String valoresPosicoesKey, String probabilidades) {

        try {

            FileInputStream fis = new FileInputStream("src/resources/" + PROPERTIES_FILE_NAME);
            properties.load(fis);
            properties.setProperty(valoresPosicoesKey, probabilidades);
            fis.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        try {

            FileOutputStream fos = new FileOutputStream("src/resources/" + PROPERTIES_FILE_NAME);
            properties.store(fos, "ARQUIVO DE PROBABILIDADES");
            fos.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        try {

            propertiesFile = new FileInputStream("src/resources/" + PROPERTIES_FILE_NAME);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProbabilidadesDeVencerCache.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("CACHING SERVICE : caching probabilidades " + probabilidades + " : key " + valoresPosicoesKey);

    }

    /**
     * Gera chave no formato posicao,valor,posicao,valor,...,posicao,valor
     *
     * @param jogoDaVelha o jogo a partir do qual se criara uma chave
     * @return chave string no formato
     * posicao,valor,posicao,valor,...,posicao,valor
     */
    public String createKey(JogoDaVelhaWrapped jogoDaVelha) {

        indiceUltimasPosicoesJogadas.clear();
        valoresNosIndiceUltimasPosicoesJogadas.clear();
       
        //conhece as posicoes OCUPADAS
        for (int i = 0; i < jogoDaVelha.getTabuleiro().getBaseCorrente().size(); i++) {
            if (jogoDaVelha.getBaseCorrente().get(i) != 0) {
                valoresNosIndiceUltimasPosicoesJogadas.add(jogoDaVelha.getBaseCorrente().get(i));
            }
        }

        Collections.sort(valoresNosIndiceUltimasPosicoesJogadas);

        for (int i = 0; i < valoresNosIndiceUltimasPosicoesJogadas.size(); i++) {

            for (int index = 0; index < jogoDaVelha.getTabuleiro().getBaseCorrente().size(); index++) {
                if (jogoDaVelha.getBaseCorrente().get(index).equals(valoresNosIndiceUltimasPosicoesJogadas.get(i))) {
                    indiceUltimasPosicoesJogadas.add(index);
                    break;
                }
            }

        }

        //posicao,valor,posicao,valor,...,posicao,valor
        String posicaoValorKey = "";
        for (int INDICE = 0; INDICE < indiceUltimasPosicoesJogadas.size(); INDICE++) {

            posicaoValorKey += indiceUltimasPosicoesJogadas.get(INDICE);
            posicaoValorKey += ",";
            posicaoValorKey += jogoDaVelha.getBaseCorrente().get(indiceUltimasPosicoesJogadas.get(INDICE));
            posicaoValorKey += ",";

        }

        posicaoValorKey = posicaoValorKey.substring(0, posicaoValorKey.length() - 1);

        return posicaoValorKey;

    }

}
