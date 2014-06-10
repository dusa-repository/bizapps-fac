package interfacedao.transacciones;

import java.util.List;

import modelo.pk.UniformePlanillaUniformeId;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUniformePlanillaUniformeDAO extends JpaRepository<UniformePlanillaUniforme, UniformePlanillaUniformeId> {

	List<UniformePlanillaUniforme> findByPlanillaUniforme(
			PlanillaUniforme planilla);

}
