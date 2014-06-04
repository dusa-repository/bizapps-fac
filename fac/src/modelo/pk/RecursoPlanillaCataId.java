package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaCata;

public class RecursoPlanillaCataId implements Serializable {

	private static final long serialVersionUID = -9101823487254670473L;
	
	private Recurso recurso;
	private PlanillaCata planillaCata;
	
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	public PlanillaCata getPlanillaCata() {
		return planillaCata;
	}
	public void setPlanillaCata(PlanillaCata planillaCata) {
		this.planillaCata = planillaCata;
	}
	
	

}
