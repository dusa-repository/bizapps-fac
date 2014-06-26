package servicio.estado;

import interfacedao.estado.IBitacoraUniformeDAO;

import java.util.List;

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

	public List<BitacoraUniforme> buscarPorPlanilla(PlanillaUniforme planilla) {
		return bitacoraUniformeDAO.findByPlanillaUniforme(planilla);
	}

	public void guardarBitacoras(List<BitacoraUniforme> listaBitacoras) {
		bitacoraUniformeDAO.save(listaBitacoras);
		
	}

	public BitacoraUniforme buscarPorEstadoYPlanilla(String estadoDefecto,
			PlanillaUniforme planillaUniforme) {
		return bitacoraUniformeDAO.findByEstadoAndPlanillaUniforme(estadoDefecto,planillaUniforme);
	}

	public void guardarVarias(List<BitacoraUniforme> listBitacoraUniforme) {
		bitacoraUniformeDAO.save(listBitacoraUniforme);
		
	}
}
