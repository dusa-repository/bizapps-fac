package modelo.transacciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.maestros.Recurso;
import modelo.pk.RecursoPlanillaCataId;

@Entity
@Table(name = "recurso_planilla_cata")
@IdClass(RecursoPlanillaCataId.class)
public class RecursoPlanillaCata {

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recurso", referencedColumnName = "id_recurso")
	private Recurso recurso;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_cata", referencedColumnName = "id_planilla_cata")
	private PlanillaCata planillaCata;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaCata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaCata(Recurso recurso, PlanillaCata planillaCata,
			Integer solicitado, Integer aprobado) {
		super();
		this.recurso = recurso;
		this.planillaCata = planillaCata;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
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

	public Integer getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(Integer solicitado) {
		this.solicitado = solicitado;
	}

	public Integer getAprobado() {
		return aprobado;
	}

	public void setAprobado(Integer aprobado) {
		this.aprobado = aprobado;
	}
	
}
