package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Zona;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IZonaDAO extends JpaRepository<Zona, String> {

	@Query("Select z from Zona z order by z.descripcion asc")
	List<Zona> findAllOrderByDescripcion();

	Zona findByDescripcion(String value);
	
}
