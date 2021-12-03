package co.prosegur.servicioAtestados.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.prosegur.servicioAtestados.Greeting;
import co.prosegur.servicioAtestados.dto.CbrLogAutomacao;
import co.prosegur.servicioAtestados.error.AtestadosNotFoundException;
import co.prosegur.servicioAtestados.error.AtestadosNullPointerException;
import co.prosegur.servicioAtestados.servicios.ServicioAtestados;


@RestController
@RequestMapping(path = "/ApiAtestados", produces = "application/json")
@Validated
public class ApiAtestados {

	@Autowired
	private ServicioAtestados servicioAtestados;
	@Autowired
	BuildProperties buildProperties;
	@Value("${spring.profiles.active:Unknown}")
	private String activeProfile;
	
	
	@GetMapping("/")
	public Map<String, String> getVersion() {
		Map<String, String> result = new HashMap<>();
		result.put("servicio", "servicio_Atestados");
		result.put("version", buildProperties.getVersion());
		result.put("buildTime", buildProperties.getTime().toString());
		result.put("profile", activeProfile);		
		return result;
	}
	
	@GetMapping("/all")
	public List<CbrLogAutomacao> all() {
		try {
			ArrayList<CbrLogAutomacao> logAutomacao = servicioAtestados.all();
			if (logAutomacao == null)
				throw new AtestadosNotFoundException("No se encontraron datos con el filtro seleccionado") ;
			return logAutomacao;
		}catch(NullPointerException ex) {
			throw  new AtestadosNullPointerException("Ocurrio un error durante el procesamiento de la petición generada", ex);
		}
	}
	
	/*
	
	
	@GetMapping("/{idRenuncia}")
	public ResponseEntity<DetalleRenuncia> obtenerDetalle(@PathVariable String idRenuncia) {
		
		DetalleRenuncia det = servicioRenuncia.buscarDetalles(idRenuncia);
		
		return new ResponseEntity<DetalleRenuncia>(det, HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<CbrLogAutomacao> all() {
		
		CbrLogAutomacao det = servicioRenuncia.buscarDetalles(idRenuncia);
		
		return new ResponseEntity<DetalleRenuncia>(det, HttpStatus.OK);
	}
	
	
	private final CbrLogAutomacaoDao resultLogAuto = null;	
	
	@GetMapping("/all")
	//@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String all() {
		
		try {
			List<CbrLogAutomacao> list = resultLogAuto.finAllLotes();
			return list.toString();
		}catch(NullPointerException ex)
		{
			return ex.toString();
		}
		
		/*
		 * 
		 * resultLogAutomacao.findByLote(idLote);
		List<CbrLogAutomacao> list=dao.getAllEmployees();
		
		for(Employee e:list)
			System.out.println(e);*/
	
	
	
	private static final String template = "Hola, %s!";
	private static AtomicLong counter = new AtomicLong();

	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="Mundo") String name) {
		return new Greeting(counter.incrementAndGet(),
						String.format(template, name));
	}
	
	@GetMapping("/obtener")
	//public CbrLogAutomacao buscarLogAutomacao(@PathVariable  String idLote) {
	public CbrLogAutomacao buscarLogAutomacao(@RequestParam(value="lote", defaultValue="1") String idLote) {
		try {
			CbrLogAutomacao logAutomacao = servicioAtestados.buscarLog(idLote);
			if (logAutomacao == null)
				throw null ;
			return logAutomacao;
		}catch(NullPointerException ex) {
			throw  ex;// System.out.println("Ocurrio un error: " + ex);
		}
	}
}
