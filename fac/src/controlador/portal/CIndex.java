package controlador.portal;

import java.io.IOException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import controlador.maestros.CGenerico;

public class CIndex extends CGenerico {

	private static final long serialVersionUID = -2911126596875079981L;
	@Wire
	private Window wdwIndex;
	@Override
	public void inicializar() throws IOException {
//		Clients.evalJavaScript("document.body.style.MozTransform = 'scale(0.75)';");
//		wdwIndex.setStyle("-moz-transform: scale(0.75);");
//		if(Executions.getCurrent().getBrowser().equals("gecko")){
//			wdwIndex.setWidth("106em");
//			wdwIndex.setHeight("50em");
//		}
	}
	
	@Listen("onClick = #lblOlvidoClave")
	public void abrirVentana(){
		Window window = (Window) Executions.createComponents(

				"/vistas/seguridad/VReinicioPassword.zul", null, null);

				window.doModal();
	}
}
