package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.ItemPlanillaCata;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "item")
public class Sku implements Serializable {

	private static final long serialVersionUID = 4682918874055954135L;

	@Id
	@Column(name = "id_item", length = 50, unique = true, nullable = false)
	private String idSku;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_marca")
	private Marca marca;
	
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
	
	@OneToMany(mappedBy="id.sku")
	private Set<ItemEstimadoPlanillaEvento> itemsEstimados;
	
	@OneToMany(mappedBy="id.sku")
	private Set<ItemDegustacionPlanillaEvento> itemsDegustados;
	
	@OneToMany(mappedBy="id.sku")
	private Set<ItemPlanillaCata> itemsCatas;

	public Sku() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sku(String idSku, Marca marca, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria, Boolean estado) {
		super();
		this.idSku = idSku;
		this.marca = marca;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.estado = estado;
	}

	public String getIdSku() {
		return idSku;
	}

	public void setIdSku(String idSku) {
		this.idSku = idSku;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
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

	public Set<ItemEstimadoPlanillaEvento> getItemsEstimados() {
		return itemsEstimados;
	}

	public void setItemsEstimados(Set<ItemEstimadoPlanillaEvento> itemsEstimados) {
		this.itemsEstimados = itemsEstimados;
	}

	public Set<ItemDegustacionPlanillaEvento> getItemsDegustados() {
		return itemsDegustados;
	}

	public void setItemsDegustados(
			Set<ItemDegustacionPlanillaEvento> itemsDegustados) {
		this.itemsDegustados = itemsDegustados;
	}

	public Set<ItemPlanillaCata> getItemsCatas() {
		return itemsCatas;
	}

	public void setItemsCatas(Set<ItemPlanillaCata> itemsCatas) {
		this.itemsCatas = itemsCatas;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
