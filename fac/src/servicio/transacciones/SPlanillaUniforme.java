package servicio.transacciones;

import interfacedao.transacciones.IPlanillaUniformeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaUniforme")
public class SPlanillaUniforme {

	@Autowired
	private IPlanillaUniformeDAO planillaUniformeDAO;
}
