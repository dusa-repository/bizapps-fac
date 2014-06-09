package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaArteDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaArte")
public class SPlanillaArte {

	@Autowired
	private IPlanillaArteDAO planillaArteDAO;

	public void guardar(PlanillaArte planillaArte) {
		planillaArteDAO.save(planillaArte);
	}

	public PlanillaArte buscar(long id) {
		return planillaArteDAO.findOne(id);
	}

	public PlanillaArte buscarUltima() {
		long id = planillaArteDAO.findMaxId();
		if (id != 0)
			return planillaArteDAO.findOne(id);
		return null;
	}

	public List<PlanillaArte> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaArteDAO.findByUsuarioAndEstado(usuarioSesion, "En Edicion");
	}

	public void eliminar(long id) {
		planillaArteDAO.delete(id);
	}
}
