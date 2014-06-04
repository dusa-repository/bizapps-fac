package servicio.transacciones;

import interfacedao.transacciones.IPlanillaEventoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaEvento")
public class SPlanillaEvento {

	@Autowired
	private IPlanillaEventoDAO planillaEventoDAO;
}
