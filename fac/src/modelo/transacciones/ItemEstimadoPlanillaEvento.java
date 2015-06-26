package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.ItemEstimadoPlanillaEventoId;

@Entity
@Table(name = "item_estimado_planilla_evento")
public class ItemEstimadoPlanillaEvento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemEstimadoPlanillaEventoId id;
	
	@Column(name = "estimado")
	private Integer estimado;

	public ItemEstimadoPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemEstimadoPlanillaEvento(ItemEstimadoPlanillaEventoId id,
			Integer estimado) {
		super();
		this.id = id;
		this.estimado = estimado;
	}

	public ItemEstimadoPlanillaEventoId getId() {
		return id;
	}

	public void setId(ItemEstimadoPlanillaEventoId id) {
		this.id = id;
	}

	public Integer getEstimado() {
		return estimado;
	}

	public void setEstimado(Integer estimado) {
		this.estimado = estimado;
	}
	
}
