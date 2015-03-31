package controlador.transacciones.notas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import componente.Botonera;
import componente.Mensaje;
import modelo.maestros.Marca;
import modelo.seguridad.Arbol;
import modelo.transacciones.notas.ConfiguracionMarca;
import modelo.transacciones.notas.DetalleNotaCredito;
import controlador.maestros.CGenerico;

public class CConfigurarMarca extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwConfigurarMarca;
	@Wire
	private Div botoneraConfigurar;
	@Wire
	private Listbox ltbLista;
	@Wire
	private Intbox txtCantidad;
	@Wire
	private Doublebox txtBs;
	Botonera botonera;
	List<ConfiguracionMarca> listaDetalle = new ArrayList<ConfiguracionMarca>();
	private int cambio;

	@Override
	public void inicializar() throws IOException {
		actualizarLista();
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwConfigurarMarca);
			}

			@Override
			public void limpiar() {
				actualizarLista();
			}

			@Override
			public void guardar() {
				if (validar()) {
					guardarConfiguracion();
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					salir();
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
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraConfigurar.appendChild(botonera);
	}

	private void actualizarLista() {
		listaDetalle.clear();
		listaDetalle = servicioConfiguracionMarca.buscar(valor);
		ltbLista.setModel(new ListModelList<ConfiguracionMarca>(listaDetalle));
		ltbLista.setMultiple(false);
		ltbLista.setMultiple(true);
		ltbLista.setCheckmark(false);
		ltbLista.setCheckmark(true);
		ltbLista.renderAll();
		if (!listaDetalle.isEmpty()) {
			Double costo = (double) 0;
			Integer cantidad = 0;
			for (int i = 0; i < listaDetalle.size(); i++) {
				cantidad = cantidad + listaDetalle.get(i).getCajas();
				costo = costo + listaDetalle.get(i).getCosto();
				String id = listaDetalle.get(i).getId().getMarca().getIdMarca();
				if (listaDetalle.get(i).getCosto() != 0) {
					for (int j = 0; j < ltbLista.getItemCount(); j++) {
						Listitem listItem = ltbLista.getItemAtIndex(j);
						ConfiguracionMarca a = listItem.getValue();
						String id2 = a.getId().getMarca().getIdMarca();
						if (id.equals(id2)) {
							listItem.setSelected(true);
							j = ltbLista.getItemCount();
						}
					}
				}
			}
			txtBs.setValue(costo);
			txtCantidad.setValue(cantidad);
		}
	}

	protected boolean validar() {
		if (ltbLista.getItemCount() == 0) {
			msj.mensajeAlerta("La lista esta vacia");
			return false;
		} else {
			boolean errorFaltante = false;
			boolean errorSobrante = false;
			for (int i = 0; i < ltbLista.getItemCount(); i++) {
				Listitem listItem = ltbLista.getItemAtIndex(i);
				if (listItem.isSelected()) {
					Double valor = ((Doublebox) ((listItem.getChildren().get(3)))
							.getFirstChild()).getValue();
					if (valor == null || valor.equals(0.0)) {
						errorFaltante = true;
						i = ltbLista.getItemCount();
					}
				} else {
					Double valor = ((Doublebox) ((listItem.getChildren().get(3)))
							.getFirstChild()).getValue();
					if (valor != null)
						if (!valor.equals(0.0)) {
							errorSobrante = true;
							i = ltbLista.getItemCount();
						}
				}
			}
			if (errorFaltante) {
				msj.mensajeAlerta("Si ha marcado una marca (debido a que pertenece al Pote MK) debe indicar su valor en Bs");
				return false;
			}
			if (errorSobrante) {
				msj.mensajeAlerta("Si la marca no pertenece al Pote MK entonces no puede tener valor en la columna de Monto en Bs");
				return false;
			}
			return true;
		}
	}

	protected void guardarConfiguracion() {
		ltbLista.renderAll();
		listaDetalle.clear();
		for (int i = 0; i < ltbLista.getItemCount(); i++) {
			Listitem listItem = ltbLista.getItemAtIndex(i);
			ConfiguracionMarca objeto = listItem.getValue();
			Integer cantidad = ((Intbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			Double porcentajeCantidad = ((Doublebox) ((listItem.getChildren()
					.get(4))).getFirstChild()).getValue();
			Double valor = (double) 0;
			Double porcentajeCosto = (double) 0;
			if (listItem.isSelected()) {
				valor = ((Doublebox) ((listItem.getChildren().get(3)))
						.getFirstChild()).getValue();
				porcentajeCosto = ((Doublebox) ((listItem.getChildren().get(5)))
						.getFirstChild()).getValue();
			}
			objeto.setCajas(cantidad);
			objeto.setCosto(valor);
			objeto.setPorcentajePlan(porcentajeCantidad);
			objeto.setPorcentajeCosto(porcentajeCosto);
			objeto.setFechaAuditoria(fechaHora);
			objeto.setHoraAuditoria(horaAuditoria);
			objeto.setUsuarioAuditoria(nombreUsuarioSesion());
			listaDetalle.add(objeto);
		}
		servicioConfiguracionMarca.guardarVarias(listaDetalle);
	}

	public int getCambio() {
		if (ltbLista.getItemCount() != 0) {
			ltbLista.renderAll();
			double bsTotal = 0;
			Integer cantidadTotal = 0;
			for (int i = 0; i < ltbLista.getItemCount(); i++) {
				Listitem listItem = ltbLista.getItemAtIndex(i);
				Integer cantidad = ((Intbox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue();
				if (cantidad == null) {
					cantidad = 0;
					((Intbox) ((listItem.getChildren().get(2))).getFirstChild())
							.setValue(0);
				}
				Double precioUnitario = ((Doublebox) ((listItem.getChildren()
						.get(3))).getFirstChild()).getValue();
				if (precioUnitario == null) {
					precioUnitario = (double) 0;
					((Doublebox) ((listItem.getChildren().get(3)))
							.getFirstChild()).setValue(0);
				}
				if (!precioUnitario.equals(0.0))
					listItem.setSelected(true);
				cantidadTotal = cantidadTotal + cantidad;
				bsTotal = bsTotal + precioUnitario;
			}
			for (int i = 0; i < ltbLista.getItemCount(); i++) {
				Listitem listItem = ltbLista.getItemAtIndex(i);
				Integer cantidad = ((Intbox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue();
				if (cantidad == null) {
					cantidad = 0;
					((Intbox) ((listItem.getChildren().get(2))).getFirstChild())
							.setValue(0);
				}
				Double precioUnitario = ((Doublebox) ((listItem.getChildren()
						.get(3))).getFirstChild()).getValue();
				if (precioUnitario == null) {
					precioUnitario = (double) 0;
					((Doublebox) ((listItem.getChildren().get(3)))
							.getFirstChild()).setValue(0);
				}
				if (cantidadTotal > 0)
					((Doublebox) ((listItem.getChildren().get(4)))
							.getFirstChild()).setValue((100 * cantidad
							.doubleValue()) / cantidadTotal.doubleValue());
				if (bsTotal > 0)
					((Doublebox) ((listItem.getChildren().get(5)))
							.getFirstChild()).setValue((100 * precioUnitario)
							/ bsTotal);
			}
			txtCantidad.setValue(cantidadTotal);
			txtBs.setValue(bsTotal);
		}
		return cambio;
	}

}
