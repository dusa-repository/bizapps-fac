package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IItemDegustacionPlanillaEventoDAO;

import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemDegustacionPlanillaEvento")
public class SItemDegustacionPlanillaEvento {

	@Autowired
	private IItemDegustacionPlanillaEventoDAO itemDegustacionDAO;

	public List<ItemDegustacionPlanillaEvento> buscarPorPlanilla(
			PlanillaEvento planilla) {
		return itemDegustacionDAO.findByPlanillaEvento(planilla);
	}

	public void limpiar(PlanillaEvento planilla) {
		List<ItemDegustacionPlanillaEvento> eliminados = itemDegustacionDAO.findByPlanillaEvento(planilla);
		if(!eliminados.isEmpty())
			itemDegustacionDAO.delete(eliminados);
	}

	public void guardar(List<ItemDegustacionPlanillaEvento> recursosPlanilla) {
		itemDegustacionDAO.save(recursosPlanilla);
	}
}
