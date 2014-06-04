package servicio.transacciones;

import interfacedao.transacciones.IItemPlanillaCataDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemPlanillaCata")
public class SItemPlanillaCata {

	@Autowired
	private IItemPlanillaCataDAO itemPlanillaCataDAO;
}
