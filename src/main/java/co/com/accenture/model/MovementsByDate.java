package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Movimientos")
public class MovementsByDate {

	@DynamoDBAttribute(attributeName = "Fecha")
	private String date;

	public MovementsByDate(Map<String, AttributeValue> item) {
		this.date = item.get("Fecha") == null ? null : item.get("Fecha").getS();
	}

	public String getDate() {
		return date;
	}

	public void setFecha(String date) {
		this.date = date;
	}

	public MovementsByDate() {
		super();

	}

}
