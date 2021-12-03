package co.prosegur.servicioAtestados.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/***** 
 * 
 * @author Segura
 * Log Detallado 
 */

@Data
@AllArgsConstructor(staticName = "ofAll")
@RequiredArgsConstructor(staticName = "ofRequired")
public class CbrLogSoliciAut {

	@NonNull private String idOrganization;
	@NonNull private String idLote;
	private String idSolicitud;
	private Integer idProceso;
	private String estado;
	private String descripcion;
	private String idApprole;
	private String idSecuser;
	private Date dtLastUpdate;		
}