package co.prosegur.servicioAtestados.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/***** 
 * 
 * @author Segura
 * Log Principal 
 */

@Data
@AllArgsConstructor(staticName = "ofAll")
@RequiredArgsConstructor(staticName = "ofRequired")
public class CbrLogAutomacao {

	@NonNull private String idOrganization;
	@NonNull private String idLote;
	private Integer registros;
	private Date dtEjecucion;
	private String estado;
	private String descripcion;
	//private String json;
	private String idApprole;
	private String idSecuser;
	private Date dtLastUpdate;
}