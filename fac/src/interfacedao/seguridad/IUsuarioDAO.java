package interfacedao.seguridad;

import java.util.List;

import modelo.maestros.Zona;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDAO extends JpaRepository<Usuario, String> {

	Usuario findByIdUsuario(String nombre);

	@Query("Select u from Usuario u order by u.nombre asc")
	List<Usuario> findAllOrderByDescripcion();

	List<Usuario> findByGrupos(Grupo grupo);

	List<Usuario> findByZona(Zona zona);
	
	Usuario findByIdUsuarioAndGrupos(String value, Grupo grupo);

	Usuario findByIdUsuarioAllIgnoreCase(String nombre);

	List<Usuario> findBySupervisor(String nombreUsuarioSesion);

	Usuario findByIdUsuarioAndMail(String value, String value2);
}
