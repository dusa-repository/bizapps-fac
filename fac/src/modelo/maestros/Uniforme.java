package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.transacciones.UniformePlanillaUniforme;

@Entity
@Table(name = "uniforme")
public class Uniforme implements Serializable {

	private static final long serialVersionUID = -1898610609280425069L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_uniforme", unique=true, nullable=false)
	private long idUniforme;
	
	@Column(length = 500)
	private String descripcion;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@OneToMany(mappedBy="id.uniforme")
	private Set<UniformePlanillaUniforme> uniformePlanillas;

	public Uniforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Uniforme(long idUniforme, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.idUniforme = idUniforme;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public long getIdUniforme() {
		return idUniforme;
	}

	public void setIdUniforme(long idUniforme) {
		this.idUniforme = idUniforme;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Set<UniformePlanillaUniforme> getUniformePlanillas() {
		return uniformePlanillas;
	}

	public void setUniformePlanillas(Set<UniformePlanillaUniforme> uniformePlanillas) {
		this.uniformePlanillas = uniformePlanillas;
	}
	
}
