package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IUniformeDAO;

import modelo.maestros.Uniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUniforme")
public class SUniforme {

	@Autowired
	private IUniformeDAO uniformeDAO;

	public void guardar(Uniforme uniforme) {
		uniformeDAO.saveAndFlush(uniforme);
	}

	public void eliminar(long id) {
		uniformeDAO.delete(id);
	}

	public List<Uniforme> buscarTodosOrdenados() {
		return uniformeDAO.findAllOrderByDescripcion();
	}

	public Uniforme buscarPorDescripcion(String value) {
		return uniformeDAO.findByDescripcion(value);
	}
}
