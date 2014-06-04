package interfacedao.transacciones;

import modelo.transacciones.PlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaEventoDAO extends JpaRepository<PlanillaEvento, Long> {

}
