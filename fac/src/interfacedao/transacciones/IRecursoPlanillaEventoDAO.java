package interfacedao.transacciones;

import modelo.pk.RecursoPlanillaEventoId;
import modelo.transacciones.RecursoPlanillaEvento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaEventoDAO extends JpaRepository<RecursoPlanillaEvento, RecursoPlanillaEventoId> {

}
