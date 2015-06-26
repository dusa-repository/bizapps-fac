package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaCata;
@Embeddable
public class RecursoPlanillaCataId implements Serializable {

	private static final long serialVersionUID = -9101823487254670473L;
	

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recurso", referencedColumnName = "id_recurso")
	private Recurso recurso;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_cata", referencedColumnName = "id_planilla_cata")
	private PlanillaCata planillaCata;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	public RecursoPlanillaCataId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
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
