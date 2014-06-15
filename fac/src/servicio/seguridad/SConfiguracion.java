package servicio.seguridad;

import java.util.List;

import interfacedao.seguridad.IConfiguracionDAO;

import modelo.seguridad.Configuracion;
import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConfiguracion")
public class SConfiguracion {

	@Autowired
	private IConfiguracionDAO configuracionDAO;

	public List<Configuracion> buscar(String string) {
		return configuracionDAO.findByTipo(string);
	}

	public Configuracion buscarAdministradorTradeMark(Usuario u, String string) {
		List<Configuracion> configuracion = configuracionDAO
				.findDistinctByUsuarioAndTipo(u, string);
		if (!configuracion.isEmpty())
			return configuracion.get(0);
		else
			return null;
	}

	public Configuracion buscarTradeMark(String string) {
		List<Configuracion> configuracion = configuracionDAO.findByTipo(string);
		if (!configuracion.isEmpty())
			return configuracion.get(0);
		else
			return null;
	}
}