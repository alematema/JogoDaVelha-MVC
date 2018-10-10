package br.edu.undra.servicos.calculadorproximajogada.analisador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabuleiro {

	public static int ZERO = 0;
	public static int UM = 1;
	public static int DOIS = 2;
	public static int TRES = 3;
	public static int QUATRO = 4;
	public static int CINCO = 5;
	public static int SEIS = 6;
	public static int SETE = 7;
	public static int OITO = 8;

	private Map<Integer, Integer> linha0;
	private Map<Integer, Integer> linha1;
	private Map<Integer, Integer> linha2;

	private String posicaoNaoJogada = ".";
	private String posicaoJogadaImpar = "x";
	private String posicaoJogadaPar = "o";

	private boolean comMascaras = true;

	private List<Integer> base;
	private List<Integer> baseCorrente;

	public Tabuleiro(List<Integer> base) {

		if (base == null)
			throw new RuntimeException("A base não pode ser nula !!!");

		for (Integer token : base) {
			if (token < 0)
				throw new RuntimeException("A base não pode ter elementos negativos !!!");
		}

		if (base.size() > 9)
			throw new RuntimeException("A base não pode ter mais do que 9 elementos !!!");

		for (Integer token : base) {
			if (token > 9)
				throw new RuntimeException("A base não pode ter elementos maiores do que 9 !!!");
		}

		if (base.size() == 0) {
			base = new ArrayList<Integer>();
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
			base.add(0);
		}

		this.setBase(base);
		this.baseCorrente = new ArrayList<>(base);

		linha0 = new HashMap<>();
		linha1 = new HashMap<>();
		linha2 = new HashMap<>();

		linha0.put(0, 0);
		linha0.put(1, 0);
		linha0.put(2, 0);

		linha1.put(3, 0);
		linha1.put(4, 0);
		linha1.put(5, 0);

		linha2.put(6, 0);
		linha2.put(7, 0);
		linha2.put(8, 0);

		for (int i = 1; i <= 9; i++) {
			int i_esimo = base.indexOf(i);
			if (i_esimo != -1) {
				if (!alguemVenceu())
					set(i_esimo, i);
			}
		}

	}

	public Tabuleiro() {
		this(new ArrayList<Integer>());
	}

	public void setBase(List<Integer> base) {
		this.base = base;
	}

	/**
	 * @return the comMascaras
	 */
	public boolean isComMascaras() {
		return comMascaras;
	}

	/**
	 * @param comMascaras
	 *            the comMascaras to set
	 */
	public void setComMascaras(boolean comMascaras) {
		this.comMascaras = comMascaras;
	}

	/**
	 * @return the base
	 */
	public List<Integer> getBase() {
		return base;
	}

	public List<Integer> getBaseCorrente() {
		return baseCorrente;
	}

	public int get(int posicao) {
		int valor = -1;
		if (posicao < 0 || posicao > 8)
			return valor;
		if (posicao <= 2)
			valor = linha0.get(posicao);
		else if (posicao <= 5)
			valor = linha1.get(posicao);
		else if (posicao <= 8)
			valor = linha2.get(posicao);
		return valor;
	}

	public void descreve() {
		
		if(isComMascaras()){
			//012
			//345
			//678
			
			if(primeiroJogadorFezAlgumaTrinca()){
				
				if(isTrincaNaLinha(0)){//linha 0
					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar,linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar, linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar);
				}else{
					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				if(isTrincaNaLinha(1)){//linha 1
					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar,linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar, linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar);
				}else{
					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				if(isTrincaNaLinha(2)){//linha 2
					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar,linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar, linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar.toUpperCase():posicaoJogadaPar);
				}else{
					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				
			}else{
				
				if(isTrincaNaLinha(ZERO)){
					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(),linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(), linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase());
				}else{
					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				
				if(isTrincaNaLinha(UM)){
					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(),linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(), linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase());
				}else{
					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				
				if(isTrincaNaLinha(DOIS)){
					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(),linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase(), linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar:posicaoJogadaPar.toUpperCase());
				}else{
					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
				}
				
			}
		}else{
			if(primeiroJogadorFezAlgumaTrinca()){
				
//				if(isTrincaNaLinha(0)){
//					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}else{
//					System.out.printf("\n%s %s %s\n",linha0.get(0)==0?posicaoNaoJogada:linha0.get(0)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha0.get(1)==0?posicaoNaoJogada:linha0.get(1)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha0.get(2)==0?posicaoNaoJogada:linha0.get(2)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}
//				if(isTrincaNaLinha(1)){
//					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}else{
//					System.out.printf("%s %s %s\n",linha1.get(3)==0?posicaoNaoJogada:linha1.get(3)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha1.get(4)==0?posicaoNaoJogada:linha1.get(4)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha1.get(5)==0?posicaoNaoJogada:linha1.get(5)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}
//				if(isTrincaNaLinha(2)){
//					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}else{
//					System.out.printf("%s %s %s\n",linha2.get(6)==0?posicaoNaoJogada:linha2.get(6)%2==1?posicaoJogadaImpar:posicaoJogadaPar,linha2.get(7)==0?posicaoNaoJogada:linha2.get(7)%2==1?posicaoJogadaImpar:posicaoJogadaPar, linha2.get(8)==0?posicaoNaoJogada:linha2.get(8)%2==1?posicaoJogadaImpar:posicaoJogadaPar);
//				}
				
				System.out.printf("\n%s %s %s\n",linha0.get(0),linha0.get(1), linha0.get(2));
				System.out.printf("%s %s %s\n",linha1.get(3),linha1.get(4), linha1.get(5));
				System.out.printf("%s %s %s\n",linha2.get(6),linha2.get(7), linha2.get(8));
		
			}else{
				
				System.out.printf("\n%s %s %s\n",linha0.get(0),linha0.get(1), linha0.get(2));
				System.out.printf("%s %s %s\n",linha1.get(3),linha1.get(4), linha1.get(5));
				System.out.printf("%s %s %s\n",linha2.get(6),linha2.get(7), linha2.get(8));				
			}
		}
	}

	private boolean isTrincaNaLinha(int i) {

		boolean isTrinca = false;

		if (i == 0)
			isTrinca = checarLinha(linha0, JogadorComecaOJogo.SIM);
		if (i == 1)
			isTrinca = checarLinha(linha1, JogadorComecaOJogo.SIM);
		if (i == 2)
			isTrinca = checarLinha(linha2, JogadorComecaOJogo.SIM);
		return isTrinca;

	}
	
	private boolean isTrincaNaColuna(int coluna) {
		
		boolean isTrinca = false;
		
		if(coluna == 0) isTrinca = checarColuna(linha0, linha1, linha2, ZERO, 1) || checarColuna(linha0, linha1, linha2, ZERO, 0);
		
		if(coluna == 1) isTrinca = checarColuna(linha0, linha1, linha2, UM, 1) || checarColuna(linha0, linha1, linha2, UM, 0);
		
		if(coluna == 2) isTrinca = checarColuna(linha0, linha1, linha2, DOIS, 1) || checarColuna(linha0, linha1, linha2, DOIS, 0);
		
		return isTrinca;
		
	}

	public boolean set(int posicao, int valor) {

		boolean set = Boolean.FALSE;

		if (0 <= posicao && posicao <= 8 && 0 <= valor && valor <= 9) {
			set = Boolean.TRUE;
			baseCorrente.set(posicao, valor);
			if (posicao <= 2)
				linha0.put(posicao, valor);
			else if (posicao <= 5)
				linha1.put(posicao, valor);
			else if (posicao <= 8)
				linha2.put(posicao, valor);
		}

		return set;
	}

	public boolean segundoJogadorFezAlgumaTrinca() {

		boolean temTrincaParaSegundoJogador = true;

		if (primeiroJogadorFezAlgumaTrinca()) {
			temTrincaParaSegundoJogador = false;
		} else {

			// linha superior,
			if (checarLinha(linha0, JogadorComecaOJogo.NAO)) {
				return temTrincaParaSegundoJogador;
			}
			// linha media
			if (checarLinha(linha1, JogadorComecaOJogo.NAO)) {
				return temTrincaParaSegundoJogador;
			}
			// linha de baixo
			if (checarLinha(linha2, JogadorComecaOJogo.NAO)) {
				return temTrincaParaSegundoJogador;
			}
			// coluna esquerda
			if (checarColuna(linha0, linha1, linha2, 0, 2)) {
				return temTrincaParaSegundoJogador;
			}

			// coluna do meio
			if (checarColuna(linha0, linha1, linha2, 1, 2)) {
				return temTrincaParaSegundoJogador;
			}

			// coluna direita
			if (checarColuna(linha0, linha1, linha2, 2, 2)) {
				return temTrincaParaSegundoJogador;
			}

			// diagonal principal
			temTrincaParaSegundoJogador = true;

			if (linha0.get(0) == 0 || linha1.get(4) == 0 || linha2.get(8) == 0) {
				temTrincaParaSegundoJogador = false;
			}

			if (linha0.get(0) % 2 != 0 || linha1.get(4) % 2 != 0 || linha2.get(8) % 2 != 0) {
				temTrincaParaSegundoJogador = temTrincaParaSegundoJogador & false;
			}

			if (temTrincaParaSegundoJogador) {
				return temTrincaParaSegundoJogador;
			}

			// diagonal secundaria
			temTrincaParaSegundoJogador = true;

			if (linha0.get(2) == 0 || linha1.get(4) == 0 || linha2.get(6) == 0) {
				temTrincaParaSegundoJogador = false;
			}

			if (linha0.get(2) % 2 != 0 || linha1.get(4) % 2 != 0 || linha2.get(6) % 2 != 0) {
				temTrincaParaSegundoJogador = false;
			}

		}
		return temTrincaParaSegundoJogador;
	}

	public boolean primeiroJogadorFezAlgumaTrinca() {

		boolean temTrincaParaPrimeiroJogador = true;

		// linha superior,
		if (checarLinha(linha0, JogadorComecaOJogo.SIM)) {
			return temTrincaParaPrimeiroJogador;
		}
		// linha media
		if (checarLinha(linha1, JogadorComecaOJogo.SIM)) {
			return temTrincaParaPrimeiroJogador;
		}
		// linha de baixo
		if (checarLinha(linha2, JogadorComecaOJogo.SIM)) {
			return temTrincaParaPrimeiroJogador;
		}

		// coluna esquerda
		if (checarColuna(linha0, linha1, linha2, 0, 1)) {
			return temTrincaParaPrimeiroJogador;
		}

		// coluna do meio
		if (checarColuna(linha0, linha1, linha2, 1, 1)) {
			return temTrincaParaPrimeiroJogador;
		}

		// coluna direita
		if (checarColuna(linha0, linha1, linha2, 2, 1)) {
			return temTrincaParaPrimeiroJogador;
		}

		// diagonal principal
		temTrincaParaPrimeiroJogador = true;

		if (linha0.get(0) % 2 != 1 || linha1.get(4) % 2 != 1 || linha2.get(8) % 2 != 1) {
			temTrincaParaPrimeiroJogador = temTrincaParaPrimeiroJogador & false;
		}

		if (temTrincaParaPrimeiroJogador) {
			return temTrincaParaPrimeiroJogador;
		}

		// diagonal secundaria
		temTrincaParaPrimeiroJogador = true;

		if (linha0.get(2) % 2 != 1 || linha1.get(4) % 2 != 1 || linha2.get(6) % 2 != 1) {
			temTrincaParaPrimeiroJogador = false;
		}

		return temTrincaParaPrimeiroJogador;
	}

	private boolean isTrincaNaDiagonalSecundaria() {
		
		boolean isTrinca = false;
		
		if (linha0.get(2) % 2 == 1 && linha1.get(4) % 2 == 1 && linha2.get(6) % 2 == 1) {
			isTrinca = true;
		}
		
		if (!(linha0.get(2) == 0 || linha1.get(4) == 0 || linha2.get(6) == 0)) {
			if (linha0.get(2) % 2 == 0 && linha1.get(4) % 2 == 0 && linha2.get(6) % 2 == 0) {
				isTrinca = true;
			}
		}

		return isTrinca;
		
	}
	
	private boolean isTrincaNaDiagonalPrincipal() {
		
		boolean isTrinca = false;
	
		if (linha0.get(0) % 2 == 1 && linha1.get(4) % 2 == 1 && linha2.get(8) % 2 == 1) {
			isTrinca = true;
		}
		
		if (!(linha0.get(0) == 0 || linha1.get(4) == 0 || linha2.get(8) == 0)) {
			if (linha0.get(0) % 2 == 0 && linha1.get(4) % 2 == 0 && linha2.get(8) % 2 == 0) {
				isTrinca=true;
			}
		}
		
		return isTrinca;
	
	}
	
	public boolean alguemVenceu() {
		return primeiroJogadorFezAlgumaTrinca() || segundoJogadorFezAlgumaTrinca();
	}

	/**
	 * @param
	 */
	private boolean checarLinha(Map<Integer, Integer> linhaAnalisada, JogadorComecaOJogo jogadorComecaJogo) {

		boolean temTrincaParaJogador = true;

		for (Integer i : linhaAnalisada.keySet())

			if (jogadorComecaJogo.equals(JogadorComecaOJogo.SIM)) {

				if (linhaAnalisada.get(i) % 2 != 1) {
					temTrincaParaJogador = temTrincaParaJogador & false;
				}
			} else {

				if (linhaAnalisada.get(i) == 0) {
					temTrincaParaJogador = false;
					break;
				}
				if (linhaAnalisada.get(i) != 0 && linhaAnalisada.get(i) % 2 != 0) {
					temTrincaParaJogador = temTrincaParaJogador & false;
				}
			}

		return temTrincaParaJogador;
	}

	/**
	 * @param
	 */
	private boolean checarColuna(Map<Integer, Integer> linhaAnalisada0, Map<Integer, Integer> linhaAnalisada1,
			Map<Integer, Integer> linhaAnalisada2, int coluna, int jogador) {

		boolean temTrincaParaJogador = true;

		if (jogador == 1) {
			if (linhaAnalisada0.get(coluna) % 2 != 1 || linhaAnalisada1.get(coluna + 3) % 2 != 1
					|| linhaAnalisada2.get(coluna + 6) % 2 != 1) {
				temTrincaParaJogador = temTrincaParaJogador & false;
			}
		} else {

			if (linhaAnalisada0.get(coluna) == 0 || linhaAnalisada1.get(coluna + 3) == 0
					|| linhaAnalisada2.get(coluna + 6) == 0) {
				temTrincaParaJogador = false;
			}

			if (linhaAnalisada0.get(coluna) != 0 && linhaAnalisada0.get(coluna) % 2 != 0
					|| linhaAnalisada1.get(coluna + 3) != 0 && linhaAnalisada1.get(coluna + 3) % 2 != 0
					|| linhaAnalisada2.get(coluna + 6) != 0 && linhaAnalisada2.get(coluna + 6) % 2 != 0) {
				temTrincaParaJogador = temTrincaParaJogador & false;
			}
		}

		return temTrincaParaJogador;
	}

	public Tabuleiro comMascara() {
		this.comMascaras = Boolean.TRUE;
		return this;
	}

	public Tabuleiro semMascara() {
		this.comMascaras = Boolean.FALSE;
		return this;
	}

	public String getOndeHouveTrinca() {
		
		String onde = "";
		
		if(isTrincaNaLinha(ZERO)) onde = "na linha 1";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		if(isTrincaNaLinha(UM)) onde = "na linha 2";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		if(isTrincaNaLinha(DOIS)) onde = "na linha 3";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		
		if(isTrincaNaColuna(ZERO)) onde = "na coluna 1";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		if(isTrincaNaColuna(UM)) onde = "na coluna 2";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		if(isTrincaNaColuna(DOIS)) onde = "na coluna 3";//mensagem apropriada ao usuário que conta a partir do 1 e nao do zero
		
		if(isTrincaNaDiagonalPrincipal()) onde = "na diagonal principal";
		if(isTrincaNaDiagonalSecundaria()) onde = "na diagonal secundária";
		
		
		return onde;
		
	}

	

	



}
