package servicio.estado;

import java.util.List;

import interfacedao.estado.IBitacoraUniformeDAO;

import modelo.estado.BitacoraUniforme;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraUniforme")
public class SBitacoraUniforme {

	@Autowired
	private IBitacoraUniformeDAO bitacoraUniformeDAO;

	public void guardar(BitacoraUniforme bitacora) {
		bitacoraUniformeDAO.save(bitacora);
	}

	public void eliminarPorPlanilla(PlanillaUniforme planilla) {
		List<BitacoraUniforme> eliminados = bitacoraUniformeDAO.findByPlanillaUniforme(planilla);
		if(!eliminados.isEmpty())
			bitacoraUniformeDAO.delete(eliminados);
	}
}
