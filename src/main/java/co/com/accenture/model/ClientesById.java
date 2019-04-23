package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Clientes")
public class ClientesById {

	@DynamoDBAttribute(attributeName = "Id")
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ClientesById(Map<String, AttributeValue> item) {
		this.id = item.get("Id") == null ? null : item.get("Id").getS();
		
	}

	public ClientesById() {
		super();
		
	}

	
	
}
