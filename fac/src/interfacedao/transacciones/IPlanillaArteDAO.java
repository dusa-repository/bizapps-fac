package interfacedao.transacciones;

import modelo.transacciones.PlanillaArte;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaArteDAO extends JpaRepository<PlanillaArte, Long> {

}
