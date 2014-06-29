package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.pk.RecursoPlanillaEventoId;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaEventoDAO extends JpaRepository<RecursoPlanillaEvento, RecursoPlanillaEventoId> {

	List<RecursoPlanillaEvento> findByPlanillaEvento(PlanillaEvento planilla);

	List<RecursoPlanillaEvento> findByRecurso(Recurso recurso);

	List<RecursoPlanillaEvento> findByMarca(Marca marca);

}
