package servicio.transacciones.notas;

import java.util.List;

import interfacedao.transacciones.notas.ICostoNotaCreditoDAO;
import modelo.transacciones.notas.CostoNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCostoNotaCredito")
public class SCostoNotaCredito {

	@Autowired
	private ICostoNotaCreditoDAO costoDAO;

	public void limpiar(NotaCredito nota) {
		List<CostoNotaCredito> lista = costoDAO.findByIdNotaCredito(nota);
		if (!lista.isEmpty())
			costoDAO.delete(lista);
	}

	public void guardarVarias(List<CostoNotaCredito> lista) {
		costoDAO.save(lista);
	}

	public void guardar(CostoNotaCredito objeto) {
		costoDAO.save(objeto);
	}
}
