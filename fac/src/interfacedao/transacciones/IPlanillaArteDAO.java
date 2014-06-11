package interfacedao.transacciones;

import java.util.List;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaArteDAO extends JpaRepository<PlanillaArte, Long> {

	@Query("select coalesce(max(planilla.idPlanillaArte), '0') from PlanillaArte planilla")
	long findMaxId();

	List<PlanillaArte> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaArte> findByEstado(String variable);

	List<PlanillaArte> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

}
