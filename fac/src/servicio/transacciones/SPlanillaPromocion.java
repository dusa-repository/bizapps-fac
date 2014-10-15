package servicio.transacciones;

import interfacedao.transacciones.IPlanillaPromocionDAO;

import java.util.Date;
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
						.findByEstadoAndTipoOrderByFechaEnvioAsc("Pendiente",
								variable2));
		planillas
				.addAll(planillaPromocionDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								"Pendiente", variable2));
		return planillas;
	}

	public List<PlanillaPromocion> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaPromocionDAO
				.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable);
	}

	public List<PlanillaPromocion> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaPromocionDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
	}

	public List<PlanillaPromocion> buscarPorUsuario(Usuario usuario) {
		return planillaPromocionDAO.findByUsuario(usuario);
	}

	public List<PlanillaPromocion> buscarPorMarca(Marca marca) {
		return planillaPromocionDAO.findByMarcaA(marca);
	}

	public List<PlanillaPromocion> buscarAdminPendientes(String tipoConfig,
			String string) {
		return planillaPromocionDAO.findByEstadoAndTipoOrderByFechaEnvioAsc(string, tipoConfig);
	}

	public List<PlanillaPromocion> buscarUsuarioEntreFechas(Usuario user,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaPromocionDAO.findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
				user, estadoBuscar, fecha1, fecha2);
	}

	public List<PlanillaPromocion> buscarAdminPendientesEntreFechas(
			String tipoP, String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaPromocionDAO
				.findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						estadoBuscar, tipoP, fecha1, fecha2);
	}

	public List<PlanillaPromocion> buscarSupervisorYEstadoEntreFechas(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2) {
		return planillaPromocionDAO
				.findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable, fecha1, fecha2);
	}
}
