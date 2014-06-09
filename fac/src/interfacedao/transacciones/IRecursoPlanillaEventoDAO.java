package interfacedao.transacciones;

import java.util.List;

import modelo.pk.RecursoPlanillaEventoId;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaEventoDAO extends JpaRepository<RecursoPlanillaEvento, RecursoPlanillaEventoId> {

	List<RecursoPlanillaEvento> findByPlanillaEvento(PlanillaEvento planilla);

}
