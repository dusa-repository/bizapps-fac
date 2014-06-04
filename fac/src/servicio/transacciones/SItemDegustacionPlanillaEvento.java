package servicio.transacciones;

import interfacedao.transacciones.IItemDegustacionPlanillaEventoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemDegustacionPlanillaEvento")
public class SItemDegustacionPlanillaEvento {

	@Autowired
	private IItemDegustacionPlanillaEventoDAO itemDegustacionDAO;
}
