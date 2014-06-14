package servicio.seguridad;

import java.util.List;

import interfacedao.seguridad.IConfiguracionDAO;

import modelo.seguridad.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConfiguracion")
public class SConfiguracion {

	@Autowired
	private IConfiguracionDAO configuracionDAO;

	public List<Configuracion> buscar(String string) {
		return configuracionDAO.findByTipo(string);
	}
}
