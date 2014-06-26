package servicio.estado;

import interfacedao.estado.IBitacoraPromocionDAO;

import java.util.List;

import modelo.estado.BitacoraPromocion;
import modelo.transacciones.PlanillaPromocion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraPromocion")
public class SBitacoraPromocion {

	@Autowired
	private IBitacoraPromocionDAO bitacoraPromocionDAO;

	public void guardar(BitacoraPromocion bitacora) {
		bitacoraPromocionDAO.save(bitacora);
	}

	public void eliminarPorPlanilla(PlanillaPromocion planilla) {
		List<BitacoraPromocion> eliminados = bitacoraPromocionDAO.findByPlanillaPromocion(planilla);
		if(!eliminados.isEmpty())
			bitacoraPromocionDAO.delete(eliminados);
	}

	public List<BitacoraPromocion> buscarPorPlanilla(PlanillaPromocion planilla) {
		// TODO Auto-generated method stub
		return bitacoraPromocionDAO.findByPlanillaPromocion(planilla);
	}

	public void guardarBitacoras(List<BitacoraPromocion> listaBitacoras) {
		bitacoraPromocionDAO.save(listaBitacoras);
		
	}

	public BitacoraPromocion buscarPorEstadoYPlanilla(String estadoDefecto,
			PlanillaPromocion planillaPromocion) {
		return bitacoraPromocionDAO.findByEstadoAndPlanillaPromocion(estadoDefecto,planillaPromocion);
	}

	public void guardarVarias(List<BitacoraPromocion> listBitacoraPromocion) {
		bitacoraPromocionDAO.save(listBitacoraPromocion);
		
	}
}
