package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.pk.RecursoPlanillaEventoId;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaEventoDAO extends JpaRepository<RecursoPlanillaEvento, RecursoPlanillaEventoId> {

	List<RecursoPlanillaEvento> findByIdPlanillaEvento(PlanillaEvento planilla);

	List<RecursoPlanillaEvento> findByIdRecurso(Recurso recurso);

	List<RecursoPlanillaEvento> findByIdMarca(Marca marca);

}
