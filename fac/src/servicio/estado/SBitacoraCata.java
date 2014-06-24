package servicio.estado;

import java.util.List;

import javax.swing.text.Position.Bias;

import interfacedao.estado.IBitacoraCataDAO;

import modelo.estado.BitacoraCata;
import modelo.transacciones.PlanillaCata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SBitacoraCata")
public class SBitacoraCata {

	@Autowired
	private IBitacoraCataDAO bitacoraCataDAO;

	public void guardar(BitacoraCata bitacora) {
		bitacoraCataDAO.saveAndFlush(bitacora);
	}

	public void eliminarPorPlanilla(PlanillaCata planilla) {
		List<BitacoraCata> bitacoras = bitacoraCataDAO.findByPlanillaCata(planilla);
		if(!bitacoras.isEmpty())
			bitacoraCataDAO.delete(bitacoras);
	}

	public List<BitacoraCata> buscarPorPlanilla(PlanillaCata planilla) {
		// TODO Auto-generated method stub
		return bitacoraCataDAO.findByPlanillaCata(planilla);
	}

	public void guardarBitacoras(List<BitacoraCata> listaBitacoras) {
		bitacoraCataDAO.save(listaBitacoras);
		
	}
}
