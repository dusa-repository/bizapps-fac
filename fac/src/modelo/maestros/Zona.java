package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.seguridad.Usuario;
import modelo.transacciones.notas.Planificacion;

@Entity
@Table(name = "zona")
public class Zona implements Serializable {

	private static final long serialVersionUID = -3230940093491331644L;

	@Id
	@Column(name = "id_zona", length = 50, unique = true, nullable = false)
	private String idZona;
	
	@Column(length = 500)
	private String descripcion;
	
	@Column(name="monto_original")
	private Double original;
	
	@Column(name="monto_consumido")
	private Double consumido;
	
	@Column(name="monto_saldo")
	private Double saldo;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@OneToMany(mappedBy="zona")
	private Set<Usuario> usuarios;
	
	@OneToMany(mappedBy="zona")
	private Set<Aliado> aliados;
	
	@OneToMany(mappedBy="zona")
	private Set<Planificacion> planificaciones;

	public Zona() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Zona(String idZona, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria, Double ori, Double consu, Double saldo) {
		super();
		this.idZona = idZona;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.original = ori;
		this.consumido = consu;
		this.saldo = saldo;
	}

	public String getIdZona() {
		return idZona;
	}

	public void setIdZona(String idZona) {
		this.idZona = idZona;
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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Aliado> getAliados() {
		return aliados;
	}

	public void setAliados(Set<Aliado> aliados) {
		this.aliados = aliados;
	}

	public Set<Planificacion> getPlanificaciones() {
		return planificaciones;
	}

	public void setPlanificaciones(Set<Planificacion> planificaciones) {
		this.planificaciones = planificaciones;
	}

	public Double getOriginal() {
		return original;
	}

	public void setOriginal(Double original) {
		this.original = original;
	}

	public Double getConsumido() {
		return consumido;
	}

	public void setConsumido(Double consumido) {
		this.consumido = consumido;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
}
