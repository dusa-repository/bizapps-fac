package servicio.estado;

import java.util.List;

import interfacedao.estado.IBitacoraFachadaDAO;

import modelo.estado.BitacoraFachada;
import modelo.transacciones.PlanillaFachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraFachada")
public class SBitacoraFachada {

	@Autowired
	private IBitacoraFachadaDAO bitacoraFachadaDAO;

	public void guardar(BitacoraFachada bitacora) {
		bitacoraFachadaDAO.save(bitacora);
	}

	public void eliminarPorPlanilla(PlanillaFachada planilla) {
		List<BitacoraFachada> eliminados = bitacoraFachadaDAO.findByPlanillaFachada(planilla);
		if(!eliminados.isEmpty())
			bitacoraFachadaDAO.delete(eliminados);
	}

	public void guardarBitacoras(List<BitacoraFachada> listaBitacoras) {
		bitacoraFachadaDAO.save(listaBitacoras);
		
	}

	public List<BitacoraFachada> buscarPorPlanilla(PlanillaFachada planilla) {
		// TODO Auto-generated method stub
		return bitacoraFachadaDAO.findByPlanillaFachada(planilla);
	}
}
