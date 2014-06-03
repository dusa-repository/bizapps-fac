package servicio.seguridad;

import interfacedao.seguridad.IArbolDAO;

import modelo.seguridad.Arbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SArbol")
public class SArbol {

	@Autowired
	private IArbolDAO arbolDAO;
	
	public Arbol buscar(long id) {
		return arbolDAO.findOne(id);
	}
}
