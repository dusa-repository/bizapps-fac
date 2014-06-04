package modelo.pk;

import java.io.Serializable;

import modelo.maestros.Uniforme;
import modelo.transacciones.PlanillaUniforme;

public class UniformePlanillaUniformeId implements Serializable {

	private static final long serialVersionUID = -7444518853790665092L;
	
	private Uniforme uniforme;
	private PlanillaUniforme planillaUniforme;
	
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
