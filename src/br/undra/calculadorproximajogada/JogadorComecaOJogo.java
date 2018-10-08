/**
 * 
 */
package br.undra.calculadorproximajogada;

/**
 * @author alexandre
 *
 */
public enum JogadorComecaOJogo {
	
	NUNCA, SIM, NAO;
	
	@Override
	public String toString() {
		if(this == SIM) return "SIM";
		if(this == NAO) return "NAO";
		return super.toString();
	}
	
	public static void main(String[] args) {
		for(JogadorComecaOJogo v : JogadorComecaOJogo.values())
		System.err.println(v.ordinal());
	}

}
