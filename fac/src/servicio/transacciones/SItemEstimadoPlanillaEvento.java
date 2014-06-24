package servicio.transacciones;

import interfacedao.transacciones.IItemEstimadoPlanillaEventoDAO;

import java.util.List;

import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemEstimadoPlanillaEvento")
public class SItemEstimadoPlanillaEvento {

	@Autowired
	private IItemEstimadoPlanillaEventoDAO itemEstimadoDAO;

	public List<ItemEstimadoPlanillaEvento> buscarPorPlanilla(
			PlanillaEvento planilla) {
		return itemEstimadoDAO.findByPlanillaEvento(planilla);
	}

	public void limpiar(PlanillaEvento planilla) {
		List<ItemEstimadoPlanillaEvento> eliminados = itemEstimadoDAO.findByPlanillaEvento(planilla);
		if(!eliminados.isEmpty())
			itemEstimadoDAO.delete(eliminados);
	}

	public void guardar(List<ItemEstimadoPlanillaEvento> recursosPlanilla) {
		itemEstimadoDAO.save(recursosPlanilla);
	}
}
