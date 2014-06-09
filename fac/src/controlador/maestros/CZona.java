package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Marca;
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

public class CZona extends CGenerico {

	private static final long serialVersionUID = 8333128941305460385L;
	@Wire
	private Window wdwZona;
	@Wire
	private Textbox txtCodigoZona;
	@Wire
	private Textbox txtDescripcionZona;
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
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				txtCodigoZona.setValue("");
				txtDescripcionZona.setValue("");
				id = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionZona.getValue();
					String codigo = txtCodigoZona.getValue();
					Zona zona = new Zona(codigo, descripcion, fechaHora,
							horaAuditoria, nombreUsuarioSesion());
					servicioZona.guardar(zona);
					Messagebox.show("Registro Guardado Exitosamente",
							"Informacion", Messagebox.OK,
							Messagebox.INFORMATION);

					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar la Marca?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										servicioZona.eliminar(id);
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
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraZona.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoZona.getText().compareTo("") == 0
				|| txtDescripcionZona.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnBuscarZonas")
	public void buscarCatalogoPropio() {
		final List<Zona> listZona = servicioZona.buscarTodosOrdenados();
		catalogo = new Catalogo<Zona>(DivCatalogoZona, "Zona", listZona, true,
				"Id", "Descripcion") {

			@Override
			protected List<Zona> buscar(List<String> valores) {

				List<Zona> lista = new ArrayList<Zona>();

				for (Zona zona : listZona) {
					if (zona.getIdZona().toLowerCase()
							.startsWith(valores.get(0))
							&& (zona.getDescripcion().startsWith(valores.get(1))
							|| zona.getDescripcion().toLowerCase()
									.startsWith(valores.get(1)))) {
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
	}

}
