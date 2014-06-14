package modelo.seguridad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.maestros.Zona;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 8084735361815028391L;

	@Id
	@Column(name = "id_usuario", length = 50, unique = true, nullable = false)
	private String idUsuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_zona")
	private Zona zona;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 50)
	private String mail;

	@Column(length = 50)
	private String password;

	@Column(name="id_supervisor", length = 50)
	private String supervisor;
	
	@Column(name = "solo_lectura", length = 1)
	private String soloLectura;
	
	@Column(name = "envio_correo", length = 50)
	private String envioCorreo;
	
	@Lob
	private byte[] imagen;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean estado;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	@ManyToMany
	@JoinTable(name = "grupo_usuario", joinColumns = { @JoinColumn(name = "id_usuario", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_grupo", nullable = false) })
	private Set<Grupo> grupos;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaArte> planillasArte;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaCata> planillasCata;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaEvento> planillasEvento;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaFachada> planillasFachada;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaPromocion> planillasPromocion;
	
	@OneToMany(mappedBy="usuario")
	private Set<PlanillaUniforme> planillasUniforme;
	
	@OneToMany(mappedBy="usuario")
	private Set<Configuracion> configuracion;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String idUsuario, Zona zona, String nombre, String mail,
			String password, String supervisor, String soloLectura,
			String envioCorreo, byte[] imagen, Boolean estado,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.idUsuario = idUsuario;
		this.zona = zona;
		this.nombre = nombre;
		this.mail = mail;
		this.password = password;
		this.supervisor = supervisor;
		this.soloLectura = soloLectura;
		this.envioCorreo = envioCorreo;
		this.imagen = imagen;
		this.estado = estado;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(String soloLectura) {
		this.soloLectura = soloLectura;
	}

	public String getEnvioCorreo() {
		return envioCorreo;
	}

	public void setEnvioCorreo(String envioCorreo) {
		this.envioCorreo = envioCorreo;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Boolean isEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
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

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Set<PlanillaArte> getPlanillasArte() {
		return planillasArte;
	}

	public void setPlanillasArte(Set<PlanillaArte> planillasArte) {
		this.planillasArte = planillasArte;
	}

	public Set<PlanillaCata> getPlanillasCata() {
		return planillasCata;
	}

	public void setPlanillasCata(Set<PlanillaCata> planillasCata) {
		this.planillasCata = planillasCata;
	}

	public Set<PlanillaEvento> getPlanillasEvento() {
		return planillasEvento;
	}

	public void setPlanillasEvento(Set<PlanillaEvento> planillasEvento) {
		this.planillasEvento = planillasEvento;
	}

	public Set<PlanillaFachada> getPlanillasFachada() {
		return planillasFachada;
	}

	public void setPlanillasFachada(Set<PlanillaFachada> planillasFachada) {
		this.planillasFachada = planillasFachada;
	}

	public Set<PlanillaPromocion> getPlanillasPromocion() {
		return planillasPromocion;
	}

	public void setPlanillasPromocion(Set<PlanillaPromocion> planillasPromocion) {
		this.planillasPromocion = planillasPromocion;
	}

	public Set<PlanillaUniforme> getPlanillasUniforme() {
		return planillasUniforme;
	}

	public void setPlanillasUniforme(Set<PlanillaUniforme> planillasUniforme) {
		this.planillasUniforme = planillasUniforme;
	}

	public Set<Configuracion> getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(Set<Configuracion> configuracion) {
		this.configuracion = configuracion;
	}
	
}
