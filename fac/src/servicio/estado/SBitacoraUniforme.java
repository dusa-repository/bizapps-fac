package servicio.estado;

import interfacedao.estado.IBitacoraUniformeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraUniforme")
public class SBitacoraUniforme {

	@Autowired
	private IBitacoraUniformeDAO bitacoraUniformeDAO;
}
