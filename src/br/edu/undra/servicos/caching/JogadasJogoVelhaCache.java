package br.edu.undra.servicos.caching;

import br.edu.undra.servicos.calculadorproximajogada.analisador.JogoDaVelhaWrapped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cache das Jogadas do jogo da velha.
 *
 * @author alexandre
 */
public class JogadasJogoVelhaCache {

    private Map<String, List<JogoDaVelhaWrapped>> cacheMap = new HashMap();

    /**
     * Verifica se uma sequencia de jogadas futuras possíveis está no cache.<br>
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

        return cacheMap.containsKey(valoresPosicoesKey);

    }

    /**
     * Retorna lista correspondentes à chave valoresPosicoes<br>
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
     * @return Lista das jogadas futuras, ou <br>
     * lista vazia, caso nao tenha alguma no cache.
     */
    public List<JogoDaVelhaWrapped> get(String valoresPosicoesKey) {
    
        System.out.println("CACHING SERVICE : retornando lista : key " + valoresPosicoesKey);
        
        if( ! isCached(valoresPosicoesKey) ) return new ArrayList<>();
        
        return cacheMap.get(valoresPosicoesKey);
    }
    
    /**
     * Cacheia lista correspondentes à chave valoresPosicoes<br>
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
     * @param jogadas a lista de jogadas.
     */
    public void put(String valoresPosicoesKey , List<JogoDaVelhaWrapped> jogadas){
        
        if( cacheMap.put(valoresPosicoesKey, jogadas) == null )
            System.out.println("CACHING SERVICE : caching jogadas : key " + valoresPosicoesKey);
        
    }
    
    

}
