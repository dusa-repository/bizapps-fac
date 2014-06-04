package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Sku;
import modelo.transacciones.PlanillaEvento;

public class ItemDegustacionPlanillaEventoId implements Serializable {

	private static final long serialVersionUID = 1198298953813214519L;
	
	private Sku sku;
	private PlanillaEvento planillaEvento;
	
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
