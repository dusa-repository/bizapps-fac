package modelo.estado;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.transacciones.PlanillaEvento;

@Entity
@Table(name = "bitacora_planilla_evento")
public class BitacoraEvento implements Serializable {

	private static final long serialVersionUID = 6577720661056807517L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bitacora_planilla_evento", unique=true, nullable=false)
	private long idBitacora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_planilla_evento")
	private PlanillaEvento planillaEvento;
	
	@Column(name="estado_planilla",length = 500)
	private String estado;
	
	@Column(name = "fecha_cambio")
	private Timestamp fechaCambio;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public BitacoraEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BitacoraEvento(long idBitacora, PlanillaEvento planillaEvento,
			String estado, Timestamp fechaCambio, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria) {
		super();
		this.idBitacora = idBitacora;
		this.planillaEvento = planillaEvento;
		this.estado = estado;
		this.fechaCambio = fechaCambio;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public PlanillaEvento getPlanillaEvento() {
		return planillaEvento;
	}

	public void setPlanillaEvento(PlanillaEvento planillaEvento) {
		this.planillaEvento = planillaEvento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Timestamp fechaCambio) {
		this.fechaCambio = fechaCambio;
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