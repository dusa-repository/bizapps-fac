package modelo.transacciones.notas;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.CostoNotaCreditoId;

@Entity
@Table(name = "costo_nota_credito")
public class CostoNotaCredito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CostoNotaCreditoId id;

	@Column(name = "costo")
	private Double costo;

	@Column(name = "porcentaje_aplicado")
	private Double porcentajeAplicado;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public CostoNotaCredito(CostoNotaCreditoId id, Double costo,
			Double porcentaje, Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.id = id;
		this.costo = costo;
		this.porcentajeAplicado = porcentaje;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public CostoNotaCredito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CostoNotaCreditoId getId() {
		return id;
	}

	public void setId(CostoNotaCreditoId id) {
		this.id = id;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Timestamp getFechaAuditoria() {
		return fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public Double getPorcentajeAplicado() {
		return porcentajeAplicado;
	}

	public void setPorcentajeAplicado(Double porcentajeAplicado) {
		this.porcentajeAplicado = porcentajeAplicado;
	}

}
