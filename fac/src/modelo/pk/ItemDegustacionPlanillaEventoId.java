package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Sku;
import modelo.transacciones.PlanillaEvento;
@Embeddable
public class ItemDegustacionPlanillaEventoId implements Serializable {

	private static final long serialVersionUID = 1198298953813214519L;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Sku sku;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_evento", referencedColumnName = "id_planilla_evento")
	private PlanillaEvento planillaEvento;
	
	public ItemDegustacionPlanillaEventoId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public PlanillaEvento getPlanillaEvento() {
		return planillaEvento;
	}
	public void setPlanillaEvento(PlanillaEvento planillaEvento) {
		this.planillaEvento = planillaEvento;
	}
	
}
