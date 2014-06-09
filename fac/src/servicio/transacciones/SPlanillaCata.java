package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaCataDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaCata")
public class SPlanillaCata {

	@Autowired
	private IPlanillaCataDAO planillaCataDAO;

	public PlanillaCata buscar(long id) {
		return planillaCataDAO.findOne(id);
	}

	public void guardar(PlanillaCata planilla) {
		planillaCataDAO.saveAndFlush(planilla);
	}

	public void eliminar(long id) {
		planillaCataDAO.delete(id);
	}

	public PlanillaCata buscarUltima() {
		long id = planillaCataDAO.findMaxId();
		if (id != 0)
			return planillaCataDAO.findOne(id);
		return null;
	}

	public List<PlanillaCata> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaCataDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}
}
