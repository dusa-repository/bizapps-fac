package servicio.seguridad;

import interfacedao.seguridad.IArbolDAO;

import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SArbol")
public class SArbol {

	@Autowired
	private IArbolDAO arbolDAO;
	
	public Arbol buscar(long id) {
		return arbolDAO.findOne(id);
	}

	public List<Arbol> buscarTodos() {
		return arbolDAO.findAll();
	}

	public List<Arbol> buscarFuncionalidadesGrupo(Grupo grupo) {
		return arbolDAO.findByGruposArbol(grupo);
	}

	public List<Arbol> buscarParaConfiguracion() {
		return arbolDAO.findByNombreBotonStartingWithAllIgnoreCase("Planilla");
	}

	public void guardar(Arbol arbolitoDeNavidadRosado) {
		arbolDAO.saveAndFlush(arbolitoDeNavidadRosado);
	}

	public void eliminar(long id) {
		arbolDAO.delete(id);
	}

	public Arbol buscarPorNombre(String value) {
		return arbolDAO.findByNombre(value);
	}
}
