package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Sku;
import modelo.transacciones.ItemDegustacionPlanillaEvento;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Mensaje;

public class CDesactivar extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Listbox ltbMarcas;
	@Wire
	private Listbox ltbMarcasInactivas;
	@Wire
	private Listbox ltbProductos;
	@Wire
	private Listbox ltbProductosInactivos;
	@Wire
	private Window wdwDesactivar;
	@Wire
	private Div botoneraDesactivar;
	@Wire
	private Tab tabMarca;
	@Wire
	private Tab tabSku;
	List<Marca> marcas = new ArrayList<Marca>();
	List<Marca> marcasInactivas = new ArrayList<Marca>();
	List<Sku> productos = new ArrayList<Sku>();
	List<Sku> productosInactivos = new ArrayList<Sku>();
	Botonera botonera;
	private List<Listbox> listas = new ArrayList<Listbox>();

	@Override
	public void inicializar() throws IOException {
		listas.add(ltbProductos);
		listas.add(ltbProductosInactivos);
		listas.add(ltbMarcas);
		listas.add(ltbMarcasInactivas);
		llenarListas();
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwDesactivar);
			}

			@Override
			public void limpiar() {
				llenarListas();
			}

			@Override
			public void guardar() {
				for (Iterator<Marca> iterator = marcas.iterator(); iterator
						.hasNext();) {
					Marca objeto = (Marca) iterator.next();
					objeto.setEstado(true);
				}
				for (Iterator<Marca> iterator = marcasInactivas.iterator(); iterator
						.hasNext();) {
					Marca objeto = (Marca) iterator.next();
					objeto.setEstado(false);
				}
				for (Iterator<Sku> iterator = productos.iterator(); iterator
						.hasNext();) {
					Sku objeto = (Sku) iterator.next();
					objeto.setEstado(true);
				}
				for (Iterator<Sku> iterator = productosInactivos.iterator(); iterator
						.hasNext();) {
					Sku objeto = (Sku) iterator.next();
					objeto.setEstado(false);
				}
				servicioMarca.guardarVarias(marcas);
				servicioMarca.guardarVarias(marcasInactivas);
				servicioSku.guardarVarios(productos);
				servicioSku.guardarVarios(productosInactivos);
				if (!marcasInactivas.isEmpty()) {
					String marcasString = "";
					for (int i = 0; i < marcasInactivas.size(); i++) {
						marcasString += marcasInactivas.get(i).getDescripcion()
								+ ".";
					}
					notificarInactivacion(
							"Las siguientes MARCAS han sido inactivadas: "
									+ marcasString, valor);
				}
				if (!productosInactivos.isEmpty()) {
					String productosString = "";
					for (int i = 0; i < productosInactivos.size(); i++) {
						productosString += productosInactivos.get(i)
								.getDescripcion() + ".";
					}
					notificarInactivacion(
							"Los siguientes SKU han sido inactivados: "
									+ productosString, valor);
				}
				msj.mensajeInformacion(Mensaje.guardado);
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
				if (tabSku.isSelected())
					tabMarca.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabMarca.isSelected())
					tabSku.setSelected(true);
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraDesactivar.appendChild(botonera);
	}

	private void llenarListas() {
		marcas = servicioMarca.buscarPorEstado(true);
		ltbMarcas.setModel(new ListModelList<Marca>(marcas));
		marcasInactivas = servicioMarca.buscarPorEstado(false);
		ltbMarcasInactivas.setModel(new ListModelList<Marca>(marcasInactivas));
		productos = servicioSku.buscarPorEstado(true);
		ltbProductos.setModel(new ListModelList<Sku>(productos));
		productosInactivos = servicioSku.buscarPorEstado(false);
		ltbProductosInactivos.setModel(new ListModelList<Sku>(
				productosInactivos));
		listasMultiples();
	}

	@Listen("onClick = #pasar1Marca")
	public void derechaMarca() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		ltbMarcas.renderAll();
		List<Listitem> listItem = ltbMarcas.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Marca marca = listItem.get(i).getValue();
					marcas.remove(marca);
					marcasInactivas.add(marca);
					ltbMarcasInactivas.setModel(new ListModelList<Marca>(
							marcasInactivas));
					ltbMarcasInactivas.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMarcas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Marca")
	public void izquierdaMArca() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		ltbMarcasInactivas.renderAll();
		List<Listitem> listItem2 = ltbMarcasInactivas.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Marca marca = listItem2.get(i).getValue();
					marcasInactivas.remove(marca);
					marcas.add(marca);
					ltbMarcas.setModel(new ListModelList<Marca>(marcas));
					ltbMarcas.renderAll();
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMarcasInactivas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1Sku")
	public void derechaProdcuto() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		ltbProductos.renderAll();
		List<Listitem> listItem = ltbProductos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Sku sku = listItem.get(i).getValue();
					productos.remove(sku);
					productosInactivos.add(sku);
					ltbProductosInactivos.setModel(new ListModelList<Sku>(
							productosInactivos));
					ltbProductosInactivos.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductos.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Sku")
	public void izquierdaProducto() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		ltbProductosInactivos.renderAll();
		List<Listitem> listItem2 = ltbProductosInactivos.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Sku sku = listItem2.get(i).getValue();
					productosInactivos.remove(sku);
					productos.add(sku);
					ltbProductos.setModel(new ListModelList<Sku>(productos));
					ltbProductos.renderAll();
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosInactivos.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	private void listasMultiples() {
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

}
