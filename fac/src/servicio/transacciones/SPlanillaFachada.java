package servicio.transacciones;

import interfacedao.transacciones.IPlanillaFachadaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaFachada")
public class SPlanillaFachada {

	@Autowired
	private IPlanillaFachadaDAO planillaFachadaDAO;
}
