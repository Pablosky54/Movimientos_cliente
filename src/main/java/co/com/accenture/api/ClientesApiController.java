package co.com.accenture.api;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.accenture.model.Clientes;
import co.com.accenture.model.ClientesById;
import co.com.accenture.model.Respuesta;
import co.com.accenture.model.RespuestaConsulta;
import co.com.accenture.repository.ClientesRepository;

@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-10T10:48:28.371-05:00")
@RestController
public class ClientesApiController implements ClientesApi {

	@SuppressWarnings("unused")
	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private ClientesRepository repository; 

	@Autowired
	private ClientesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public ResponseEntity<Respuesta> save(@RequestBody Clientes cliente) {
		repository.save(cliente);
		return new ResponseEntity<Respuesta>(new Respuesta("200"), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> getAll() {
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> update(@RequestBody Clientes clienteact) {
		repository.actualizar(clienteact);
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<RespuestaConsulta> delete(@RequestBody Clientes clientedel) {
		repository.elimina(clientedel);
		return new ResponseEntity<RespuestaConsulta>(new RespuestaConsulta(repository.getClientes()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Clientes> postById(@RequestBody ClientesById clienteId) {
		return new ResponseEntity<Clientes>(repository.consulta(clienteId), HttpStatus.OK);
	}

}