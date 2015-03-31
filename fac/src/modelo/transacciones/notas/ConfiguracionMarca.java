package modelo.transacciones.notas;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.ConfiguracionMarcaId;

@Entity
@Table(name = "configuracion_marca")
public class ConfiguracionMarca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConfiguracionMarcaId id;

	@Column(name = "plan_caja")
	private Integer cajas;

	@Column(name = "porcentaje_plan")
	private Double porcentajePlan;

	@Column(name = "costo")
	private Double costo;

	@Column(name = "porcentaje_costo")
	private Double porcentajeCosto;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public ConfiguracionMarca(ConfiguracionMarcaId id, Integer cajas,
			Double porcentajePlan, Double costo, Double porcentajeCosto,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.id = id;
		this.cajas = cajas;
		this.porcentajePlan = porcentajePlan;
		this.costo = costo;
		this.porcentajeCosto = porcentajeCosto;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public ConfiguracionMarca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfiguracionMarcaId getId() {
		return id;
	}

	public void setId(ConfiguracionMarcaId id) {
		this.id = id;
	}

	public Integer getCajas() {
		return cajas;
	}

	public void setCajas(Integer cajas) {
		this.cajas = cajas;
	}

	public Double getPorcentajePlan() {
		return porcentajePlan;
	}

	public void setPorcentajePlan(Double porcentajePlan) {
		this.porcentajePlan = porcentajePlan;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getPorcentajeCosto() {
		return porcentajeCosto;
	}

	public void setPorcentajeCosto(Double porcentajeCosto) {
		this.porcentajeCosto = porcentajeCosto;
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
