package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMarcaDAO extends JpaRepository<Marca, String> {

	@Query("Select a from Marca a order by a.descripcion asc")
	List<Marca> findAllOrderByDescripcion();

	List<Marca> findByItems(Sku sku);

	List<Marca> findByIdMarcaNotIn(List<String> ids, Sort o);

	List<Marca> findByEmpresaIdEmpresa(Long id);

	List<Marca> findByEstado(boolean b, Sort o);
}
