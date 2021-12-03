package co.prosegur.servicioAtestados.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import co.prosegur.servicioAtestados.dao.CbrLogAutomacaoDao;
import co.prosegur.servicioAtestados.dto.CbrLogAutomacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Repository
public class CbrLogAutomacaoDaoOracleImpl implements CbrLogAutomacaoDao {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public CbrLogAutomacaoDaoOracleImpl(NamedParameterJdbcTemplate ds) {
		this.jdbcTemplate = ds;
	}
	
	//Forzar en tiempo de compilación que realmente esta sobrescribiendo correctamente un método
	@Override
	public ArrayList<CbrLogAutomacao> finAllLotes(){
		
		return (ArrayList<CbrLogAutomacao>) jdbcTemplate
					.query("SELECT id_organization, id_lote, registros, dt_ejecucion, estado, descripcion,  id_approle, id_secuser, dt_last_update FROM CBR_LOG_AUTOMACAO",
								new ResultSetExtractor<List<CbrLogAutomacao>>(){
			@Override
			public List<CbrLogAutomacao> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				
				List<CbrLogAutomacao> list= new ArrayList<CbrLogAutomacao>();
				while(rs.next()){						
					CbrLogAutomacao resultLogAutomacao = CbrLogAutomacao.ofAll(
							rs.getString("id_organization"),
							rs.getString("id_lote"),
							rs.getInt("registros"),
							rs.getTimestamp("dt_ejecucion"),
							rs.getString("estado"),
							rs.getString("descripcion"),
						//	rs.getString("json"),
							rs.getString("id_approle"),
							rs.getString("id_secuser"),							
							rs.getTimestamp("dt_last_update"));
							list.add(resultLogAutomacao);
				}
				return list;
			}
		});
	}
		
	@Override
	public CbrLogAutomacao findByLote(String idLote) {		

	log.info("consulta a base idRenuncia");
	log.debug("findById():"+idLote);
		return jdbcTemplate.query("SELECT id_organization, id_lote, registros, dt_ejecucion, estado, descripcion,  id_approle, id_secuser, dt_last_update FROM CBR_LOG_AUTOMACAO A WHERE A.ID_LOTE= :idLote", 
				new MapSqlParameterSource().addValue("idLote", idLote), new ResultSetExtractor<CbrLogAutomacao>() {									
					@Override
					public CbrLogAutomacao extractData(ResultSet rs) throws SQLException, DataAccessException {

						//Resultado
						CbrLogAutomacao resultLogAutomacao = null;
						//El ResultSet es FOWARD_ONLY, haciendo un map se facilita crear el objeto padre que se repite en todos los registros
						Map<String, CbrLogAutomacao> mapLog  = new HashMap<>();
		
						while(rs.next()) {
							resultLogAutomacao = mapLog.get(rs.getString("idLote"));
							if(resultLogAutomacao == null) {
								//La lista de datos (objeto padre, se crea una sola vez)
								resultLogAutomacao = CbrLogAutomacao.ofAll(
									rs.getString("id_organization"),
									rs.getString("id_lote"),
									rs.getInt("registros"),
									rs.getTimestamp("dt_ejecucion"),
									rs.getString("estado"),
									rs.getString("descripcion"),
								//	rs.getString("json"),
									rs.getString("id_approle"),
									rs.getString("id_secuser"),							
									rs.getTimestamp("dt_last_update"));
								mapLog.put(rs.getString("idLote"), resultLogAutomacao);
							}				
						}			
						//System.out.println(rs.toString());
						return resultLogAutomacao;			
					}								
								
				});
	}
	
}
