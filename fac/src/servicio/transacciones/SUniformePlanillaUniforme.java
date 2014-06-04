package servicio.transacciones;

import interfacedao.transacciones.IUniformePlanillaUniformeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUniformePlanillaUniforme")
public class SUniformePlanillaUniforme {

	@Autowired
	private IUniformePlanillaUniformeDAO uniformePlanillaDAO;
}
