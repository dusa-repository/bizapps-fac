package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraArte;
import modelo.transacciones.PlanillaArte;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraArteDAO extends JpaRepository<BitacoraArte, Long> {

	List<BitacoraArte> findByPlanillaArte(PlanillaArte planilla);

	BitacoraArte findByEstadoAndPlanillaArte(String estadoDefecto,
			PlanillaArte planillaArte);

}
