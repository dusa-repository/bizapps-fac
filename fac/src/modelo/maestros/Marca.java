package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.RecursoPlanillaEvento;

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
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaArte> planillasArte;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaCata> planillasCata;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaEvento> planillasEvento;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaFachada> planillasFachada;
	
	@OneToMany(mappedBy="marcaA")
	private Set<PlanillaPromocion> planillasPromocionA;
	
	@OneToMany(mappedBy="marcaB")
	private Set<PlanillaPromocion> planillasPromocionB;
	
	@OneToMany(mappedBy="marca")
	private Set<PlanillaUniforme> planillasUniforme;
	
	@OneToMany(mappedBy="marca")
	private Set<RecursoPlanillaEvento> recursosPlanillasEvento;
	
	@OneToMany(mappedBy="marca")
	private Set<Sku> items;

	public Marca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Marca(String idMarca, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria) {
		super();
		this.idMarca = idMarca;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
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
	
	
}
