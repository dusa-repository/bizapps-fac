package interfacedao.transacciones.notas;

import modelo.transacciones.notas.Planificacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanificacionDAO extends JpaRepository<Planificacion, Long> {

}
