package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IRecursoPlanillaCataDAO;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.RecursoPlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaCata")
public class SRecursoPlanillaCata {

	@Autowired
	private IRecursoPlanillaCataDAO recursoPlanillaCataDAO;

	public List<RecursoPlanillaCata> buscarPorPlanilla(PlanillaCata planilla) {
		return recursoPlanillaCataDAO.findByPlanillaCata(planilla);
	}

	public void limpiar(PlanillaCata planilla) {
		List<RecursoPlanillaCata> eliminados = recursoPlanillaCataDAO.findByPlanillaCata(planilla);
		if(!eliminados.isEmpty())
			recursoPlanillaCataDAO.delete(eliminados);
	}

	public void guardar(List<RecursoPlanillaCata> recursosPlanilla) {
		recursoPlanillaCataDAO.save(recursosPlanilla);
	}
}
