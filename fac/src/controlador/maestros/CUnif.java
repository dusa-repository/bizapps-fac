package controlador.maestros;

import java.io.IOException;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CUnif extends CGenerico {

	@Wire
	private Window wdwUniforme;
	@Wire
	private Textbox txtDescripcionUniforme;
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
