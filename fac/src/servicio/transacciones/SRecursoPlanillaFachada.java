package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaFachadaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaFachada")
public class SRecursoPlanillaFachada {

	@Autowired
	private IRecursoPlanillaFachadaDAO recursoPlanillaFachadaDAO;
}
