package modelo.seguridad;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "configuracion_envio")
public class ConfiguracionEnvio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_configuracion_envio", unique = true, nullable = false)
	private long idConfiguracionEnvio;

	@Column(name = "estado_configuracion")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean estado;

	@Column(name = "desde")
	private Integer desde;

	@Column(name = "hasta")
	private Integer hasta;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public ConfiguracionEnvio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfiguracionEnvio(long idConfiguracionEnvio, Boolean estado,
			Integer desde, Integer hasta, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria) {
		super();
		this.idConfiguracionEnvio = idConfiguracionEnvio;
		this.estado = estado;
		this.desde = desde;
		this.hasta = hasta;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public long getIdConfiguracionEnvio() {
		return idConfiguracionEnvio;
	}

	public void setIdConfiguracionEnvio(long idConfiguracionEnvio) {
		this.idConfiguracionEnvio = idConfiguracionEnvio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getDesde() {
		return desde;
	}

	public void setDesde(Integer desde) {
		this.desde = desde;
	}

	public Integer getHasta() {
		return hasta;
	}

	public void setHasta(Integer hasta) {
		this.hasta = hasta;
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

}
