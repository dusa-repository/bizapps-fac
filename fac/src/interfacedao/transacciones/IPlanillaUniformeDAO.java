package interfacedao.transacciones;

import java.util.List;

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

	List<PlanillaUniforme> findByEstadoNot(String variable);

}
