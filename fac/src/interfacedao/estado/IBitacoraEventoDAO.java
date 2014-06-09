package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraEventoDAO extends JpaRepository<BitacoraEvento, Long> {

	List<BitacoraEvento> findByPlanillaEvento(PlanillaEvento planilla);

}
