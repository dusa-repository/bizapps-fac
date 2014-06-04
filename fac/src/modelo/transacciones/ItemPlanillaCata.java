package modelo.transacciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.maestros.Sku;
import modelo.pk.ItemPlanillaCataId;

@Entity
@Table(name = "item_planilla_cata")
@IdClass(ItemPlanillaCataId.class)
public class ItemPlanillaCata {
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Sku sku;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_cata", referencedColumnName = "id_planilla_cata")
	private PlanillaCata planillaCata;

	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public ItemPlanillaCata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemPlanillaCata(Sku sku, PlanillaCata planillaCata,
			Integer solicitado, Integer aprobado) {
		super();
		this.sku = sku;
		this.planillaCata = planillaCata;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
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
