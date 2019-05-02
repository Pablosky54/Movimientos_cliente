package co.com.accenture.api;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.accenture.model.Answer;
import co.com.accenture.model.AnswerQuery;
import co.com.accenture.model.Movements;
import co.com.accenture.model.MovementsByDate;
import co.com.accenture.model.MovementsById;
import co.com.accenture.repository.MovementsRepository;

@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-10T10:48:28.371-05:00")
@RestController
public class MovementsApiController implements MovementsApi {

	@SuppressWarnings("unused")
	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;
	
	@Autowired
	private MovementsRepository repository; 
	
	@Autowired
	private MovementsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public ResponseEntity<Answer> save(@Valid @RequestBody Movements movement) {
		repository.save(movement);
		return new ResponseEntity<Answer>(new Answer("200"), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<AnswerQuery> getAll() {
		return new ResponseEntity<AnswerQuery>(new AnswerQuery(repository.getMovements()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AnswerQuery> update(@Valid @RequestBody Movements movementUpdate) {
		repository.update(movementUpdate);
		return new ResponseEntity<AnswerQuery>(new AnswerQuery(repository.getMovements()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Answer> delete(@RequestBody MovementsById movementsDelete) {
		repository.delete(movementsDelete);
		return new ResponseEntity<Answer>(new Answer("200"), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Movements> postById(@RequestBody MovementsById movementsId) {
		return new ResponseEntity<Movements>(repository.search(movementsId), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<AnswerQuery> postByFecha(@RequestBody MovementsByDate date) {
		return new ResponseEntity<AnswerQuery>(new AnswerQuery(repository.searchDate(date)), HttpStatus.OK);
	}

}