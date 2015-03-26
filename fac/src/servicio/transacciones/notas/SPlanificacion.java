package servicio.transacciones.notas;

import java.util.ArrayList;
import java.util.List;

import interfacedao.transacciones.notas.IPlanificacionDAO;
import modelo.transacciones.notas.Planificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SPlanificacion")
public class SPlanificacion {

	@Autowired
	private IPlanificacionDAO planificacionDAO;

	public void eliminar(Long id) {
		planificacionDAO.delete(id);
	}

	public List<Planificacion> buscarTodosOrdenados() {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("fechaDesde");
		ordenar.add("mes");
		ordenar.add("marca.descripcion");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return planificacionDAO.findAll(o);
	}

	public void guardar(Planificacion planificacion) {
		planificacionDAO.save(planificacion);
	}
}
