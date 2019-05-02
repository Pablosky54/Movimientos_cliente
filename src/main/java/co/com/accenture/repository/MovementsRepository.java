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

import co.com.accenture.model.Movements;
import co.com.accenture.model.MovementsByDate;
import co.com.accenture.model.MovementsById;

@Repository
public class MovementsRepository {

	private final DynamoDBMapperConfig configs = new DynamoDBMapperConfig.Builder()
			.withTableNameOverride(TableNameOverride.withTableNameReplacement("Movimientos")).build();
	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8081", "us-east-1"))
			.build();
	private DynamoDBMapper mapper = new DynamoDBMapper(client, configs);

	public void save(Movements movements) {
		mapper.save(movements);
	}

	public List<Movements> getMovements() {
		ScanRequest request = new ScanRequest("Movimientos");
		return getMovements(client.scan(request).getItems());
	}

	public List<Movements> getMovements(List<Map<String, AttributeValue>> items) {
		List<Movements> logs = new ArrayList<>();
		if (!items.isEmpty()) {
			for (Map<String, AttributeValue> item : items) {
				Movements log = new Movements(item);
				logs.add(log);
			}
		}
		return logs;
	}

	public Movements search(MovementsById movementsId) {
		Map<String, Condition> keyConditions = new HashMap<>();
		keyConditions.put("IdMovimiento",
				new Condition().withAttributeValueList(new AttributeValue(movementsId.getId()))
						.withComparisonOperator(ComparisonOperator.EQ));
		QueryRequest request = new QueryRequest("Movimientos").withConsistentRead(true)
				.withKeyConditions(keyConditions);
		QueryResult result = client.query(request);
		List<Movements> list = getMovements(result.getItems());
		System.out.println(result.getItems().toString());
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public List<Movements> searchDate(MovementsByDate movementsDate) {
		List<Map<String, AttributeValue>> items = new ArrayList<>();
		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#Fecha", "Fecha");
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":Fecha", new AttributeValue().withS(movementsDate.getDate()));
		QueryRequest queryRequest = new QueryRequest().withTableName("Movimientos")
				.withKeyConditionExpression("#Fecha = :Fecha ").withIndexName("Fecha-index")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues);
		QueryResult queryResult = client.query(queryRequest);
		List<Movements> list = getMovements(queryResult.getItems());
		System.out.println(queryResult.getItems().toString());
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}

	public void delete(MovementsById movementsDelete) {
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Movimientos");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", movementsDelete.getId());
		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			DeleteItemSpec deleteSpec = new DeleteItemSpec().withPrimaryKey("IdMovimiento", movementsDelete.getId());
			AttributeValue item = new AttributeValue();
			table.deleteItem(deleteSpec).getDeleteItemResult().addAttributesEntry(movementsDelete.getId(), item);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
		} catch (Exception e) {
			System.err.println("Unable to update item: " + movementsDelete.getId() + " ");
			System.err.println(e.getMessage());
		}
	}

	public void update(Movements movementsUpdate) {
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Movimientos");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("IdMovimiento", movementsUpdate.getId())
				.withUpdateExpression("set Productos = :p, TipoId=:t,IdCliente=:c")
				.withValueMap(new ValueMap().withString(":p", movementsUpdate.getProduct())
						.withString(":t", movementsUpdate.getTypeId()).withString(":c", movementsUpdate.getIdClient()))
				.withReturnValues(ReturnValue.UPDATED_NEW);
		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
		} catch (Exception e) {
			System.err.println("Unable to update item: " + movementsUpdate.getId() + " ");
			System.err.println(e.getMessage());
		}
	}
}
