package interfacedao.transacciones.notas;

import java.util.List;

import modelo.pk.ConfiguracionMarcaId;
import modelo.transacciones.notas.ConfiguracionMarca;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfiguracionMarcaDAO extends
		JpaRepository<ConfiguracionMarca, ConfiguracionMarcaId> {

	List<ConfiguracionMarca> findByIdTipo(String valor, Sort o);

	List<ConfiguracionMarca> findByIdTipoAndCostoNot(String valor, Double cero, Sort o);

	List<ConfiguracionMarca> findByIdTipoAndCosto(String valor, Double cero);

}
