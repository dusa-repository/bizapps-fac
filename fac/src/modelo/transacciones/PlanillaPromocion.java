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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.estado.BitacoraPromocion;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_promocion")
public class PlanillaPromocion implements Serializable {

	private static final long serialVersionUID = -1676391727389645784L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_promocion", unique=true, nullable=false)
	private long idPlanillaPromocion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca_a")
	private Marca marca;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca_b")
	private Marca marcaB;
	
	@Column(name="nombre_actividad",length = 500)
	private String nombreActividad;
	
	@Column(name="tipo_actividad",length = 500)
	private String tipoActividad;
	
	@Column(name="tipo_local",length = 500)
	private String tipoLocal;
	
	@Column(name="ciudad",length = 500)
	private String ciudad;
	
	@Column(name="estado",length = 500)
	private String estadoPdv;
	
	@Column(name="nombre_cliente",length = 500)
	private String nombreCliente;
	
	@Column(name="nombre_pdv",length = 500)
	private String nombrePdv;
	
	@Column(name="rif_pdv",length = 500)
	private String rifPdv;
	
	@Column(name="telefono_pdv",length = 500)
	private String telefonoPdv;
	
	@Column(name="correo_pdv",length = 500)
	private String correoPdv;
	
	@Column(name="direccion_pdv",length = 500)
	private String direccionPdv;
	
	@Column(name = "fecha_desde")
	private Timestamp fechaDesde;
	
	@Column(name = "fecha_hasta")
	private Timestamp fechaHasta;
	
	@Column(name="modalidad_pago",length = 500)
	private String modalidadPago;
	
	@Column(name="frecuencia_pago",length = 500)
	private String frecuenciaPago;
	
	@Column(name="material",length = 500)
	private String material;
	
	@Column(name="comunicacion",length = 500)
	private String comunicacion;
	
	@Column(name = "costo")
	private Double costo;
	
	@Column(name="descripcion_marca_a",length = 500)
	private String descripcionMarcaA;

	@Column(name="descripcion_marca_b",length = 500)
	private String descripcionMarcaB;
	
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
	
	@OneToMany(mappedBy="planillaPromocion")
	private Set<BitacoraPromocion> bitacoras;
	
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
	
	public PlanillaPromocion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaPromocion(long idPlanillaPromocion, Usuario usuario,
			Marca marcaA, Marca marcaB, String nombreActividad,
			String tipoActividad, String tipoLocal, String ciudad,
			String estado_pdv, String nombreCliente, String nombrePdv,
			String rifPdv, String telefonoPdv, String correoPdv,
			String direccionPdv, Timestamp fechaDesde, Timestamp fechaHasta,
			String modalidad_Pago, String frecuenciaPago, String material,
			String comunicacion, Double costo, String descripcionMarcaA,
			String descripcionMarcaB, Timestamp fechaAuditoria, Timestamp fecha,
			String horaAuditoria, String usuarioAuditoria, String estado, String zo, String tipo, String referencia, long padre, String motivoC) {
		super();
		this.idPlanillaPromocion = idPlanillaPromocion;
		this.usuario = usuario;
		this.marca = marcaA;
		this.marcaB = marcaB;
		this.nombreActividad = nombreActividad;
		this.tipoActividad = tipoActividad;
		this.tipoLocal = tipoLocal;
		this.ciudad = ciudad;
		this.estadoPdv = estado_pdv;
		this.nombreCliente = nombreCliente;
		this.nombrePdv = nombrePdv;
		this.rifPdv = rifPdv;
		this.telefonoPdv = telefonoPdv;
		this.correoPdv = correoPdv;
		this.direccionPdv = direccionPdv;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.modalidadPago = modalidad_Pago;
		this.frecuenciaPago = frecuenciaPago;
		this.material = material;
		this.comunicacion = comunicacion;
		this.costo = costo;
		this.descripcionMarcaA = descripcionMarcaA;
		this.descripcionMarcaB = descripcionMarcaB;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.estado = estado;
		this.zona = zo;
		this.tipo = tipo;
		this.refencia = referencia;
		this.padre = padre;
		this.fechaEnvio = fecha;
		this.motivoCancelacion = motivoC;
	}

	public long getIdPlanillaPromocion() {
		return idPlanillaPromocion;
	}

	public void setIdPlanillaPromocion(long idPlanillaPromocion) {
		this.idPlanillaPromocion = idPlanillaPromocion;
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

	public Marca getMarcaB() {
		return marcaB;
	}

	public void setMarcaB(Marca marcaB) {
		this.marcaB = marcaB;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getTipoLocal() {
		return tipoLocal;
	}

	public void setTipoLocal(String tipoLocal) {
		this.tipoLocal = tipoLocal;
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

	public String getCorreoPdv() {
		return correoPdv;
	}

	public void setCorreoPdv(String correoPdv) {
		this.correoPdv = correoPdv;
	}

	public String getDireccionPdv() {
		return direccionPdv;
	}

	public void setDireccionPdv(String direccionPdv) {
		this.direccionPdv = direccionPdv;
	}

	public Timestamp getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Timestamp fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Timestamp getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Timestamp fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getModalidadPago() {
		return modalidadPago;
	}

	public void setModalidadPago(String modalidadPago) {
		this.modalidadPago = modalidadPago;
	}

	public String getFrecuenciaPago() {
		return frecuenciaPago;
	}

	public void setFrecuenciaPago(String frecuenciaPago) {
		this.frecuenciaPago = frecuenciaPago;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getComunicacion() {
		return comunicacion;
	}

	public void setComunicacion(String comunicacion) {
		this.comunicacion = comunicacion;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDescripcionMarcaA() {
		return descripcionMarcaA;
	}

	public void setDescripcionMarcaA(String descripcionMarcaA) {
		this.descripcionMarcaA = descripcionMarcaA;
	}

	public String getDescripcionMarcaB() {
		return descripcionMarcaB;
	}

	public void setDescripcionMarcaB(String descripcionMarcaB) {
		this.descripcionMarcaB = descripcionMarcaB;
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

	public String getEstadoPdv() {
		return estadoPdv;
	}

	public void setEstadoPdv(String estadoPdv) {
		this.estadoPdv = estadoPdv;
	}

	public Set<BitacoraPromocion> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraPromocion> bitacoras) {
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
