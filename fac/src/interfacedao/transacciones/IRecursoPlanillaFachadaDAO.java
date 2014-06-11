package interfacedao.transacciones;

import java.util.List;

import modelo.pk.RecursoPlanillaFachadaId;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.RecursoPlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaFachadaDAO extends JpaRepository<RecursoPlanillaFachada, RecursoPlanillaFachadaId> {

	List<RecursoPlanillaFachada> findByPlanillaFachada(PlanillaFachada planilla);

}