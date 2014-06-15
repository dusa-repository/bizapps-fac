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

import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;

@Entity
@Table(name = "planilla_evento")
public class PlanillaEvento implements Serializable {
	
	private static final long serialVersionUID = -1652829153566977749L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planilla_evento", unique=true, nullable=false)
	private long idPlanillaEvento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@Column(name="nombre_actividad",length = 500)
	private String nombreActividad;
	
	@Column(name = "fecha_desde")
	private Timestamp fechaDesde;
	
	@Column(name = "fecha_hasta")
	private Timestamp fechaHasta;
	
	@Column(name="ciudad",length = 500)
	private String ciudad;

	@Column(name="region",length = 500)
	private String region;
	
	@Column(name = "hora_evento", length = 10)
	private String horaEvento;
	
	@Column(name = "direccion", length = 500)
	private String direccion;
	
	@Column(name = "numero_personas")
	private Integer personas;
	
	@Column(name = "persona_contacto", length = 500)
	private String personaContacto;
	
	@Column(name = "telefono_contacto", length = 500)
	private String telefono;
	
	@Column(name = "nivel_socioeconomico", length = 500)
	private String nivel;
	
	@Column(name = "edad_target", length = 500)
	private String edadTarget;
	
	@Column(name = "medio", length = 500)
	private String medio;
	
	@Column(name = "venta_directa", length = 500)
	private String venta;
	
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

	@OneToMany(mappedBy="planillaEvento")
	private Set<ItemDegustacionPlanillaEvento> itemsDegustados;
	
	@OneToMany(mappedBy="planillaEvento")
	private Set<ItemEstimadoPlanillaEvento> itemsEstimados;
	
	@OneToMany(mappedBy="planillaEvento")
	private Set<RecursoPlanillaEvento> recursos;
	
	@Column(name="id_zona",length = 500)
	private String zona;
	
	@OneToMany(mappedBy="planillaEvento")
	private Set<BitacoraEvento> bitacoras;
	
	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name="referencia_pago",length = 500)
	private String refencia;
	
	@Column(name="padre")
	private Long padre;
	
	public PlanillaEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaEvento(long idPlanillaEvento, Usuario usuario, Marca marca,
			String nombreActividad, Timestamp fechaDesde, Timestamp fechaHasta,
			String ciudad, String region, String horaEvento, String direccion,
			Integer personas, String personaContacto, String telefono,
			String nivel, String edadTarget, String medio, String venta,
			Double costo, String descripcion, String mecanica,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria, String estado,String zona, String tipo, String referencia, long padre) {
		super();
		this.idPlanillaEvento = idPlanillaEvento;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.ciudad = ciudad;
		this.region = region;
		this.horaEvento = horaEvento;
		this.direccion = direccion;
		this.personas = personas;
		this.personaContacto = personaContacto;
		this.telefono = telefono;
		this.nivel = nivel;
		this.edadTarget = edadTarget;
		this.medio = medio;
		this.venta = venta;
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

	public long getIdPlanillaEvento() {
		return idPlanillaEvento;
	}

	public void setIdPlanillaEvento(long idPlanillaEvento) {
		this.idPlanillaEvento = idPlanillaEvento;
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

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(String horaEvento) {
		this.horaEvento = horaEvento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getPersonas() {
		return personas;
	}

	public void setPersonas(Integer personas) {
		this.personas = personas;
	}

	public String getPersonaContacto() {
		return personaContacto;
	}

	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public String getVenta() {
		return venta;
	}

	public void setVenta(String venta) {
		this.venta = venta;
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

	public Set<ItemDegustacionPlanillaEvento> getItemsDegustados() {
		return itemsDegustados;
	}

	public void setItemsDegustados(
			Set<ItemDegustacionPlanillaEvento> itemsDegustados) {
		this.itemsDegustados = itemsDegustados;
	}

	public Set<ItemEstimadoPlanillaEvento> getItemsEstimados() {
		return itemsEstimados;
	}

	public void setItemsEstimados(Set<ItemEstimadoPlanillaEvento> itemsEstimados) {
		this.itemsEstimados = itemsEstimados;
	}

	public Set<RecursoPlanillaEvento> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<RecursoPlanillaEvento> recursos) {
		this.recursos = recursos;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Set<BitacoraEvento> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(Set<BitacoraEvento> bitacoras) {
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
