package br.undra.calculadorproximajogada;

import java.util.List;

public class JogoDaVelhaWrapped {

	private Tabuleiro tabuleiro;
	private int valor;
	private int posicao;
	private int ultimaPosicao;
	private int ultimoValor;
	private String id;
	private boolean comMascara = false;

	/**
	 * @return the ultimoValor
	 */
	public int getPenultimoValorJogado() {
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
	public int getUltimaPosicaoJogada() {
		return ultimaPosicao;
	}

	/**
	 * @param ultimaPosicao the ultimaPosicao to set
	 */
	public void setUltimaPosicao(int ultimaPosicao) {
		this.ultimaPosicao = ultimaPosicao;
	}

	/**
	 * @return the comMascara
	 */
	public boolean isComMascara() {
		return comMascara;
	}

	/**
	 * @param comMascara
	 *            the comMascara to set
	 */
	public void setComMascara(boolean comMascara) {
		this.comMascara = comMascara;
	}

	public boolean isPosicaoLivre(int i) {
		if(0<=i && i<=8)
			return getBaseCorrente().get(i) == 0;
		return false;
	}

	public JogoDaVelhaWrapped(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		this.id = tabuleiro.getBase().toString();
	}

	public JogoDaVelhaWrapped comMascara() {
		this.setComMascara(Boolean.TRUE);
		return this;
	}

	public JogoDaVelhaWrapped semMascara() {
		this.setComMascara(Boolean.FALSE);
		return this;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public List<Integer> getBaseCorrente() {
		return this.tabuleiro.getBaseCorrente();
	}

	public JogoDaVelhaWrapped setValor(int valor) {
		this.valor = valor;
		return this;
	}

	public JogoDaVelhaWrapped naPosicao(int posicao) {
		this.posicao = posicao;
		return this;
	}

	public boolean jogar() {

		if(validarPosicaoEMemorizarValorDoTabuleiroAntesDeAlterar())
		  return this.tabuleiro.set(posicao, valor);
		
		return false;
		
	}

	/**
	 * 
	 */
	private boolean validarPosicaoEMemorizarValorDoTabuleiroAntesDeAlterar() {
		
		if (isPosicaoValidaNoTabuleiro()) {/// memoriza ultimo par
			setUltimaPosicao(posicao);
			setUltimoValor(this.getTabuleiro().get(getUltimaPosicaoJogada()));
			return true;
		}
		
		return false;
		
	}

	/**
	 * @return
	 */
	private boolean isPosicaoValidaNoTabuleiro() {
		if(!(0<=posicao && posicao<=8)) return false;
		if(!isPosicaoLivre(posicao)) return false;
		return this.getTabuleiro().get(posicao) != -1;
	}

	public boolean desfazerJogada() {
//		boolean desfez = false;
//
//		if (!this.tabuleiro.alguemVenceu()) {
//			desfez = this.tabuleiro.set(getUltimaPosicao(), getUltimoValor());
//		}

		//restaura posicao da ultima jogada com penultimo valor jogado naquela posicao
		return this.tabuleiro.set(getUltimaPosicaoJogada(), 0);
	}
	
	public boolean desfazerJogada(int ultimaPosicao, int ultimoValor) {
		 
		setUltimaPosicao(ultimaPosicao);
		//setUltimoValor(ultimoValor);

		return this.desfazerJogada();
	}

	public boolean alguemVenceu() {
		return this.tabuleiro.primeiroJogadorFezAlgumaTrinca() || tabuleiro.segundoJogadorFezAlgumaTrinca();
	}
	
	public boolean terminou(){
		return !this.getBaseCorrente().contains(0) || alguemVenceu();
	}
	

	public void descreve() {
		System.out.println("\n\n--------------------------------------------------------------");
		System.out.println("\tBASE INICIAL: " + this.tabuleiro.getBase().toString());
		System.out.println("\tBASE CORRENTE: " + this.tabuleiro.getBaseCorrente().toString());
		System.out.println(
				"\tJOGADOR 1 VENCEU JOGO(?):  " + (this.tabuleiro.primeiroJogadorFezAlgumaTrinca() ? "SIM" : "NÃO"));
		System.out.println(
				"\tJOGADOR 2 VENCEU JOGO(?):  " + (this.tabuleiro.segundoJogadorFezAlgumaTrinca() ? "SIM" : "NÃO"));

		System.out.println("\tTABULEIRO: ");
		if (isComMascara()) {
			this.tabuleiro.comMascara().descreve();
		} else {
			this.tabuleiro.semMascara().descreve();
		}
		System.out.println("--------------------------------------------------------------");
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof JogoDaVelhaWrapped)) {
			return false;
		}
		JogoDaVelhaWrapped other = (JogoDaVelhaWrapped) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public boolean jogadorVenceriaComEssaJogada(int jogada) {
		
		if(jogada>0){
			
			if(jogada%2==1){//eh jogada do PRIMEIRO jogador, aquele que INICIA O JOGO
				return getTabuleiro().primeiroJogadorFezAlgumaTrinca();
			}else{//ej jogada do 
				return getTabuleiro().segundoJogadorFezAlgumaTrinca();
			}
		}
		
		return false;
	}

	public String getOndeHouveTrinca() {
		return getTabuleiro().getOndeHouveTrinca();		
	}

}
