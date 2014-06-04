package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaEvento;

public class RecursoPlanillaEventoId implements Serializable {

	private static final long serialVersionUID = -5646288851915981096L;
	
	private Recurso recurso;
	private PlanillaEvento planillaEvento;
	
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
	
}
