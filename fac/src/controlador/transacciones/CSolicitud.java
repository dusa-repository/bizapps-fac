package controlador.transacciones;

import java.io.IOException;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import componente.Botonera;

import controlador.maestros.CGenerico;

public class CSolicitud extends CGenerico {

	@Wire
	private Window wdwSolicitud;

	@Override
	public void inicializar() throws IOException {

		
	}

	@Listen("onClick= #btnCerrar")
	public void salir ()
	{
		cerrarVentana(wdwSolicitud);
	}
}
