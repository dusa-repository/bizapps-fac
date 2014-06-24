package servicio.transacciones;

import interfacedao.transacciones.IItemPlanillaCataDAO;

import java.util.List;

import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemPlanillaCata")
public class SItemPlanillaCata {

	@Autowired
	private IItemPlanillaCataDAO itemPlanillaCataDAO;

	public List<ItemPlanillaCata> buscarPorPlanilla(PlanillaCata planilla) {
		return itemPlanillaCataDAO.findByPlanillaCata(planilla);
	}

	public void limpiar(PlanillaCata planilla) {
		List<ItemPlanillaCata> eliminados = itemPlanillaCataDAO
				.findByPlanillaCata(planilla);
		if (!eliminados.isEmpty())
			itemPlanillaCataDAO.delete(eliminados);
	}

	public void guardar(List<ItemPlanillaCata> recursosPlanilla) {
		itemPlanillaCataDAO.save(recursosPlanilla);
	}
}
