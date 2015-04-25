package interfacedao.transacciones.notas;

import java.util.Date;
import java.util.List;

import modelo.pk.DetalleNotaCreditoId;
import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleNotaCreditoDAO extends JpaRepository<DetalleNotaCredito, DetalleNotaCreditoId>{

	List<DetalleNotaCredito> findByIdNotaCredito(NotaCredito planilla);

	List<DetalleNotaCredito> findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoLikeAndEstadoLikeAndFechaCreacionBetween(
			String codigoMarca, String codigoAliado, String codigoZona,
			String tipo, String estado, Date desde, Date hasta, Sort o);

	List<DetalleNotaCredito> findByMarcaIdMarcaLikeAndIdNotaCreditoAliadoNombreLikeAndIdNotaCreditoAliadoZonaIdZonaLikeAndIdNotaCreditoTipoLikeAndEstadoLikeAndIdNotaCreditoIdNotaCreditoAndFechaCreacionBetween(
			String codigoMarca, String codigoAliado, String codigoZona,
			String tipo, String estado, long codigo, Date desde, Date hasta, Sort o);

}
