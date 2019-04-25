package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Movimientos")
public class MovimientosById {

	@DynamoDBAttribute(attributeName = "IdMovimiento")
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MovimientosById(Map<String, AttributeValue> item) {
		this.id = item.get("IdMovimiento") == null ? null : item.get("IdMovimiento").getS();
		
	}

	public MovimientosById() {
		super();
		
	}

	
	
}
