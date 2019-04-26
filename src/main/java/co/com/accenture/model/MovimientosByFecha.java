package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Movimientos")
public class MovimientosByFecha {

	@DynamoDBAttribute(attributeName = "Fecha")
	private String fecha;	

	public MovimientosByFecha(Map<String, AttributeValue> item) {
		this.fecha = item.get("Fecha") == null ? null : item.get("Fecha").getS();
		
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public MovimientosByFecha() {
		super();
		
	}

	
	
}
