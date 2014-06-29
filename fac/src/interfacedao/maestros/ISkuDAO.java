package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ISkuDAO extends JpaRepository<Sku, String> {

	@Query("Select a from Sku a order by a.descripcion asc")
	List<Sku> findAllOrderByDescripcion();

	List<Sku> findByIdSkuNotIn(List<String> ids);

	List<Sku> findByMarca(Marca marca);
	
}
