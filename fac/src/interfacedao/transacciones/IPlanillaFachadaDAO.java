package interfacedao.transacciones;

import java.util.Collection;
import java.util.List;

import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanillaFachadaDAO extends JpaRepository<PlanillaFachada, Long> {
	
	@Query("select coalesce(max(planilla.idPlanillaFachada), '0') from PlanillaFachada planilla")
	long findMaxId();

	List<PlanillaFachada> findByUsuarioAndEstado(Usuario usuarioSesion,
			String string);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstado(
			String nombreUsuarioSesion, String variable);

	List<PlanillaFachada> findByEstadoNotAndTipo(String variable,
			String variable2);

	List<PlanillaFachada> findByUsuarioAndEstadoOrderByFechaEnvioAsc(
			Usuario usuarioSesion, String variable);

	List<PlanillaFachada> findByEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String variable2);

	List<PlanillaFachada> findByUsuarioSupervisorAndEstadoOrderByFechaEnvioAsc(
			String nombreUsuarioSesion, String variable);

	List<PlanillaFachada> findByUsuario(Usuario usuario);

	List<PlanillaFachada> findByMarca(Marca marca);

	List<PlanillaFachada> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String variable2);

	List<PlanillaFachada> findByEstadoAndTipoOrderByFechaEnvioAsc(
			String string, String variable2);

	List<PlanillaFachada> findByEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndEstadoNotAndTipoOrderByFechaEnvioAsc(
			String variable, String string, String string2, String string3,
			String string4, String variable2);

}
