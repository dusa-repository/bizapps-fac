package servicio.maestros;

import interfacedao.maestros.IMarcaDAO;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service("SMarca")
public class SMarca {

	@Autowired
	private IMarcaDAO marcaDAO;
	private Sort o = new Sort(new Order(Direction.ASC, "idMarca"), new Order(
			Direction.ASC, "descripcion"));

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

	public List<Marca> buscarPorIdEmpresa(Long id) {
		return marcaDAO.findByEmpresaIdEmpresa(id);
	}

	public List<Marca> buscarPorEstado(boolean b) {
		return marcaDAO.findByEstado(b, o);
	}

	public void guardarVarias(List<Marca> marcas) {
		marcaDAO.save(marcas);
	}
}
