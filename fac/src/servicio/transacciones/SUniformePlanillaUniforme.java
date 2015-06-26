package servicio.transacciones;

import interfacedao.transacciones.IUniformePlanillaUniformeDAO;

import java.util.List;

import modelo.maestros.Uniforme;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUniformePlanillaUniforme")
public class SUniformePlanillaUniforme {

	@Autowired
	private IUniformePlanillaUniformeDAO uniformePlanillaDAO;

	public List<UniformePlanillaUniforme> buscarPorPlanilla(
			PlanillaUniforme planilla) {
		return uniformePlanillaDAO.findByIdPlanillaUniforme(planilla);
	}

	public void limpiar(PlanillaUniforme planilla) {
		List<UniformePlanillaUniforme> eliminados = uniformePlanillaDAO.findByIdPlanillaUniforme(planilla);
		if(!eliminados.isEmpty())
			uniformePlanillaDAO.delete(eliminados);
	}

	public void guardar(List<UniformePlanillaUniforme> recursosPlanilla) {
		uniformePlanillaDAO.save(recursosPlanilla);	
	}

	public List<UniformePlanillaUniforme> buscarPorUniforme(Uniforme uni) {
		return uniformePlanillaDAO.findByIdUniforme(uni);
	}
}
