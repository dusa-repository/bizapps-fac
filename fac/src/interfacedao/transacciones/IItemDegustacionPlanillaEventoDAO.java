package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Sku;
import modelo.pk.ItemDegustacionPlanillaEventoId;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemDegustacionPlanillaEventoDAO extends JpaRepository<ItemDegustacionPlanillaEvento, ItemDegustacionPlanillaEventoId> {

	List<ItemDegustacionPlanillaEvento> findByIdPlanillaEvento(
			PlanillaEvento planilla);

	List<ItemDegustacionPlanillaEvento> findByIdSku(Sku sku);

}
