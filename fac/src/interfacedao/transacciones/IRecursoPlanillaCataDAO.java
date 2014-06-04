package interfacedao.transacciones;

import modelo.pk.RecursoPlanillaCataId;
import modelo.transacciones.RecursoPlanillaCata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecursoPlanillaCataDAO extends JpaRepository<RecursoPlanillaCata, RecursoPlanillaCataId> {

}
