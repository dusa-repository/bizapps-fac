package modelo.estado;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.transacciones.PlanillaFachada;

@Entity
@Table(name = "bitacora_planilla_fachada")
public class BitacoraFachada implements Serializable {

	private static final long serialVersionUID = 6456037838461731293L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bitacora_planilla_fachada", unique=true, nullable=false)
	private long idBitacora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_planilla_fachada")
	private PlanillaFachada planillaFachada;
	
	@Column(name="estado_planilla",length = 500)
	private String estado;
	
	@Column(name = "fecha_cambio")
	private Timestamp fechaCambio;
	
	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	@Lob
	private byte[] imagen;
	
	public BitacoraFachada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BitacoraFachada(long idBitacora, PlanillaFachada planillaFachada,
			String estado, Timestamp fechaCambio, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria,	byte[] imagen) {
		super();
		this.idBitacora = idBitacora;
		this.planillaFachada = planillaFachada;
		this.estado = estado;
		this.fechaCambio = fechaCambio;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.imagen = imagen;
	}

	public long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public PlanillaFachada getPlanillaFachada() {
		return planillaFachada;
	}

	public void setPlanillaFachada(PlanillaFachada planillaFachada) {
		this.planillaFachada = planillaFachada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Timestamp fechaCambio) {
		this.fechaCambio = fechaCambio;
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

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	public BufferedImage traerImagen(String Imagen) throws IOException {
		BufferedImage imagenes;
		imagenes = ImageIO.read(new ByteArrayInputStream(imagen));
		return imagenes;
	}
	
	
}
