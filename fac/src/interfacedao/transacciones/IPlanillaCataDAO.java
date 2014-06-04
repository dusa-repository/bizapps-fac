package interfacedao.transacciones;

import modelo.transacciones.PlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaCataDAO extends JpaRepository<PlanillaCata, Long> {

}
