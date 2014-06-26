package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraUniforme;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraUniformeDAO extends JpaRepository<BitacoraUniforme, Long> {

	List<BitacoraUniforme> findByPlanillaUniforme(PlanillaUniforme planilla);

	BitacoraUniforme findByEstadoAndPlanillaUniforme(String estadoDefecto,
			PlanillaUniforme planillaUniforme);

}
