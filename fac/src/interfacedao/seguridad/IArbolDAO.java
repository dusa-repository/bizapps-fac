package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IArbolDAO extends JpaRepository<Arbol, Long> {

	List<Arbol> findByGruposArbol(Grupo grupo);

	List<Arbol> findByNombreBotonStartingWithAllIgnoreCase(String string);

	Arbol findByNombre(String value);

}
