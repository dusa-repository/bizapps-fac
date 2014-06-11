package interfacedao.transacciones;

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

	List<PlanillaPromocion> findByEstadoNot(String variable);

}
