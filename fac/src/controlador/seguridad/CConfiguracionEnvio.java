package controlador.seguridad;

import java.io.IOException;

import modelo.seguridad.ConfiguracionEnvio;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Mensaje;
import controlador.maestros.CGenerico;

public class CConfiguracionEnvio extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraConfiguracionEnvio;
	@Wire
	private Window wdwConfiguracionEnvio;
	@Wire
	private Radio rdoNo;
	@Wire
	private Radio rdoSi;
	@Wire
	private Intbox txtDesde;
	@Wire
	private Intbox txtHasta;

	@Override
	public void inicializar() throws IOException {
		ConfiguracionEnvio objeto = servicioConfiguracionEnvio.buscar((long) 1);
		if (objeto != null) {
			txtDesde.setValue(objeto.getDesde());
			txtHasta.setValue(objeto.getHasta());
			if (objeto.getEstado())
				rdoSi.setChecked(true);
			else
				rdoNo.setChecked(true);
		}
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwConfiguracionEnvio);
			}

			@Override
			public void limpiar() {
			}

			@Override
			public void guardar() {
				if (validar()) {
					int desde = 0;
					if (txtDesde.getValue() != null)
						desde = txtDesde.getValue();
					int hasta = 0;
					if (txtHasta.getValue() != null)
						hasta = txtHasta.getValue();
					boolean estado = false;
					if (rdoSi.isChecked())
						estado = true;
					ConfiguracionEnvio objeto = new ConfiguracionEnvio(1,
							estado, desde, hasta, fechaHora, horaAuditoria,
							nombreUsuarioSesion());
					servicioConfiguracionEnvio.guardar(objeto);
					msj.mensajeInformacion(Mensaje.guardado);
				}
			}

			@Override
			public void enviar() {
			}

			@Override
			public void eliminar() {
			}

			@Override
			public void buscar() {
			}

			@Override
			public void atras() {
			}

			@Override
			public void adelante() {
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraConfiguracionEnvio.appendChild(botonera);
	}

	protected boolean validar() {
		if (rdoSi.isChecked()
				&& (txtDesde.getText().compareTo("") == 0 || txtHasta.getText()
						.compareTo("") == 0)) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else {
			if (rdoSi.isChecked()
					&& (txtDesde.getValue() == 0 || txtHasta.getValue() == 0)) {
				msj.mensajeAlerta("Los valores de las fechas deben ser mayores o iguales a 1");
				return false;
			} else {
				if (rdoSi.isChecked()
						&& (txtDesde.getValue() > 31 || txtHasta.getValue() > 31)) {
					msj.mensajeAlerta("El valor maximo permitido para las fechas es de 31");
					return false;
				} else {
					if (rdoSi.isChecked()
							&& (txtDesde.getValue() >= txtHasta.getValue())) {
						msj.mensajeAlerta("La fecha de inicio debe ser menor a la fecha de Finalizacion");
						return false;
					} else
						return true;
				}
			}
		}
	}
}
