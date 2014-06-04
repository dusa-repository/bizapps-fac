package interfacedao.transacciones;

import modelo.pk.ItemEstimadoPlanillaEventoId;
import modelo.transacciones.ItemEstimadoPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemEstimadoPlanillaEventoDAO extends JpaRepository<ItemEstimadoPlanillaEvento, ItemEstimadoPlanillaEventoId> {

}
