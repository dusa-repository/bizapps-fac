package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IZonaDAO;

import modelo.maestros.Zona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SZona")
public class SZona {

	@Autowired
	private IZonaDAO zonaDAO;

	public List<Zona> buscarTodosOrdenados() {
		return zonaDAO.findAllOrderByDescripcion();
	}

	public Zona buscar(String idZona) {
		return zonaDAO.findOne(idZona);
	}

	public Zona buscarPorDescripcion(String value) {
		return zonaDAO.findByDescripcion(value);
	}

	public void guardar(Zona zona) {
		zonaDAO.saveAndFlush(zona);
	}

	public void eliminar(String id) {
		zonaDAO.delete(id);
	}
}
