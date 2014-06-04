package interfacedao.transacciones;

import modelo.transacciones.PlanillaPromocion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaPromocionDAO extends JpaRepository<PlanillaPromocion, Long> {

}
