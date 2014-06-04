package controlador.maestros;

import java.io.IOException;

import modelo.maestros.Zona;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Catalogo;

public class CZona extends CGenerico {

	private static final long serialVersionUID = 8333128941305460385L;
	@Wire
	private Window wdwZona;
	@Wire
	private Textbox txtDescripcionZona;
	@Wire
	private Div botoneraZona;
	@Wire
	private Div DivCatalogoZona;
	Catalogo<Zona> catalogo;
	String id = "";
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
