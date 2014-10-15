package interfacedao.transacciones;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaArteDAO extends JpaRepository<PlanillaArte, Long> {

	@Query("select coalesce(max(planilla.idPlanillaArte), '0') from PlanillaArte planilla")
	long findMaxId();

	List<PlanillaArte> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaArte> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaArte> findByEstadoNotAndTipo(String variable, String variable2);

	List<PlanillaArte> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaArte> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaArte> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaArte> findByUsuario(Usuario usuario);

	List<PlanillaArte> findByMarca(Marca marca);

	List<PlanillaArte> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaArte> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaArte> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

	List<PlanillaArte> findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2);

	List<PlanillaArte> findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2);

	List<PlanillaArte> findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2);

}
