package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Sku;
import modelo.transacciones.PlanillaCata;
@Embeddable
public class ItemPlanillaCataId implements Serializable {
	
	private static final long serialVersionUID = -4513656288445656960L;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Sku sku;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_cata", referencedColumnName = "id_planilla_cata")
	private PlanillaCata planillaCata;
	
	public ItemPlanillaCataId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
