package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Configuracion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfiguracionDAO extends JpaRepository<Configuracion, Long> {

	List<Configuracion> findByTipo(String string);

}
