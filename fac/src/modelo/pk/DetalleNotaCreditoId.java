package modelo.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.transacciones.notas.NotaCredito;

@Embeddable
public class DetalleNotaCreditoId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="id_detalle_credito", nullable=false)
	private long idDetalleCredito;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="id_nota_credito")
	private NotaCredito notaCredito;

	public DetalleNotaCreditoId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getIdDetalleCredito() {
		return idDetalleCredito;
	}

	public void setIdDetalleCredito(long idDetalleCredito) {
		this.idDetalleCredito = idDetalleCredito;
	}

	public NotaCredito getNotaCredito() {
		return notaCredito;
	}

	public void setNotaCredito(NotaCredito notaCredito) {
		this.notaCredito = notaCredito;
	}
	
	

}
