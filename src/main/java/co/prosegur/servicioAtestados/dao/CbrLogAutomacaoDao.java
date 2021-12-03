package co.prosegur.servicioAtestados.dao;

import java.util.ArrayList;

import co.prosegur.servicioAtestados.dto.CbrLogAutomacao;

public interface CbrLogAutomacaoDao {

	CbrLogAutomacao findByLote(String idLote);

	ArrayList<CbrLogAutomacao> finAllLotes();

}