package interfacedao.transacciones;

import modelo.pk.RecursoPlanillaFachadaId;
import modelo.transacciones.RecursoPlanillaFachada;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaFachadaDAO extends JpaRepository<RecursoPlanillaFachada, RecursoPlanillaFachadaId> {

}
