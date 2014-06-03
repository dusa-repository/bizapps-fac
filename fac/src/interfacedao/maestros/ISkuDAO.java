package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Recurso;
import modelo.maestros.Sku;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ISkuDAO extends JpaRepository<Sku, String> {

	@Query("Select a from Sku a order by a.descripcion asc")
	List<Sku> findAllOrderByDescripcion();
	
}
