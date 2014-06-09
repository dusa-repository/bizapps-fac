package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaFachadaDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaFachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaFachada")
public class SPlanillaFachada {

	@Autowired
	private IPlanillaFachadaDAO planillaFachadaDAO;

	public PlanillaFachada buscar(long id) {
		return planillaFachadaDAO.findOne(id);
	}

	public void guardar(PlanillaFachada planillaFachada) {
		planillaFachadaDAO.saveAndFlush(planillaFachada);
	}

	public PlanillaFachada buscarUltima() {
		long id = planillaFachadaDAO.findMaxId();
		if (id != 0)
			return planillaFachadaDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaFachadaDAO.delete(id);
	}

	public List<PlanillaFachada> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaFachadaDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}
}
