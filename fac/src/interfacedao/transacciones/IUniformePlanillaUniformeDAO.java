package interfacedao.transacciones;

import modelo.pk.UniformePlanillaUniformeId;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUniformePlanillaUniformeDAO extends JpaRepository<UniformePlanillaUniforme, UniformePlanillaUniformeId> {

}
