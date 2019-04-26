package co.com.accenture.repository;

import java.math.BigDecimal;
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

import co.com.accenture.model.Movimientos;
import co.com.accenture.model.MovimientosByFecha;
import co.com.accenture.model.MovimientosById;

@Repository
public class MovimientosRepository {

	private final DynamoDBMapperConfig configs = new DynamoDBMapperConfig.Builder()
			.withTableNameOverride(TableNameOverride.withTableNameReplacement("Movimientos")).build();

	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8081", "us-east-1"))
			.build();
	private DynamoDBMapper mapper = new DynamoDBMapper(client, configs);

	public void save(Movimientos cliente) {

		mapper.save(cliente);

	}

	public List<Movimientos> getClientes() {
		ScanRequest request = new ScanRequest("Movimientos");
		return getClientes(client.scan(request).getItems());
	}

	public List<Movimientos> getClientes(List<Map<String, AttributeValue>> items) {
		List<Movimientos> logs = new ArrayList<>();

		if (!items.isEmpty()) {

			for (Map<String, AttributeValue> item : items) {
				Movimientos log = new Movimientos(item);

				logs.add(log);
			}
		}

		return logs;
	}

	public Movimientos consulta(MovimientosById clienteId) {

		Map<String, Condition> keyConditions = new HashMap<>();
		keyConditions.put("IdMovimiento", new Condition().withAttributeValueList(new AttributeValue(clienteId.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));

		QueryRequest request = new QueryRequest("Movimientos").withConsistentRead(true)
				.withKeyConditions(keyConditions);

		QueryResult result = client.query(request);
		List<Movimientos> list = getClientes(result.getItems());

		System.out.println(result.getItems().toString());
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);

		}

	}

	public List<Movimientos> consultaFecha(MovimientosByFecha clienteFecha) {

		List<Map<String, AttributeValue>> items = new ArrayList<>();
		Map<String, String> expressionAttributesNames = new HashMap<>();

		expressionAttributesNames.put("#Fecha", "Fecha");
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();

		expressionAttributeValues.put(":Fecha", new AttributeValue().withS(clienteFecha.getFecha()));
		QueryRequest queryRequest = new QueryRequest().withTableName("Movimientos")
				.withKeyConditionExpression("#Fecha = :Fecha ").withIndexName("Fecha-index")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues);
		QueryResult queryResult = client.query(queryRequest);
		List<Movimientos> list = getClientes(queryResult.getItems());

		System.out.println(queryResult.getItems().toString());

		if (list.isEmpty()) {
			return null;
		} else {

			return list;

		}

	}

	public void elimina(MovimientosById clientedel) {
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Movimientos");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", clientedel.getId());

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			DeleteItemSpec deleteSpec = new DeleteItemSpec().withPrimaryKey("IdMovimiento", clientedel.getId());
			AttributeValue item = new AttributeValue();
			table.deleteItem(deleteSpec).getDeleteItemResult().addAttributesEntry(clientedel.getId(), item);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Unable to update item: " + clientedel.getId() + " ");
			System.err.println(e.getMessage());
		}
	}

	public void actualizar(Movimientos clienteact) {
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Movimientos");

		// String id = "000ecc62-f2d9-4b87-94b3-a4c65e698bbe";

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", clienteact.getId())
				.withUpdateExpression("set Productos = :p, TipoId=:t,IdCliente=:c")
				.withValueMap(new ValueMap().withString(":p", clienteact.getProducto())
						.withString(":t", clienteact.getTipoId()).withString(":c", clienteact.getIdCliente()))
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
