package servicio.maestros;

import interfacedao.maestros.IAliadoDAO;

import java.util.List;

import modelo.maestros.Aliado;
import modelo.maestros.Zona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SAliado")
public class SAliado {

	@Autowired
	private IAliadoDAO aliadoDAO;

	public List<Aliado> buscarTodosOrdenados() {
		return aliadoDAO.findAllOrderByDescripcion();
	}

	public void guardar(Aliado aliado) {
		aliadoDAO.save(aliado);
	}

	public void eliminar(long id) {
		aliadoDAO.delete(id);
	}

	public Aliado buscarPorDescripcion(String value) {
		return aliadoDAO.findByNombre(value);
	}

	public List<Aliado> buscarPorZona(Zona zona) {
		return aliadoDAO.findByZona(zona);
	}

	public Aliado buscarPorCodigo(String value) {
		return aliadoDAO.findByCodigo(value);
	}

	public Aliado buscar(Long idAliado) {
		return aliadoDAO.findOne(idAliado);
	}
}
