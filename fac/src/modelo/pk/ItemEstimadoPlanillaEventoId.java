package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Sku;
import modelo.transacciones.PlanillaEvento;

public class ItemEstimadoPlanillaEventoId implements Serializable {

	private static final long serialVersionUID = -1666745954902911602L;
	
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
