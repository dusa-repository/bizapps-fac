package servicio.transacciones.notas;

import java.util.ArrayList;
import java.util.List;

import interfacedao.transacciones.notas.INotaCreditoDAO;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SNotaCredito")
public class SNotaCredito {

	@Autowired
	private INotaCreditoDAO notaDAO;

	public NotaCredito buscarUltima() {
		Long id = notaDAO.buscarUltima();
		if (id != 0)
			return notaDAO.findOne(id);
		return null;
	}

	public NotaCredito buscar(Long id) {
		return notaDAO.findOne(id);
	}

	public void eliminar(Long id) {
		notaDAO.delete(id);
	}

	public List<NotaCredito> buscarTodosOrdenados() {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("fechaNota");
		ordenar.add("estado");
		ordenar.add("aliado.codigo");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return notaDAO.findAll(o);
	}

	public List<NotaCredito> buscarTodosOrdenadosPorTipo(String valor) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("fechaNota");
		ordenar.add("estado");
		ordenar.add("aliado.codigo");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return notaDAO.findByTipo(valor, o);
	}

	public void guardar(NotaCredito nota) {
		notaDAO.save(nota);
	}
}
