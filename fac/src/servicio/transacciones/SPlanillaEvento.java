package servicio.transacciones;

import interfacedao.transacciones.IPlanillaEventoDAO;

import java.util.List;

import modelo.seguridad.Usuario;
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

	public void guardarVarias(List<PlanillaEvento> listEvento) {
		planillaEventoDAO.save(listEvento);
	}

	public List<PlanillaEvento> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaEventoDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion, variable);
	}

	public List<PlanillaEvento> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaEventoDAO.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
				nombreUsuarioSesion, variable);
	}

	public List<PlanillaEvento> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaEvento> planillas = planillaEventoDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
		planillas.addAll(planillaEventoDAO
				.findByEstadoNotAndTipoOrderByFechaEnvioAsc(variable, variable2));
		return planillas;
	}
}
