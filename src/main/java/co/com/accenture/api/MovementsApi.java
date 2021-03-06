/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package co.com.accenture.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.com.accenture.model.Movements;
import co.com.accenture.model.MovementsByDate;
import co.com.accenture.model.MovementsById;
import co.com.accenture.model.Answer;
import co.com.accenture.model.AnswerQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-10T10:48:28.371-05:00")
@Api(value = "log", description = "the data API")
public interface MovementsApi {
	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Answer.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/guardar", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<Answer> save(@RequestBody Movements movements);

	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful operation", response = AnswerQuery.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/consultarcliente", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<AnswerQuery> getAll();

	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Movements.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/consultar", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<Movements> postById(@RequestBody MovementsById id);

	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Movements.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/consultarfecha", produces = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<AnswerQuery> postByFecha(@RequestBody MovementsByDate date);

	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Answer.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/actualizar", produces = { "application/json" }, method = RequestMethod.PUT)
	ResponseEntity<AnswerQuery> update(@RequestBody Movements movementsUpdate);
	
	@ApiOperation(value = "", nickname = "", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Answer.class),
			@ApiResponse(code = 400, message = "Invalid status value"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(value = "/movimientos/eliminar", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Answer> delete(@RequestBody MovementsById movementsDelete);
}
