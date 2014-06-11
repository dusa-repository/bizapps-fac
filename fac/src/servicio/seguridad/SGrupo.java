package servicio.seguridad;

import java.util.List;

import interfacedao.seguridad.IGrupoDAO;

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
}
