package interfacedao.transacciones;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaUniformeDAO extends JpaRepository<PlanillaUniforme, Long> {

	@Query("select coalesce(max(planilla.idPlanillaUniforme), '0') from PlanillaUniforme planilla")
	long findMaxId();

	List<PlanillaUniforme> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaUniforme> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaUniforme> findByEstadoNotAndTipo(String variable,
			String variable2);

	List<PlanillaUniforme> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaUniforme> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaUniforme> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaUniforme> findByUsuario(Usuario usuario);

	List<PlanillaUniforme> findByMarca(Marca marca);

	List<PlanillaUniforme> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaUniforme> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

	List<PlanillaUniforme> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaUniforme> findByUsuarioAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			Usuario user, String estadoBuscar, Date fecha1, Date fecha2);

	List<PlanillaUniforme> findByEstadoAndTipoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String estadoBuscar, String tipoP, Date fecha1, Date fecha2);

	List<PlanillaUniforme> findByUsuarioSupervisorAndEstadoAndFechaEnvioBetweenOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable, Date fecha1,
			Date fecha2);

}
