package servicio.estado;

import java.util.List;

import interfacedao.estado.IBitacoraArteDAO;

import modelo.estado.BitacoraArte;
import modelo.transacciones.PlanillaArte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraArte")
public class SBitacoraArte {

	@Autowired
	private IBitacoraArteDAO bitacoraArteDAO;

	public void guardar(BitacoraArte bitacora) {
		bitacoraArteDAO.save(bitacora);
	}

	public void eliminar(long id) {
		bitacoraArteDAO.delete(id);
	}

	public void eliminarPorPlanilla(PlanillaArte planilla) {
		List<BitacoraArte> eliminados = bitacoraArteDAO.findByPlanillaArte(planilla);
		if(!eliminados.isEmpty())
			bitacoraArteDAO.delete(eliminados);
	}

	public List<BitacoraArte> buscarPorPlanilla(PlanillaArte planilla) {
		// TODO Auto-generated method stub
		return bitacoraArteDAO.findByPlanillaArte(planilla);
	}

	public void guardarBitacoras(List<BitacoraArte> listaBitacoras) {
		bitacoraArteDAO.save(listaBitacoras);
		
	}
}
