package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Empresa;
import modelo.maestros.Marca;

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

public class CEmpresa extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwEmpresa;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtDescripcion;
	@Wire
	private Div botoneraEmpresa;
	@Wire
	private Div divCatalogoEmpresa;
	Catalogo<Empresa> catalogo;
	Long id = (long) 0;

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwEmpresa);
			}

			@Override
			public void limpiar() {
				txtDescripcion.setValue("");
				txtNombre.setValue("");
				id = (long) 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcion.getValue();
					String codigo = txtNombre.getValue();
					Empresa empresa = new Empresa(id, codigo, descripcion,
							fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioEmpresa.guardar(empresa);
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
					Messagebox.show("¿Esta Seguro de Eliminar la Empresa?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										List<Marca> marcas = servicioMarca
												.buscarPorIdEmpresa(id);
										if (!marcas.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioEmpresa.eliminar(id);
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
			public void buscar() {
				buscarCatalogoPropio();
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
		botoneraEmpresa.appendChild(botonera);

	}

	protected boolean validar() {
		if (txtDescripcion.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Empresa> listMarca = servicioEmpresa.buscarTodosOrdenados();
		catalogo = new Catalogo<Empresa>(divCatalogoEmpresa,
				"Catalogo de Empresas", listMarca, true, "Nombre",
				"Descripcion") {

			@Override
			protected List<Empresa> buscar(List<String> valores) {

				List<Empresa> lista = new ArrayList<Empresa>();

				for (Empresa marca : listMarca) {
					if (marca.getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& marca.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(marca);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Empresa marca) {
				String[] registros = new String[2];
				registros[0] = marca.getNombre();
				registros[1] = marca.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(divCatalogoEmpresa);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpresa")
	public void seleccionPropia() {
		Empresa marca = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(marca);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtNombre")
	public void buscarPorNombrePropio() {
		Empresa marca = servicioEmpresa.buscarPorNombre(txtNombre.getValue());
		if (marca != null)
			llenarCamposPropios(marca);
	}

	public void llenarCamposPropios(Empresa marca) {
		txtNombre.setValue(marca.getNombre());
		txtDescripcion.setValue(marca.getDescripcion());
		id = marca.getIdEmpresa();
	}

}
