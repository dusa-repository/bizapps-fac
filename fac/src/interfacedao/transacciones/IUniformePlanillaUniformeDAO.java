package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Uniforme;
import modelo.pk.UniformePlanillaUniformeId;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUniformePlanillaUniformeDAO extends JpaRepository<UniformePlanillaUniforme, UniformePlanillaUniformeId> {

	List<UniformePlanillaUniforme> findByIdPlanillaUniforme(
			PlanillaUniforme planilla);

	List<UniformePlanillaUniforme> findByIdUniforme(Uniforme uni);

}
