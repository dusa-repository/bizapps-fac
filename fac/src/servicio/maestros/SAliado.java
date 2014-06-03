package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IAliadoDAO;

import modelo.maestros.Aliado;

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
}
