package servicio.maestros;

import interfacedao.estado.IEmpresaDAO;

import java.util.List;

import modelo.maestros.Empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service("SEmpresa")
public class SEmpresa {

	@Autowired
	private IEmpresaDAO empresaDAO;
	private Sort o = new Sort(new Order(Direction.ASC, "nombre"));

	public void guardar(Empresa empresa) {
		empresaDAO.saveAndFlush(empresa);
	}

	public void eliminar(Long id) {
		empresaDAO.delete(id);
	}

	public List<Empresa> buscarTodosOrdenados() {
		return empresaDAO.findAll(o);
	}

	public Empresa buscarPorNombre(String value) {
		List<Empresa> empresas = empresaDAO.findByNombre(value);
		Empresa objeto = null;
		if (!empresas.isEmpty())
			objeto = empresas.get(0);
		return objeto;
	}

	public Empresa buscar(Long idEmpresa) {
		return empresaDAO.findOne(idEmpresa);
	}
}
