package servicio.transacciones;

import interfacedao.transacciones.IPlanillaUniformeDAO;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaUniforme")
public class SPlanillaUniforme {

	@Autowired
	private IPlanillaUniformeDAO planillaUniformeDAO;

	public PlanillaUniforme buscar(long id) {
		return planillaUniformeDAO.findOne(id);
	}

	public void guardar(PlanillaUniforme planillaUniforme) {
		planillaUniformeDAO.save(planillaUniforme);
	}

	public PlanillaUniforme buscarUltima() {
		long id = planillaUniformeDAO.findMaxId();
		if (id != 0)
			return planillaUniformeDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaUniformeDAO.delete(id);
	}

	public List<PlanillaUniforme> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaUniformeDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}

	public void guardarVarias(List<PlanillaUniforme> listUniforme) {
		planillaUniformeDAO.save(listUniforme);
	}

	public List<PlanillaUniforme> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaUniformeDAO.findByUsuarioAndEstadoOrderByFechaEnvioAsc(
				usuarioSesion, variable);
	}

	public List<PlanillaUniforme> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaUniformeDAO
				.findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable);
	}

	public List<PlanillaUniforme> buscarAdminEstado(String variable,
			String variable2, Usuario usuarioSesion) {
		List<PlanillaUniforme> planillas = planillaUniformeDAO
				.findByUsuarioAndEstadoOrderByFechaEnvioAsc(usuarioSesion,
						variable);
		planillas
				.addAll(planillaUniformeDAO
						.findByEstadoAndTipoOrderByFechaEnvioAsc("Pendiente",
								variable2));
		planillas
				.addAll(planillaUniformeDAO
						.findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
								variable, "Pagada", "Rechazada", "Cancelada",
								"Pendiente", variable2));
		return planillas;
	}

	public List<PlanillaUniforme> buscarPorUsuario(Usuario usuario) {
		return planillaUniformeDAO.findByUsuario(usuario);
	}

	public List<PlanillaUniforme> buscarPorMarca(Marca marca) {
		return planillaUniformeDAO.findByMarca(marca);
	}

	public List<PlanillaUniforme> buscarAdminPendientes(String tipoConfig,
			String string) {
		return planillaUniformeDAO.findByEstadoAndTipoOrderByFechaEnvioAsc(string, tipoConfig);
	}

	public List<PlanillaUniforme> buscarUsuarioEntreFechas(Usuario user,
			String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaUniformeDAO.findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
				user, estadoBuscar, fecha1, fecha2);
	}

	public List<PlanillaUniforme> buscarAdminPendientesEntreFechas(
			String tipoP, String estadoBuscar, Date fecha1, Date fecha2) {
		return planillaUniformeDAO
				.findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						estadoBuscar, tipoP, fecha1, fecha2);
	}

	public List<PlanillaUniforme> buscarSupervisorYEstadoEntreFechas(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2) {
		return planillaUniformeDAO
				.findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
						nombreUsuarioSesion, variable, fecha1, fecha2);
	}
}
