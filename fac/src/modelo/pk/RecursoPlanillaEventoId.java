package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaEvento;

public class RecursoPlanillaEventoId implements Serializable {

	private static final long serialVersionUID = -5646288851915981096L;
	
	private Recurso recurso;
	private PlanillaEvento planillaEvento;
	private Marca marca;
	
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	public PlanillaEvento getPlanillaEvento() {
		return planillaEvento;
	}
	public void setPlanillaEvento(PlanillaEvento planillaEvento) {
		this.planillaEvento = planillaEvento;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
}
