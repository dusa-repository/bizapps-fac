package interfacedao.transacciones.notas;

import java.util.List;

import modelo.pk.CostoNotaCreditoId;
import modelo.transacciones.notas.CostoNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICostoNotaCreditoDAO extends
		JpaRepository<CostoNotaCredito, CostoNotaCreditoId> {

	List<CostoNotaCredito> findByIdNotaCredito(NotaCredito nota);

}
