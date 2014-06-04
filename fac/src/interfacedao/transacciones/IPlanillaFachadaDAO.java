package interfacedao.transacciones;

import modelo.transacciones.PlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanillaFachadaDAO extends JpaRepository<PlanillaFachada, Long> {

}
