package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Aliado;
import modelo.maestros.Zona;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CZona extends CGenerico {

	private static final long serialVersionUID = 8333128941305460385L;
	@Wire
	private Window wdwZona;
	@Wire
	private Textbox txtCodigoZona;
	@Wire
	private Textbox txtDescripcionZona;
	@Wire
	private Doublespinner spnOriginal;
	@Wire
	private Doublespinner spnConsumido;
	@Wire
	private Doublebox txtSaldo;
	@Wire
	private Div botoneraZona;
	@Wire
	private Div DivCatalogoZona;
	Catalogo<Zona> catalogo;
	String id = "";

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwZona);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtCodigoZona.setValue("");
				txtDescripcionZona.setValue("");
				spnConsumido.setValue((double) 0);
				spnOriginal.setValue((double) 0);
				txtSaldo.setValue(0);
				id = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionZona.getValue();
					String codigo = txtCodigoZona.getValue();
					Zona zona = new Zona(codigo, descripcion, fechaHora,
							horaAuditoria, nombreUsuarioSesion(),
							spnOriginal.getValue(), spnConsumido.getValue(),
							txtSaldo.getValue());
					servicioZona.guardar(zona);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("�Esta Seguro de Eliminar la Zona?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Zona zona = servicioZona.buscar(id);
										List<Usuario> usuarios = servicioUsuario
												.buscarPorZona(zona);
										List<Aliado> aliados = servicioAliado
												.buscarPorZona(zona);

										if (!usuarios.isEmpty()
												|| !aliados.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioZona.eliminar(id);
											limpiar();
											msj.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
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
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraZona.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoZona.getText().compareTo("") == 0
				|| txtDescripcionZona.getText().compareTo("") == 0
				|| txtSaldo.getText().compareTo("") == 0
				|| spnConsumido.getText().compareTo("") == 0
				|| spnOriginal.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (spnConsumido.getValue() > spnOriginal.getValue()) {
				msj.mensajeAlerta("El monto consumido no puede ser mayor al monto original");
				return false;
			} else
				return true;
		}

	}

	public void buscarCatalogoPropio() {
		final List<Zona> listZona = servicioZona.buscarTodosOrdenados();
		catalogo = new Catalogo<Zona>(DivCatalogoZona, "Catalogo de Zonas",
				listZona, true, "Id", "Descripcion") {

			@Override
			protected List<Zona> buscar(List<String> valores) {

				List<Zona> lista = new ArrayList<Zona>();

				for (Zona zona : listZona) {
					if (zona.getIdZona().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& (zona.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase()))) {
						lista.add(zona);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Zona zona) {
				String[] registros = new String[2];
				registros[0] = zona.getIdZona();
				registros[1] = zona.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoZona);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #DivCatalogoZona")
	public void seleccionPropia() {
		Zona zona = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(zona);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtCodigoZona")
	public void buscarPorNombrePropio() {
		Zona zona = servicioZona.buscar(txtCodigoZona.getValue());
		if (zona != null)
			llenarCamposPropios(zona);
	}

	public void llenarCamposPropios(Zona zona) {
		txtCodigoZona.setValue(zona.getIdZona());
		txtDescripcionZona.setValue(zona.getDescripcion());
		id = zona.getIdZona();
		if (zona.getSaldo() != null) {
			spnConsumido.setValue(zona.getConsumido());
			spnOriginal.setValue(zona.getOriginal());
			txtSaldo.setValue(zona.getSaldo());
		} else {
			spnConsumido.setValue((double) 0);
			spnOriginal.setValue((double) 0);
			txtSaldo.setValue(0);
		}
	}

	@Listen("onChange = #spnConsumido,#spnOriginal; onOK = #spnConsumido,#spnOriginal ")
	public void actualizarSaldo() {
		if (spnConsumido.getValue() != null && spnOriginal.getValue() != null) {
			if (spnConsumido.getValue() <= spnOriginal.getValue()) {
				txtSaldo.setValue(spnOriginal.getValue()
						- spnConsumido.getValue());
			} else
				msj.mensajeAlerta("El monto consumido no puede ser mayor al monto original");
		}
	}

}
