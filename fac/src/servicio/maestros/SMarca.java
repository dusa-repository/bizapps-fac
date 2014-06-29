package servicio.maestros;

import interfacedao.maestros.IMarcaDAO;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;

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

	public List<Marca> buscarPorSku(Sku sku) {
		// TODO Auto-generated method stub
		return marcaDAO.findByItems(sku);
	}
}
