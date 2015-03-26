package modelo.transacciones.notas;

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

import modelo.maestros.Marca;
import modelo.maestros.Zona;

@Entity
@Table(name = "planificacion")
public class Planificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_planificacion", unique=true, nullable=false)
	private long idPlanificacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_zona")
	private Zona zona;
	
	@Column(name = "fecha_desde")
	private Timestamp fechaDesde;

	@Column(name = "fecha_hasta")
	private Timestamp fechaHasta;
	
	@Column(name="mes",length = 50)
	private String mes;
	
	@Column(name="tipo_inversion",length = 50)
	private String tipoInversion;
	
	@Column(name="pdv_cliente",length = 500)
	private String pdvCliente;
	
	@Column(name="pdv_tipo",length = 50)
	private String pdvTipo;
	
	@Column(name="pdv_cantidad")
	private Integer cantidadPdv;
	
	@Column(name="actividad_detalle",length = 1500)
	private String actividaDetalles;
	
	@Column(name="actividad",length = 2000)
	private String actividades;
	
	@Column(name="actividad_cantidad")
	private Integer actividadCantidad;
	
	@Column(name="actividad_cantidad_pagar")
	private Integer actividadesAPagar;
	
	@Column(name="actividad_costo_individual")
	private Double actividadCosto;
	
	@Column(name="inversion_real")
	private Double inversionReal;
	
	@Column(name="estado",length = 50)
	private String estado;
	
	@Column(name="tipo_configuracion",length = 500)
	private String tipo;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	public Planificacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Planificacion(long idPlanificacion, Marca marca, Zona zona,
			Timestamp fechaDesde, Timestamp fechaHasta, String mes,
			String tipoInversion, String pdvCliente, String pdvTipo,
			Integer cantidadPdv, String actividaDetalles, String actividades,
			Integer actividadCantidad, Integer actividadesAPagar,
			Double actividadCosto, Double inversionReal, String estado,
			String tipo, Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.idPlanificacion = idPlanificacion;
		this.marca = marca;
		this.zona = zona;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.mes = mes;
		this.tipoInversion = tipoInversion;
		this.pdvCliente = pdvCliente;
		this.pdvTipo = pdvTipo;
		this.cantidadPdv = cantidadPdv;
		this.actividaDetalles = actividaDetalles;
		this.actividades = actividades;
		this.actividadCantidad = actividadCantidad;
		this.actividadesAPagar = actividadesAPagar;
		this.actividadCosto = actividadCosto;
		this.inversionReal = inversionReal;
		this.estado = estado;
		this.tipo = tipo;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public long getIdPlanificacion() {
		return idPlanificacion;
	}

	public void setIdPlanificacion(long idPlanificacion) {
		this.idPlanificacion = idPlanificacion;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
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

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getTipoInversion() {
		return tipoInversion;
	}

	public void setTipoInversion(String tipoInversion) {
		this.tipoInversion = tipoInversion;
	}

	public String getPdvCliente() {
		return pdvCliente;
	}

	public void setPdvCliente(String pdvCliente) {
		this.pdvCliente = pdvCliente;
	}

	public String getPdvTipo() {
		return pdvTipo;
	}

	public void setPdvTipo(String pdvTipo) {
		this.pdvTipo = pdvTipo;
	}

	public Integer getCantidadPdv() {
		return cantidadPdv;
	}

	public void setCantidadPdv(Integer cantidadPdv) {
		this.cantidadPdv = cantidadPdv;
	}

	public String getActividaDetalles() {
		return actividaDetalles;
	}

	public void setActividaDetalles(String actividaDetalles) {
		this.actividaDetalles = actividaDetalles;
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public Integer getActividadCantidad() {
		return actividadCantidad;
	}

	public void setActividadCantidad(Integer actividadCantidad) {
		this.actividadCantidad = actividadCantidad;
	}

	public Integer getActividadesAPagar() {
		return actividadesAPagar;
	}

	public void setActividadesAPagar(Integer actividadesAPagar) {
		this.actividadesAPagar = actividadesAPagar;
	}

	public Double getActividadCosto() {
		return actividadCosto;
	}

	public void setActividadCosto(Double actividadCosto) {
		this.actividadCosto = actividadCosto;
	}

	public Double getInversionReal() {
		return inversionReal;
	}

	public void setInversionReal(Double inversionReal) {
		this.inversionReal = inversionReal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
