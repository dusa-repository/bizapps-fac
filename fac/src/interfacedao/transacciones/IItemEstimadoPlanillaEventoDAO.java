package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Sku;
import modelo.pk.ItemEstimadoPlanillaEventoId;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemEstimadoPlanillaEventoDAO extends JpaRepository<ItemEstimadoPlanillaEvento, ItemEstimadoPlanillaEventoId> {

	List<ItemEstimadoPlanillaEvento> findByIdPlanillaEvento(
			PlanillaEvento planilla);

	List<ItemEstimadoPlanillaEvento> findByIdSku(Sku sku);

}
