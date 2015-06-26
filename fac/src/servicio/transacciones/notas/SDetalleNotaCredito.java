package servicio.transacciones.notas;

import interfacedao.transacciones.notas.IDetalleNotaCreditoDAO;

import java.util.Date;
import java.util.List;

import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service("SDetalleNotaCredito")
public class SDetalleNotaCredito {

	@Autowired
	private IDetalleNotaCreditoDAO detalleDAO;
	private Sort o = new Sort(new Order(Direction.ASC, "fechaCreacion"),
			new Order(Direction.ASC, "idNotaCreditoAliadoNombre"), new Order(
					Direction.ASC, "marcaDescripcion"));
	private Sort o2 = new Sort(new Order(Direction.ASC,
			"idNotaCreditoAliadoNombre"), new Order(Direction.ASC,
			"marcaDescripcion"));

	public void limpiar(NotaCredito planilla) {
		List<DetalleNotaCredito> lista = detalleDAO
				.findByIdNotaCredito(planilla);
		if (!lista.isEmpty())
			detalleDAO.delete(lista);
	}

	public List<DetalleNotaCredito> buscarPorNota(NotaCredito nota) {
		return detalleDAO.findByIdNotaCredito(nota);
	}

	public void guardarVarios(List<DetalleNotaCredito> listaDetalle) {
		detalleDAO.save(listaDetalle);
	}

	public void guardar(DetalleNotaCredito detalle) {
		detalleDAO.saveAndFlush(detalle);
	}

	public List<DetalleNotaCredito> buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYTipoYFechasEntre(
			String codigoMarca, String codigoAliado, String codigoZona,
			String tipo, Date desde, Date hasta, String estado, boolean reporte) {
		Sort nuevo = o;
		if (reporte)
			nuevo = o2;
		return detalleDAO
				.findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoLikeAndEstadoLikeAndFechaCreacionBetween(
						codigoMarca, codigoAliado, codigoZona, tipo, estado,
						desde, hasta, nuevo);
	}

	public List<DetalleNotaCredito> buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYCodigoNotaYTipoYFechasEntre(
			String codigoMarca, String codigoAliado, String codigoZona,
			long codigo, String tipo, Date desde, Date hasta, String estado,
			boolean reporte) {
		Sort nuevo = o;
		if (reporte)
			nuevo = o2;
		return detalleDAO
				.findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoLikeAndEstadoLikeAndIdNotaCreditoIdNotaCreditoAndFechaCreacionBetween(
						codigoMarca, codigoAliado, codigoZona, tipo, estado,
						codigo, desde, hasta, nuevo);
	}
}
