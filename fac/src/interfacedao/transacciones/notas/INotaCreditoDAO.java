package interfacedao.transacciones.notas;

import java.util.List;

import modelo.transacciones.notas.NotaCredito;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface INotaCreditoDAO extends JpaRepository<NotaCredito, Long>{

	@Query("select coalesce(max(planilla.idNotaCredito), '0') from NotaCredito planilla")
	Long buscarUltima();

	List<NotaCredito> findByTipo(String valor, Sort o);

}
