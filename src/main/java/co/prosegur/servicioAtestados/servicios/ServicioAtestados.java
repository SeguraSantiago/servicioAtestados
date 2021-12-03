package co.prosegur.servicioAtestados.servicios;

import java.util.ArrayList;

import co.prosegur.servicioAtestados.dto.CbrLogAutomacao;

public interface ServicioAtestados {
	
	CbrLogAutomacao buscarLog(String idLote);

	ArrayList<CbrLogAutomacao> all();
}