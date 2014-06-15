package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Configuracion;
import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfiguracionDAO extends JpaRepository<Configuracion, Long> {

	List<Configuracion> findByTipo(String string);

	List<Configuracion> findDistinctByUsuarioAndTipo(Usuario u, String string);

}
