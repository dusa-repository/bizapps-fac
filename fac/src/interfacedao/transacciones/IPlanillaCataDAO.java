package interfacedao.transacciones;

import java.util.List;

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

	List<PlanillaCata> findByEstado(String variable);

}
