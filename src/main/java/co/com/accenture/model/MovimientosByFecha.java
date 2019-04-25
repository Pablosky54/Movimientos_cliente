package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Movimientos")
public class MovimientosByFecha {

	@DynamoDBAttribute(attributeName = "IdCliente")
	private String idCliente;	

	public MovimientosByFecha(Map<String, AttributeValue> item) {
		this.idCliente = item.get("IdCliente") == null ? null : item.get("IdCliente").getS();
		
	}

	public String getIdCliente() {
		return idCliente;
	}



	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}



	public MovimientosByFecha() {
		super();
		
	}

	
	
}
