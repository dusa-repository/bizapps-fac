package servicio.maestros;

import interfacedao.maestros.IUniformeDAO;
import interfacedao.transacciones.IUniformePlanillaUniformeDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Uniforme;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SUniforme")
public class SUniforme {

	@Autowired
	private IUniformeDAO uniformeDAO;
	@Autowired
	private IUniformePlanillaUniformeDAO uniformePlanillaDAO;

	public void guardar(Uniforme uniforme) {
		uniformeDAO.saveAndFlush(uniforme);
	}

	public void eliminar(long id) {
		uniformeDAO.delete(id);
	}

	public List<Uniforme> buscarTodosOrdenados() {
		return uniformeDAO.findAllOrderByDescripcion();
	}

	public Uniforme buscarPorDescripcion(String value) {
		return uniformeDAO.findByDescripcion(value);
	}

	public List<Uniforme> buscarDisponibles(PlanillaUniforme planilla) {
		List<UniformePlanillaUniforme> uniformePlanilla = uniformePlanillaDAO.findByIdPlanillaUniforme(planilla);
		List<Long> ids = new ArrayList<Long>();
		if(uniformePlanilla.isEmpty())
			return uniformeDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<uniformePlanilla.size();i++){
				ids.add(uniformePlanilla.get(i).getId().getUniforme().getIdUniforme());
			}
			return uniformeDAO.findByIdUniformeNotIn(ids);
		}
	}

	public Uniforme buscar(long idUniforme) {
		return uniformeDAO.findOne(idUniforme);
	}
}
