package interfacedao.transacciones;

import java.util.Collection;
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

	Collection<? extends PlanillaUniforme> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaUniforme> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaUniforme> findByUsuario(Usuario usuario);

	List<PlanillaUniforme> findByMarca(Marca marca);

}
