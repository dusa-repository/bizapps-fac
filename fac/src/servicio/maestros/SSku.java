package servicio.maestros;

import interfacedao.maestros.ISkuDAO;
import interfacedao.transacciones.IItemDegustacionPlanillaEventoDAO;
import interfacedao.transacciones.IItemEstimadoPlanillaEventoDAO;
import interfacedao.transacciones.IItemPlanillaCataDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Sku;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SSku")
public class SSku {

	@Autowired
	private ISkuDAO skuDAO;
	@Autowired
	private IItemPlanillaCataDAO itemPlanillaCataDAO;
	@Autowired
	private IItemDegustacionPlanillaEventoDAO itemDegustacionPlanillaDAO;
	@Autowired
	private IItemEstimadoPlanillaEventoDAO itemEstimadoPlanillaDAO;

	public void guardar(Sku sku) {
		skuDAO.save(sku);
	}

	public void eliminar(String id) {
		skuDAO.delete(id);
	}

	public List<Sku> buscarTodosOrdenados() {
		return skuDAO.findAllOrderByDescripcion();
	}

	public Sku buscar(String value) {
		return skuDAO.findOne(value);
	}

	public List<Sku> buscarDisponibles(PlanillaCata planilla) {
		List<ItemPlanillaCata> itemPlanilla = itemPlanillaCataDAO.findByPlanillaCata(planilla);
		List<String> ids = new ArrayList<String>();
		if(itemPlanilla.isEmpty())
			return skuDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<itemPlanilla.size();i++){
				ids.add(itemPlanilla.get(i).getSku().getIdSku());
			}
			return skuDAO.findByIdSkuNotIn(ids);
		}
	}

	public List<Sku> buscarDisponiblesDegustacion(PlanillaEvento planilla) {
		List<ItemDegustacionPlanillaEvento> itemPlanilla = itemDegustacionPlanillaDAO.findByPlanillaEvento(planilla);
		List<String> ids = new ArrayList<String>();
		if(itemPlanilla.isEmpty())
			return skuDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<itemPlanilla.size();i++){
				ids.add(itemPlanilla.get(i).getSku().getIdSku());
			}
			return skuDAO.findByIdSkuNotIn(ids);
		}
	}

	public List<Sku> buscarDisponiblesEstimacion(PlanillaEvento planilla) {
		// TODO Auto-generated method stub
		List<ItemEstimadoPlanillaEvento> itemPlanilla = itemEstimadoPlanillaDAO.findByPlanillaEvento(planilla);
		List<String> ids = new ArrayList<String>();
		if(itemPlanilla.isEmpty())
			return skuDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<itemPlanilla.size();i++){
				ids.add(itemPlanilla.get(i).getSku().getIdSku());
			}
			return skuDAO.findByIdSkuNotIn(ids);
		}
	}
}
