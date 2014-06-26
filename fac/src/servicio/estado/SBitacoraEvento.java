package servicio.estado;

import interfacedao.estado.IBitacoraEventoDAO;

import java.util.List;

import modelo.estado.BitacoraEvento;
import modelo.transacciones.PlanillaEvento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraEvento")
public class SBitacoraEvento {

	@Autowired
	private IBitacoraEventoDAO bitacoraEventoDAO;

	public void guardar(BitacoraEvento bitacora) {
		bitacoraEventoDAO.save(bitacora);
	}

	public void eliminarPorPlanilla(PlanillaEvento planilla) {
		List<BitacoraEvento> eliminados = bitacoraEventoDAO.findByPlanillaEvento(planilla);
		if(!eliminados.isEmpty())
			bitacoraEventoDAO.delete(eliminados);
	}

	public List<BitacoraEvento> buscarPorPlanilla(PlanillaEvento planilla) {
		return bitacoraEventoDAO.findByPlanillaEvento(planilla);
		 
	}

	public void guardarBitacoras(List<BitacoraEvento> listaBitacoras) {
		bitacoraEventoDAO.save(listaBitacoras);
		
	}


	public BitacoraEvento buscarPorEstadoYPlanilla(String estadoDefecto,
			PlanillaEvento planillaEvento) {
		return bitacoraEventoDAO.findByEstadoAndPlanillaEvento(estadoDefecto,planillaEvento);
	}

	public void guardarVarias(List<BitacoraEvento> listBitacoraEvento) {
		bitacoraEventoDAO.save(listBitacoraEvento);
		
	}
}
