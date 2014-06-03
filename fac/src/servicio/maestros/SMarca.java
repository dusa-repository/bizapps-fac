package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IMarcaDAO;

import modelo.maestros.Aliado;
import modelo.maestros.Marca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SMarca")
public class SMarca {

	@Autowired
	private IMarcaDAO marcaDAO;

	public void guardar(Marca marca) {
		marcaDAO.save(marca);
	}

	public void eliminar(String id) {
		marcaDAO.delete(id);
	}

	public List<Marca> buscarTodosOrdenados() {
		return marcaDAO.findAllOrderByDescripcion();
	}

	public Marca buscar(String value) {
		return marcaDAO.findOne(value);
	}
}
