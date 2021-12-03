package co.prosegur.servicioAtestados.error;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Segura
 *
 *@SuppressWarnings("serial") : Se utiliza para suprimir advertencias, en este caso permite mostrar únicamente las advertencias de dicha clase
 *
 */

@Getter
@Setter
@SuppressWarnings("serial")
public class AtestadosNotFoundException extends RuntimeException{

	//private int status;
	private String lote;
	
	public AtestadosNotFoundException (String lote) {		
		this.lote = lote;		
	}
		
}
