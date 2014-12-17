package controlador.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;

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

import controlador.maestros.CGenerico;

public class CFuncionalidad extends CGenerico {

	private static final long serialVersionUID = 5276371374357687348L;
	@Wire
	private Window wdwFuncionalidad;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtNombreBoton;
	@Wire
	private Textbox txtUrl;
	@Wire
	private Div botoneraFuncionalidad;
	@Wire
	private Div catalogoFuncionalidad;
	Catalogo<Arbol> catalogo;
	long id = 0;

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwFuncionalidad);
			}

			@Override
			public void limpiar() {
				txtNombre.setValue("");
				txtNombreBoton.setValue("");
				txtUrl.setValue("");
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombre.getValue();
					String boton = txtNombreBoton.getValue();
					String url = txtUrl.getValue();
					Arbol arbolitoDeNavidadRosado = new Arbol(id, nombre,
							boton, url);
					servicioArbol.guardar(arbolitoDeNavidadRosado);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void enviar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show(Mensaje.deseaEliminar, "Alerta",
							Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Arbol arbol = servicioArbol.buscar(id);
										List<Grupo> grupo = servicioGrupo
												.buscarPorArbol(arbol);

										if (!grupo.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioArbol.eliminar(id);
											msj.mensajeInformacion(Mensaje.eliminado);
											limpiar();
										}
									}
								}
							});
				} else
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
			}

			@Override
			public void buscar() {
				final List<Arbol> listArbol = servicioArbol.buscarTodos();
				catalogo = new Catalogo<Arbol>(catalogoFuncionalidad,
						"Catalogo de Funcionalidades", listArbol, true,
						"Nombre", "Nombre de Boton", "Url") {

					@Override
					protected List<Arbol> buscar(List<String> valores) {

						List<Arbol> lista = new ArrayList<Arbol>();

						for (Arbol arbol : listArbol) {
							if (arbol.getNombre().toLowerCase()
									.contains(valores.get(0).toLowerCase())
									&& arbol.getNombreBoton()
											.toLowerCase()
											.contains(
													valores.get(1)
															.toLowerCase())
									&& arbol.getUrl()
											.toLowerCase()
											.contains(
													valores.get(2)
															.toLowerCase())) {
								lista.add(arbol);
							}
						}
						return lista;
					}

					@Override
					protected String[] crearRegistros(Arbol arbol) {
						String[] registros = new String[3];
						registros[0] = arbol.getNombre();
						registros[1] = arbol.getNombreBoton();
						registros[2] = arbol.getUrl();
						return registros;
					}
				};
				catalogo.setParent(catalogoFuncionalidad);
				catalogo.doModal();
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
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraFuncionalidad.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtNombre.getText().compareTo("") == 0
				|| txtNombreBoton.getText().compareTo("") == 0
				|| txtUrl.getText().compareTo("") == 0) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	@Listen("onSeleccion = #catalogoFuncionalidad")
	public void seleccionPropia() {
		Arbol arbol = catalogo.objetoSeleccionadoDelCatalogo();
		if (arbol != null)
			settear(arbol);
		catalogo.setParent(null);
	}

	private void settear(Arbol arbol) {
		txtNombre.setValue(arbol.getNombre());
		txtNombreBoton.setValue(arbol.getNombreBoton());
		txtUrl.setValue(arbol.getUrl());
		id = arbol.getIdArbol();
	}

	@Listen("onChange = #txtNombre")
	public void seleccionsPropia() {
		Arbol arbol = servicioArbol.buscarPorNombre(txtNombre.getValue());
		if (arbol != null)
			settear(arbol);
	}

}
