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
import modelo.pk.RecursoPlanillaFachadaId;

@Entity
@Table(name = "recurso_planilla_fachada")
@IdClass(RecursoPlanillaFachadaId.class)
public class RecursoPlanillaFachada {
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recurso", referencedColumnName = "id_recurso")
	private Recurso recurso;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_fachada", referencedColumnName = "id_planilla_fachada")
	private PlanillaFachada planillaFachada;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaFachada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaFachada(Recurso recurso,
			PlanillaFachada planillaFachada, Integer solicitado,
			Integer aprobado) {
		super();
		this.recurso = recurso;
		this.planillaFachada = planillaFachada;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
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
