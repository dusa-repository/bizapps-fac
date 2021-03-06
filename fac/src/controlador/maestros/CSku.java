package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.ItemPlanillaCata;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

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
	private Radio rdoNo;
	@Wire
	private Radio rdoSi;
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
				rdoSi.setChecked(false);
				rdoNo.setChecked(false);
				id = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionSku.getValue();
					String codigo = txtCodigoSku.getValue();
					Marca marca = servicioMarca.buscar(idMarca);
					Boolean estado = false;
					if (rdoSi.isChecked())
						estado = true;
					Sku sku = new Sku(codigo, marca, descripcion, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), estado);
					servicioSku.guardar(sku);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					if (!estado) {
						notificarInactivacion(
								"Los siguientes SKU han sido inactivados: "
										+ descripcion, valor);
					}
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("�Esta Seguro de Eliminar el Producto?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Sku sku = servicioSku.buscar(id);
										List<ItemEstimadoPlanillaEvento> list1 = servicioItemEstimadoPlanillaEvento
												.buscarPorSku(sku);
										List<ItemDegustacionPlanillaEvento> list2 = servicioItemDegustacionPlanillaEvento
												.buscarPorSku(sku);
										List<ItemPlanillaCata> list3 = servicioItemPlanillaCata
												.buscarPorSku(sku);

										if (!list1.isEmpty()
												|| !list2.isEmpty()
												|| !list3.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioSku.eliminar(id);
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
		botoneraSku.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoMarca.getText().compareTo("") == 0
				|| txtCodigoSku.getText().compareTo("") == 0
				|| txtDescripcionSku.getText().compareTo("") == 0
				|| (!rdoSi.isChecked() && !rdoNo.isChecked())) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Sku> listSku = servicioSku.buscarTodosOrdenados();
		catalogo = new Catalogo<Sku>(DivCatalogoSku, "Catalogo de Sku",
				listSku, true, "Id", "Descripcion", "Marca", "Estado") {

			@Override
			protected List<Sku> buscar(List<String> valores) {

				List<Sku> lista = new ArrayList<Sku>();

				for (Sku sku : listSku) {
					String estado = "Activo";
					if (sku.getEstado() != null)
						if (!sku.getEstado())
							estado = "Inactivo";
					if (sku.getIdSku().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& sku.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& sku.getMarca().getDescripcion().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& estado.toLowerCase().contains(
									valores.get(3).toLowerCase())) {
						lista.add(sku);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Sku sku) {
				String estado = "Activo";
				if (sku.getEstado() != null)
					if (!sku.getEstado())
						estado = "Inactivo";
				String[] registros = new String[4];
				registros[0] = sku.getIdSku();
				registros[1] = sku.getDescripcion();
				registros[2] = sku.getMarca().getDescripcion();
				registros[3] = estado;
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoSku);
		catalogo.doModal();
	}

	@Listen("onClick = #btnBuscarMarcas")
	public void buscarCatalogoAjeno() {
		final List<Marca> listMarca = servicioMarca.buscarTodosOrdenados();
		catalogoMarca = new Catalogo<Marca>(DivCatalogoMarca, "Marca",
				listMarca, true, "Id", "Descripcion") {

			@Override
			protected List<Marca> buscar(List<String> valores) {

				List<Marca> lista = new ArrayList<Marca>();

				for (Marca marca : listMarca) {
					if (marca.getIdMarca().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& marca.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
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
		Sku sku = servicioSku.buscar(txtCodigoSku.getValue());
		if (sku != null)
			llenarCamposPropios(sku);
	}

	@Listen("onChange = #txtCodigoMarca")
	public void buscarPorNombreAjeno() {
		Marca marca = servicioMarca.buscar(txtCodigoMarca.getValue());
		if (marca != null)
			llenarCamposAjenos(marca);
		else
			txtCodigoMarca.setValue("");

	}

	public void llenarCamposAjenos(Marca marca) {
		txtCodigoMarca.setValue(marca.getDescripcion());
		idMarca = marca.getIdMarca();
	}

	public void llenarCamposPropios(Sku sku) {
		txtCodigoSku.setValue(sku.getIdSku());
		txtDescripcionSku.setValue(sku.getDescripcion());
		if (sku.getEstado() != null)
			if (sku.getEstado())
				rdoSi.setChecked(true);
			else
				rdoNo.setChecked(true);
		else {
			rdoSi.setChecked(false);
			rdoNo.setChecked(false);
		}
		txtCodigoMarca.setValue(sku.getMarca().getDescripcion());
		idMarca = sku.getMarca().getIdMarca();
		id = sku.getIdSku();
	}

}
