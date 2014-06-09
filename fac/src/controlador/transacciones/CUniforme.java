package controlador.transacciones;

import java.io.IOException;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import componente.Botonera;

import controlador.maestros.CGenerico;

public class CUniforme extends CGenerico {

	ListModelList generos;
	ListModelList tallas;
	@Wire
	private Window wdwSolicitudUniforme;
	@Wire
	private Div botoneraUniformes;
	
	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {
			
			@Override
			public void salir() {
				cerrarVentana(wdwSolicitudUniforme);
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

			@Override
			public void enviar() {
				// TODO Auto-generated method stub
				
			}
		};
		botonera.getChildren().get(2).setVisible(false);
		botoneraUniformes.appendChild(botonera);
	}

}
