package modelo.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Uniforme;
import modelo.transacciones.PlanillaUniforme;
@Embeddable
public class UniformePlanillaUniformeId implements Serializable {

	private static final long serialVersionUID = -7444518853790665092L;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_uniforme", referencedColumnName = "id_uniforme")
	private Uniforme uniforme;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_planilla_uniforme", referencedColumnName = "id_planilla_uniforme")
	private PlanillaUniforme planillaUniforme;

	@Column(name="talla",length = 500)
	private String talla;
	
	public UniformePlanillaUniformeId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTalla() {
		return talla;
	}
	public void setTalla(String talla) {
		this.talla = talla;
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
	
}
