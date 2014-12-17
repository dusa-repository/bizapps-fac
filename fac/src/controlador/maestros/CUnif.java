package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Uniforme;
import modelo.transacciones.UniformePlanillaUniforme;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

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
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwUniforme);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtDescripcionUniforme.setValue("");
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionUniforme.getValue();
					Uniforme uniforme = new Uniforme(id, descripcion,
							fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioUniforme.guardar(uniforme);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Uniforme?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Uniforme uni = servicioUniforme
												.buscar(id);
										List<UniformePlanillaUniforme> planillas = servicioUniformePlanillaUniforme
												.buscarPorUniforme(uni);
										if (!planillas.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioUniforme.eliminar(id);
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
		botoneraUniforme.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtDescripcionUniforme.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Uniforme> listUniforme = servicioUniforme
				.buscarTodosOrdenados();
		catalogo = new Catalogo<Uniforme>(DivCatalogoUniforme,
				"Catalogo de Uniformes", listUniforme, true, "Descripcion") {

			@Override
			protected List<Uniforme> buscar(List<String> valores) {

				List<Uniforme> lista = new ArrayList<Uniforme>();

				for (Uniforme recurso : listUniforme) {

					if (recurso.getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(recurso);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Uniforme recurso) {
				String[] registros = new String[1];
				registros[0] = recurso.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoUniforme);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #DivCatalogoUniforme")
	public void seleccionPropia() {
		Uniforme uniforme = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(uniforme);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtDescripcionUniforme")
	public void buscarPorNombrePropio() {
		Uniforme uniforme = servicioUniforme
				.buscarPorDescripcion(txtDescripcionUniforme.getValue());
		if (uniforme != null)
			llenarCamposPropios(uniforme);
	}

	public void llenarCamposPropios(Uniforme uniforme) {
		txtDescripcionUniforme.setValue(uniforme.getDescripcion());
		id = uniforme.getIdUniforme();
	}

}
