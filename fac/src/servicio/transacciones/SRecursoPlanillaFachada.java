package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaFachadaDAO;

import java.util.List;

import modelo.maestros.Recurso;
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
		return recursoPlanillaFachadaDAO.findByIdPlanillaFachada(planilla);
	}

	public void limpiar(PlanillaFachada planilla) {
		List<RecursoPlanillaFachada> eliminados = recursoPlanillaFachadaDAO
				.findByIdPlanillaFachada(planilla);
		if (!eliminados.isEmpty())
			recursoPlanillaFachadaDAO.delete(eliminados);
	}

	public void guardar(List<RecursoPlanillaFachada> recursosPlanilla) {
		recursoPlanillaFachadaDAO.save(recursosPlanilla);
	}

	public List<RecursoPlanillaFachada> buscarPorRecurso(Recurso recurso) {
		return recursoPlanillaFachadaDAO.findByIdRecurso(recurso);
	}
}
