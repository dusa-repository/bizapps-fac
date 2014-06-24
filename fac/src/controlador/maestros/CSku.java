package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;

public class CSku extends CGenerico {

	private static final long serialVersionUID = -1478489125463235132L;
	@Wire
	private Window wdwSku;
	@Wire
	private Textbox txtCodigoSku;
	@Wire
	private Textbox txtDescripcionSku;
	@Wire
	private Textbox txtCodigoMarca;
	@Wire
	private Div botoneraSku;
	@Wire
	private Div DivCatalogoMarca;
	@Wire
	private Div DivCatalogoSku;
	Catalogo<Sku> catalogo;
	Catalogo<Marca> catalogoMarca;
	String id = "";
	String idMarca = "";

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwSku);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();
			}

			@Override
			public void limpiar() {
				txtCodigoMarca.setValue("");
				txtCodigoSku.setValue("");
				txtDescripcionSku.setValue("");
				id = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionSku.getValue();
					String codigo = txtCodigoSku.getValue();
					Marca marca = servicioMarca.buscar(idMarca);
					Sku sku = new Sku(codigo, marca, descripcion, fechaHora,
							horaAuditoria, nombreUsuarioSesion());
					servicioSku.guardar(sku);
					Messagebox.show("Registro Guardado Exitosamente",
							"Informacion", Messagebox.OK,
							Messagebox.INFORMATION);

					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar el Producto?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										servicioSku.eliminar(id);
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
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraSku.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoMarca.getText().compareTo("") == 0
				|| txtCodigoSku.getText().compareTo("") == 0
				|| txtDescripcionSku.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Sku> listSku = servicioSku.buscarTodosOrdenados();
		catalogo = new Catalogo<Sku>(DivCatalogoSku, "Catalogo de Sku", listSku, true,
				"Id", "Descripcion", "Marca") {

			@Override
			protected List<Sku> buscar(List<String> valores) {

				List<Sku> lista = new ArrayList<Sku>();

				for (Sku sku : listSku) {
					if (sku.getIdSku().toLowerCase()
							.startsWith(valores.get(0))
							&& sku.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))
							&& sku.getMarca().getDescripcion().toLowerCase()
									.startsWith(valores.get(2))) {
						lista.add(sku);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Sku sku) {
				String[] registros = new String[3];
				registros[0] = sku.getIdSku();
				registros[1] = sku.getDescripcion();
				registros[2] = sku.getMarca().getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoSku);
		catalogo.doModal();
	}

	@Listen("onClick = #btnBuscarMarcas")
	public void buscarCatalogoAjeno() {
		final List<Marca> listMarca = servicioMarca.buscarTodosOrdenados();
		catalogoMarca = new Catalogo<Marca>(DivCatalogoMarca, "Marca", listMarca,
				true, "Id", "Descripcion") {

			@Override
			protected List<Marca> buscar(List<String> valores) {

				List<Marca> lista = new ArrayList<Marca>();

				for (Marca marca : listMarca) {
					if (marca.getIdMarca().toLowerCase()
							.startsWith(valores.get(0))
							&& marca.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))) {
						lista.add(marca);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Marca marca) {
				String[] registros = new String[2];
				registros[0] = marca.getIdMarca();
				registros[1] = marca.getDescripcion();
				return registros;
			}
		};
		catalogoMarca.setParent(DivCatalogoMarca);
		catalogoMarca.doModal();
	}
	
	@Listen("onSeleccion = #DivCatalogoSku")
	public void seleccionPropia() {
		Sku sku = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(sku);
		catalogo.setParent(null);
	}

	@Listen("onSeleccion = #DivCatalogoMarca")
	public void seleccionAjena() {
		Marca marca = catalogoMarca.objetoSeleccionadoDelCatalogo();
		llenarCamposAjenos(marca);
		catalogoMarca.setParent(null);
	}

	@Listen("onChange = #txtCodigoSku")
	public void buscarPorNombrePropio() {
		Sku sku = servicioSku
				.buscar(txtCodigoSku.getValue());
		if (sku != null)
			llenarCamposPropios(sku);
	}

	@Listen("onChange = #txtCodigoMarca")
	public void buscarPorNombreAjeno() {
		Marca marca = servicioMarca.buscar(txtCodigoMarca.getValue());
		if (marca != null)
			llenarCamposAjenos(marca);
	}

	public void llenarCamposAjenos(Marca marca) {
		txtCodigoMarca.setValue(marca.getDescripcion());
		idMarca = marca.getIdMarca();
	}

	public void llenarCamposPropios(Sku sku) {
		txtCodigoSku.setValue(sku.getIdSku());
		txtDescripcionSku.setValue(sku.getDescripcion());
		txtCodigoMarca.setValue(sku.getMarca().getIdMarca());
		idMarca = sku.getMarca().getIdMarca();
		id = sku.getIdSku();
	}

}
