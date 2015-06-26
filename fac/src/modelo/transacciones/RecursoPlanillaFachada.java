package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.RecursoPlanillaFachadaId;

@Entity
@Table(name = "recurso_planilla_fachada")
public class RecursoPlanillaFachada implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RecursoPlanillaFachadaId id;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaFachada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaFachada(RecursoPlanillaFachadaId id, Integer solicitado,
			Integer aprobado) {
		super();
		this.id = id;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

	public RecursoPlanillaFachadaId getId() {
		return id;
	}

	public void setId(RecursoPlanillaFachadaId id) {
		this.id = id;
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
