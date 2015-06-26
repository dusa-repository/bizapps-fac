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

import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;
import modelo.transacciones.RecursoPlanillaFachada;

@Entity
@Table(name = "recurso")
public class Recurso implements Serializable {

	private static final long serialVersionUID = 2573379258335587750L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recurso", unique=true, nullable=false)
	private long idRecurso;
	
	@Column(length = 500)
	private String descripcion;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@OneToMany(mappedBy="id.recurso")
	private Set<RecursoPlanillaEvento> recursos;
	
	@OneToMany(mappedBy="id.recurso")
	private Set<RecursoPlanillaCata> recursosCatas;
	
	@OneToMany(mappedBy="id.recurso")
	private Set<RecursoPlanillaFachada> recursosFachadas;

	public Recurso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recurso(long idRecurso, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.idRecurso = idRecurso;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(long idRecurso) {
		this.idRecurso = idRecurso;
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

	public Set<RecursoPlanillaEvento> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<RecursoPlanillaEvento> recursos) {
		this.recursos = recursos;
	}

	public Set<RecursoPlanillaCata> getRecursosCatas() {
		return recursosCatas;
	}

	public void setRecursosCatas(Set<RecursoPlanillaCata> recursosCatas) {
		this.recursosCatas = recursosCatas;
	}

	public Set<RecursoPlanillaFachada> getRecursosFachadas() {
		return recursosFachadas;
	}

	public void setRecursosFachadas(Set<RecursoPlanillaFachada> recursosFachadas) {
		this.recursosFachadas = recursosFachadas;
	}
	
	
}
