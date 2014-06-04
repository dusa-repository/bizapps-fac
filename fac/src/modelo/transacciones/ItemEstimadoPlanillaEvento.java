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
import modelo.pk.ItemEstimadoPlanillaEventoId;

@Entity
@Table(name = "item_estimado_planilla_evento")
@IdClass(ItemEstimadoPlanillaEventoId.class)
public class ItemEstimadoPlanillaEvento {

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Sku sku;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_evento", referencedColumnName = "id_planilla_evento")
	private PlanillaEvento planillaEvento;
	
	@Column(name = "estimado")
	private Integer estimado;

	public ItemEstimadoPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemEstimadoPlanillaEvento(Sku sku, PlanillaEvento planillaEvento,
			Integer estimado) {
		super();
		this.sku = sku;
		this.planillaEvento = planillaEvento;
		this.estimado = estimado;
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

	public Integer getEstimado() {
		return estimado;
	}

	public void setEstimado(Integer estimado) {
		this.estimado = estimado;
	}
	
}
