package servicio.estado;

import interfacedao.estado.IBitacoraPromocionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraPromocion")
public class SBitacoraPromocion {

	@Autowired
	private IBitacoraPromocionDAO bitacoraPromocionDAO;
}
