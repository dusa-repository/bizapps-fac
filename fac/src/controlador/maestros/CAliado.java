package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Aliado;
import modelo.maestros.Zona;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;

public class CAliado extends CGenerico {

	private static final long serialVersionUID = -7934953817552412985L;
	@Wire
	private Window wdwAliado;
	@Wire
	private Textbox txtDescripcionAliado;
	@Wire
	private Textbox txtZonaAliado;
	@Wire
	private Textbox txtAnexoAliado;
	@Wire
	private Div botoneraAliado;
	@Wire
	private Div DivCatalogoAliado;
	@Wire
	private Div DivCatalogoZona;
	Catalogo<Aliado> catalogo;
	Catalogo<Zona> catalogoZona;
	long id = 0;
	String idZona = "";

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwAliado);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();
			}

			@Override
			public void limpiar() {
				txtAnexoAliado.setValue("");
				txtDescripcionAliado.setValue("");
				txtZonaAliado.setValue("");
				id = 0;
				idZona = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionAliado.getValue();
					String anexo = txtAnexoAliado.getValue();
					Zona zona = servicioZona.buscar(idZona);
					Aliado aliado = new Aliado(id, zona, descripcion, anexo,
							fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioAliado.guardar(aliado);
					Messagebox.show("Registro Guardado Exitosamente",
							"Informacion", Messagebox.OK,
							Messagebox.INFORMATION);

					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Aliado?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										servicioAliado.eliminar(id);
										limpiar();
										Messagebox
												.show("Registro Eliminado Exitosamente",
														"Informacion",
														Messagebox.OK,
														Messagebox.INFORMATION);
									}
								}
							});
				} else {
					Messagebox.show("No ha Seleccionado Ningun Registro",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}

			@Override
			public void atras() {
			}

			@Override
			public void adelante() {
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
		botoneraAliado.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtAnexoAliado.getText().compareTo("") == 0
				|| txtDescripcionAliado.getText().compareTo("") == 0
				|| txtZonaAliado.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Aliado> listAliado = servicioAliado.buscarTodosOrdenados();
		catalogo = new Catalogo<Aliado>(DivCatalogoAliado, "Catalogo de Aliados",
				listAliado, true, "Nombre", "Zona", "Anexo") {

			@Override
			protected List<Aliado> buscar(List<String> valores) {

				List<Aliado> lista = new ArrayList<Aliado>();

				for (Aliado aliado : listAliado) {
					if (aliado.getNombre().toLowerCase()
							.startsWith(valores.get(0))
							&& aliado.getZona().getDescripcion().toLowerCase()
									.startsWith(valores.get(1))
							&& aliado.getAnexo().toLowerCase()
									.startsWith(valores.get(2))) {
						lista.add(aliado);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Aliado aliado) {
				String[] registros = new String[3];
				registros[0] = aliado.getNombre();
				registros[1] = aliado.getZona().getDescripcion();
				registros[2] = aliado.getAnexo();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoAliado);
		catalogo.doModal();
	}

	@Listen("onClick = #btnBuscarZonas")
	public void buscarCatalogoAjeno() {
		final List<Zona> listZona = servicioZona.buscarTodosOrdenados();
		catalogoZona = new Catalogo<Zona>(DivCatalogoZona, "Catalogo de Zonas", listZona,
				true, "Id", "Descripcion") {

			@Override
			protected List<Zona> buscar(List<String> valores) {

				List<Zona> lista = new ArrayList<Zona>();

				for (Zona zona : listZona) {
					if (zona.getIdZona().toLowerCase()
							.startsWith(valores.get(0))
							&& zona.getDescripcion().toLowerCase()
									.startsWith(valores.get(2))) {
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
		catalogoZona.setParent(DivCatalogoZona);
		catalogoZona.doModal();
	}

	@Listen("onSeleccion = #DivCatalogoAliado")
	public void seleccionPropia() {
		Aliado aliado = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(aliado);
		catalogo.setParent(null);
	}

	@Listen("onSeleccion = #DivCatalogoZona")
	public void seleccionAjena() {
		Zona zona = catalogoZona.objetoSeleccionadoDelCatalogo();
		llenarCamposAjenos(zona);
		catalogoZona.setParent(null);
	}

	@Listen("onChange = #txtDescripcionAliado")
	public void buscarPorNombrePropio() {
		Aliado aliado = servicioAliado
				.buscarPorDescripcion(txtDescripcionAliado.getValue());
		if (aliado != null)
			llenarCamposPropios(aliado);
	}

	@Listen("onChange = #txtZonaAliado")
	public void buscarPorNombreAjeno() {
		Zona zona = servicioZona.buscarPorDescripcion(txtZonaAliado.getValue());
		if (zona != null)
			llenarCamposAjenos(zona);
	}

	public void llenarCamposAjenos(Zona zona) {
		txtZonaAliado.setValue(zona.getDescripcion());
		idZona = zona.getIdZona();
	}

	public void llenarCamposPropios(Aliado aliado) {
		txtAnexoAliado.setValue(aliado.getAnexo());
		txtDescripcionAliado.setValue(aliado.getNombre());
		txtZonaAliado.setValue(aliado.getZona().getDescripcion());
		idZona = aliado.getZona().getIdZona();
		id = aliado.getIdAliado();
	}
}
