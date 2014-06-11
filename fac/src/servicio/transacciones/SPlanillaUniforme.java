package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IPlanillaUniformeDAO;

import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaUniforme")
public class SPlanillaUniforme {

	@Autowired
	private IPlanillaUniformeDAO planillaUniformeDAO;

	public PlanillaUniforme buscar(long id) {
		return planillaUniformeDAO.findOne(id);
	}

	public void guardar(PlanillaUniforme planillaUniforme) {
		planillaUniformeDAO.save(planillaUniforme);
	}

	public PlanillaUniforme buscarUltima() {
		long id = planillaUniformeDAO.findMaxId();
		if (id != 0)
			return planillaUniformeDAO.findOne(id);
		return null;
	}

	public void eliminar(long id) {
		planillaUniformeDAO.delete(id);
	}

	public List<PlanillaUniforme> buscarTodosOrdenados(Usuario usuarioSesion) {
		return planillaUniformeDAO.findByUsuarioAndEstado(usuarioSesion,
				"En Edicion");
	}

	public void guardarVarias(List<PlanillaUniforme> listUniforme) {
		planillaUniformeDAO.save(listUniforme);
	}

	public List<PlanillaUniforme> buscarUsuarioSessionYEstado(
			Usuario usuarioSesion, String variable) {
		return planillaUniformeDAO.findByUsuarioAndEstado(usuarioSesion,
				variable);
	}

	public List<PlanillaUniforme> buscarSupervisorYEstado(
			String nombreUsuarioSesion, String variable) {
		return planillaUniformeDAO.findByUsuarioSupervisorAndEstado(nombreUsuarioSesion,
				variable);
	}

	public List<PlanillaUniforme> buscarAdminEstado(String variable) {
		return planillaUniformeDAO.findByEstadoNot(variable);
	}
}
