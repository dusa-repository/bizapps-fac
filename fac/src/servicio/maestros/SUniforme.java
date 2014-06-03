package servicio.maestros;

import interfacedao.maestros.IUniformeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUniforme")
public class SUniforme {

	@Autowired
	private IUniformeDAO uniformeDAO;
}
