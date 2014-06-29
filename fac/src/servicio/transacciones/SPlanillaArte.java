package servicio.transacciones;

import interfacedao.transacciones.IPlanillaArteDAO;

import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaArte")
public class SPlanillaArte {

	@Autowired
	private IPlanillaArteDAO planillaArteDAO;

	public void guardar(PlanillaArte planillaArte) {
		planillaArteDAO.save(planillaArte);
	}

	public PlanillaArte buscar(long id) {
		return planillaArteDAO.findOne(id);
	}

	public PlanillaArte buscarUltima() {
		long id = planillaArteDAO.findMaxId();
		if (id != 0)
			return planillaArteDAO.findOne(id);
		return null;
	}

	public List<PlanillaArte> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaArteDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}

	public void eliminar(long id) {
		planillaArteDAO.delete(id);
	}

	public void guardarVarias(List<PlanillaArte> listArte) {
		planillaArteDAO.save(listArte);
	}

	public List<PlanillaArte> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaArteDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion, variable);
	}

	public List<PlanillaArte> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaArteDAO.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
				nombreUsuarioSesion, variable);
	}

	public List<PlanillaArte> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaArte> planillas = planillaArteDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaArteDAO
						.findByEstadoNotAndTipoOrderByFechaEnvioAsc(variable,
								variable2));
		return planillas;
	}

	public List<PlanillaArte> buscarPorUsuario(Usuario usuario) {
		return planillaArteDAO.findByUsuario(usuario);
	}

	public List<PlanillaArte> buscarPorMarca(Marca marca) {
		return planillaArteDAO.findByMarca(marca);
	}
}
