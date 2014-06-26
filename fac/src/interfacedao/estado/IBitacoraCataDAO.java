package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraCata;
import modelo.transacciones.PlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraCataDAO extends JpaRepository<BitacoraCata, Long> {

	List<BitacoraCata> findByPlanillaCata(PlanillaCata planilla);

	BitacoraCata findByEstadoAndPlanillaCata(String estadoDefecto,
			PlanillaCata planillaCata);

}
