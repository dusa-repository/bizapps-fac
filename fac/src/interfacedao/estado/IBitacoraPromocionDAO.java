package interfacedao.estado;

import java.util.List;

import modelo.estado.BitacoraPromocion;
import modelo.transacciones.PlanillaPromocion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBitacoraPromocionDAO extends JpaRepository<BitacoraPromocion, Long> {

	List<BitacoraPromocion> findByPlanillaPromocion(PlanillaPromocion planilla);

}
