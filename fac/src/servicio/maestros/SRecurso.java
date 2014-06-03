package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IRecursoDAO;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecurso")
public class SRecurso {

	@Autowired
	private IRecursoDAO recursoDAO;

	public void guardar(Recurso recurso) {
		recursoDAO.save(recurso);
	}

	public void eliminar(long id) {
		recursoDAO.delete(id);
	}

	public Recurso buscarPorDescripcion(String value) {
		return recursoDAO.findByDescripcion(value);
	}

	public List<Recurso> buscarTodosOrdenados() {
		return recursoDAO.findAllOrderByDescripcion();
	}
}
