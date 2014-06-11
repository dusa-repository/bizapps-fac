package interfacedao.transacciones;

import java.util.List;

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

	List<PlanillaEvento> findByEstado(String variable);

}
