package modelo.generico;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PlanillaGenerica {
	
	private long id;
	
	private String usuario;

	private String marca;
	
	private String nombreActividad;
	
	private Timestamp fecha;
	
	private String estado;
	
	private String tipoPlanilla;
	
	private String referencia;
	
	private String motivo;

	public PlanillaGenerica() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanillaGenerica(long id, String usuario, String marca,
			String nombreActividad, Timestamp fecha, String estado,
			String tipoPlanilla) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.marca = marca;
		this.nombreActividad = nombreActividad;
		this.fecha = fecha;
		this.estado = estado;
		this.tipoPlanilla = tipoPlanilla;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoPlanilla() {
		return tipoPlanilla;
	}

	public void setTipoPlanilla(String tipoPlanilla) {
		this.tipoPlanilla = tipoPlanilla;
	}
	


	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String traerFecha() {
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		return String.valueOf(formatoFecha.format(fecha));
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
