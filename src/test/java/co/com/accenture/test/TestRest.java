package co.com.accenture.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.writer.CollectionMapper.MapClass;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)

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

	@SuppressWarnings("unused")
	private List<Map<String, AttributeValue>> listLogs = new ArrayList<>();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void validacion_idCliente_empty() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_idCliente_empty.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}

	@Test
	public void validacion_idCliente_null() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_idCliente_null.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void validacion_tipoId_empty() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_tipoId_empty.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void validacion_tipoId_null() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_tipoId_null.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void validacion_valor_mayor1000() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_valor_mayor1000.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void validacion_valor_null() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_valor_null.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void guarda() throws Exception {

		final InputStream file = getClass().getClassLoader().getResourceAsStream("movimiento_guarda.json");
		Map json = objectMapper.readValue(file, Map.class);
		// System.out.print(json);
		String valjson = new String();
		valjson = json.toString();

		mockMvc.perform(post("/movimientos/guardar").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(valjson)).andExpect(status().isOk());

	}

}
