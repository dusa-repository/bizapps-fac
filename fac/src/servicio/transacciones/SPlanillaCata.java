package servicio.transacciones;

import interfacedao.transacciones.IPlanillaCataDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaCata")
public class SPlanillaCata {

	@Autowired
	private IPlanillaCataDAO planillaCataDAO;
}
