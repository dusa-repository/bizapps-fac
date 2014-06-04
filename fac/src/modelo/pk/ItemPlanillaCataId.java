package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Sku;
import modelo.transacciones.PlanillaCata;

public class ItemPlanillaCataId implements Serializable {
	
	private static final long serialVersionUID = -4513656288445656960L;
	
	private Sku sku;
	private PlanillaCata planillaCata;
	
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public PlanillaCata getPlanillaCata() {
		return planillaCata;
	}
	public void setPlanillaCata(PlanillaCata planillaCata) {
		this.planillaCata = planillaCata;
	}
	

}
