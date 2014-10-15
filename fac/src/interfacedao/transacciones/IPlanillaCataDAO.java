package interfacedao.transacciones;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaCataDAO extends JpaRepository<PlanillaCata, Long> {

	@Query("select coalesce(max(planilla.idPlanillaCata), '0') from PlanillaCata planilla")
	long findMaxId();

	List<PlanillaCata> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaCata> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaCata> findByEstadoNotAndTipo(String variable, String variable2);

	List<PlanillaCata> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaCata> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaCata> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaCata> findByUsuario(Usuario usuario);

	List<PlanillaCata> findByMarca(Marca marca);

	List<PlanillaCata> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaCata> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaCata> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

	List<PlanillaCata> findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2);

	List<PlanillaCata> findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2);

	List<PlanillaCata> findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2);

}
