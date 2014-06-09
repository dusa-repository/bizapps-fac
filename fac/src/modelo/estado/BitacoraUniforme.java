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

import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaUniforme;

@Entity
@Table(name = "bitacora_planilla_uniforme")
public class BitacoraUniforme implements Serializable {

	private static final long serialVersionUID = 6099541383465636907L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bitacora_planilla_uniforme", unique=true, nullable=false)
	private long idBitacora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_planilla_uniforme")
	private PlanillaUniforme planillaUniforme;
	
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

	public BitacoraUniforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BitacoraUniforme(long idBitacora, PlanillaUniforme planillaUniforme,
			String estado, Timestamp fechaCambio, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria) {
		super();
		this.idBitacora = idBitacora;
		this.planillaUniforme = planillaUniforme;
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

	public PlanillaUniforme getPlanillaUniforme() {
		return planillaUniforme;
	}

	public void setPlanillaUniforme(PlanillaUniforme planillaUniforme) {
		this.planillaUniforme = planillaUniforme;
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
