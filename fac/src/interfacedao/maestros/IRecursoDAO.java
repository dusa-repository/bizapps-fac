package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRecursoDAO extends JpaRepository<Recurso, Long> {

	@Query("Select a from Recurso a order by a.descripcion asc")
	List<Recurso> findAllOrderByDescripcion();

	Recurso findByDescripcion(String value);

	List<Recurso> findByIdRecursoNotIn(List<Long> ids);
	
}
