package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import modelo.pk.UniformePlanillaUniformeId;

@Entity
@Table(name = "uniforme_planilla_uniforme")
public class UniformePlanillaUniforme implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UniformePlanillaUniformeId id;
	
	@Column(name="genero",length = 500)
	private String genero;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name="precio_unitario")
	private Double precioUnitario;

	public UniformePlanillaUniforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniformePlanillaUniforme(UniformePlanillaUniformeId id, String genero,
			Integer personas, Double precioUnitario) {
		super();
		this.id = id;
		this.genero = genero;
		this.cantidad = personas;
		this.precioUnitario = precioUnitario;
	}

	public UniformePlanillaUniformeId getId() {
		return id;
	}

	public void setId(UniformePlanillaUniformeId id) {
		this.id = id;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
}
