package servicio.transacciones;

import interfacedao.transacciones.IPlanillaFachadaDAO;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
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

	public void guardarVarias(List<PlanillaFachada> listFachada) {
		planillaFachadaDAO.save(listFachada);
	}

	public List<PlanillaFachada> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaFachada> planillas = planillaFachadaDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaFachadaDAO
						.findByEstadoAndTipoOrderByFechaEnvioAsc("Pendiente",
								variable2));
		planillas
				.addAll(planillaFachadaDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								"Pendiente", variable2));
		return planillas;
	}

	public List<PlanillaFachada> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaFachadaDAO
				.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable);
	}

	public List<PlanillaFachada> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaFachadaDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
	}

	public List<PlanillaFachada> buscarPorUsuario(Usuario usuario) {
		return planillaFachadaDAO.findByUsuario(usuario);
	}

	public List<PlanillaFachada> buscarPorMarca(Marca marca) {
		return planillaFachadaDAO.findByMarca(marca);
	}

	public List<PlanillaFachada> buscarAdminPendientes(String tipoConfig,
			String string) {
		return planillaFachadaDAO.findByEstadoAndTipoOrderByFechaEnvioAsc(string, tipoConfig);
	}

	public List<PlanillaFachada> buscarUsuarioEntreFechas(Usuario user,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaFachadaDAO.findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
				user, estadoBuscar, fecha1, fecha2);
	}

	public List<PlanillaFachada> buscarAdminPendientesEntreFechas(String tipoP,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaFachadaDAO
				.findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						estadoBuscar, tipoP, fecha1, fecha2);
	}

	public List<PlanillaFachada> buscarSupervisorYEstadoEntreFechas(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2) {
		return planillaFachadaDAO
				.findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable, fecha1, fecha2);
	}
}
