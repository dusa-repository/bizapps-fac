package servicio.transacciones;

import interfacedao.transacciones.IPlanillaArteDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaArte")
public class SPlanillaArte {

	@Autowired
	private IPlanillaArteDAO planillaArteDAO;
}
