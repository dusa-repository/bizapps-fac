package servicio.transacciones;

import interfacedao.transacciones.IPlanillaEventoDAO;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
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
		return planillaEventoDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
	}

	public List<PlanillaEvento> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaEventoDAO
				.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable);
	}

	public List<PlanillaEvento> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaEvento> planillas = planillaEventoDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaEventoDAO
						.findByEstadoAndTipoOrderByFechaEnvioAsc("Pendiente",
								variable2));
		planillas
				.addAll(planillaEventoDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								"Pendiente", variable2));
		return planillas;
	}

	public List<PlanillaEvento> buscarPorUsuario(Usuario usuario) {
		return planillaEventoDAO.findByUsuario(usuario);
	}

	public List<PlanillaEvento> buscarPorMarca(Marca marca) {
		return planillaEventoDAO.findByMarca(marca);
	}

	public List<PlanillaEvento> buscarAdminPendientes(String tipoConfig,
			String string) {
		// TODO Auto-generated method stub
		return planillaEventoDAO.findByEstadoAndTipoOrderByFechaEnvioAsc(
				string, tipoConfig);
	}

	public List<PlanillaEvento> buscarUsuarioEntreFechas(Usuario user,
			String estadoBuscar, Date fecha1, Date fecha2, String codigoMarca,
			String codigoUsuario, long codigo) {
		if (codigo == 0)
			return planillaEventoDAO
					.findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeOrderByFechaEnvioAsc(
							user, estadoBuscar, fecha1, fecha2, codigoMarca);
		else
			return planillaEventoDAO
					.findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndIdPlanillaEventoOrderByFechaEnvioAsc(
							user, estadoBuscar, fecha1, fecha2, codigoMarca,
							codigo);
	}

	public List<PlanillaEvento> buscarAdminPendientesEntreFechas(String tipoP,
			String estadoBuscar, Date fecha1, Date fecha2, String codigoMarca,
			String codigoUsuario, long codigo) {
		if (codigo == 0)
			return planillaEventoDAO
					.findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
							estadoBuscar, tipoP, fecha1, fecha2, codigoMarca,
							codigoUsuario);
		else
			return planillaEventoDAO
					.findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaEventoOrderByFechaEnvioAsc(
							estadoBuscar, tipoP, fecha1, fecha2, codigoMarca,
							codigoUsuario, codigo);
	}

	public List<PlanillaEvento> buscarSupervisorYEstadoEntreFechas(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2, String codigoMarca, String codigoUsuario, long codigo) {
		if (codigo == 0)
			return planillaEventoDAO
					.findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
							nombreUsuarioSesion, variable, fecha1, fecha2,
							codigoMarca, codigoUsuario);
		return planillaEventoDAO
				.findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaEventoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable, fecha1, fecha2,
						codigoMarca, codigoUsuario, codigo);
	}
}
