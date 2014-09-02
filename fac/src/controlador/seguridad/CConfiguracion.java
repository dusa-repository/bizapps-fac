package controlador.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CConfiguracion extends CGenerico {

	private static final long serialVersionUID = -2800409643154176860L;
	@Wire
	private Listbox ltbTradeMark;
	@Wire
	private Listbox ltbMarca;
	@Wire
	private Window wdwConfiguracion;
	@Wire
	private Textbox txtCorreoTrade;
	@Wire
	private Textbox txtCorreoMarca;
	@Wire
	private Div botoneraConfiguracion;
	ListModelList<Arbol> configuracion;
	List<Listbox> listas = new ArrayList<Listbox>();

	@Override
	public void inicializar() throws IOException {

		llenarListas();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwConfiguracion);
			}

			@Override
			public void limpiar() {

			}

			@Override
			public void guardar() {
				if (validar()) {
					servicioConfiguracion.limpiar();
					List<Configuracion> lista = new ArrayList<Configuracion>();
					String correoMarca = txtCorreoMarca.getValue();
					if (ltbMarca.getItemCount() != 0) {
						for (int i = 0; i < ltbMarca.getItemCount(); i++) {
							Listitem listItem = ltbMarca.getItemAtIndex(i);
							if (listItem.isSelected()) {
								Arbol arbol = listItem.getValue();
								Configuracion configuracion = new Configuracion(
										0, arbol.getNombre(), "Marca",
										correoMarca, fechaHora, horaAuditoria,
										nombreUsuarioSesion());
								lista.add(configuracion);
							}
						}
					}
					String correoTrade = txtCorreoTrade.getValue();
					if (ltbTradeMark.getItemCount() != 0) {
						for (int i = 0; i < ltbTradeMark.getItemCount(); i++) {
							Listitem listItem = ltbTradeMark.getItemAtIndex(i);
							if (listItem.isSelected()) {
								Arbol arbol = listItem.getValue();
								Configuracion configuracion = new Configuracion(
										0, arbol.getNombre(), "TradeMark",
										correoTrade, fechaHora, horaAuditoria,
										nombreUsuarioSesion());
								lista.add(configuracion);
							}
						}
					}
					servicioConfiguracion.guardar(lista);
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
		botoneraConfiguracion.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCorreoMarca.getText().compareTo("") == 0
				|| txtCorreoTrade.getText().compareTo("") == 0) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else {
			if (!validarCorreo())
				return false;
			else {
				if (!validarCorreo2())
					return false;
				else
					return true;
			}
		}
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtCorreoMarca")
	public boolean validarCorreo() {
		if (!Validador.validarCorreo(txtCorreoMarca.getValue())) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
			return false;
		}
		return true;
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtCorreoTrade")
	public boolean validarCorreo2() {
		if (!Validador.validarCorreo(txtCorreoTrade.getValue())) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
			return false;
		}
		return true;
	}

	private void llenarListas() {
		List<Configuracion> configuracionesTrade = servicioConfiguracion
				.buscarTipo("TradeMark");
		if (!configuracionesTrade.isEmpty())
			txtCorreoTrade.setValue(configuracionesTrade.get(0).getCorreo());
		List<Configuracion> configuracionesMarca = servicioConfiguracion
				.buscarTipo("Marca");
		if (!configuracionesMarca.isEmpty()) {
			txtCorreoMarca.setValue(configuracionesMarca.get(0).getCorreo());
		}
		listasMultiples();
		ltbMarca.renderAll();
		ltbTradeMark.renderAll();
		if (!configuracionesMarca.isEmpty()) {
			for (int i = 0; i < configuracionesMarca.size(); i++) {
				String id = configuracionesMarca.get(i).getPlanilla();
				for (int j = 0; j < ltbMarca.getItemCount(); j++) {
					Listitem listItem = ltbMarca.getItemAtIndex(j);
					Arbol a = listItem.getValue();
					String id2 = a.getNombre();
					if (id.equals(id2)) {
						listItem.setSelected(true);
						j = ltbMarca.getItemCount();
					}
				}

			}
		}
		if (!configuracionesTrade.isEmpty()) {
			for (int i = 0; i < configuracionesTrade.size(); i++) {
				String id = configuracionesTrade.get(i).getPlanilla();
				for (int j = 0; j < ltbTradeMark.getItemCount(); j++) {
					Listitem listItem = ltbTradeMark.getItemAtIndex(j);
					Arbol a = listItem.getValue();
					String id2 = a.getNombre();
					if (id.equals(id2)) {
						listItem.setSelected(true);
						j = ltbTradeMark.getItemCount();
					}
				}

			}
		}

	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
			listas.add(ltbMarca);
			listas.add(ltbTradeMark);
		}
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

	public ListModelList<Arbol> getConfiguracion() {
		configuracion = new ListModelList<Arbol>(
				servicioArbol.buscarParaConfiguracion());
		return configuracion;
	}
}
