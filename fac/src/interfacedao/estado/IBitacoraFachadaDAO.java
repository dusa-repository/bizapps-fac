package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraFachada;
import modelo.transacciones.PlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IBitacoraFachadaDAO extends JpaRepository<BitacoraFachada, Long> {

	List<BitacoraFachada> findByPlanillaFachada(PlanillaFachada planilla);

}
