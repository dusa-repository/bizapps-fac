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

import modelo.estado.BitacoraArte;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_arte")
public class PlanillaArte implements Serializable {

	private static final long serialVersionUID = -7497275495401220247L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_arte", unique=true, nullable=false)
	private long idPlanillaArte;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name="nombre_actividad",length = 500)
	private String nombreActividad;
	
	@Column(name="nombre_cliente",length = 500)
	private String nombreCliente;
	
	@Column(name="tipo_arte",length = 500)
	private String tipoArte;
	
	@Column(name="rif",length = 500)
	private String rif;
	
	@Column(name="patente",length = 500)
	private String patente;
	
	@Column(name="formato_entrega",length = 500)
	private String formato;
	
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
	
	@Column(name="lineamiento",length = 5000)
	private String lineamiento;
	
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

	@Column(name="id_zona",length = 500)
	private String zona;
	
	@OneToMany(mappedBy="planillaArte")
	private Set<BitacoraArte> bitacoras;
	
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
	
	public PlanillaArte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaArte(long idPlanillaArte, Usuario usuario, Marca marca,
			String nombreActividad, String nombreCliente, String tipoArte,
			String rif, String patente, String formato, Double alto,
			Double largo, Double ancho, byte[] imagenA, byte[] imagenB,
			byte[] imagenC, byte[] imagenD, String lineamiento,
			Timestamp fechaAuditoria,Timestamp fecha, String horaAuditoria,
			String usuarioAuditoria, String estado, String zona, String tipo, String referencia, long padre, String motivoC) {
		super();
		this.idPlanillaArte = idPlanillaArte;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.nombreCliente = nombreCliente;
		this.tipoArte = tipoArte;
		this.rif = rif;
		this.patente = patente;
		this.formato = formato;
		this.alto = alto;
		this.largo = largo;
		this.ancho = ancho;
		this.imagenA = imagenA;
		this.imagenB = imagenB;
		this.imagenC = imagenC;
		this.imagenD = imagenD;
		this.lineamiento = lineamiento;
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

	public long getIdPlanillaArte() {
		return idPlanillaArte;
	}

	public void setIdPlanillaArte(long idPlanillaArte) {
		this.idPlanillaArte = idPlanillaArte;
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

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getTipoArte() {
		return tipoArte;
	}

	public void setTipoArte(String tipoArte) {
		this.tipoArte = tipoArte;
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
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

	public String getLineamiento() {
		return lineamiento;
	}

	public void setLineamiento(String lineamiento) {
		this.lineamiento = lineamiento;
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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Set<BitacoraArte> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraArte> bitacoras) {
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
	
	
}
