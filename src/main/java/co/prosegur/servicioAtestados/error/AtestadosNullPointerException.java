package co.prosegur.servicioAtestados.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class AtestadosNullPointerException extends RuntimeException {

	private String lote;
	private Throwable ex;
	
	public AtestadosNullPointerException( String lote, Throwable ex){
		super("Lote(" + lote +"): " + ex.getLocalizedMessage(), ex);
		this.lote = lote;
		this.ex = ex;
	}	
	
}