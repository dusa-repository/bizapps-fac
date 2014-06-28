package interfacedao.transacciones;

import java.util.Collection;
import java.util.List;

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

	Collection<? extends PlanillaPromocion> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaPromocion> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

}
