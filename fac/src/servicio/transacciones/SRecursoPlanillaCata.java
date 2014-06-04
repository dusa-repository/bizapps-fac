package servicio.transacciones;

import interfacedao.transacciones.IRecursoPlanillaCataDAO;
import modelo.transacciones.RecursoPlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecursoPlanillaCata")
public class SRecursoPlanillaCata {

	@Autowired
	private IRecursoPlanillaCataDAO recursoPlanillaCataDAO;
}
