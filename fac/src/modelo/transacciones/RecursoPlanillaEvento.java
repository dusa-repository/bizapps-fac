package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.RecursoPlanillaEventoId;

@Entity
@Table(name = "recurso_planilla_evento")
public class RecursoPlanillaEvento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RecursoPlanillaEventoId id;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaEvento(RecursoPlanillaEventoId id, Integer solicitado, Integer aprobado) {
		super();
		this.id = id;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

	public RecursoPlanillaEventoId getId() {
		return id;
	}

	public void setId(RecursoPlanillaEventoId id) {
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
