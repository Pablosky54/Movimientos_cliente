package co.com.accenture.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.accenture.RunApplication;
import co.com.accenture.model.Movimientos;
import co.com.accenture.model.MovimientosById;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RunApplication.class)
public class TestRest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private DynamoDBMapper mapper;

	@Mock
	private AmazonDynamoDB client;

	@Mock
	private QueryResult result;

	@Mock
	private Movimientos clientes;

	@Mock
	private Table dynamoDb;

	@SuppressWarnings("unused")
	private List<Map<String, AttributeValue>> listLogs = new ArrayList<>();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/*
	 * @Test public void validacion_idCliente_empty() throws Exception {
	 * 
	 * final InputStream file = getClass().getClassLoader().getResourceAsStream(
	 * "movimiento_idCliente_empty.json"); Movimientos json =
	 * objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void validacion_idCliente_null() throws Exception {
	 * 
	 * final InputStream file = getClass().getClassLoader().getResourceAsStream(
	 * "movimiento_idCliente_null.json"); Movimientos json =
	 * objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void validacion_tipoId_empty() throws Exception {
	 * 
	 * final InputStream file = getClass().getClassLoader().getResourceAsStream(
	 * "movimiento_tipoId_empty.json"); Movimientos json =
	 * objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void validacion_tipoId_null() throws Exception {
	 * 
	 * final InputStream file =
	 * getClass().getClassLoader().getResourceAsStream("movimiento_tipoId_null.json"
	 * ); Movimientos json = objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void validacion_valor_mayor1000() throws Exception {
	 * 
	 * final InputStream file = getClass().getClassLoader().getResourceAsStream(
	 * "movimiento_valor_mayor1000.json"); Movimientos json =
	 * objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void validacion_valor_null() throws Exception {
	 * 
	 * final InputStream file =
	 * getClass().getClassLoader().getResourceAsStream("movimiento_valor_null.json")
	 * ; Movimientos json = objectMapper.readValue(file, Movimientos.class);
	 * 
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isBadRequest());
	 * 
	 * }
	 * 
	 * @Test public void guarda() throws Exception {
	 * 
	 * final InputStream file =
	 * getClass().getClassLoader().getResourceAsStream("movimiento_guarda.json");
	 * Movimientos movimiento = objectMapper.readValue(file, Movimientos.class);
	 * 
	 * Mockito.doAnswer(new Answer<Void>() { public Void answer(InvocationOnMock
	 * invocation) { return null; } }).when(mapper).save(movimiento);
	 * 
	 * mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(movimiento))).
	 * andExpect(status().isOk());
	 * 
	 * }
	 * 
	 * @Test public void consulta() throws Exception {
	 * 
	 * 
	 * 
	 * mockMvc.perform(get("/movimientos/consultarcliente").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	 * 
	 * }
	 * 
	 * 
	 * @Test public void consulta_fecha() throws Exception {
	 * 
	 * List<Map<String, AttributeValue>> items = new ArrayList<>(); Map<String,
	 * String> expressionAttributesNames = new HashMap<>();
	 * 
	 * expressionAttributesNames.put("#Fecha", "Fecha"); Map<String, AttributeValue>
	 * expressionAttributeValues = new HashMap<>();
	 * 
	 * final InputStream file =
	 * getClass().getClassLoader().getResourceAsStream("fecha.json"); Movimientos
	 * json = objectMapper.readValue(file, Movimientos.class);
	 * 
	 * expressionAttributeValues.put(":Fecha", new
	 * AttributeValue().withS(json.getFecha())); QueryRequest queryRequest = new
	 * QueryRequest()
	 * .withKeyConditionExpression("#Fecha = :Fecha ").withIndexName("Fecha-index")
	 * .withExpressionAttributeNames(expressionAttributesNames)
	 * .withExpressionAttributeValues(expressionAttributeValues);
	 * 
	 * Mockito.doAnswer(new Answer<Void>() { public Void answer(InvocationOnMock
	 * invocation) { return null; } }).when(client).query(queryRequest);
	 * 
	 * mockMvc.perform(post("/movimientos/consultarfecha").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(
	 * status().isOk());
	 * 
	 * }
	 

	@Test
	public void actualizar() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento.json");
		Movimientos json = objectMapper.readValue(file, Movimientos.class);

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", json.getId())
				.withUpdateExpression("set Productos = :p, TipoId=:t,IdCliente=:c")
				.withValueMap(new ValueMap().withString(":p", json.getProducto()).withString(":t", json.getTipoId())
						.withString(":c", json.getIdCliente()))
				.withReturnValues(ReturnValue.UPDATED_NEW);
				dynamoDb.updateItem(updateItemSpec);

		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(dynamoDb).updateItem(updateItemSpec);

		mockMvc.perform(put("/movimientos/actualizar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(status().isOk());

	}*/
	
	@Test
	public void consultar() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("id.json");
		MovimientosById json = objectMapper.readValue(file, MovimientosById.class);

		Map<String, Condition> keyConditions = new HashMap<>();
		keyConditions.put("IdMovimiento", new Condition().withAttributeValueList(new AttributeValue(json.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));

		QueryRequest queryRequest = new QueryRequest().withConsistentRead(true)
				.withKeyConditions(keyConditions);

		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(client).query(queryRequest);

		mockMvc.perform(post("/movimientos/consultar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(status().isOk());

	}
	
	@Test
	public void elimina() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("id.json");
		MovimientosById json = objectMapper.readValue(file, MovimientosById.class);

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", json.getId());
		DeleteItemSpec deleteSpec = new DeleteItemSpec().withPrimaryKey("IdMovimiento", json.getId());
		
		
		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				return null;
			}
		}).when(dynamoDb).deleteItem(deleteSpec);

		mockMvc.perform(delete("/movimientos/eliminar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(json))).andExpect(status().isOk());

	}


	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
