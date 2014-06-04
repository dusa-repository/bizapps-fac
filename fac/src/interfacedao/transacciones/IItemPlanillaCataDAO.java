package interfacedao.transacciones;

import modelo.pk.ItemPlanillaCataId;
import modelo.transacciones.ItemPlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPlanillaCataDAO extends JpaRepository<ItemPlanillaCata, ItemPlanillaCataId> {

}
