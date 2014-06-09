package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaEventoDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaEvento")
public class SPlanillaEvento {

	@Autowired
	private IPlanillaEventoDAO planillaEventoDAO;

	public PlanillaEvento buscar(long id) {
		return planillaEventoDAO.findOne(id);
	}

	public void guardar(PlanillaEvento planillaEvento) {
		planillaEventoDAO.saveAndFlush(planillaEvento);
	}

	public PlanillaEvento buscarUltima() {
		long id = planillaEventoDAO.findMaxId();
		if (id != 0)
			return planillaEventoDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaEventoDAO.delete(id);
	}

	public List<PlanillaEvento> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaEventoDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}
}
