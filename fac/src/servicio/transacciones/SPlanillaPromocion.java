package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaPromocionDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaPromocion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaPromocion")
public class SPlanillaPromocion {

	@Autowired
	private IPlanillaPromocionDAO planillaPromocionDAO;

	public void guardar(PlanillaPromocion planillaPromocion) {
		planillaPromocionDAO.save(planillaPromocion);
	}

	public PlanillaPromocion buscar(long id) {
		return planillaPromocionDAO.findOne(id);
	}

	public PlanillaPromocion buscarUltima() {
		long id = planillaPromocionDAO.findMaxId();
		if (id != 0)
			return planillaPromocionDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaPromocionDAO.delete(id);
	}

	public List<PlanillaPromocion> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaPromocionDAO.findByUsuarioAndEstado(usuarioSesion,"En Edicion");
	}
}
