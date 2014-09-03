package servicio.maestros;

import interfacedao.maestros.IRecursoDAO;
import interfacedao.transacciones.IRecursoPlanillaCataDAO;
import interfacedao.transacciones.IRecursoPlanillaEventoDAO;
import interfacedao.transacciones.IRecursoPlanillaFachadaDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Recurso;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;
import modelo.transacciones.RecursoPlanillaFachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRecurso")
public class SRecurso {

	@Autowired
	private IRecursoDAO recursoDAO;
	@Autowired
	private IRecursoPlanillaCataDAO recursoPlanillaCataDAO;
	@Autowired
	private IRecursoPlanillaEventoDAO recursoPlanillaEventoDAO;
	@Autowired
	private IRecursoPlanillaFachadaDAO recursoPlanillaFachadaDAO;

	public void guardar(Recurso recurso) {
		recursoDAO.save(recurso);
	}

	public void eliminar(long id) {
		recursoDAO.delete(id);
	}

	public Recurso buscarPorDescripcion(String value) {
		return recursoDAO.findByDescripcion(value);
	}

	public List<Recurso> buscarTodosOrdenados() {
		return recursoDAO.findAllOrderByDescripcion();
	}

	public List<Recurso> buscarDisponibles(PlanillaCata planilla) {
		List<RecursoPlanillaCata> recursoPlanilla = recursoPlanillaCataDAO.findByPlanillaCata(planilla);
		List<Long> ids = new ArrayList<Long>();
		if(recursoPlanilla.isEmpty())
			return recursoDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<recursoPlanilla.size();i++){
				ids.add(recursoPlanilla.get(i).getRecurso().getIdRecurso());
			}
			return recursoDAO.findByIdRecursoNotIn(ids);
		}
	}

	public Recurso buscar(long recursoId) {
		return recursoDAO.findOne(recursoId);
	}

	public List<Recurso> buscarDisponibles(PlanillaEvento planilla) {
//		List<RecursoPlanillaEvento> recursoPlanilla = recursoPlanillaEventoDAO.findByPlanillaEvento(planilla);
//		List<Long> ids = new ArrayList<Long>();
//		if(recursoPlanilla.isEmpty())
			return recursoDAO.findAllOrderByDescripcion();
//		else{
//			for(int i=0; i<recursoPlanilla.size();i++){
//				ids.add(recursoPlanilla.get(i).getRecurso().getIdRecurso());
//			}
//			return recursoDAO.findByIdRecursoNotIn(ids);
//		}
	}

	public List<Recurso> buscarDisponiblesFachada(PlanillaFachada planilla) {
		List<RecursoPlanillaFachada> recursoPlanilla = recursoPlanillaFachadaDAO.findByPlanillaFachada(planilla);
		List<Long> ids = new ArrayList<Long>();
		if(recursoPlanilla.isEmpty())
			return recursoDAO.findAllOrderByDescripcion();
		else{
			for(int i=0; i<recursoPlanilla.size();i++){
				ids.add(recursoPlanilla.get(i).getRecurso().getIdRecurso());
			}
			return recursoDAO.findByIdRecursoNotIn(ids);
		}
	}
}
