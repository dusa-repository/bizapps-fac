package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;
import modelo.transacciones.RecursoPlanillaFachada;
import modelo.transacciones.notas.ConfiguracionMarca;
import modelo.transacciones.notas.CostoNotaCredito;
import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.Planificacion;

@Entity
@Table(name = "marca")
public class Marca implements Serializable {

	private static final long serialVersionUID = -5123552668757608465L;

	@Id
	@Column(name = "id_marca", length = 50, unique = true, nullable = false)
	private String idMarca;
	
	@Column(length = 500)
	private String descripcion;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@Column(name = "estado")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean estado;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaArte> planillasArte;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaCata> planillasCata;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaEvento> planillasEvento;
	
	@OneToMany(mappedBy="marca")
	private Set<Planificacion> planificaciones;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaFachada> planillasFachada;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaPromocion> planillasPromocionA;
	
	@OneToMany(mappedBy="marcaB")
	private Set<PlanillaPromocion> planillasPromocionB;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaUniforme> planillasUniforme;
	
	@OneToMany(mappedBy="marca")
	private Set<RecursoPlanillaEvento> recursosPlanillasEvento;
	
	@OneToMany(mappedBy="marca")
	private Set<RecursoPlanillaCata> recursosPlanillasCata;
	
	@OneToMany(mappedBy="marca")
	private Set<RecursoPlanillaFachada> recursosPlanillasFachada;
	
	@OneToMany(mappedBy="marca")
	private Set<Sku> items;
	
	@OneToMany(mappedBy="marca")
	private Set<DetalleNotaCredito> detallesNota;
	
	@OneToMany(mappedBy = "id.marca")
	private List<ConfiguracionMarca> configuracionesMarca;
	
	@OneToMany(mappedBy = "id.marca")
	private List<CostoNotaCredito> costoNotas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	private Empresa empresa;

	public Marca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Marca(String idMarca, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria, Empresa empresa, Boolean estado) {
		super();
		this.idMarca = idMarca;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.empresa = empresa;
		this.estado = estado;
	}

	public String getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(String idMarca) {
		this.idMarca = idMarca;
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

	public Set<Sku> getItems() {
		return items;
	}

	public void setItems(Set<Sku> items) {
		this.items = items;
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

	public Set<PlanillaUniforme> getPlanillasUniforme() {
		return planillasUniforme;
	}

	public void setPlanillasUniforme(Set<PlanillaUniforme> planillasUniforme) {
		this.planillasUniforme = planillasUniforme;
	}

	public Set<PlanillaPromocion> getPlanillasPromocionA() {
		return planillasPromocionA;
	}

	public void setPlanillasPromocionA(Set<PlanillaPromocion> planillasPromocionA) {
		this.planillasPromocionA = planillasPromocionA;
	}

	public Set<PlanillaPromocion> getPlanillasPromocionB() {
		return planillasPromocionB;
	}

	public void setPlanillasPromocionB(Set<PlanillaPromocion> planillasPromocionB) {
		this.planillasPromocionB = planillasPromocionB;
	}

	public Set<RecursoPlanillaEvento> getRecursosPlanillasEvento() {
		return recursosPlanillasEvento;
	}

	public void setRecursosPlanillasEvento(
			Set<RecursoPlanillaEvento> recursosPlanillasEvento) {
		this.recursosPlanillasEvento = recursosPlanillasEvento;
	}

	public Set<RecursoPlanillaCata> getRecursosPlanillasCata() {
		return recursosPlanillasCata;
	}

	public void setRecursosPlanillasCata(
			Set<RecursoPlanillaCata> recursosPlanillasCata) {
		this.recursosPlanillasCata = recursosPlanillasCata;
	}

	public Set<RecursoPlanillaFachada> getRecursosPlanillasFachada() {
		return recursosPlanillasFachada;
	}

	public void setRecursosPlanillasFachada(
			Set<RecursoPlanillaFachada> recursosPlanillasFachada) {
		this.recursosPlanillasFachada = recursosPlanillasFachada;
	}

	public Set<DetalleNotaCredito> getDetallesNota() {
		return detallesNota;
	}

	public void setDetallesNota(Set<DetalleNotaCredito> detallesNota) {
		this.detallesNota = detallesNota;
	}

	public Set<Planificacion> getPlanificaciones() {
		return planificaciones;
	}

	public void setPlanificaciones(Set<Planificacion> planificaciones) {
		this.planificaciones = planificaciones;
	}

	public List<ConfiguracionMarca> getConfiguracionesMarca() {
		return configuracionesMarca;
	}

	public void setConfiguracionesMarca(
			List<ConfiguracionMarca> configuracionesMarca) {
		this.configuracionesMarca = configuracionesMarca;
	}

	public List<CostoNotaCredito> getCostoNotas() {
		return costoNotas;
	}

	public void setCostoNotas(List<CostoNotaCredito> costoNotas) {
		this.costoNotas = costoNotas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
}
