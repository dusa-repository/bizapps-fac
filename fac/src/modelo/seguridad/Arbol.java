package modelo.seguridad;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="arbol")
public class Arbol implements Serializable {

	private static final long serialVersionUID = 5655425087743970440L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_arbol", unique=true, nullable=false)
	private long idArbol;

	@Column(length=500)
	private String nombre;

	private long padre;

	@Column(length=500)
	private String url;

	//bi-directional many-to-many association to Grupo
	@ManyToMany(mappedBy="arbols")
	private Set<Grupo> gruposArbol;

	public Arbol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Arbol(long idArbol, String nombre, long padre, String url) {
		super();
		this.idArbol = idArbol;
		this.nombre = nombre;
		this.padre = padre;
		this.url = url;
	}

	public long getIdArbol() {
		return idArbol;
	}

	public void setIdArbol(long idArbol) {
		this.idArbol = idArbol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getPadre() {
		return padre;
	}

	public void setPadre(long padre) {
		this.padre = padre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Grupo> getGruposArbol() {
		return gruposArbol;
	}

	public void setGruposArbol(Set<Grupo> gruposArbol) {
		this.gruposArbol = gruposArbol;
	}
	
	
}
