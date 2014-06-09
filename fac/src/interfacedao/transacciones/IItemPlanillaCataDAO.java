package interfacedao.transacciones;

import java.util.List;

import modelo.pk.ItemPlanillaCataId;
import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPlanillaCataDAO extends JpaRepository<ItemPlanillaCata, ItemPlanillaCataId> {

	List<ItemPlanillaCata> findByPlanillaCata(PlanillaCata planilla);

}
