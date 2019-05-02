package co.com.accenture.model;

import java.util.List;

public class AnswerQuery {

	private List<Movements> movements;

	public AnswerQuery(List<Movements> movements) {
		this.movements = movements;
	}

	public List<Movements> getMovements() {
		return movements;
	}

	public void setClientes(List<Movements> movemets) {
		this.movements = movemets;
	}	
}