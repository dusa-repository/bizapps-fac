package servicio.transacciones;

import interfacedao.transacciones.IPlanillaPromocionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaPromocion")
public class SPlanillaPromocion {

	@Autowired
	private IPlanillaPromocionDAO planillaPromocionDAO;
}
