package modelo.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Marca;
import modelo.transacciones.notas.NotaCredito;

@Embeddable
public class CostoNotaCreditoId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="id_nota_credito")
	private NotaCredito notaCredito;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
		
	@Column(name="id_linea", nullable=false)
	private long idLinea;

	public CostoNotaCreditoId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotaCredito getNotaCredito() {
		return notaCredito;
	}

	public void setNotaCredito(NotaCredito notaCredito) {
		this.notaCredito = notaCredito;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(long idLinea) {
		this.idLinea = idLinea;
	}

}
