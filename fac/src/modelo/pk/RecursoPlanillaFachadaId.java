package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaFachada;

public class RecursoPlanillaFachadaId implements Serializable {
	
	private static final long serialVersionUID = 2024696051830945480L;
	
	private Recurso recurso;
	private PlanillaFachada planillaFachada;
	private Marca marca;
	
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	public PlanillaFachada getPlanillaFachada() {
		return planillaFachada;
	}
	public void setPlanillaFachada(PlanillaFachada planillaFachada) {
		this.planillaFachada = planillaFachada;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	

}
