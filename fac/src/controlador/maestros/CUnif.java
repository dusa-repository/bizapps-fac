package controlador.maestros;

import java.io.IOException;

import modelo.maestros.Marca;
import modelo.maestros.Sku;
import modelo.maestros.Uniforme;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Catalogo;

public class CUnif extends CGenerico {

	private static final long serialVersionUID = -981235791386186859L;
	@Wire
	private Window wdwUniforme;
	@Wire
	private Textbox txtDescripcionUniforme;
	@Wire
	private Div botoneraUniforme;
	@Wire
	private Div DivCatalogoUniforme;
	Catalogo<Uniforme> catalogo;
	long id = 0;
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
