package interfacedao.transacciones;

import java.util.Collection;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaEventoDAO extends JpaRepository<PlanillaEvento, Long> {

	@Query("select coalesce(max(planilla.idPlanillaEvento), '0') from PlanillaEvento planilla")
	long findMaxId();

	List<PlanillaEvento> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaEvento> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaEvento> findByEstadoNotAndTipo(String variable,
			String variable2);

	List<PlanillaEvento> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaEvento> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaEvento> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaEvento> findByUsuario(Usuario usuario);

	List<PlanillaEvento> findByMarca(Marca marca);

	List<PlanillaEvento> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaEvento> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaEvento> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

}
