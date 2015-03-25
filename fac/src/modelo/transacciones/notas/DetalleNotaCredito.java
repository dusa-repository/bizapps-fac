package modelo.transacciones.notas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.maestros.Marca;
import modelo.pk.DetalleNotaCreditoId;

@Entity
@Table(name = "nota_credito_detalle")
public class DetalleNotaCredito implements	Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DetalleNotaCreditoId id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name="tipo_actividad",length = 500)
	private String tipoActividad;
	
	@Column(name="descripcion",length = 500)
	private String descripcion;
	
	@Column(name="botellas")
	private Integer botellas;
	
	@Column(name="costo")
	private Double costo;

	@Column(name="estado",length = 500)
	private String estado;
	
	@Column(name="observacion",length = 500)
	private String observacion;
	
	@Column(name = "fecha_creacion")
	private Timestamp fechaCreacion;
	
	@Column(name = "fecha_aprobacion")
	private Timestamp fechaAprobacion;
	
	@Column(name = "fecha_confirmacion")
	private Timestamp fechaConfirmacion;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public DetalleNotaCredito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetalleNotaCredito(DetalleNotaCreditoId id, Marca marca,
			String tipoActividad, String descripcion, Integer botellas,
			Double costo, String estado, String observacion,
			Timestamp fechaCreacion, Timestamp fechaAprobacion,
			Timestamp fechaConfirmacion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria) {
		super();
		this.id = id;
		this.marca = marca;
		this.tipoActividad = tipoActividad;
		this.descripcion = descripcion;
		this.botellas = botellas;
		this.costo = costo;
		this.estado = estado;
		this.observacion = observacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaAprobacion = fechaAprobacion;
		this.fechaConfirmacion = fechaConfirmacion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public DetalleNotaCreditoId getId() {
		return id;
	}

	public void setId(DetalleNotaCreditoId id) {
		this.id = id;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getBotellas() {
		return botellas;
	}

	public void setBotellas(Integer botellas) {
		this.botellas = botellas;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Timestamp fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Timestamp getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	public void setFechaConfirmacion(Timestamp fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
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
	
	public String traerFecha() {
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		return String.valueOf(formatoFecha.format(fechaCreacion));
	}

}
