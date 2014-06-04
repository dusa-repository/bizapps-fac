package servicio.transacciones;

import interfacedao.transacciones.IItemEstimadoPlanillaEventoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SItemEstimadoPlanillaEvento")
public class SItemEstimadoPlanillaEvento {

	@Autowired
	private IItemEstimadoPlanillaEventoDAO itemEstimadoDAO;
}
