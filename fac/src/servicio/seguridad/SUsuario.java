package servicio.seguridad;

import java.util.List;

import interfacedao.seguridad.IUsuarioDAO;

import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SUsuario")
public class SUsuario {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Transactional
	public Usuario buscarUsuarioPorNombre(String nombre) {
		return usuarioDAO.findByIdUsuario(nombre);
	}

	public void guardar(Usuario usuario) {
		usuarioDAO.saveAndFlush(usuario);
	}

	public void eliminar(String id) {
		usuarioDAO.delete(id);
	}

	public List<Usuario> buscarTodosOrdenados() {
		return usuarioDAO.findAllOrderByDescripcion();
	}

	public Usuario buscar(String value) {
		return usuarioDAO.findOne(value);
	}
}
