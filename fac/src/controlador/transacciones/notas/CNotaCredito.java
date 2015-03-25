package controlador.transacciones.notas;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Aliado;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.pk.DetalleNotaCreditoId;
import modelo.seguridad.Usuario;
import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.maestros.CGenerico;

public class CNotaCredito extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwNotaCredito;
	@Wire
	private Textbox txtAliado;
	@Wire
	private Label lblNombre;
	@Wire
	private Label lblZona;
	@Wire
	private Datebox dtbFecha;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbActividad;
	@Wire
	private Textbox txtDescripcion;
	@Wire
	private Spinner spnCantidad;
	@Wire
	private Doublespinner spnCostoLinea;
	@Wire
	private Div botoneraNota;
	@Wire
	private Div catalogoNota;
	@Wire
	private Div divCatalogoAliado;
	@Wire
	private Listbox ltbLista;
	Botonera botonera;
	ListModelList<Marca> marcas;
	Catalogo<NotaCredito> catalogo;
	Catalogo<Aliado> catalogoAliado;
	List<DetalleNotaCredito> listaDetalle = new ArrayList<DetalleNotaCredito>();
	long contador = 0;
	boolean editar = true;
	private Long id = null;
	private Long idAliado = null;

	@Override
	public void inicializar() throws IOException {
		txtAliado.setFocus(true);
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "17");
		cmbActividad.setModel(new ListModelList<F0005>(udc));
		dtbFecha.setValue(fechaHora);
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwNotaCredito);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {
					guardarDatos("Pendiente");
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					salir();
				}
			}

			@Override
			public void enviar() {
				if (validar()) {
					guardarDatos("Enviada");
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					salir();
				}
			}

			@Override
			public void eliminar() {
				if (id != null) {
					if (editar)
						Messagebox
								.show("¿Esta Seguro de Eliminar la Planilla?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													NotaCredito planilla = servicioNotaCredito
															.buscar(id);
													servicioDetalleCredito
															.limpiar(planilla);
													servicioNotaCredito
															.eliminar(id);
													limpiar();
													msj.mensajeInformacion(Mensaje.eliminado);
												}
											}
										});
					else
						msj.mensajeAlerta("No puede eliminar Planillas que han sido enviadas");
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
		botoneraNota.appendChild(botonera);
	}

	protected void limpiarCampos() {
		botonera.getChildren().get(0).setVisible(true);
		botonera.getChildren().get(3).setVisible(true);
		botonera.getChildren().get(8).setVisible(true);
		txtAliado.setValue("");
		txtCosto.setValue(0);
		limpiarCamposItem();
		limpiarColores(txtAliado, txtCosto, txtDescripcion, cmbActividad,
				cmbMarca, spnCantidad, spnCostoLinea);
		lblNombre.setValue("");
		lblZona.setValue("");
		editar = true;
		id = null;
		idAliado = null;
		listaDetalle.clear();
		ltbLista.renderAll();
		ltbLista.getItems().clear();
	}

	protected void guardarDatos(String estado) {
		if (id != null) {
			NotaCredito planilla = servicioNotaCredito.buscar(id);
			servicioDetalleCredito.limpiar(planilla);
		} else
			id = (long) 0;
		Aliado aliado = servicioAliado.buscar(idAliado);
		NotaCredito nota = new NotaCredito(id, aliado, new Timestamp(dtbFecha
				.getValue().getTime()), fechaHora, horaAuditoria,
				nombreUsuarioSesion(), valor, txtCosto.getValue(), estado);
		servicioNotaCredito.guardar(nota);

		if (id != 0)
			nota = servicioNotaCredito.buscar(id);
		else
			nota = servicioNotaCredito.buscarUltima();

		DetalleNotaCreditoId clave = new DetalleNotaCreditoId();
		clave.setNotaCredito(nota);
		Integer contador = 0;
		for (int i = 0; i < listaDetalle.size(); i++) {
			contador++;
			clave.setIdDetalleCredito(contador.longValue());
			DetalleNotaCredito detalle = listaDetalle.get(i);
			detalle.setId(clave);
			detalle.setEstado(estado);
			detalle.setFechaCreacion(fechaHora);
			detalle.setFechaAuditoria(fechaHora);
			detalle.setHoraAuditoria(horaAuditoria);
			detalle.setUsuarioAuditoria(nombreUsuarioSesion());
			servicioDetalleCredito.guardar(detalle);
		}
		Usuario usuario = servicioUsuario
				.buscarUsuarioPorNombre(nombreUsuarioSesion());
		if (estado.equals("Enviada")) {
			F0005 udc = servicioF0005.buscar("00", "20", "00");
			if (udc == null)
				msj.mensajeAlerta("No se ha enviado el correo debido a que no se ha configurado un correo para el grupo de nota de credito");
			else
				enviarEmailNota(valor, nombreUsuarioSesion(),
						nota.getIdNotaCredito(), usuario.getMail(),
						udc.getDrdl01());
		}

	}

	protected boolean validar() {
		if (lblNombre.getValue().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0) {
			aplicarColores(txtAliado, txtCosto);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!editar) {
				msj.mensajeAlerta("No puede Editar notas que ya han sido enviadas");
				return false;
			} else
				return true;
		}
	}

	public void buscarCatalogoPropio() {
		final List<NotaCredito> listAliado = servicioNotaCredito
				.buscarTodosOrdenadosPorTipo(valor);
		catalogo = new Catalogo<NotaCredito>(catalogoNota,
				"Catalogo de Aliados", listAliado, true, "Aliado",
				"Codigo Aliado", "Zona", "Fecha", "Estado") {

			@Override
			protected List<NotaCredito> buscar(List<String> valores) {

				List<NotaCredito> lista = new ArrayList<NotaCredito>();

				for (NotaCredito aliado : listAliado) {
					String codigo = "";
					if (aliado.getAliado().getCodigo() != null)
						codigo = aliado.getAliado().getCodigo();
					if (aliado.getAliado().getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& codigo.toLowerCase().contains(
									valores.get(1).toLowerCase())
							&& aliado.getAliado().getZona().getDescripcion()
									.toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& formatoFecha.format(aliado.getFechaNota())
									.toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& aliado.getEstado().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						lista.add(aliado);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(NotaCredito aliado) {
				String codigo = "";
				if (aliado.getAliado().getCodigo() != null)
					codigo = aliado.getAliado().getCodigo();
				String[] registros = new String[5];
				registros[0] = aliado.getAliado().getNombre();
				registros[1] = codigo;
				registros[2] = aliado.getAliado().getZona().getDescripcion();
				registros[3] = formatoFecha.format(aliado.getFechaNota());
				registros[4] = aliado.getEstado();
				return registros;
			}
		};
		catalogo.setParent(catalogoNota);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoNota")
	public void seleccionPropia() {
		NotaCredito aliado = catalogo.objetoSeleccionadoDelCatalogo();
		limpiarCampos();
		llenarCamposPropios(aliado);
		catalogo.setParent(null);
	}

	@Listen("onClick = #btnBuscarAliado")
	public void buscarCatalogoAjeno() {
		final List<Aliado> listZona = servicioAliado.buscarTodosOrdenados();
		catalogoAliado = new Catalogo<Aliado>(divCatalogoAliado,
				"Catalogo de Aliados", listZona, true, "Codigo", "Nombre",
				"Zona") {

			@Override
			protected List<Aliado> buscar(List<String> valores) {

				List<Aliado> lista = new ArrayList<Aliado>();

				for (Aliado aliado : listZona) {
					String codigo = "";
					if (aliado.getCodigo() != null)
						codigo = aliado.getCodigo();
					if (codigo.toLowerCase().contains(
							valores.get(0).toLowerCase())
							&& aliado.getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& aliado.getZona().getDescripcion().toLowerCase()
									.contains(valores.get(2).toLowerCase())) {
						lista.add(aliado);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Aliado aliado) {
				String codigo = "";
				if (aliado.getCodigo() != null)
					codigo = aliado.getCodigo();
				String[] registros = new String[3];
				registros[0] = codigo;
				registros[1] = aliado.getNombre();
				registros[2] = aliado.getZona().getDescripcion();
				return registros;
			}
		};
		catalogoAliado.setParent(divCatalogoAliado);
		catalogoAliado.doModal();
	}

	@Listen("onSeleccion = #divCatalogoAliado")
	public void seleccionAjena() {
		Aliado zona = catalogoAliado.objetoSeleccionadoDelCatalogo();
		llenarCamposAjenos(zona);
		catalogoAliado.setParent(null);
	}

	@Listen("onChange = #txtAliado; onOK = #txtAliado")
	public void buscarPorNombrePropio() {
		Aliado aliado = servicioAliado.buscarPorCodigo(txtAliado.getValue());
		if (aliado != null)
			llenarCamposAjenos(aliado);
		else {
			txtAliado.setValue("");
			lblNombre.setValue("");
			lblZona.setValue("");
			txtAliado.setFocus(true);
			msj.mensajeAlerta(Mensaje.noHayRegistros);
		}
	}

	public void llenarCamposAjenos(Aliado aliado) {
		idAliado = aliado.getIdAliado();
		txtAliado.setValue(aliado.getCodigo());
		lblNombre.setValue(aliado.getNombre());
		lblZona.setValue(aliado.getZona().getDescripcion());
	}

	public void llenarCamposPropios(NotaCredito nota) {
		id = nota.getIdNotaCredito();
		llenarCamposAjenos(nota.getAliado());
		txtCosto.setValue(nota.getCosto());
		dtbFecha.setValue(nota.getFechaNota());
		if (!nota.getEstado().contains("Pendiente")) {
			botonera.getChildren().get(0).setVisible(false);
			botonera.getChildren().get(3).setVisible(false);
			botonera.getChildren().get(8).setVisible(false);
			editar = false;
		}
		listaDetalle = servicioDetalleCredito.buscarPorNota(nota);
		ltbLista.setModel(new ListModelList<DetalleNotaCredito>(listaDetalle));
		ltbLista.renderAll();
	}

	private void limpiarCamposItem() {
		spnCantidad.setValue(0);
		spnCostoLinea.setValue((double) 0);
		txtDescripcion.setValue("");
		cmbActividad.setValue("");
		cmbMarca.setValue("");
	}

	private boolean validarItems() {
		if (cmbMarca.getText().compareTo("") == 0
				|| cmbActividad.getText().compareTo("") == 0
				|| dtbFecha.getText().compareTo("") == 0
				|| txtDescripcion.getText().compareTo("") == 0) {
			aplicarColores(cmbMarca, cmbActividad, txtDescripcion);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnAgregar")
	public void annadirLista() {
		if (validarItems()) {
			Marca marca = servicioMarca.buscar(cmbMarca.getSelectedItem()
					.getContext());
			DetalleNotaCredito objeto = new DetalleNotaCredito(null, marca,
					cmbActividad.getValue(), txtDescripcion.getValue(),
					spnCantidad.getValue(), spnCostoLinea.getValue(),
					"Pendiente", "", fechaHora, null, null, fechaHora,
					horaAuditoria, nombreUsuarioSesion());
			listaDetalle.add(objeto);
			ltbLista.setModel(new ListModelList<DetalleNotaCredito>(
					listaDetalle));
			ltbLista.renderAll();
			limpiarCamposItem();
			calcularCostoTotal();
		} else
			msj.mensajeError("Debe llenar todos los campos de la seccion de detalle");
	}

	private void calcularCostoTotal() {
		double costo = 0;
		for (int i = 0; i < listaDetalle.size(); i++) {
			costo = costo + listaDetalle.get(i).getCosto();
		}
		txtCosto.setValue(costo);
	}

	@Listen("onClick = #btnVer")
	public void seleccionarItemLista() {
		if (ltbLista.getItemCount() != 0) {
			if (ltbLista.getSelectedItems().size() == 1) {
				Listitem listItem = ltbLista.getSelectedItem();
				DetalleNotaCredito modelo = listItem.getValue();
				spnCantidad.setValue(modelo.getBotellas());
				txtDescripcion.setValue(modelo.getDescripcion());
				cmbActividad.setValue(modelo.getTipoActividad());
				spnCostoLinea.setValue(modelo.getCosto());
				cmbMarca.setValue(modelo.getMarca().getDescripcion());
				ltbLista.removeItemAt(listItem.getIndex());
				listaDetalle.remove(modelo);
				ltbLista.renderAll();
				calcularCostoTotal();
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	@Listen("onClick = #btnRemover")
	public void removerItem() {
		if (ltbLista.getItemCount() != 0) {
			if (ltbLista.getSelectedItems().size() == 1) {
				Listitem listItem = ltbLista.getSelectedItem();
				DetalleNotaCredito modelo = listItem.getValue();
				int i = listItem.getIndex();
				ltbLista.removeItemAt(i);
				listaDetalle.remove(modelo);
				ltbLista.renderAll();
				calcularCostoTotal();
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}
}
