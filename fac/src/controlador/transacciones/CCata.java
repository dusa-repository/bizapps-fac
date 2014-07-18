package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraCata;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.maestros.Sku;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.RecursoPlanillaCata;

import org.zkforge.ckez.CKeditor;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Sessions;
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
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CCata extends CGenerico {

	private static final long serialVersionUID = 4147094760250053871L;
	@Wire
	private Window wdwCata;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabDescripcion;
	@Wire
	private Tab tabMecanica;
	@Wire
	private Tab tabProductos;
	@Wire
	private Tab tabRecursos;
	@Wire
	private Textbox txtNombreActividad;
	@Wire
	private Textbox txtRespActividad;
	@Wire
	private Textbox txtRespZona;
	@Wire
	private Combobox cmbCata;
	@Wire
	private Datebox dtbActividad;
	@Wire
	private Textbox txtCiudad;
	@Wire
	private Textbox txtContacto;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Combobox cmbMarcaSugerida;
	@Wire
	private Spinner spnPersonas;
	@Wire
	private Combobox cmbMotivo;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Textbox txtEMail;
	@Wire
	private Combobox cmbNivelEconomico;
	@Wire
	private Combobox cmbTarget;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private CKeditor cdtDescripcion;
	@Wire
	private CKeditor cdtMecanica;
	@Wire
	private Listbox ltbProductos;
	@Wire
	private Listbox ltbProductosAgregados;
	@Wire
	private Listbox ltbRecursos;
	@Wire
	private Listbox ltbRecursosAgregados;
	@Wire
	private Div botoneraCataInduccion;
	@Wire
	private Div catalogoCataInduccion;
	Catalogo<PlanillaCata> catalogo;
	ListModelList<Marca> marcas;
	List<Sku> items = new ArrayList<Sku>();
	List<Recurso> recursos = new ArrayList<Recurso>();
	List<ItemPlanillaCata> itemsAgregados = new ArrayList<ItemPlanillaCata>();
	List<RecursoPlanillaCata> recursosAgregados = new ArrayList<RecursoPlanillaCata>();
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

		txtNombreActividad.setFocus(true);
		txtRespActividad.setValue(nombreUsuarioSesion());
		txtRespZona.setValue(usuarioSesion(nombreUsuarioSesion())
				.getSupervisor());
		boolean editar = false;
		List<Grupo> grupos = servicioGrupo
				.buscarGruposUsuario(usuarioSesion(nombreUsuarioSesion()));
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getNombre().equals("Administrador")) {
				i = grupos.size();
				editar = true;
			}
		}
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwCata);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtCiudad.setValue("");
				txtContacto.setValue("");
				txtCosto.setValue(null);
				txtDireccion.setValue("");
				txtEMail.setValue("");
				txtNombreActividad.setValue("");
				txtTelefono.setValue("");
				cmbCata.setValue("");
				cmbMarcaSugerida.setValue("");
				cmbMotivo.setValue("");
				cmbNivelEconomico.setValue("");
				cmbTarget.setValue("");
				spnPersonas.setValue(0);
				cdtDescripcion.setValue("");
				cdtMecanica.setValue("");
				dtbActividad.setValue(fecha);
				tabDatos.setSelected(true);
				id = 0;
				usuarioEditador = null;
				estadoInbox = "";
				tipoInbox = "";
				inbox = false;
				listaGenerica = null;
				planillaGenerica = null;
				catalogoGenerico = null;
				fechaInbox = null;
				llenarListas();
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
					Messagebox.show("¿Esta Seguro de Eliminar la Planilla?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										PlanillaCata planilla = servicioPlanillaCata
												.buscar(id);
										servicioBitacoraCata
												.eliminarPorPlanilla(planilla);
										servicioRecursoPlanillaCata
												.limpiar(planilla);
										servicioItemPlanillaCata
												.limpiar(planilla);
										servicioPlanillaCata.eliminar(id);
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
				if (tabProductos.isSelected())
					tabMecanica.setSelected(true);
				if (tabRecursos.isSelected())
					tabProductos.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabDatos.isSelected())
					tabDescripcion.setSelected(true);
				else {
					if (tabDescripcion.isSelected())
						tabMecanica.setSelected(true);
					else {
						if (tabMecanica.isSelected())
							tabProductos.setSelected(true);
						else {
							if (tabProductos.isSelected())
								tabRecursos.setSelected(true);
						}
					}
				}
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
		botoneraCataInduccion.appendChild(botonera);

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaCata planilla = servicioPlanillaCata.buscar((Long) map
						.get("id"));
				usuarioEditador = planilla.getUsuario();
				estadoInbox = (String) map.get("estadoInbox");
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
		}
		cargarCombos();
		llenarListas();
	}

	public void guardarDatos(String string) {
		boolean envio = false;
		boolean guardo = false;
		if (id != 0) {
			PlanillaCata planilla = servicioPlanillaCata.buscar(id);
			servicioRecursoPlanillaCata.limpiar(planilla);
			servicioItemPlanillaCata.limpiar(planilla);
		}
		String ciudad, contacto, direccion, mail, nombreActividad, telefono, cata, motivo, nivel, edadTarget, descripcion, mecanica;
		edadTarget = cmbTarget.getValue();
		cata = cmbCata.getSelectedItem().getContext();
		motivo = cmbMotivo.getSelectedItem().getContext();
		descripcion = cdtDescripcion.getValue();
		mecanica = cdtMecanica.getValue();
		nivel = cmbNivelEconomico.getSelectedItem().getContext();
		ciudad = txtCiudad.getValue();
		contacto = txtContacto.getValue();
		direccion = txtDireccion.getValue();
		mail = txtEMail.getValue();
		nombreActividad = txtNombreActividad.getValue();
		telefono = txtTelefono.getValue();
		Usuario usuario = new Usuario();

		if (!estadoInbox.equals("Pendiente") && string.equals("Pendiente"))
			envio = true;

		Timestamp fechaEnvio = fechaHora;
		if (estadoInbox.equals("Pendiente"))
			fechaEnvio = fechaInbox;

		if (!estadoInbox.equals("Pendiente") && string.equals("En Edicion")
				&& id == 0)
			guardo = true;

		if (inbox)
			usuario = usuarioEditador;
		else
			usuario = usuarioSesion(nombreUsuarioSesion());
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
		Date valorFecha = dtbActividad.getValue();
		int personas = spnPersonas.getValue();
		double costo = txtCosto.getValue();
		Timestamp fechaActividad = new Timestamp(valorFecha.getTime());
		PlanillaCata planilla = new PlanillaCata(id, usuario, marca,
				nombreActividad, fechaActividad, cata, ciudad, contacto,
				telefono, mail, direccion, personas, motivo, nivel, edadTarget,
				costo, descripcion, mecanica, fechaHora, fechaEnvio,
				horaAuditoria, nombreUsuarioSesion(), string, usuario.getZona()
						.getDescripcion(), tipoConfig, "", 0);
		servicioPlanillaCata.guardar(planilla);
		if (id != 0)
			planilla = servicioPlanillaCata.buscar(id);
		else
			planilla = servicioPlanillaCata.buscarUltima();

		if (inbox) {
			PlanillaGenerica planillita = new PlanillaGenerica(
					planilla.getIdPlanillaCata(), usuario.getNombre(),
					marca.getDescripcion(), nombreActividad,
					planilla.getFechaEnvio(), string, "Cata Induccion");
			int indice = listaGenerica.indexOf(planillaGenerica);
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(indice, planillita);
			control.actualizar(listaGenerica, catalogoGenerico);
		}

		if (guardo)
			guardarBitacora(planilla, true);
		if (envio)
			guardarBitacora(planilla, false);

		guardarItems(planilla);
		guardarRecursos(planilla);
		// if (tipoConfig.equals("TradeMark") && envio) {
		// Configuracion con = servicioConfiguracion
		// .buscarTradeMark("TradeMark");
		// Usuario usuarioAdmin = new Usuario();
		// if (con != null)
		// usuarioAdmin = con.getUsuario();
		// PlanillaCata planillaAdmin = new PlanillaCata(0, usuarioAdmin,
		// marca, nombreActividad, fechaActividad, cata, ciudad,
		// contacto, telefono, mail, direccion, personas, motivo,
		// nivel, edadTarget, costo, descripcion, mecanica, fechaHora,
		// fechaEnvio, horaAuditoria, nombreUsuarioSesion(), string,
		// usuario.getZona().getDescripcion(), "Marca", "",
		// planilla.getIdPlanillaCata());
		// servicioPlanillaCata.guardar(planillaAdmin);
		// planillaAdmin = servicioPlanillaCata.buscarUltima();
		// guardarBitacora(planillaAdmin, false);
		// guardarItems(planillaAdmin);
		// guardarRecursos(planillaAdmin);
		// }
	}

	private void guardarBitacora(PlanillaCata planillaCata, boolean edicion) {

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
			BitacoraCata bitacora = new BitacoraCata(0, planillaCata,
					"Planilla en Edicion", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			servicioBitacoraCata.guardar(bitacora);
		} else {

			if (id == 0) {
				BitacoraCata bitacora = new BitacoraCata(0, planillaCata,
						"Planilla en Edicion", fechaHora, fechaHora,
						horaAuditoria, nombreUsuarioSesion(), imagen);
				servicioBitacoraCata.guardar(bitacora);
			}
			List<BitacoraCata> listaBitacoras = new ArrayList<BitacoraCata>();
			BitacoraCata bitacora = new BitacoraCata(0, planillaCata,
					"Planilla Enviada", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraCata bitacora2 = new BitacoraCata(0, planillaCata,
					"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraCata bitacora3 = new BitacoraCata(0, planillaCata,
					"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraCata bitacora4 = new BitacoraCata(0, planillaCata,
					"Esperando Pago de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraCata.guardarBitacoras(listaBitacoras);
		}
	}

	protected void guardarRecursos(PlanillaCata planilla) {
		List<RecursoPlanillaCata> recursosPlanilla = new ArrayList<RecursoPlanillaCata>();
		for (int i = 0; i < ltbRecursosAgregados.getItemCount(); i++) {
			Listitem listItem = ltbRecursosAgregados.getItemAtIndex(i);
			Integer solicitado = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Integer aprobado = ((Spinner) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			long recursoId = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Recurso recurso = servicioRecurso.buscar(recursoId);
			RecursoPlanillaCata recursoPlanilla = new RecursoPlanillaCata(
					recurso, planilla, solicitado, aprobado);
			recursosPlanilla.add(recursoPlanilla);
		}
		servicioRecursoPlanillaCata.guardar(recursosPlanilla);

	}

	protected void guardarItems(PlanillaCata planilla) {
		List<ItemPlanillaCata> recursosPlanilla = new ArrayList<ItemPlanillaCata>();
		for (int i = 0; i < ltbProductosAgregados.getItemCount(); i++) {
			Listitem listItem = ltbProductosAgregados.getItemAtIndex(i);
			Integer solicitado = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Integer aprobado = ((Spinner) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String skyId = ((Textbox) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Sku sku = servicioSku.buscar(skyId);
			ItemPlanillaCata planillaCata = new ItemPlanillaCata(sku, planilla,
					solicitado, aprobado);
			recursosPlanilla.add(planillaCata);
		}
		servicioItemPlanillaCata.guardar(recursosPlanilla);
	}

	protected boolean validar() {
		if (txtCiudad.getText().compareTo("") == 0
				|| txtContacto.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtDireccion.getText().compareTo("") == 0
				|| txtEMail.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0
				|| cmbCata.getText().compareTo("") == 0
				|| cmbMarcaSugerida.getText().compareTo("") == 0
				|| cmbMotivo.getText().compareTo("") == 0
				|| cmbNivelEconomico.getText().compareTo("") == 0
				|| cmbTarget.getText().compareTo("") == 0
				|| cdtDescripcion.getValue().compareTo("") == 0
				|| cdtMecanica.getValue().compareTo("") == 0
				|| dtbActividad.getText().compareTo("") == 0
				|| spnPersonas.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarCorreo(txtEMail.getValue())) {
				msj.mensajeAlerta(Mensaje.correoInvalido);
				return false;
			} else {
				if (!Validador.validarTelefono(txtTelefono.getValue())) {
					msj.mensajeAlerta(Mensaje.telefonoInvalido);
					return false;
				} else
					return true;
			}
		}
	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
			listas.add(ltbProductos);
			listas.add(ltbProductosAgregados);
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

	private void llenarListas() {
		PlanillaCata planilla = servicioPlanillaCata.buscar(id);

		items = servicioSku.buscarDisponibles(planilla);
		ltbProductos.setModel(new ListModelList<Sku>(items));
		itemsAgregados = servicioItemPlanillaCata.buscarPorPlanilla(planilla);
		ltbProductosAgregados.setModel(new ListModelList<ItemPlanillaCata>(
				itemsAgregados));

		recursos = servicioRecurso.buscarDisponibles(planilla);
		ltbRecursos.setModel(new ListModelList<Recurso>(recursos));
		recursosAgregados = servicioRecursoPlanillaCata
				.buscarPorPlanilla(planilla);
		ltbRecursosAgregados.setModel(new ListModelList<RecursoPlanillaCata>(
				recursosAgregados));

		listasMultiples();
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "06");
		cmbCata.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "05");
		cmbMotivo.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "04");
		cmbNivelEconomico.setModel(new ListModelList<F0005>(udc));
	}

	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}

	public void buscarCatalogoPropio() {
		final List<PlanillaCata> listPlanilla = servicioPlanillaCata
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaCata>(catalogoCataInduccion,
				"Planillas de Cata Induccion", listPlanilla, true,
				"Nombre Actividad", "Ciudad", "Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaCata> buscar(List<String> valores) {

				List<PlanillaCata> lista = new ArrayList<PlanillaCata>();

				for (PlanillaCata planillaCata : listPlanilla) {
					if (planillaCata.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0).toLowerCase())
							&& planillaCata.getCiudad().toLowerCase()
									.startsWith(valores.get(1).toLowerCase())
							&& planillaCata.getMarca().getDescripcion()
									.toLowerCase()
									.startsWith(valores.get(2).toLowerCase())
							&& String
									.valueOf(
											formatoFecha.format(planillaCata
													.getFechaAuditoria()))
									.toLowerCase().startsWith(valores.get(3))) {
						lista.add(planillaCata);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaCata planillaCata) {
				String[] registros = new String[4];
				registros[0] = planillaCata.getNombreActividad().toLowerCase();
				registros[1] = planillaCata.getCiudad().toLowerCase();
				registros[2] = planillaCata.getMarca().getDescripcion()
						.toLowerCase();
				registros[3] = String.valueOf(formatoFecha.format(planillaCata
						.getFechaAuditoria()));
				return registros;
			}
		};
		catalogo.setParent(catalogoCataInduccion);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoCataInduccion")
	public void seleccionPropia() {
		PlanillaCata planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		llenarListas();
		catalogo.setParent(null);
	}

	private void settearCampos(PlanillaCata planilla) {
		txtCiudad.setValue(planilla.getCiudad());
		txtContacto.setValue(planilla.getNombreCliente());
		txtCosto.setValue(planilla.getCosto());
		txtDireccion.setValue(planilla.getDireccionPdv());
		txtEMail.setValue(planilla.getCorreoPdv());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		txtTelefono.setValue(planilla.getTelefonoPdv());
		F0005 f05 = servicioF0005.buscar("00", "06", planilla.getCata());
		cmbCata.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "05", planilla.getMotivo());
		cmbMotivo.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "04", planilla.getNivel());
		cmbNivelEconomico.setValue(f05.getDrdl01());
		cmbMarcaSugerida.setValue(planilla.getMarca().getDescripcion());
		cmbTarget.setValue(planilla.getEdadTarget());
		spnPersonas.setValue(planilla.getPersonas());
		cdtDescripcion.setValue(planilla.getDescripcion());
		cdtMecanica.setValue(planilla.getMecanica());
		dtbActividad.setValue(planilla.getFechaActividad());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanillaCata();
	}

	@Listen("onClick = #pasar1")
	public void derechaPt() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbProductos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Sku sku = listItem.get(i).getValue();
					items.remove(sku);
					ItemPlanillaCata itemPlanilla = new ItemPlanillaCata();
					itemPlanilla.setSku(sku);
					itemsAgregados.clear();
					for (int j = 0; j < ltbProductosAgregados.getItemCount(); j++) {

						Listitem listItemj = ltbProductosAgregados
								.getItemAtIndex(j);
						Integer solicitado = ((Spinner) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						Integer aprobado = ((Spinner) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						String skyId = ((Textbox) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						Sku sku2 = servicioSku.buscar(skyId);
						ItemPlanillaCata planillaCata = new ItemPlanillaCata(
								sku2, null, solicitado, aprobado);
						itemsAgregados.add(planillaCata);
					}
					itemsAgregados.add(itemPlanilla);
					ltbProductosAgregados
							.setModel(new ListModelList<ItemPlanillaCata>(
									itemsAgregados));
					ltbProductosAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductos.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2")
	public void izquierdaPt() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbProductosAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ItemPlanillaCata itemPlanilla = listItem2.get(i).getValue();
					itemsAgregados.remove(itemPlanilla);
					items.add(itemPlanilla.getSku());
					ltbProductos.setModel(new ListModelList<Sku>(items));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbProductosAgregados.removeItemAt(listitemEliminar.get(i)
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
					RecursoPlanillaCata recursoPlanilla = new RecursoPlanillaCata();
					recursoPlanilla.setRecurso(recurso);
					recursosAgregados.clear();
					for (int j = 0; j < ltbRecursosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbRecursosAgregados.getItemAtIndex(j);
						Integer solicitado = ((Spinner) ((listItemj.getChildren().get(1)))
								.getFirstChild()).getValue();
						Integer aprobado = ((Spinner) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getValue();
						long recursoId = ((Spinner) ((listItemj.getChildren().get(3)))
								.getFirstChild()).getValue();
						Recurso recursoa = servicioRecurso.buscar(recursoId);
						RecursoPlanillaCata recursoPlanillas = new RecursoPlanillaCata(
								recursoa, null, solicitado, aprobado);
						recursosAgregados.add(recursoPlanillas);
					}
					recursosAgregados.add(recursoPlanilla);
					ltbRecursosAgregados
							.setModel(new ListModelList<RecursoPlanillaCata>(
									recursosAgregados));
					ltbRecursosAgregados.renderAll();
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
					RecursoPlanillaCata recursoPlanilla = listItem2.get(i)
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

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono")
	public void validarTelefono2E() throws IOException {
		if (Validador.validarTelefono(txtTelefono.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtEMail")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtEMail.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

}
