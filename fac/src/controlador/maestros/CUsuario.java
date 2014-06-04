package controlador.maestros;

import java.io.IOException;

import modelo.maestros.Zona;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Catalogo;

public class CUsuario extends CGenerico {

	private static final long serialVersionUID = 7879830599305337459L;
	@Wire
	private Window wdwUsuario;
	@Wire
	private Textbox txtCodigoUsuario;
	@Wire
	private Textbox txtNombreUsuario;
	@Wire
	private Textbox txtPasswordUsuario;
	@Wire
	private Textbox txtEmailUsuario;
	@Wire
	private Textbox txtZonaUsuario;
	@Wire
	private Textbox txtSupervisorUsuario;
	@Wire
	private Div botoneraUsuario;
	@Wire
	private Div DivCatalogoZona;
	@Wire
	private Div DivCatalogoUsuario;
	Catalogo<Usuario> catalogoUsuario;
	Catalogo<Zona> catalogo;
	String id = "";
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
