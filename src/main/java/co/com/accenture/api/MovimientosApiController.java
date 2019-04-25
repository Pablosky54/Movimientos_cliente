package co.com.accenture.api;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.accenture.model.Movimientos;
import co.com.accenture.model.MovimientosByFecha;
import co.com.accenture.model.MovimientosById;
import co.com.accenture.model.Respuesta;
import co.com.accenture.model.RespuestaConsulta;
import co.com.accenture.repository.MovimientosRepository;

@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-10T10:48:28.371-05:00")
@RestController
public class MovimientosApiController implements MovimientosApi {

	@SuppressWarnings("unused")
	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private MovimientosRepository repository; 

	@Autowired
	private MovimientosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public ResponseEntity<Respuesta> save(@RequestBody Movimientos cliente) {
		repository.save(cliente);
		return new ResponseEntity<Respuesta>(new Respuesta("200"), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Respuesta> validate(@RequestBody MovimientosById id) {		
		return new ResponseEntity<Respuesta>(new Respuesta(repository.valida(id)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> getAll() {
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> update(@RequestBody Movimientos clienteact) {
		repository.actualizar(clienteact);
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> delete(@RequestBody Movimientos clientedel) {
		repository.elimina(clientedel);
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Movimientos> postById(@RequestBody MovimientosById clienteId) {
		return new ResponseEntity<Movimientos>(repository.consulta(clienteId), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Movimientos> postByFecha(@RequestBody MovimientosByFecha fecha) {
		return new ResponseEntity<Movimientos>(repository.consultaFecha(fecha), HttpStatus.OK);
	}

}