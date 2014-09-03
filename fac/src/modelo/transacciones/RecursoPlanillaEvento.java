package modelo.transacciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.pk.RecursoPlanillaEventoId;

@Entity
@Table(name = "recurso_planilla_evento")
@IdClass(RecursoPlanillaEventoId.class)
public class RecursoPlanillaEvento {
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recurso", referencedColumnName = "id_recurso")
	private Recurso recurso;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_evento", referencedColumnName = "id_planilla_evento")
	private PlanillaEvento planillaEvento;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaEvento(Recurso recurso,
			PlanillaEvento planillaEvento, Marca marca, Integer solicitado, Integer aprobado) {
		super();
		this.recurso = recurso;
		this.planillaEvento = planillaEvento;
		this.marca = marca;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

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
