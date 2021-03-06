package interfacedao.transacciones;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaFachadaDAO extends JpaRepository<PlanillaFachada, Long> {
	
	@Query("select coalesce(max(planilla.idPlanillaFachada), '0') from PlanillaFachada planilla")
	long findMaxId();

	List<PlanillaFachada> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaFachada> findByEstadoNotAndTipo(String variable,
			String variable2);

	List<PlanillaFachada> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaFachada> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaFachada> findByUsuario(Usuario usuario);

	List<PlanillaFachada> findByMarca(Marca marca);

	List<PlanillaFachada> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaFachada> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaFachada> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

	List<PlanillaFachada> findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2);

	List<PlanillaFachada> findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2);

	List<PlanillaFachada> findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndIdPlanillaFachadaOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2,
			String codigoMarca, long codigo);

	List<PlanillaFachada> findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2,
			String codigoMarca);

	List<PlanillaFachada> findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaFachadaOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2,
			String codigoMarca, String codigoUsuario, long codigo);

	List<PlanillaFachada> findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2,
			String codigoMarca, String codigoUsuario);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaFachadaOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2, String codigoMarca, String codigoUsuario, long codigo);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2, String codigoMarca, String codigoUsuario);

}
