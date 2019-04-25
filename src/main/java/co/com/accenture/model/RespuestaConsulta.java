package co.com.accenture.model;

import java.util.List;

public class RespuestaConsulta {

	private List<Movimientos> clientes;

	public RespuestaConsulta(List<Movimientos> clientes) {
		this.clientes = clientes;
	}

	public List<Movimientos> getClientes() {
		return clientes;
	}

	public void setClientes(List<Movimientos> clientes) {
		this.clientes = clientes;
	}
	
}