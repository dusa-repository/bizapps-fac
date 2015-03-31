package modelo.transacciones.notas;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.maestros.Aliado;

@Entity
@Table(name = "nota_credito")
public class NotaCredito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nota_credito", unique=true, nullable=false)
	private long idNotaCredito;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_aliado")
	private Aliado aliado;
	
	@Column(name = "fecha_nota")
	private Timestamp fechaNota;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name="estado",length = 500)
	private String estado;
	
	@Column(name="costo")
	private Double costo;
	
	@OneToMany(mappedBy = "id.notaCredito")
	private List<DetalleNotaCredito> existencias;
	
	@OneToMany(mappedBy = "id.notaCredito")
	private List<CostoNotaCredito> costoNotaCredito;

	public NotaCredito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotaCredito(long idNotaCredito, Aliado aliado, Timestamp fechaNota,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria, String tipo, Double costo,
			String estado) {
		super();
		this.idNotaCredito = idNotaCredito;
		this.aliado = aliado;
		this.fechaNota = fechaNota;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.tipo = tipo;
		this.costo = costo;
		this.estado = estado;
	}

	public long getIdNotaCredito() {
		return idNotaCredito;
	}

	public void setIdNotaCredito(long idNotaCredito) {
		this.idNotaCredito = idNotaCredito;
	}

	public Aliado getAliado() {
		return aliado;
	}

	public void setAliado(Aliado aliado) {
		this.aliado = aliado;
	}

	public Timestamp getFechaNota() {
		return fechaNota;
	}

	public void setFechaNota(Timestamp fechaNota) {
		this.fechaNota = fechaNota;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public List<DetalleNotaCredito> getExistencias() {
		return existencias;
	}

	public void setExistencias(List<DetalleNotaCredito> existencias) {
		this.existencias = existencias;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<CostoNotaCredito> getCostoNotaCredito() {
		return costoNotaCredito;
	}

	public void setCostoNotaCredito(List<CostoNotaCredito> costoNotaCredito) {
		this.costoNotaCredito = costoNotaCredito;
	}
	
	
}
