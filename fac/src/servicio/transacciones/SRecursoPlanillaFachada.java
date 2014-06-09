package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IRecursoPlanillaFachadaDAO;

import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.RecursoPlanillaFachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaFachada")
public class SRecursoPlanillaFachada {

	@Autowired
	private IRecursoPlanillaFachadaDAO recursoPlanillaFachadaDAO;

	public List<RecursoPlanillaFachada> buscarPorPlanilla(
			PlanillaFachada planilla) {
		return recursoPlanillaFachadaDAO.findByPlanillaFachada(planilla);
	}

	public void limpiar(PlanillaFachada planilla) {
		List<RecursoPlanillaFachada> eliminados = recursoPlanillaFachadaDAO
				.findByPlanillaFachada(planilla);
		if (!eliminados.isEmpty())
			recursoPlanillaFachadaDAO.delete(eliminados);
	}

	public void guardar(List<RecursoPlanillaFachada> recursosPlanilla) {
		recursoPlanillaFachadaDAO.save(recursosPlanilla);
	}
}
