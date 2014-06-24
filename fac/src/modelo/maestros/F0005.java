package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.F0005PK;



/**
 * The persistent class for the F0005 database table.
 * 
 */
@Entity
@Table(name = "F0005")
public class F0005 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private F0005PK id;

	@Column(name="DRDL01")
	private String drdl01;

	@Column(name="DRDL02")
	private String drdl02;

	@Column(name="DRHRDC")
	private String drhrdc;

	@Column(name="DRJOBN")
	private String drjobn;

	@Column(name="DRPID")
	private String drpid;

	@Column(name="DRSPHD")
	private String drsphd;

	@Column(name="DRUDCO")
	private String drudco;

	@Column(name="DRUPMT")
	private Double drupmt;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public F0005() {
	}

	public F0005PK getId() {
		return id;
	}

	public void setId(F0005PK id) {
		this.id = id;
	}

	public String getDrdl01() {
		return drdl01;
	}

	public void setDrdl01(String drdl01) {
		this.drdl01 = drdl01;
	}

	public String getDrdl02() {
		return drdl02;
	}

	public void setDrdl02(String drdl02) {
		this.drdl02 = drdl02;
	}

	public String getDrhrdc() {
		return drhrdc;
	}

	public void setDrhrdc(String drhrdc) {
		this.drhrdc = drhrdc;
	}

	public String getDrjobn() {
		return drjobn;
	}

	public void setDrjobn(String drjobn) {
		this.drjobn = drjobn;
	}

	public String getDrpid() {
		return drpid;
	}

	public void setDrpid(String drpid) {
		this.drpid = drpid;
	}

	public String getDrsphd() {
		return drsphd;
	}

	public void setDrsphd(String drsphd) {
		this.drsphd = drsphd;
	}

	public String getDrudco() {
		return drudco;
	}

	public void setDrudco(String drudco) {
		this.drudco = drudco;
	}

	public Double getDrupmt() {
		return drupmt;
	}

	public void setDrupmt(Double drupmt) {
		this.drupmt = drupmt;
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