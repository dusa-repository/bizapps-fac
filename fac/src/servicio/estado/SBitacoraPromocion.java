package servicio.estado;

import java.util.List;

import interfacedao.estado.IBitacoraPromocionDAO;

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
}
