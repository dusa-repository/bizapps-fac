package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGrupoDAO extends JpaRepository<Grupo, Long> {

	List<Grupo> findByUsuariosOrderByNombreAsc(Usuario u);

	@Query("select g from Grupo g order by g.nombre asc")
	List<Grupo> findAllOrderByNombre();

	Grupo findByNombre(String value);

	List<Grupo> findByUsuariosNot(Usuario usuario);

	List<Grupo> findByUsuarios(Usuario usuario);

	List<Grupo> findByIdGrupoNotIn(List<Long> ids);

}
