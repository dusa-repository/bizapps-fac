package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraEvento;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.maestros.Sku;
import modelo.pk.ItemDegustacionPlanillaEventoId;
import modelo.pk.ItemEstimadoPlanillaEventoId;
import modelo.pk.RecursoPlanillaEventoId;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.RecursoPlanillaEvento;

import org.zkforge.ckez.CKeditor;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Image;
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
import componente.Validador;

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
	boolean inbox = false;
	String estadoInbox = "";
	String tipoInbox = "";
	Botonera botonera;
	Usuario usuarioEditador = new Usuario();
	@Wire
	private Image imagenSi;
	@Wire
	private Image imagenNo;

	CSolicitud control = new CSolicitud();
	List<PlanillaGenerica> listaGenerica = new ArrayList<PlanillaGenerica>();
	PlanillaGenerica planillaGenerica = new PlanillaGenerica();
	Catalogo<PlanillaGenerica> catalogoGenerico;
	Timestamp fechaInbox;

	@Override
	public void inicializar() throws IOException {
		dtbFin.setValue(fecha);
		dtbInicio.setValue(fecha);
		txtNombreActividad.setFocus(true);
		txtRespActividad.setValue(nombreUsuarioSesion());
		txtRespZona.setValue(usuarioSesion(nombreUsuarioSesion())
				.getSupervisor());
		boolean editar = false;
		List<Grupo> grupos = servicioGrupo
				.buscarGruposUsuario(usuarioSesion(nombreUsuarioSesion()));
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getNombre().equals("TRADE MARKETING")
					|| grupos.get(i).getNombre().equals("MARCA")) {
				i = grupos.size();
				editar = true;
			}
		}
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwEvento);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				dtbFin.setConstraint("no past");
				dtbInicio.setConstraint("no past");
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
				limpiarColores(txtCiudad, txtContacto, txtCosto, txtDireccion,
						cmbRegion, txtNombreActividad, txtTelefono, cmbMedio,
						cmbMarcaSugerida, cmbVenta, cmbTarget,
						cmbNivelEconomico, cmbTarget, spnPersonas);
				llenarListas();
				usuarioEditador = null;
				estadoInbox = "";
				tipoInbox = "";
				inbox = false;
				listaGenerica = null;
				planillaGenerica = null;
				catalogoGenerico = null;
				fechaInbox = null;
			}

			@Override
			public void guardar() {
				if (validar()) {
					guardarDatos("En Edicion");
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					salir();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar la Planilla?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										PlanillaEvento planilla = servicioPlanillaEvento
												.buscar(id);
										servicioBitacoraEvento
												.eliminarPorPlanilla(planilla);
										servicioRecursoPlanillaEvento
												.limpiar(planilla);
										servicioItemDegustacionPlanillaEvento
												.limpiar(planilla);
										servicioItemEstimadoPlanillaEvento
												.limpiar(planilla);
										servicioPlanillaEvento.eliminar(id);
										limpiar();
										msj.mensajeInformacion(Mensaje.eliminado);
									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
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
					salir();
				}
			}
		};
		botoneraEvento.appendChild(botonera);
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaEvento planilla = servicioPlanillaEvento
						.buscar((Long) map.get("id"));
				estadoInbox = (String) map.get("estadoInbox");
				usuarioEditador = planilla.getUsuario();
				tipoInbox = planilla.getTipo();
				listaGenerica = (List<PlanillaGenerica>) map.get("lista");
				planillaGenerica = (PlanillaGenerica) map.get("planilla");
				catalogoGenerico = (Catalogo<PlanillaGenerica>) map
						.get("catalogo");
				fechaInbox = (Timestamp) map.get("fechaInbox");
				settearCampos(planilla);
				switch (estadoInbox) {
				case "Pendiente":
					if (editar) {
						botonera.getChildren().get(1).setVisible(false);
						botonera.getChildren().get(2).setVisible(false);
						botonera.getChildren().get(3).setVisible(false);
						botonera.getChildren().get(7).setVisible(false);
						botonera.getChildren().get(8).setVisible(false);
					} else {
						botonera.getChildren().get(0).setVisible(false);
						botonera.getChildren().get(1).setVisible(false);
						botonera.getChildren().get(2).setVisible(false);
						botonera.getChildren().get(3).setVisible(false);
						botonera.getChildren().get(7).setVisible(false);
						botonera.getChildren().get(8).setVisible(false);
					}
					break;

				case "En Edicion":
					break;

				default:
					botonera.getChildren().get(0).setVisible(false);
					botonera.getChildren().get(1).setVisible(false);
					botonera.getChildren().get(2).setVisible(false);
					botonera.getChildren().get(3).setVisible(false);
					botonera.getChildren().get(7).setVisible(false);
					botonera.getChildren().get(8).setVisible(false);
				}
				inbox = true;
				map.clear();
				map = null;
				editar = false;
			}
		} else {
			dtbFin.setConstraint("no past");
			dtbInicio.setConstraint("no past");
		}
		cargarCombos();
		llenarListas();
	}

	public void guardarDatos(String string) {
		boolean envio = false;
		boolean guardo = false;
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
		Usuario usuario = new Usuario();
		if (inbox)
			usuario = usuarioEditador;
		else
			usuario = usuarioSesion(nombreUsuarioSesion());

		if (!estadoInbox.equals("Pendiente") && string.equals("Pendiente"))
			envio = true;

		Timestamp fechaEnvio = fechaHora;
		if (estadoInbox.equals("Pendiente"))
			fechaEnvio = fechaInbox;

		if (!estadoInbox.equals("Pendiente") && string.equals("En Edicion")
				&& id == 0)
			guardo = true;

		if (estadoInbox.equals("Pendiente"))
			string = "Pendiente";
		String tipoConfig = "";
		if (tipoInbox.equals("TradeMark"))
			tipoConfig = "TradeMark";
		else {
			if (tipoInbox.equals("Marca"))
				tipoConfig = "Marca";
			else
				tipoConfig = valor;
		}
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
				fechaHora, fechaEnvio, horaAuditoria, nombreUsuarioSesion(),
				string, usuario.getZona().getDescripcion(), tipoConfig, "", 0,
				"");
		String origenPlanilla = valor;
		if (!tipoConfig.equals(""))
			origenPlanilla = tipoConfig;
		if (origenPlanilla.equals("TradeMark"))
			origenPlanilla = "Trade Marketing";
		planillaEvento.setOrigen(origenPlanilla);
		servicioPlanillaEvento.guardar(planillaEvento);
		if (id != 0)
			planillaEvento = servicioPlanillaEvento.buscar(id);
		else
			planillaEvento = servicioPlanillaEvento.buscarUltima();

		if (inbox) {
			String origen = "TRADE MARKETING";
			if (tipoConfig.equals("Marca"))
				origen = "MARCA";
			PlanillaGenerica planillita = new PlanillaGenerica(
					planillaEvento.getIdPlanillaEvento(), usuario.getNombre(),
					marca.getDescripcion(), nombreActividad,
					planillaEvento.getFechaEnvio(), string,
					"Eventos Especiales", origen);
			int indice = listaGenerica.indexOf(planillaGenerica);
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(indice, planillita);
			control.actualizar(listaGenerica, catalogoGenerico);
		}

		if (guardo)
			guardarBitacora(planillaEvento, true);
		if (envio) {
			guardarBitacora(planillaEvento, false);
			enviarEmail(tipoConfig, nombreUsuarioSesion(),
					planillaEvento.getIdPlanillaEvento(), "Eventos Especiales",
					usuario.getMail());
		}

		guardarItemsDegustacion(planillaEvento);
		guardarItemsEstimados(planillaEvento);
		guardarRecursos(planillaEvento);
	}

	private void guardarBitacora(PlanillaEvento planillaEvento, boolean edicion) {

		/* Busca las imagenes representativas de los estados */
		URL url = getClass().getResource("/imagenes/si.png");
		try {
			imagenSi.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] imagen = imagenSi.getContent().getByteData();

		URL url2 = getClass().getResource("/imagenes/no.png");
		try {
			imagenNo.setContent(new AImage(url2));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] imagenX = imagenNo.getContent().getByteData();

		if (edicion) {
			BitacoraEvento bitacora = new BitacoraEvento(0, planillaEvento,
					"Planilla en Edicion", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			servicioBitacoraEvento.guardar(bitacora);
		} else {
			if (id == 0) {
				BitacoraEvento bitacora = new BitacoraEvento(0, planillaEvento,
						"Planilla en Edicion", fechaHora, fechaHora,
						horaAuditoria, nombreUsuarioSesion(), imagen);
				servicioBitacoraEvento.guardar(bitacora);

			}
			List<BitacoraEvento> listaBitacoras = new ArrayList<BitacoraEvento>();
			BitacoraEvento bitacora = new BitacoraEvento(0, planillaEvento,
					"Planilla Enviada", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraEvento bitacora2 = new BitacoraEvento(0, planillaEvento,
					"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraEvento bitacora3 = new BitacoraEvento(0, planillaEvento,
					"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraEvento bitacora4 = new BitacoraEvento(0, planillaEvento,
					"Esperando Pago de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraEvento.guardarBitacoras(listaBitacoras);
		}
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
			ItemEstimadoPlanillaEventoId clave = new ItemEstimadoPlanillaEventoId();
			clave.setPlanillaEvento(planillaEvento);
			clave.setSku(sku);
			ItemEstimadoPlanillaEvento planillaItem = new ItemEstimadoPlanillaEvento(
					clave, estimado);
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
			ItemDegustacionPlanillaEventoId clave = new ItemDegustacionPlanillaEventoId();
			clave.setPlanillaEvento(planillaEvento);
			clave.setSku(sku);
			ItemDegustacionPlanillaEvento planillaItem = new ItemDegustacionPlanillaEvento(
					clave, solicitado, aprobado);
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
			RecursoPlanillaEventoId clave = new RecursoPlanillaEventoId();
			clave.setMarca(marca);
			clave.setPlanillaEvento(planillaEvento);
			clave.setRecurso(recurso);
			RecursoPlanillaEvento recursoPlanilla = new RecursoPlanillaEvento(
					clave, solicitado, aprobado);
			recursosPlanilla.add(recursoPlanilla);
		}
		servicioRecursoPlanillaEvento.guardar(recursosPlanilla);
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
				|| tmbHora.getText().compareTo("") == 0) {
			tabDatos.setSelected(true);
			aplicarColores(txtCiudad, txtContacto, txtCosto, txtDireccion,
					cmbRegion, txtNombreActividad, txtTelefono, cmbMedio,
					cmbMarcaSugerida, cmbVenta, cmbTarget, cmbNivelEconomico,
					cmbTarget);
			if (cdtDescripcion.getValue().compareTo("") == 0)
				tabDescripcion.setSelected(true);
			if (cdtMecanica.getValue().compareTo("") == 0)
				tabMecanica.setSelected(true);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!validarMax())
				return false;
			else {
				if (!validarMax2())
					return false;
				else {
					if (!Validador.validarTelefono(txtTelefono.getValue())) {
						msj.mensajeAlerta(Mensaje.telefonoInvalido);
						return false;
					} else {
						if (!validarLista()) {
							msj.mensajeAlerta(Mensaje.faltaMarca);
							return false;
						} else
							return true;
					}
				}
			}
		}

	}

	private boolean validarLista() {
		for (int j = 0; j < ltbRecursosAgregados.getItemCount(); j++) {
			Listitem listItemj = ltbRecursosAgregados.getItemAtIndex(j);
			if (((Combobox) ((listItemj.getChildren().get(1))).getFirstChild())
					.getSelectedItem() == null) {
				return false;
			}
		}
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

		// recursos = servicioRecurso.buscarDisponibles(planilla);
		recursos = servicioRecurso.buscarTodosOrdenados();
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

	public void buscarCatalogoPropio() {
		final List<PlanillaEvento> listPlanilla = servicioPlanillaEvento
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaEvento>(catalogoEvento,
				"Planillas de Eventos Especiales", listPlanilla, true,
				"Nombre Actividad", "Ciudad", "Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaEvento> buscar(List<String> valores) {

				List<PlanillaEvento> lista = new ArrayList<PlanillaEvento>();

				for (PlanillaEvento planillaEvento : listPlanilla) {
					if (planillaEvento.getNombreActividad().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& planillaEvento.getCiudad().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& planillaEvento.getMarca().getDescripcion()
									.toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& String
									.valueOf(
											formatoFecha.format(planillaEvento
													.getFechaAuditoria()))
									.toLowerCase().contains(valores.get(3))) {
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
				registros[3] = String.valueOf(formatoFecha
						.format(planillaEvento.getFechaAuditoria()));
				return registros;
			}
		};
		catalogo.setParent(catalogoEvento);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoEvento")
	public void seleccionPropia() {
		dtbFin.setConstraint("");
		dtbInicio.setConstraint("");
		PlanillaEvento planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		llenarListas();
		catalogo.setParent(null);
	}

	private void settearCampos(PlanillaEvento planilla) {
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
					ItemDegustacionPlanillaEventoId clave2 = new ItemDegustacionPlanillaEventoId();
					clave2.setSku(sku);
					itemPlanilla.setId(clave2);
					itemsDegustacionAgregados.clear();
					for (int j = 0; j < ltbProductosDegustacionAgregados
							.getItemCount(); j++) {
						Listitem listItemj = ltbProductosDegustacionAgregados
								.getItemAtIndex(j);
						Integer solicitado = ((Spinner) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						Integer aprobado = ((Spinner) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						String skyId = ((Textbox) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						Sku skus = servicioSku.buscar(skyId);
						ItemDegustacionPlanillaEventoId clave = new ItemDegustacionPlanillaEventoId();
						clave.setSku(skus);
						ItemDegustacionPlanillaEvento planillaItem = new ItemDegustacionPlanillaEvento(
								clave, solicitado, aprobado);
						itemsDegustacionAgregados.add(planillaItem);
					}
					itemsDegustacionAgregados.add(itemPlanilla);
					ltbProductosDegustacionAgregados
							.setModel(new ListModelList<ItemDegustacionPlanillaEvento>(
									itemsDegustacionAgregados));
					ltbProductosDegustacionAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosDegustacion.removeItemAt(listitemEliminar.get(i)
					.getIndex());
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
					ItemDegustacionPlanillaEvento itemPlanilla = listItem2.get(
							i).getValue();
					itemsDegustacionAgregados.remove(itemPlanilla);
					itemsDegustacion.add(itemPlanilla.getId().getSku());
					ltbProductosDegustacion.setModel(new ListModelList<Sku>(
							itemsDegustacion));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosDegustacionAgregados.removeItemAt(listitemEliminar.get(
					i).getIndex());
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
					ItemEstimadoPlanillaEventoId clave2 = new ItemEstimadoPlanillaEventoId();
					clave2.setSku(sku);
					itemPlanilla.setId(clave2);
					itemsEstimacionAgregados.clear();
					for (int j = 0; j < ltbProductosVentaAgregados
							.getItemCount(); j++) {
						Listitem listItemj = ltbProductosVentaAgregados
								.getItemAtIndex(j);
						Integer estimado = ((Spinner) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						String skyId = ((Textbox) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						Sku skus = servicioSku.buscar(skyId);
						ItemEstimadoPlanillaEventoId clave = new ItemEstimadoPlanillaEventoId();
						clave.setSku(skus);
						ItemEstimadoPlanillaEvento planillaItem = new ItemEstimadoPlanillaEvento(
								clave, estimado);
						itemsEstimacionAgregados.add(planillaItem);
					}
					itemsEstimacionAgregados.add(itemPlanilla);
					ltbProductosVentaAgregados
							.setModel(new ListModelList<ItemEstimadoPlanillaEvento>(
									itemsEstimacionAgregados));
					ltbProductosVentaAgregados.renderAll();
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
					ItemEstimadoPlanillaEvento itemPlanilla = listItem2.get(i)
							.getValue();
					itemsEstimacionAgregados.remove(itemPlanilla);
					itemsEstimacion.add(itemPlanilla.getId().getSku());
					ltbProductosVenta.setModel(new ListModelList<Sku>(
							itemsEstimacion));
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
					RecursoPlanillaEventoId clave2 = new RecursoPlanillaEventoId();
					clave2.setRecurso(recurso);
					recursoPlanilla.setId(clave2);
					recursosAgregados.clear();
					for (int j = 0; j < ltbRecursosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbRecursosAgregados
								.getItemAtIndex(j);
						String idMarca = "";
						if (((Combobox) ((listItemj.getChildren().get(1)))
								.getFirstChild()).getSelectedItem() != null) {
							idMarca = ((Combobox) ((listItemj.getChildren()
									.get(1))).getFirstChild())
									.getSelectedItem().getContext();
						}
						Marca marca = servicioMarca.buscar(idMarca);
						Integer solicitado = ((Spinner) ((listItemj
								.getChildren().get(2))).getFirstChild())
								.getValue();
						Integer aprobado = ((Spinner) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						long recursoId = ((Spinner) ((listItemj.getChildren()
								.get(4))).getFirstChild()).getValue();
						Recurso recursos = servicioRecurso.buscar(recursoId);
						RecursoPlanillaEventoId clave = new RecursoPlanillaEventoId();
						clave.setMarca(marca);
						clave.setRecurso(recursos);
						RecursoPlanillaEvento recursoPlanillaj = new RecursoPlanillaEvento(
								clave, solicitado, aprobado);
						recursosAgregados.add(recursoPlanillaj);
					}
					recursosAgregados.add(recursoPlanilla);
					ltbRecursosAgregados
							.setModel(new ListModelList<RecursoPlanillaEvento>(
									recursosAgregados));
					ltbRecursosAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		// for (int i = 0; i < listitemEliminar.size(); i++) {
		// ltbRecursos.removeItemAt(listitemEliminar.get(i).getIndex());
		// }
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
					recursos.add(recursoPlanilla.getId().getRecurso());
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

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono")
	public void validarTelefono2E() throws IOException {
		if (Validador.validarTelefono(txtTelefono.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	@Listen("onChange = #cdtDescripcion")
	public boolean validarMax() {
		if (cdtDescripcion.getValue().length() > 4999) {
			msj.mensajeAlerta("Longitud maxima excedida (5000 caracteres) "
					+ "en campo Descripcion de Actividad");
			tabDescripcion.setSelected(true);
			return false;
		}
		return true;
	}

	@Listen("onChange = #cdtMecanica")
	public boolean validarMax2() {
		if (cdtMecanica.getValue().length() > 4999) {
			msj.mensajeAlerta("Longitud maxima excedida (5000 caracteres) en campo Mecanica de Actividad");
			tabMecanica.setSelected(true);
			return false;
		}
		return true;
	}

}
