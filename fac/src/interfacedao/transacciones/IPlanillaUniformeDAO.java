package interfacedao.transacciones;

import modelo.transacciones.PlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaUniformeDAO extends JpaRepository<PlanillaUniforme, Long> {

}
