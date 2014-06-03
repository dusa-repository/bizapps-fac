package interfacedao.seguridad;

import modelo.seguridad.Grupo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IGrupoDAO extends JpaRepository<Grupo, Long> {

}
