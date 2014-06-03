package interfacedao.seguridad;

import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, String> {

	Usuario findByIdUsuario(String nombre);

}
