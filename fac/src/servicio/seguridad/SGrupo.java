package servicio.seguridad;

import interfacedao.seguridad.IGrupoDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SGrupo")
public class SGrupo {

	@Autowired
	private IGrupoDAO grupoDAO;

	public List<Grupo> buscarGruposUsuario(Usuario u) {
		return grupoDAO.findByUsuariosOrderByNombreAsc(u);
	}

	public void guardarGrupo(Grupo grupo) {
		grupoDAO.save(grupo);
	}

	public Grupo buscar(long id) {
		return grupoDAO.findOne(id);
	}

	public void eliminar(long id) {
		grupoDAO.delete(id);
	}

	public List<Grupo> buscarTodos() {
		return grupoDAO.findAllOrderByNombre();
	}

	public Grupo buscarPorNombre(String value) {
		return grupoDAO.findByNombre(value);
	}

	public List<Grupo> buscarDisponibles(Usuario usuario) {
		List<Grupo> gupos = new ArrayList<Grupo>();
		if (usuario != null)
			gupos = grupoDAO.findByUsuarios(usuario);
		else
			return grupoDAO.findAllOrderByNombre();
		List<Long> ids = new ArrayList<Long>();
		if (gupos.isEmpty())
			return grupoDAO.findAllOrderByNombre();
		else {
			for (int i = 0; i < gupos.size(); i++) {
				ids.add(gupos.get(i).getIdGrupo());
			}
			return grupoDAO.findByIdGrupoNotIn(ids);
		}
	}

	public List<Grupo> buscarPorArbol(Arbol arbol) {
		// TODO Auto-generated method stub
		return grupoDAO.findByArbols(arbol);
	}

}
