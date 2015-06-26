package modelo.transacciones;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.estado.BitacoraFachada;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_fachada")
public class PlanillaFachada implements Serializable {
	
	private static final long serialVersionUID = -6002464372287106073L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_fachada", unique=true, nullable=false)
	private long idPlanillaFachada;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name="nombre_actividad",length = 500)
	private String nombreActividad;
	
	@Column(name = "fecha_actividad")
	private Timestamp fechaActividad;
	
	@Column(name="tipo_actividad",length = 500)
	private String tipoActividad;
	
	@Column(name="ciudad",length = 500)
	private String ciudad;
	
	@Column(name="nombre_cliente",length = 500)
	private String nombreCliente;
	
	@Column(name="nombre_pdv",length = 500)
	private String nombrePdv;
	
	@Column(name="rif_pdv",length = 500)
	private String rifPdv;
	
	@Column(name="telefono_pdv",length = 500)
	private String telefonoPdv;
	
	@Column(name="direccion_pdv",length = 500)
	private String direccionPdv;
	
	@Column(name="correo_pdv",length = 500)
	private String correoPdv;
	
	@Column(name = "numero_personas")
	private Integer personas;
	
	@Column(name="duracion",length = 500)
	private String duracion;
	
	@Column(name = "nivel_socioeconomico", length = 500)
	private String nivel;
	
	@Column(name = "patente", length = 500)
	private String patente;
	
	@Column(name = "costo")
	private Double costo;
	
	@Column(name = "descripcion", length = 5000)
	private String descripcion;
	
	@Column(name = "justificacion", length = 5000)
	private String justificacion;
	
	@Column(name = "tipo_decoracion", length = 500)
	private String tipoDecoracion;
	
	@Column(name = "formato", length = 500)
	private String formato;
	
	@Column(name = "arte", length = 500)
	private String arte;
	
	@Column(name="alto")
	private Double alto;
	
	@Column(name="largo")
	private Double largo;
	
	@Column(name="ancho")
	private Double ancho;
	
	@Lob
	private byte[] imagenA;
	
	@Lob
	private byte[] imagenB;
	
	@Lob
	private byte[] imagenC;
	
	@Lob
	private byte[] imagenD;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;
	
	@Column(name = "fecha_envio")
	private Timestamp fechaEnvio;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@Column(name="estado_planilla",length = 500)
	private String estado;
	
	@OneToMany(mappedBy="id.planillaFachada")
	private Set<RecursoPlanillaFachada> recursosFachadas;

	@Column(name="id_zona",length = 500)
	private String zona;
	
	@OneToMany(mappedBy="planillaFachada")
	private Set<BitacoraFachada> bitacoras;
	
	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name="referencia_pago",length = 500)
	private String refencia;
	
	@Column(name="padre")
	private Long padre;
	
	@Column(name="motivo_cancelacion",length = 100)
	private String motivoCancelacion;
	
	@Column(name="descripcion_motivo",length = 250)
	private String descripcionMotivo;
	
	@Column(name="origen",length = 20)
	private String origen;
	
	public PlanillaFachada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaFachada(long idPlanillaFachada, Usuario usuario,
			Marca marca, String nombreActividad, Timestamp fechaActividad,
			String tipoActividad, String ciudad, String nombreCliente,
			String nombrePdv, String rifPdv, String telefonoPdv,
			String direccionPdv, String correoPdv, Integer personas,
			String duracion, String nivel, String patente, Double costo,
			String descripcion, String justificacion, String tipoDecoracion,
			String formato, String arte, Double alto, Double largo,
			Double ancho, byte[] imagenA, byte[] imagenB, byte[] imagenC,
			byte[] imagenD, Timestamp fechaAuditoria, Timestamp fecha, String horaAuditoria,
			String usuarioAuditoria, String estado, String zona, String tipo, String referencia, long padre, String motivoC) {
		super();
		this.idPlanillaFachada = idPlanillaFachada;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.fechaActividad = fechaActividad;
		this.tipoActividad = tipoActividad;
		this.ciudad = ciudad;
		this.nombreCliente = nombreCliente;
		this.nombrePdv = nombrePdv;
		this.rifPdv = rifPdv;
		this.telefonoPdv = telefonoPdv;
		this.direccionPdv = direccionPdv;
		this.correoPdv = correoPdv;
		this.personas = personas;
		this.duracion = duracion;
		this.nivel = nivel;
		this.patente = patente;
		this.costo = costo;
		this.descripcion = descripcion;
		this.justificacion = justificacion;
		this.tipoDecoracion = tipoDecoracion;
		this.formato = formato;
		this.arte = arte;
		this.alto = alto;
		this.largo = largo;
		this.ancho = ancho;
		this.imagenA = imagenA;
		this.imagenB = imagenB;
		this.imagenC = imagenC;
		this.imagenD = imagenD;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.estado = estado;
		this.zona = zona;
		this.tipo = tipo;
		this.refencia = referencia;
		this.padre = padre;
		this.fechaEnvio = fecha;
		this.motivoCancelacion = motivoC;
	}

	public long getIdPlanillaFachada() {
		return idPlanillaFachada;
	}

	public void setIdPlanillaFachada(long idPlanillaFachada) {
		this.idPlanillaFachada = idPlanillaFachada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public Timestamp getFechaActividad() {
		return fechaActividad;
	}

	public void setFechaActividad(Timestamp fechaActividad) {
		this.fechaActividad = fechaActividad;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNombrePdv() {
		return nombrePdv;
	}

	public void setNombrePdv(String nombrePdv) {
		this.nombrePdv = nombrePdv;
	}

	public String getRifPdv() {
		return rifPdv;
	}

	public void setRifPdv(String rifPdv) {
		this.rifPdv = rifPdv;
	}

	public String getTelefonoPdv() {
		return telefonoPdv;
	}

	public void setTelefonoPdv(String telefonoPdv) {
		this.telefonoPdv = telefonoPdv;
	}

	public String getDireccionPdv() {
		return direccionPdv;
	}

	public void setDireccionPdv(String direccionPdv) {
		this.direccionPdv = direccionPdv;
	}

	public String getCorreoPdv() {
		return correoPdv;
	}

	public void setCorreoPdv(String correoPdv) {
		this.correoPdv = correoPdv;
	}

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getTipoDecoracion() {
		return tipoDecoracion;
	}

	public void setTipoDecoracion(String tipoDecoracion) {
		this.tipoDecoracion = tipoDecoracion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getArte() {
		return arte;
	}

	public void setArte(String arte) {
		this.arte = arte;
	}

	public Double getAlto() {
		return alto;
	}

	public void setAlto(Double alto) {
		this.alto = alto;
	}

	public Double getLargo() {
		return largo;
	}

	public void setLargo(Double largo) {
		this.largo = largo;
	}

	public Double getAncho() {
		return ancho;
	}

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}

	public byte[] getImagenA() {
		return imagenA;
	}

	public void setImagenA(byte[] imagenA) {
		this.imagenA = imagenA;
	}

	public byte[] getImagenB() {
		return imagenB;
	}

	public void setImagenB(byte[] imagenB) {
		this.imagenB = imagenB;
	}

	public byte[] getImagenC() {
		return imagenC;
	}

	public void setImagenC(byte[] imagenC) {
		this.imagenC = imagenC;
	}

	public byte[] getImagenD() {
		return imagenD;
	}

	public void setImagenD(byte[] imagenD) {
		this.imagenD = imagenD;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<RecursoPlanillaFachada> getRecursosFachadas() {
		return recursosFachadas;
	}

	public void setRecursosFachadas(Set<RecursoPlanillaFachada> recursosFachadas) {
		this.recursosFachadas = recursosFachadas;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Set<BitacoraFachada> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraFachada> bitacoras) {
		this.bitacoras = bitacoras;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRefencia() {
		return refencia;
	}

	public void setRefencia(String refencia) {
		this.refencia = refencia;
	}

	public Long getPadre() {
		return padre;
	}

	public void setPadre(Long padre) {
		this.padre = padre;
	}

	public Timestamp getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Timestamp fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	
}
