package interfacedao.seguridad;

import java.util.List;

import modelo.maestros.Aliado;
import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDAO extends JpaRepository<Usuario, String> {

	Usuario findByIdUsuario(String nombre);

	@Query("Select u from Usuario u order by u.nombre asc")
	List<Usuario> findAllOrderByDescripcion();
}
