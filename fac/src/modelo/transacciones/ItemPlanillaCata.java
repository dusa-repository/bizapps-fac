package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.ItemPlanillaCataId;

@Entity
@Table(name = "item_planilla_cata")
public class ItemPlanillaCata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPlanillaCataId id;

	@Column(name = "solicitado")
	private Integer solicitado;

	@Column(name = "aprobado")
	private Integer aprobado;

	public ItemPlanillaCata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemPlanillaCata(ItemPlanillaCataId id, Integer solicitado,
			Integer aprobado) {
		super();
		this.id = id;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

	public ItemPlanillaCataId getId() {
		return id;
	}

	public void setId(ItemPlanillaCataId id) {
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
