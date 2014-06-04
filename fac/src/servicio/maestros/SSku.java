package servicio.maestros;

import java.util.List;

import interfacedao.maestros.ISkuDAO;

import modelo.maestros.Sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SSku")
public class SSku {

	@Autowired
	private ISkuDAO skuDAO;

	public void guardar(Sku sku) {
		skuDAO.save(sku);
	}

	public void eliminar(String id) {
		skuDAO.delete(id);
	}

	public List<Sku> buscarTodosOrdenados() {
		return skuDAO.findAllOrderByDescripcion();
	}

	public Sku buscar(String value) {
		return skuDAO.findOne(value);
	}
}
