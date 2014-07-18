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

import modelo.estado.BitacoraUniforme;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_uniforme")
public class PlanillaUniforme implements Serializable {

	private static final long serialVersionUID = 8525944718554092675L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_uniforme", unique=true, nullable=false)
	private long idPlanillaUniforme;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name="nombre_actividad",length = 500)
	private String nombreActividad;
	
	@Column(name = "fecha_entrega")
	private Timestamp fechaEntrega;
	
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
	
	@Column(name="correo_pdv",length = 500)
	private String correoPdv;
	
	@Column(name="direccion_pdv",length = 500)
	private String direccionPdv;
	
	@Column(name="logo",length = 500)
	private String logo;
	
	@Column(name = "costo")
	private Double costo;
	
	@Column(name="justificacion",length = 5000)
	private String justificacion;
	
	@Column(name="contrato_condicionado",length = 500)
	private String contrato;
	
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
	
	@OneToMany(mappedBy="planillaUniforme")
	private Set<UniformePlanillaUniforme> uniformePlanillas;
	
	@Column(name="id_zona",length = 500)
	private String zona;
	
	@OneToMany(mappedBy="planillaUniforme")
	private Set<BitacoraUniforme> bitacoras;

	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name="referencia_pago",length = 500)
	private String refencia;
	
	@Column(name="padre")
	private Long padre;
	
	@Column(name="motivo_cancelacion",length = 100)
	private String motivoCancelacion;
	
	public PlanillaUniforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaUniforme(long idPlanillaUniforme, Usuario usuario,
			Marca marca, String nombreActividad, Timestamp fechaEntrega,
			String tipoActividad, String ciudad, String nombreCliente,
			String nombrePdv, String rifPdv, String telefonoPdv,
			String correoPdv, String direccionPdv, String logo, Double costo,
			String justificacion, String contrato, Timestamp fechaAuditoria,Timestamp fecha,
			String horaAuditoria, String usuarioAuditoria, String estado, String zona, String tipo, String referencia, long padre, String motivoC) {
		super();
		this.idPlanillaUniforme = idPlanillaUniforme;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.fechaEntrega = fechaEntrega;
		this.tipoActividad = tipoActividad;
		this.ciudad = ciudad;
		this.nombreCliente = nombreCliente;
		this.nombrePdv = nombrePdv;
		this.rifPdv = rifPdv;
		this.telefonoPdv = telefonoPdv;
		this.correoPdv = correoPdv;
		this.direccionPdv = direccionPdv;
		this.logo = logo;
		this.costo = costo;
		this.justificacion = justificacion;
		this.contrato = contrato;
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

	public long getIdPlanillaUniforme() {
		return idPlanillaUniforme;
	}

	public void setIdPlanillaUniforme(long idPlanillaUniforme) {
		this.idPlanillaUniforme = idPlanillaUniforme;
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

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
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

	public Set<UniformePlanillaUniforme> getUniformePlanillas() {
		return uniformePlanillas;
	}

	public void setUniformePlanillas(Set<UniformePlanillaUniforme> uniformePlanillas) {
		this.uniformePlanillas = uniformePlanillas;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Set<BitacoraUniforme> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraUniforme> bitacoras) {
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
	
	
}
