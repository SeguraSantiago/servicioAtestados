package co.prosegur.servicioAtestados.servicios.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.prosegur.servicioAtestados.dao.CbrLogAutomacaoDao;
import co.prosegur.servicioAtestados.dto.CbrLogAutomacao;
import co.prosegur.servicioAtestados.servicios.ServicioAtestados;

@Service
public class ServicioAtestadosImpl implements ServicioAtestados{

	private final CbrLogAutomacaoDao resultLogAutomacao;
	/*private final EstadoRenunciaDAO estRenDao;
	private final UltimaLiquidacionDAO ultLiqDao;*/
	
	@Autowired
	public ServicioAtestadosImpl(CbrLogAutomacaoDao resultLogAutomacao) {
		this.resultLogAutomacao = resultLogAutomacao;
	}
	
	
	@Override
	public CbrLogAutomacao buscarLog(String idLote) {		
		return resultLogAutomacao.findByLote(idLote);
	}

	
	@Override
	public ArrayList<CbrLogAutomacao> all() {
		return resultLogAutomacao.finAllLotes();		 
	}
	
/*
	@Override
	public ArrayList<DetalleRenuncia> listadoRenuncias() {
		return dao.listAllRenuncias();
	}*/
}
