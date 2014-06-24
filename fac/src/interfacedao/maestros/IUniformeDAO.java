package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Uniforme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUniformeDAO extends JpaRepository<Uniforme, Long> {

	@Query("Select a from Uniforme a order by a.descripcion asc")
	List<Uniforme> findAllOrderByDescripcion();

	Uniforme findByDescripcion(String value);

	List<Uniforme> findByIdUniformeNotIn(List<Long> ids);
	
}
