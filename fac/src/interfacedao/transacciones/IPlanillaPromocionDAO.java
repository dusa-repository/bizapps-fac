package interfacedao.transacciones;

import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaPromocion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaPromocionDAO extends JpaRepository<PlanillaPromocion, Long> {

	@Query("select coalesce(max(planilla.idPlanillaPromocion), '0') from PlanillaPromocion planilla")
	long findMaxId();

	List<PlanillaPromocion> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaPromocion> findByEstadoNotAndTipo(String variable,
			String variable2);

	List<PlanillaPromocion> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaPromocion> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaPromocion> findByUsuario(Usuario usuario);

	List<PlanillaPromocion> findByMarca(Marca marca);

	List<PlanillaPromocion> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaPromocion> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

	List<PlanillaPromocion> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaPromocion> findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2);

	List<PlanillaPromocion> findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2);

	List<PlanillaPromocion> findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2, String codigoMarca);

	List<PlanillaPromocion> findByUsuarioAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndIdPlanillaPromocionOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2, String codigoMarca, long codigo);

	List<PlanillaPromocion> findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2,
			String codigoMarca, String codigoUsuario);

	List<PlanillaPromocion> findByEstadoLikeAndTipoAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaPromocionOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2,
			String codigoMarca, String codigoUsuario, long codigo);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeAndIdPlanillaPromocionOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2, String codigoMarca, String codigoUsuario, long codigo);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstadoLikeAndFechaEnvioBetweenAndMarcaIdMarcaLikeAndUsuarioIdUsuarioLikeOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2, String codigoMarca, String codigoUsuario);

}
