package servicio.transacciones.notas;

import java.util.Date;
import java.util.List;

import interfacedao.transacciones.notas.IDetalleNotaCreditoDAO;
import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDetalleNotaCredito")
public class SDetalleNotaCredito {

	@Autowired
	private IDetalleNotaCreditoDAO detalleDAO;

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
			String tipo, Date desde, Date hasta, String estado) {
		return detalleDAO
				.findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoAndEstadoLikeAndFechaCreacionBetweenAndEstadoNot(
						codigoMarca, codigoAliado, codigoZona, tipo, estado,
						desde, hasta, "Pendiente");
	}

	public List<DetalleNotaCredito> buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYCodigoNotaYTipoYFechasEntre(
			String codigoMarca, String codigoAliado, String codigoZona,
			long codigo, String tipo, Date desde, Date hasta, String estado) {
		return detalleDAO
				.findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoAndEstadoLikeAndIdNotaCreditoIdNotaCreditoAndFechaCreacionBetweenAndEstadoNot(
						codigoMarca, codigoAliado, codigoZona, tipo, estado,
						codigo, desde, hasta, "Pendiente");
	}
}
