package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaEventoDAO;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaEvento")
public class SRecursoPlanillaEvento {

	@Autowired
	private IRecursoPlanillaEventoDAO recursoPlanillaEventoDAO;

	public List<RecursoPlanillaEvento> buscarPorPlanilla(PlanillaEvento planilla) {
		return recursoPlanillaEventoDAO.findByPlanillaEvento(planilla);
	}

	public void limpiar(PlanillaEvento planilla) {
		List<RecursoPlanillaEvento> eliminados = recursoPlanillaEventoDAO.findByPlanillaEvento(planilla);
		if(!eliminados.isEmpty())
			recursoPlanillaEventoDAO.delete(eliminados);
	}

	public void guardar(List<RecursoPlanillaEvento> recursosPlanilla) {
		recursoPlanillaEventoDAO.save(recursosPlanilla);
	}

	public List<RecursoPlanillaEvento> buscarPorRecurso(Recurso recurso) {
		return recursoPlanillaEventoDAO.findByRecurso(recurso);
	}

	public List<RecursoPlanillaEvento> buscarPorMarca(Marca marca) {
		return recursoPlanillaEventoDAO.findByMarca(marca);
	}
}
