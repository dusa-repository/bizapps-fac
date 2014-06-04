package interfacedao.transacciones;

import modelo.pk.ItemDegustacionPlanillaEventoId;
import modelo.transacciones.ItemDegustacionPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemDegustacionPlanillaEventoDAO extends JpaRepository<ItemDegustacionPlanillaEvento, ItemDegustacionPlanillaEventoId> {

}
