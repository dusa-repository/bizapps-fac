package servicio.transacciones;

import interfacedao.transacciones.IPlanillaPromocionDAO;

import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaPromocion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaPromocion")
public class SPlanillaPromocion {

	@Autowired
	private IPlanillaPromocionDAO planillaPromocionDAO;

	public void guardar(PlanillaPromocion planillaPromocion) {
		planillaPromocionDAO.save(planillaPromocion);
	}

	public PlanillaPromocion buscar(long id) {
		return planillaPromocionDAO.findOne(id);
	}

	public PlanillaPromocion buscarUltima() {
		long id = planillaPromocionDAO.findMaxId();
		if (id != 0)
			return planillaPromocionDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaPromocionDAO.delete(id);
	}

	public List<PlanillaPromocion> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaPromocionDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}

	public void guardarVarias(List<PlanillaPromocion> listPromocion) {
		planillaPromocionDAO.save(listPromocion);
	}

	public List<PlanillaPromocion> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaPromocion> planillas = planillaPromocionDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaPromocionDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								variable2));
		return planillas;
	}

	public List<PlanillaPromocion> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaPromocionDAO.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
				nombreUsuarioSesion, variable);
	}

	public List<PlanillaPromocion> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaPromocionDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
				variable);
	}

	public List<PlanillaPromocion> buscarPorUsuario(Usuario usuario) {
		return planillaPromocionDAO.findByUsuario(usuario);
	}

	public List<PlanillaPromocion> buscarPorMarca(Marca marca) {
		return planillaPromocionDAO.findByMarcaA(marca);
	}
}
