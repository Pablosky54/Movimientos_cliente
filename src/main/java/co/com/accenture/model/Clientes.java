package co.com.accenture.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerated;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedTimestamp;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

@DynamoDBTable(tableName = "Movimientos")
public class Clientes {

	@DynamoDBHashKey(attributeName = "IdMovimiento")
	@DynamoDBAutoGeneratedKey
	private String id;

	@DynamoDBAttribute(attributeName = "Producto")
	private String producto;

	@DynamoDBAttribute(attributeName = "ValorTransaccion")
	private String valor;

	@DynamoDBAttribute(attributeName = "TipoId")
	private String tipoId;

	@DynamoDBAttribute(attributeName = "IdCliente")
	private String idCliente;

	@DynamoDBAutoGeneratedTimestamp
	@DynamoDBAttribute(attributeName = "Fecha")
	private String fecha;
	
	

	public Clientes(Map<String, AttributeValue> item) {
		this.id = item.get("IdMovimiento") == null ? null : item.get("IdMovimiento").getS();
		this.producto = item.get("Producto") == null ? null : item.get("Producto").getS();
		this.valor = item.get("ValorTransaccion") == null ? null : item.get("ValorTransaccion").getS();
		this.tipoId = item.get("TipoId") == null ? null : item.get("TipoId").getS();
		this.idCliente = item.get("IdCliente") == null ? null : item.get("IdCliente").getS();
		this.fecha = item.get("Fecha") == null ? null : item.get("Fecha").getS();
		
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getProducto() {
		return producto;
	}



	public void setProducto(String producto) {
		this.producto = producto;
	}



	public String getValor() {
		return valor;
	}



	public void setValor(String valor) {
		this.valor = valor;
	}



	public String getTipoId() {
		return tipoId;
	}



	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}



	public String getIdCliente() {
		return idCliente;
	}



	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public Clientes() {
		super();
	}

}
