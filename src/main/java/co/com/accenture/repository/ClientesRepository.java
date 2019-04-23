package co.com.accenture.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
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
import com.amazonaws.services.dynamodbv2.model.ScanRequest;

import co.com.accenture.model.Clientes;
import co.com.accenture.model.ClientesById;

@Repository
public class ClientesRepository {

	private final DynamoDBMapperConfig configs = new DynamoDBMapperConfig.Builder()
			.withTableNameOverride(TableNameOverride.withTableNameReplacement("Clientes")).build();

	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8081", "us-east-1"))
			.build();
	private DynamoDBMapper mapper = new DynamoDBMapper(client, configs);

	public void save(Clientes cliente) {

		mapper.save(cliente);

	}

	public List<Clientes> getClientes() {
		ScanRequest request = new ScanRequest("Clientes");
		return getClientes(client.scan(request).getItems()); 
	}

	public List<Clientes> getClientes(List<Map<String, AttributeValue>> items) {
		List<Clientes> logs = new ArrayList<>();

		if (!items.isEmpty()) {

			for (Map<String, AttributeValue> item : items) {
				Clientes log = new Clientes(item);

				logs.add(log);
			}
		}

		return logs;
	}

	public Clientes consulta(ClientesById clienteId) {

		Map<String, Condition> keyConditions = new HashMap<>();
		keyConditions.put("Id", new Condition().withAttributeValueList(new AttributeValue(clienteId.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));

		QueryRequest request = new QueryRequest("Clientes").withConsistentRead(true).withKeyConditions(keyConditions);

		QueryResult result = client.query(request);
		List<Clientes> list = getClientes(result.getItems());

		System.out.println(result.getItems().toString());
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);

		}

	}

	public void elimina(Clientes clientedel) {
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Clientes");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", clientedel.getId());

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			DeleteItemSpec deleteSpec = new DeleteItemSpec().withPrimaryKey("Id", clientedel.getId());
			AttributeValue item = new AttributeValue();
			table.deleteItem(deleteSpec).getDeleteItemResult().addAttributesEntry(clientedel.getId(), item);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Unable to update item: " + clientedel.getId() + " ");
			System.err.println(e.getMessage());
		}
	}

	public void actualizar(Clientes clienteact) {
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Clientes");

		// String id = "000ecc62-f2d9-4b87-94b3-a4c65e698bbe";

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", clienteact.getId())
				.withUpdateExpression(
						"set Nombres = :n, Apellidos=:a,Telefono=:t,Celular=:cel,Direccion=:d,Genero=:g, Ciudad=:c,Empresa=:e")
				.withValueMap(new ValueMap().withString(":n", clienteact.getNombres())
						.withString(":a", clienteact.getApellidos()).withString(":c", clienteact.getCiudad())
						.withString(":t", clienteact.getTelefono()).withString(":cel", clienteact.getCelular())
						.withString(":d", clienteact.getDireccion()).withString(":g", clienteact.getGenero())
						.withString(":e", clienteact.getEmpresa()))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Unable to update item: " + clienteact.getId() + " ");
			System.err.println(e.getMessage());
		}

	}

}
