package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaFachada;
@Embeddable
public class RecursoPlanillaFachadaId implements Serializable {
	
	private static final long serialVersionUID = 2024696051830945480L;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recurso", referencedColumnName = "id_recurso")
	private Recurso recurso;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_fachada", referencedColumnName = "id_planilla_fachada")
	private PlanillaFachada planillaFachada;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	public RecursoPlanillaFachadaId() {
		super();
		// TODO Auto-generated constructor stub
	}
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
