package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaEventoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaEvento")
public class SRecursoPlanillaEvento {

	@Autowired
	private IRecursoPlanillaEventoDAO recursoPlanillaEventoDAO;
}
