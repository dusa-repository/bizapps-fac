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

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraCata;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_cata")
public class PlanillaCata implements Serializable {
	
	private static final long serialVersionUID = -5386856953275308724L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_cata", unique=true, nullable=false)
	private long idPlanillaCata;
	
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
	
	@Column(name="cata_induccion",length = 500)
	private String cata;
	
	@Column(name="ciudad",length = 500)
	private String ciudad;
	
	@Column(name="nombre_cliente",length = 500)
	private String nombreCliente;
	
	@Column(name="telefono_pdv",length = 500)
	private String telefonoPdv;
	
	@Column(name="correo_pdv",length = 500)
	private String correoPdv;
	
	@Column(name="direccion_pdv",length = 500)
	private String direccionPdv;
	
	@Column(name = "numero_personas")
	private Integer personas;
	
	@Column(name="motivo",length = 500)
	private String motivo;
	
	@Column(name = "nivel_socioeconomico", length = 500)
	private String nivel;
	
	@Column(name = "edad_target", length = 500)
	private String edadTarget;
	
	@Column(name = "costo")
	private Double costo;
	
	@Column(name = "descripcion", length = 5000)
	private String descripcion;
	
	@Column(name = "mecanica", length = 5000)
	private String mecanica;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@Column(name="estado_planilla",length = 500)
	private String estado;
	
	@OneToMany(mappedBy="planillaCata")
	private Set<ItemPlanillaCata> planillasCatas;
	
	@OneToMany(mappedBy="planillaCata")
	private Set<RecursoPlanillaCata> recursosCatas;

	@Column(name="id_zona",length = 500)
	private String zona;
	
	@OneToMany(mappedBy="planillaCata")
	private Set<BitacoraCata> bitacoras;
	
	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name="referencia_pago",length = 500)
	private String refencia;
	
	@Column(name="padre")
	private Long padre;
	
	public PlanillaCata() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaCata(long idPlanillaCata, Usuario usuario, Marca marca,
			String nombreActividad, Timestamp fechaActividad, String cata,
			String ciudad, String nombreCliente, String telefonoPdv,
			String correoPdv, String direccionPdv, Integer personas,
			String motivo, String nivel, String edadTarget, Double costo,
			String descripcion, String mecanica, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria, String estado, String zona, String tipo, String referencia, long padre) {
		super();
		this.idPlanillaCata = idPlanillaCata;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.fechaActividad = fechaActividad;
		this.cata = cata;
		this.ciudad = ciudad;
		this.nombreCliente = nombreCliente;
		this.telefonoPdv = telefonoPdv;
		this.correoPdv = correoPdv;
		this.direccionPdv = direccionPdv;
		this.personas = personas;
		this.motivo = motivo;
		this.nivel = nivel;
		this.edadTarget = edadTarget;
		this.costo = costo;
		this.descripcion = descripcion;
		this.mecanica = mecanica;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.estado = estado;
		this.zona = zona;
		this.tipo = tipo;
		this.refencia = referencia;
		this.padre = padre;
	}

	public long getIdPlanillaCata() {
		return idPlanillaCata;
	}

	public void setIdPlanillaCata(long idPlanillaCata) {
		this.idPlanillaCata = idPlanillaCata;
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

	public String getCata() {
		return cata;
	}

	public void setCata(String cata) {
		this.cata = cata;
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

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getEdadTarget() {
		return edadTarget;
	}

	public void setEdadTarget(String edadTarget) {
		this.edadTarget = edadTarget;
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

	public String getMecanica() {
		return mecanica;
	}

	public void setMecanica(String mecanica) {
		this.mecanica = mecanica;
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

	public Set<ItemPlanillaCata> getPlanillasCatas() {
		return planillasCatas;
	}

	public void setPlanillasCatas(Set<ItemPlanillaCata> planillasCatas) {
		this.planillasCatas = planillasCatas;
	}

	public Set<RecursoPlanillaCata> getRecursosCatas() {
		return recursosCatas;
	}

	public void setRecursosCatas(Set<RecursoPlanillaCata> recursosCatas) {
		this.recursosCatas = recursosCatas;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Set<BitacoraCata> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraCata> bitacoras) {
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
	
	
}
