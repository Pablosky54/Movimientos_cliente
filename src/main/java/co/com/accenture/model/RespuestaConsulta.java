package co.com.accenture.model;

import java.util.List;

public class RespuestaConsulta {

	private List<Clientes> clientes;

	public RespuestaConsulta(List<Clientes> clientes) {
		this.clientes = clientes;
	}

	public List<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(List<Clientes> clientes) {
		this.clientes = clientes;
	}
	
}