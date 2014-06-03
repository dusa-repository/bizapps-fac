package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Aliado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAliadoDAO extends JpaRepository<Aliado, Long> {

	@Query("Select a from Aliado a order by a.nombre asc")
	List<Aliado> findAllOrderByDescripcion();

	Aliado findByNombre(String value);
}
