package servicio.transacciones;

import interfacedao.transacciones.IPlanillaCataDAO;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaCata")
public class SPlanillaCata {

	@Autowired
	private IPlanillaCataDAO planillaCataDAO;

	public PlanillaCata buscar(long id) {
		return planillaCataDAO.findOne(id);
	}

	public void guardar(PlanillaCata planilla) {
		planillaCataDAO.saveAndFlush(planilla);
	}

	public void eliminar(long id) {
		planillaCataDAO.delete(id);
	}

	public PlanillaCata buscarUltima() {
		long id = planillaCataDAO.findMaxId();
		if (id != 0)
			return planillaCataDAO.findOne(id);
		return null;
	}

	public List<PlanillaCata> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaCataDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}

	public void guardarVarias(List<PlanillaCata> listCata) {
		planillaCataDAO.save(listCata);
	}

	public List<PlanillaCata> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaCataDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
	}

	public List<PlanillaCata> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaCataDAO
				.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable);
	}

	public List<PlanillaCata> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaCata> planillas = planillaCataDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaCataDAO
						.findByEstadoAndTipoOrderByFechaEnvioAsc("Pendiente",
								variable2));
		planillas
				.addAll(planillaCataDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								"Pendiente", variable2));
		return planillas;
	}

	public List<PlanillaCata> buscarPorUsuario(Usuario usuario) {
		return planillaCataDAO.findByUsuario(usuario);
	}

	public List<PlanillaCata> buscarPorMarca(Marca marca) {
		return planillaCataDAO.findByMarca(marca);
	}

	public List<PlanillaCata> buscarAdminPendientes(String tipoConfig,
			String string) {
		return planillaCataDAO.findByEstadoAndTipoOrderByFechaEnvioAsc(string,
				tipoConfig);
	}

	public List<PlanillaCata> buscarUsuarioEntreFechas(Usuario user,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaCataDAO
				.findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						user, estadoBuscar, fecha1, fecha2);
	}

	public List<PlanillaCata> buscarAdminPendientesEntreFechas(String tipoP,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaCataDAO
				.findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						estadoBuscar, tipoP, fecha1, fecha2);
	}

	public List<PlanillaCata> buscarSupervisorYEstadoEntreFechas(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2) {
		return planillaCataDAO
				.findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable, fecha1, fecha2);
	}

}
