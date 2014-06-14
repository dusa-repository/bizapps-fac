package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.maestros.Sku;
import modelo.seguridad.Usuario;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CEvento extends CGenerico {

	private static final long serialVersionUID = 604131753481348882L;
	@Wire
	private Window wdwEvento;
	@Wire
	private Div botoneraEvento;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabDescripcion;
	@Wire
	private Tab tabMecanica;
	@Wire
	private Tab tabDegustacion;
	@Wire
	private Tab tabVenta;
	@Wire
	private Tab tabRecursos;
	@Wire
	private Textbox txtNombreActividad;
	@Wire
	private Textbox txtRespActividad;
	@Wire
	private Textbox txtRespZona;
	@Wire
	private Datebox dtbInicio;
	@Wire
	private Datebox dtbFin;
	@Wire
	private Textbox txtCiudad;
	@Wire
	private Combobox cmbRegion;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Timebox tmbHora;
	@Wire
	private Combobox cmbMarcaSugerida;
	@Wire
	private Spinner spnPersonas;
	@Wire
	private Textbox txtContacto;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Combobox cmbNivelEconomico;
	@Wire
	private Combobox cmbTarget;
	@Wire
	private Combobox cmbMedio;
	@Wire
	private Combobox cmbVenta;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private CKeditor cdtDescripcion;
	@Wire
	private CKeditor cdtMecanica;
	@Wire
	private Listbox ltbProductosDegustacion;
	@Wire
	private Listbox ltbProductosDegustacionAgregados;
	@Wire
	private Listbox ltbProductosVenta;
	@Wire
	private Listbox ltbProductosVentaAgregados;
	@Wire
	private Listbox ltbRecursos;
	@Wire
	private Listbox ltbRecursosAgregados;
	@Wire
	private Div catalogoEvento;
	Catalogo<PlanillaEvento> catalogo;
	ListModelList<Marca> marcas;
	List<Sku> itemsDegustacion = new ArrayList<Sku>();
	List<Sku> itemsEstimacion = new ArrayList<Sku>();
	List<Recurso> recursos = new ArrayList<Recurso>();
	List<ItemDegustacionPlanillaEvento> itemsDegustacionAgregados = new ArrayList<ItemDegustacionPlanillaEvento>();
	List<ItemEstimadoPlanillaEvento> itemsEstimacionAgregados = new ArrayList<ItemEstimadoPlanillaEvento>();
	List<RecursoPlanillaEvento> recursosAgregados = new ArrayList<RecursoPlanillaEvento>();
	List<Listbox> listas = new ArrayList<Listbox>();
	long id = 0;

	@Override
	public void inicializar() throws IOException {
		System.out.println("Valor"+valor);
		cargarCombos();
		llenarListas();
		txtRespActividad.setValue(nombreUsuarioSesion());
		txtRespZona.setValue(usuarioSesion(nombreUsuarioSesion())
				.getSupervisor());
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwEvento);
			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				txtCiudad.setValue("");
				txtContacto.setValue("");
				txtCosto.setValue(null);
				txtDireccion.setValue("");
				txtNombreActividad.setValue("");
				txtTelefono.setValue("");
				cmbMedio.setValue("");
				cmbMarcaSugerida.setValue("");
				cmbRegion.setValue("");
				cmbNivelEconomico.setValue("");
				cmbTarget.setValue("");
				cmbVenta.setValue("");
				spnPersonas.setValue(0);
				cdtDescripcion.setValue("");
				cdtMecanica.setValue("");
				dtbFin.setValue(fecha);
				dtbInicio.setValue(fecha);
				tabDatos.setSelected(true);
				tmbHora.setValue(fecha);
				id = 0;
				llenarListas();
			}

			@Override
			public void guardar() {
				if (validar()) {
					guardarDatos("En Edicion");
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar la Planilla?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										PlanillaEvento planilla = servicioPlanillaEvento.buscar(id);
										servicioBitacoraEvento.eliminarPorPlanilla(planilla);
										servicioRecursoPlanillaEvento.limpiar(planilla);
										servicioItemDegustacionPlanillaEvento.limpiar(planilla);
										servicioItemEstimadoPlanillaEvento.limpiar(planilla);
										servicioPlanillaEvento.eliminar(id);
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
				if (tabDescripcion.isSelected())
					tabDatos.setSelected(true);
				if (tabMecanica.isSelected())
					tabDescripcion.setSelected(true);
				if (tabDegustacion.isSelected())
					tabMecanica.setSelected(true);
				if (tabVenta.isSelected())
					tabDegustacion.setSelected(true);
				if (tabRecursos.isSelected())
					tabVenta.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabVenta.isSelected())
					tabRecursos.setSelected(true);
				if (tabDegustacion.isSelected())
					tabVenta.setSelected(true);
				if (tabMecanica.isSelected())
					tabDegustacion.setSelected(true);
				if (tabDescripcion.isSelected())
					tabMecanica.setSelected(true);
				if (tabDatos.isSelected())
					tabDescripcion.setSelected(true);
			}

			@Override
			public void enviar() {
				if (validar()) {
					guardarDatos("Pendiente");
					msj.mensajeInformacion(Mensaje.enviado);
					limpiar();
				}
			}
		};
		botoneraEvento.appendChild(botonera);
	}

	public void guardarDatos(String string) {
		if (id != 0) {
			PlanillaEvento planilla = servicioPlanillaEvento.buscar(id);
			servicioRecursoPlanillaEvento.limpiar(planilla);
			servicioItemDegustacionPlanillaEvento.limpiar(planilla);
			servicioItemEstimadoPlanillaEvento.limpiar(planilla);
		}
		String ciudad, venta, contacto, direccion, mail, nombreActividad, telefono, region, medio, nivel, edadTarget, descripcion, mecanica;
		edadTarget = cmbTarget.getValue();
		venta = cmbVenta.getValue();
		region = cmbRegion.getSelectedItem().getContext();
		medio = cmbMedio.getSelectedItem().getContext();
		nivel = cmbNivelEconomico.getSelectedItem().getContext();
		descripcion = cdtDescripcion.getValue();
		mecanica = cdtMecanica.getValue();
		ciudad = txtCiudad.getValue();
		contacto = txtContacto.getValue();
		direccion = txtDireccion.getValue();
		mail = txtCiudad.getValue();
		nombreActividad = txtNombreActividad.getValue();
		telefono = txtTelefono.getValue();
		Usuario usuario = usuarioSesion(nombreUsuarioSesion());
		Marca marca = servicioMarca.buscar(cmbMarcaSugerida.getSelectedItem()
				.getContext());
		Date fechaI = dtbInicio.getValue();
		Date fechaF = dtbFin.getValue();
		int personas = spnPersonas.getValue();
		double costo = txtCosto.getValue();
		Date horaEventos = tmbHora.getValue();
		String horaEvento = df.format(horaEventos);
		Timestamp fechaInicio = new Timestamp(fechaI.getTime());
		Timestamp fechaFin = new Timestamp(fechaF.getTime());
		PlanillaEvento planillaEvento = new PlanillaEvento(id, usuario, marca,
				nombreActividad, fechaInicio, fechaFin, ciudad, region,
				horaEvento, direccion, personas, contacto, telefono, nivel,
				edadTarget, medio, venta, costo, descripcion, mecanica,
				fechaHora, horaAuditoria, nombreUsuarioSesion(), string,
				usuario.getZona().getDescripcion());
		servicioPlanillaEvento.guardar(planillaEvento);
		if (id != 0)
			planillaEvento = servicioPlanillaEvento.buscar(id);
		else
			planillaEvento = servicioPlanillaEvento.buscarUltima();
		guardarBitacora(planillaEvento, string);
		guardarItemsDegustacion(planillaEvento);
		guardarItemsEstimados(planillaEvento);
		guardarRecursos(planillaEvento);
	}

	private void guardarItemsEstimados(PlanillaEvento planillaEvento) {
		List<ItemEstimadoPlanillaEvento> recursosPlanilla = new ArrayList<ItemEstimadoPlanillaEvento>();
		for (int i = 0; i < ltbProductosVentaAgregados.getItemCount(); i++) {
			Listitem listItem = ltbProductosVentaAgregados.getItemAtIndex(i);
			Integer estimado = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			String skyId = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			Sku sku = servicioSku.buscar(skyId);
			ItemEstimadoPlanillaEvento planillaItem = new ItemEstimadoPlanillaEvento(
					sku, planillaEvento, estimado);
			recursosPlanilla.add(planillaItem);
		}
		servicioItemEstimadoPlanillaEvento.guardar(recursosPlanilla);
	}

	private void guardarItemsDegustacion(PlanillaEvento planillaEvento) {
		List<ItemDegustacionPlanillaEvento> recursosPlanilla = new ArrayList<ItemDegustacionPlanillaEvento>();
		for (int i = 0; i < ltbProductosDegustacionAgregados.getItemCount(); i++) {
			Listitem listItem = ltbProductosDegustacionAgregados
					.getItemAtIndex(i);
			Integer solicitado = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Integer aprobado = ((Spinner) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String skyId = ((Textbox) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Sku sku = servicioSku.buscar(skyId);
			ItemDegustacionPlanillaEvento planillaItem = new ItemDegustacionPlanillaEvento(
					sku, planillaEvento, solicitado, aprobado);
			recursosPlanilla.add(planillaItem);
		}
		servicioItemDegustacionPlanillaEvento.guardar(recursosPlanilla);
	}

	private void guardarRecursos(PlanillaEvento planillaEvento) {
		List<RecursoPlanillaEvento> recursosPlanilla = new ArrayList<RecursoPlanillaEvento>();
		for (int i = 0; i < ltbRecursosAgregados.getItemCount(); i++) {
			Listitem listItem = ltbRecursosAgregados.getItemAtIndex(i);
			String idMarca = ((Combobox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getSelectedItem().getContext();
			Marca marca = servicioMarca.buscar(idMarca);
			Integer solicitado = ((Spinner) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			Integer aprobado = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			long recursoId = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			Recurso recurso = servicioRecurso.buscar(recursoId);
			RecursoPlanillaEvento recursoPlanilla = new RecursoPlanillaEvento(
					recurso, planillaEvento, marca, solicitado, aprobado);
			recursosPlanilla.add(recursoPlanilla);
		}
		servicioRecursoPlanillaEvento.guardar(recursosPlanilla);
	}

	private void guardarBitacora(PlanillaEvento planillaEvento, String string) {
		BitacoraEvento bitacora = new BitacoraEvento(0, planillaEvento, string,
				fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion());
		servicioBitacoraEvento.guardar(bitacora);
	}

	protected boolean validar() {
		if (txtCiudad.getText().compareTo("") == 0
				|| txtContacto.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtDireccion.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0
				|| cmbMedio.getText().compareTo("") == 0
				|| cmbMarcaSugerida.getText().compareTo("") == 0
				|| cmbRegion.getText().compareTo("") == 0
				|| cmbNivelEconomico.getText().compareTo("") == 0
				|| cmbTarget.getText().compareTo("") == 0
				|| cmbVenta.getText().compareTo("") == 0
				|| cdtDescripcion.getValue().compareTo("") == 0
				|| cdtMecanica.getValue().compareTo("") == 0
				|| dtbFin.getText().compareTo("") == 0
				|| dtbInicio.getText().compareTo("") == 0
				|| tmbHora.getText().compareTo("") == 0
				|| spnPersonas.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

	private void llenarListas() {
		PlanillaEvento planilla = servicioPlanillaEvento.buscar(id);

		itemsDegustacion = servicioSku.buscarDisponiblesDegustacion(planilla);
		ltbProductosDegustacion.setModel(new ListModelList<Sku>(
				itemsDegustacion));
		itemsDegustacionAgregados = servicioItemDegustacionPlanillaEvento
				.buscarPorPlanilla(planilla);
		ltbProductosDegustacionAgregados
				.setModel(new ListModelList<ItemDegustacionPlanillaEvento>(
						itemsDegustacionAgregados));

		itemsEstimacion = servicioSku.buscarDisponiblesEstimacion(planilla);
		ltbProductosVenta.setModel(new ListModelList<Sku>(itemsEstimacion));
		itemsEstimacionAgregados = servicioItemEstimadoPlanillaEvento
				.buscarPorPlanilla(planilla);
		ltbProductosVentaAgregados
				.setModel(new ListModelList<ItemEstimadoPlanillaEvento>(
						itemsEstimacionAgregados));

		recursos = servicioRecurso.buscarDisponibles(planilla);
		ltbRecursos.setModel(new ListModelList<Recurso>(recursos));
		recursosAgregados = servicioRecursoPlanillaEvento
				.buscarPorPlanilla(planilla);
		ltbRecursosAgregados.setModel(new ListModelList<RecursoPlanillaEvento>(
				recursosAgregados));

		listasMultiples();
	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
			listas.add(ltbProductosDegustacion);
			listas.add(ltbProductosDegustacionAgregados);
			listas.add(ltbProductosVenta);
			listas.add(ltbProductosVentaAgregados);
			listas.add(ltbRecursos);
			listas.add(ltbRecursosAgregados);
		}
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "11");
		cmbRegion.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "04");
		cmbNivelEconomico.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "12");
		cmbMedio.setModel(new ListModelList<F0005>(udc));
	}
	
	@Listen("onClick = #btnBuscarPlanillas")
	public void buscarCatalogoPropio() {
		final List<PlanillaEvento> listPlanilla = servicioPlanillaEvento
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaEvento>(catalogoEvento,
				"PlanillaEvento", listPlanilla, true, "Nombre Actividad",
				"Ciudad", "Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaEvento> buscar(List<String> valores) {

				List<PlanillaEvento> lista = new ArrayList<PlanillaEvento>();

				for (PlanillaEvento planillaEvento : listPlanilla) {
					if (planillaEvento.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0))
							&& planillaEvento.getCiudad().toLowerCase()
									.startsWith(valores.get(1))
							&& planillaEvento.getMarca().getDescripcion()
									.toLowerCase().startsWith(valores.get(2))
							&& String
									.valueOf(
											planillaEvento.getFechaAuditoria()
													.getTime()).toLowerCase()
									.startsWith(valores.get(3))) {
						lista.add(planillaEvento);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaEvento planillaEvento) {
				String[] registros = new String[4];
				registros[0] = planillaEvento.getNombreActividad();
				registros[1] = planillaEvento.getCiudad();
				registros[2] = planillaEvento.getMarca().getDescripcion();
				registros[3] = String.valueOf(planillaEvento.getFechaAuditoria()
						.getTime());
				return registros;
			}
		};
		catalogo.setParent(catalogoEvento);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoEvento")
	public void seleccionPropia() {
		llenarListas();
		PlanillaEvento planilla = catalogo.objetoSeleccionadoDelCatalogo();
		txtCiudad.setValue(planilla.getCiudad());
		txtContacto.setValue(planilla.getPersonaContacto());
		txtCosto.setValue(planilla.getCosto());
		txtDireccion.setValue(planilla.getDireccion());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		txtTelefono.setValue(planilla.getTelefono());
		F0005 f05 = servicioF0005.buscar("00", "11", planilla.getRegion());
		cmbRegion.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "12", planilla.getMedio());
		cmbMedio.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "04", planilla.getNivel());
		cmbNivelEconomico.setValue(f05.getDrdl01());
		cmbMarcaSugerida.setValue(planilla.getMarca().getDescripcion());
		cmbTarget.setValue(planilla.getEdadTarget());
		cmbVenta.setValue(planilla.getVenta());
		spnPersonas.setValue(planilla.getPersonas());
		cdtDescripcion.setValue(planilla.getDescripcion());
		cdtMecanica.setValue(planilla.getMecanica());
		dtbInicio.setValue(planilla.getFechaDesde());
		dtbFin.setValue(planilla.getFechaHasta());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanillaEvento();
		try {
			tmbHora.setValue(df.parse(planilla.getHoraEvento()));
		} catch (WrongValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		llenarListas();
		catalogo.setParent(null);
	}
	
	@Listen("onClick = #pasar1Degustacion")
	public void derechaPtDegustacion() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbProductosDegustacion.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Sku sku = listItem.get(i).getValue();
					itemsDegustacion.remove(sku);
					ItemDegustacionPlanillaEvento itemPlanilla = new ItemDegustacionPlanillaEvento();
					itemPlanilla.setSku(sku);
					itemsDegustacionAgregados.add(itemPlanilla);
					ltbProductosDegustacionAgregados
							.setModel(new ListModelList<ItemDegustacionPlanillaEvento>(
									itemsDegustacionAgregados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosDegustacion.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}
	
	@Listen("onClick = #pasar2Degustacion")
	public void izquierdaPtDegustacion() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbProductosDegustacionAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ItemDegustacionPlanillaEvento itemPlanilla = listItem2.get(i).getValue();
					itemsDegustacionAgregados.remove(itemPlanilla);
					itemsDegustacion.add(itemPlanilla.getSku());
					ltbProductosDegustacion.setModel(new ListModelList<Sku>(itemsDegustacion));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosDegustacionAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}
	
	@Listen("onClick = #pasar1Venta")
	public void derechaPt() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbProductosVenta.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Sku sku = listItem.get(i).getValue();
					itemsEstimacion.remove(sku);
					ItemEstimadoPlanillaEvento itemPlanilla = new ItemEstimadoPlanillaEvento();
					itemPlanilla.setSku(sku);
					itemsEstimacionAgregados.add(itemPlanilla);
					ltbProductosVentaAgregados
							.setModel(new ListModelList<ItemEstimadoPlanillaEvento>(
									itemsEstimacionAgregados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosVenta.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}
	
	@Listen("onClick = #pasar2Venta")
	public void izquierdaPt() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbProductosVentaAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ItemEstimadoPlanillaEvento itemPlanilla = listItem2.get(i).getValue();
					itemsEstimacionAgregados.remove(itemPlanilla);
					itemsEstimacion.add(itemPlanilla.getSku());
					ltbProductosVenta.setModel(new ListModelList<Sku>(itemsEstimacion));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosVentaAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1Recurso")
	public void derechaRecurso() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbRecursos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Recurso recurso = listItem.get(i).getValue();
					recursos.remove(recurso);
					RecursoPlanillaEvento recursoPlanilla = new RecursoPlanillaEvento();
					recursoPlanilla.setRecurso(recurso);
					recursosAgregados.add(recursoPlanilla);
					ltbRecursosAgregados
							.setModel(new ListModelList<RecursoPlanillaEvento>(
									recursosAgregados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbRecursos.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Recurso")
	public void izquierdaRecurso() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbRecursosAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					RecursoPlanillaEvento recursoPlanilla = listItem2.get(i)
							.getValue();
					recursosAgregados.remove(recursoPlanilla);
					recursos.add(recursoPlanilla.getRecurso());
					ltbRecursos.setModel(new ListModelList<Recurso>(recursos));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbRecursosAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}
	
	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}

}
