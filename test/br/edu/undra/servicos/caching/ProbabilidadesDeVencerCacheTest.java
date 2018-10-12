package br.edu.undra.servicos.caching;

import br.edu.undra.servicos.calculadorproximajogada.analisador.JogoDaVelhaWrapped;
import br.edu.undra.servicos.calculadorproximajogada.analisador.Tabuleiro;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandre
 */
public class ProbabilidadesDeVencerCacheTest {

    public ProbabilidadesDeVencerCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isCached method, of class ProbabilidadesDeVencerCache.
     */
    @Test
    public void testIsCached() {

        System.out.println("isCached");
        
        String valoresPosicoesKey = "";
        
        ProbabilidadesDeVencerCache cache = new ProbabilidadesDeVencerCache("testes.properties");
        
        //arquivo de propriedades NAO CONTEM ESSA CHAVE GERADA AGORA
        valoresPosicoesKey ="isCachedTest"+ Long.toString(new Date().getTime());
        assertEquals(false, cache.isCached(valoresPosicoesKey));

        //arquivo de propriedades tem ao menos uma entrada
        cache.put(valoresPosicoesKey, "1.00");
        assertEquals(true, cache.isCached(valoresPosicoesKey));
        
        
    }

    /**
     * Test of get method, of class ProbabilidadesDeVencerCache.
     */
    @Test
    public void testGet() {
        
        System.out.println("get");
        
        String valoresPosicoesKey = "";
        ProbabilidadesDeVencerCache cache = new ProbabilidadesDeVencerCache("testes.properties");
        
        valoresPosicoesKey = "getTest"+Long.toString(new Date().getTime());
        assertEquals("",cache.get(valoresPosicoesKey));
        
        cache.put(valoresPosicoesKey, "10.23-2;24.23-5;1.23-3");
        
        assertEquals("10.23-2;24.23-5;1.23-3",cache.get(valoresPosicoesKey));
        
    }

    /**
     * Test of put method, of class ProbabilidadesDeVencerCache.
     */
    @Test
    public void testPut() {

        System.out.println("put");
        String valoresPosicoesKey = "";
        String probabilidades = "10.23-2;24.23-5;1.23-3";
        ProbabilidadesDeVencerCache cache = new ProbabilidadesDeVencerCache("testes.properties");

        valoresPosicoesKey = "putTest"+Long.toString(new Date().getTime());
        cache.put(valoresPosicoesKey, probabilidades);
        
        assertEquals(probabilidades, cache.get(valoresPosicoesKey));
        

    }

    /**
     * Test of createKey method, of class ProbabilidadesDeVencerCache.
     */
    @Test
    public void testCreateKey() {
        
        System.out.println("createKey");
        
        JogoDaVelhaWrapped jogoDaVelha = new JogoDaVelhaWrapped(new Tabuleiro());
        ProbabilidadesDeVencerCache cache = new ProbabilidadesDeVencerCache();
        
        //simula jogadas nas posicoes 3, 5, 8, 1 dos jogadores 1 , 2 , 1 e 2 respectivamente

        jogoDaVelha.getTabuleiro().set(2, 1);
        // a chave deve ser 2,1
        assertEquals(cache.createKey(jogoDaVelha),"2,1");
        
        jogoDaVelha.getTabuleiro().set(4, 2);
        // a chave deve ser 2,1,4,2
        assertEquals(cache.createKey(jogoDaVelha),"2,1,4,2");
        
        jogoDaVelha.getTabuleiro().set(7, 3);
        // a chave deve ser 2,1,4,2,7,3
        assertEquals(cache.createKey(jogoDaVelha),"2,1,4,2,7,3");
        
        jogoDaVelha.getTabuleiro().set(0, 4);
        // a chave deve ser 2,1,4,2,7,3
        assertEquals(cache.createKey(jogoDaVelha),"2,1,4,2,7,3,0,4");
        
        
    }

}
