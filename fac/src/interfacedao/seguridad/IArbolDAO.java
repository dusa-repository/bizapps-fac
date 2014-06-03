package interfacedao.seguridad;

import modelo.seguridad.Arbol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IArbolDAO extends JpaRepository<Arbol, Long> {

}
