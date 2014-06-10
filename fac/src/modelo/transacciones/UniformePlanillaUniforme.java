package modelo.transacciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.maestros.Uniforme;
import modelo.pk.UniformePlanillaUniformeId;

@Entity
@Table(name = "uniforme_planilla_uniforme")
@IdClass(UniformePlanillaUniformeId.class)
public class UniformePlanillaUniforme {
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_uniforme", referencedColumnName = "id_uniforme")
	private Uniforme uniforme;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_uniforme", referencedColumnName = "id_planilla_uniforme")
	private PlanillaUniforme planillaUniforme;
	
	@Column(name="genero",length = 500)
	private String genero;
	
	@Column(name="talla",length = 500)
	private String talla;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name="precio_unitario")
	private Double precioUnitario;

	public UniformePlanillaUniforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniformePlanillaUniforme(Uniforme uniforme,
			PlanillaUniforme planillaUniforme, String genero, String talla,
			Integer personas, Double precioUnitario) {
		super();
		this.uniforme = uniforme;
		this.planillaUniforme = planillaUniforme;
		this.genero = genero;
		this.talla = talla;
		this.cantidad = personas;
		this.precioUnitario = precioUnitario;
	}

	public Uniforme getUniforme() {
		return uniforme;
	}

	public void setUniforme(Uniforme uniforme) {
		this.uniforme = uniforme;
	}

	public PlanillaUniforme getPlanillaUniforme() {
		return planillaUniforme;
	}

	public void setPlanillaUniforme(PlanillaUniforme planillaUniforme) {
		this.planillaUniforme = planillaUniforme;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
}
