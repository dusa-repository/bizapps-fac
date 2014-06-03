package controlador.transacciones;

import java.io.IOException;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tab;

import controlador.maestros.CGenerico;

public class CSolicitudArte extends CGenerico {

	@Wire
	private Tab tabImagenes;
	
	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Listen("onClick = #txtNombreActividad")
	public void ok(){
		tabImagenes.setSelected(true);
	}
}
