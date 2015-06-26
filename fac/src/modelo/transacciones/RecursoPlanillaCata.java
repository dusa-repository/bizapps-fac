package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.RecursoPlanillaCataId;

@Entity
@Table(name = "recurso_planilla_cata")
public class RecursoPlanillaCata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RecursoPlanillaCataId id;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public RecursoPlanillaCata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecursoPlanillaCata(RecursoPlanillaCataId id,
			Integer solicitado, Integer aprobado) {
		super();
		this.id = id;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

	public RecursoPlanillaCataId getId() {
		return id;
	}

	public void setId(RecursoPlanillaCataId id) {
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
