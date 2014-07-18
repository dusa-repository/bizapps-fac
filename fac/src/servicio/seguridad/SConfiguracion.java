package servicio.seguridad;

import interfacedao.seguridad.IConfiguracionDAO;
import interfacedao.seguridad.IGrupoDAO;

import java.util.List;

import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConfiguracion")
public class SConfiguracion {

	@Autowired
	private IConfiguracionDAO configuracionDAO;
	@Autowired
	private IGrupoDAO grupoDAO;

	public List<Configuracion> buscar(String string) {
		return configuracionDAO.findByTipo(string);
	}

	public Configuracion buscarTradeMark(String string) {
		List<Configuracion> configuracion = configuracionDAO.findByTipo(string);
		if (!configuracion.isEmpty())
			return configuracion.get(0);
		else
			return null;
	}

	public List<Configuracion> buscarTipo(String string) {
		return configuracionDAO.findByTipo(string);
	}

	public void limpiar() {
		configuracionDAO.deleteAll();
	}

	public void guardar(List<Configuracion> lista) {
		configuracionDAO.save(lista);
	}
}
