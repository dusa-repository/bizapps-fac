package controlador.transacciones;

import java.io.IOException;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import componente.Botonera;

import controlador.maestros.CGenerico;

public class CPromocion extends CGenerico {

	@Wire
	private Window wdwPromocion;
	@Wire
	private Div botoneraPromocion;
	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {
			
			@Override
			public void salir() {
				cerrarVentana(wdwPromocion);
			}
			
			@Override
			public void reporte() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void limpiar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void guardar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void atras() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void adelante() {
				// TODO Auto-generated method stub
				
			}
		};
		botonera.getChildren().get(2).setVisible(false);
		botoneraPromocion.appendChild(botonera);
	}

}
