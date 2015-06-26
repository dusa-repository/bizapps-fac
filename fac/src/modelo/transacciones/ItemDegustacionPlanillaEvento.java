package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.ItemDegustacionPlanillaEventoId;


@Entity
@Table(name = "item_degustacion_planilla_evento")
public class ItemDegustacionPlanillaEvento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ItemDegustacionPlanillaEventoId id;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public ItemDegustacionPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemDegustacionPlanillaEvento(ItemDegustacionPlanillaEventoId id, Integer solicitado, Integer aprobado) {
		super();
		this.id = id;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
	}

	public ItemDegustacionPlanillaEventoId getId() {
		return id;
	}

	public void setId(ItemDegustacionPlanillaEventoId id) {
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
