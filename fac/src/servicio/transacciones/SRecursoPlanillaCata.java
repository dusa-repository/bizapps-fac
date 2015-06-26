package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaCataDAO;

import java.util.List;

import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.RecursoPlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaCata")
public class SRecursoPlanillaCata {

	@Autowired
	private IRecursoPlanillaCataDAO recursoPlanillaCataDAO;

	public List<RecursoPlanillaCata> buscarPorPlanilla(PlanillaCata planilla) {
		return recursoPlanillaCataDAO.findByIdPlanillaCata(planilla);
	}

	public void limpiar(PlanillaCata planilla) {
		List<RecursoPlanillaCata> eliminados = recursoPlanillaCataDAO.findByIdPlanillaCata(planilla);
		if(!eliminados.isEmpty())
			recursoPlanillaCataDAO.delete(eliminados);
	}

	public void guardar(List<RecursoPlanillaCata> recursosPlanilla) {
		recursoPlanillaCataDAO.save(recursosPlanilla);
	}

	public List<RecursoPlanillaCata> buscarPorRecurso(Recurso recurso) {
		return recursoPlanillaCataDAO.findByIdRecurso(recurso);
	}
}
