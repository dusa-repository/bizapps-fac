package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Recurso;
import modelo.pk.RecursoPlanillaCataId;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.RecursoPlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaCataDAO extends JpaRepository<RecursoPlanillaCata, RecursoPlanillaCataId> {

	List<RecursoPlanillaCata> findByPlanillaCata(PlanillaCata planilla);

	List<RecursoPlanillaCata> findByRecurso(Recurso recurso);

}
