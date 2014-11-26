package servicio.seguridad;

import interfacedao.seguridad.IGrupoDAO;
import interfacedao.seguridad.IUsuarioDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import modelo.maestros.Zona;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SUsuario")
public class SUsuario {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	@Autowired
	private IGrupoDAO grupoDAO;

	@Transactional
	public Usuario buscarUsuarioPorNombre(String nombre) {
		return usuarioDAO.findByIdUsuario(nombre);
	}
	
	@Transactional
	public Usuario buscarUsuarioPorNombre2(String nombre) {
		return usuarioDAO.findByIdUsuarioAllIgnoreCase(nombre);
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

	public Usuario buscarAdmin(String id) {
		Grupo grupo = grupoDAO.findByNombre("Administrador");
		return usuarioDAO.findByIdUsuarioAndGrupos(id, grupo);
	}

	public Usuario buscar(String value) {
		return usuarioDAO.findOne(value);
	}

	public List<Usuario> buscarAdministradores() {
		Grupo grupo = grupoDAO.findByNombre("Administrador");
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (grupo != null)
			usuarios = usuarioDAO.findByGrupos(grupo);
		return usuarios;
	}

	public List<Usuario> buscarPorZona(Zona zona) {
		return usuarioDAO.findByZona(zona);
	}

	public List<Usuario> buscarPorGrupo(Grupo grupo) {
		return usuarioDAO.findByGrupos(grupo);
	}

	public List<Usuario> buscarSupervisados(
			String nombreUsuarioSesion) {
		return usuarioDAO.findBySupervisor(nombreUsuarioSesion);
	}
}
