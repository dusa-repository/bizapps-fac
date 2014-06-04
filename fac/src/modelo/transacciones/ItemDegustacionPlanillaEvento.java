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
import modelo.pk.ItemDegustacionPlanillaEventoId;


@Entity
@Table(name = "item_degustacion_planilla_evento")
@IdClass(ItemDegustacionPlanillaEventoId.class)
public class ItemDegustacionPlanillaEvento {
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Sku sku;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_evento", referencedColumnName = "id_planilla_evento")
	private PlanillaEvento planillaEvento;
	
	@Column(name = "solicitado")
	private Integer solicitado;
	
	@Column(name = "aprobado")
	private Integer aprobado;

	public ItemDegustacionPlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemDegustacionPlanillaEvento(Sku sku,
			PlanillaEvento planillaEvento, Integer solicitado, Integer aprobado) {
		super();
		this.sku = sku;
		this.planillaEvento = planillaEvento;
		this.solicitado = solicitado;
		this.aprobado = aprobado;
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
