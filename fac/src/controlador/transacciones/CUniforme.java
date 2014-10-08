package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraUniforme;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Uniforme;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.UniformePlanillaUniforme;

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
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CUniforme extends CGenerico {

	private static final long serialVersionUID = -5755307919473799508L;
	@Wire
	private Window wdwSolicitudUniforme;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabUniforme;
	@Wire
	private Tab tabJustificacion;
	@Wire
	private Textbox txtNombreActividad;
	@Wire
	private Textbox txtRespActividad;
	@Wire
	private Textbox txtRespZona;
	@Wire
	private Combobox cmbActividad;
	@Wire
	private Datebox dtbActividad;
	@Wire
	private Textbox txtCiudad;
	@Wire
	private Combobox cmbMarcaSugerida;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtRif;
	@Wire
	private Combobox cmbLogo;
	@Wire
	private Textbox txtEMail;
	@Wire
	private Textbox txtCliente;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private CKeditor cdtJustificacion;
	@Wire
	private Radio rdoSi;
	@Wire
	private Radio rdoNo;
	@Wire
	private Div catalogoUniformes;
	@Wire
	private Div botoneraUniformes;
	@Wire
	private Listbox ltbUniformes;
	@Wire
	private Listbox ltbUniformesAgregados;
	Catalogo<PlanillaUniforme> catalogo;
	ListModelList<Marca> marcas;
	ListModelList<F0005> tallas;
	int cambio;
	List<Uniforme> uniformes = new ArrayList<Uniforme>();
	List<UniformePlanillaUniforme> uniformesAgregados = new ArrayList<UniformePlanillaUniforme>();
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
		dtbActividad.setValue(fecha);
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
				cerrarVentana(wdwSolicitudUniforme);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();
			}

			@Override
			public void limpiar() {
				txtCiudad.setValue("");
				txtCosto.setValue(null);
				txtDireccion.setValue("");
				txtNombreActividad.setValue("");
				txtTelefono.setValue("");
				txtNombre.setValue("");
				txtRif.setValue("");
				txtCliente.setValue("");
				txtEMail.setValue("");
				cmbActividad.setValue("");
				cmbMarcaSugerida.setValue("");
				cmbLogo.setValue("");
				if (rdoNo.isChecked())
					rdoNo.setChecked(false);
				else
					rdoSi.setChecked(false);
				cdtJustificacion.setValue("");
				dtbActividad.setValue(fecha);
				tabDatos.setSelected(true);
				usuarioEditador = null;
				estadoInbox = "";
				tipoInbox = "";
				inbox = false;
				id = 0;
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
										PlanillaUniforme planilla = servicioPlanillaUniforme
												.buscar(id);
										servicioBitacoraUniforme
												.eliminarPorPlanilla(planilla);
										servicioUniformePlanillaUniforme
												.limpiar(planilla);
										servicioPlanillaUniforme.eliminar(id);
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
				if (tabUniforme.isSelected())
					tabDatos.setSelected(true);
				if (tabJustificacion.isSelected())
					tabUniforme.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabUniforme.isSelected())
					tabJustificacion.setSelected(true);
				if (tabDatos.isSelected())
					tabUniforme.setSelected(true);
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
		botoneraUniformes.appendChild(botonera);
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaUniforme planilla = servicioPlanillaUniforme
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
				editar = false;
				map.clear();
				map = null;
			}
		}
		cargarCombos();
		llenarListas();
	}

	protected void guardarDatos(String string) {
		boolean envio = false;
		boolean guardo = false;
		if (id != 0) {
			PlanillaUniforme planilla = servicioPlanillaUniforme.buscar(id);
			servicioUniformePlanillaUniforme.limpiar(planilla);
		}
		String contrato, nombre, rif, ciudad, direccion, mail, nombreActividad, telefono, tipoActividad, cliente, justificacion, logo;
		tipoActividad = cmbActividad.getSelectedItem().getContext();
		logo = cmbLogo.getValue();
		justificacion = cdtJustificacion.getValue();
		nombre = txtNombre.getValue();
		cliente = txtCliente.getValue();
		rif = txtRif.getValue();
		ciudad = txtCiudad.getValue();
		direccion = txtDireccion.getValue();
		mail = txtEMail.getValue();
		nombreActividad = txtNombreActividad.getValue();
		telefono = txtTelefono.getValue();
		double costo = txtCosto.getValue();
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
		Date valorFecha = dtbActividad.getValue();
		Timestamp fechaActividad = new Timestamp(valorFecha.getTime());

		if (rdoNo.isChecked())
			contrato = "N";
		else
			contrato = "Y";
		PlanillaUniforme planillaUniforme = new PlanillaUniforme(id, usuario,
				marca, nombreActividad, fechaActividad, tipoActividad, ciudad,
				cliente, nombre, rif, telefono, mail, direccion, logo, costo,
				justificacion, contrato, fechaHora, fechaEnvio, horaAuditoria,
				nombreUsuarioSesion(), string, usuario.getZona()
						.getDescripcion(), tipoConfig, "", 0, "");
		servicioPlanillaUniforme.guardar(planillaUniforme);
		if (id != 0)
			planillaUniforme = servicioPlanillaUniforme.buscar(id);
		else
			planillaUniforme = servicioPlanillaUniforme.buscarUltima();

		if (inbox) {
			PlanillaGenerica planillita = new PlanillaGenerica(
					planillaUniforme.getIdPlanillaUniforme(),
					usuario.getNombre(), marca.getDescripcion(),
					nombreActividad, planillaUniforme.getFechaEnvio(), string,
					"Uniformes");
			int indice = listaGenerica.indexOf(planillaGenerica);
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(indice, planillita);
			control.actualizar(listaGenerica, catalogoGenerico);
		}

		if (guardo)
			guardarBitacora(planillaUniforme, true);
		if (envio) {
			guardarBitacora(planillaUniforme, false);
			enviarEmail(tipoConfig, nombreUsuarioSesion(),
					planillaUniforme.getIdPlanillaUniforme(), "Uniformes", usuario.getMail());
		}

		guardarUniformes(planillaUniforme);
	}

	private void guardarUniformes(PlanillaUniforme planillaUniforme) {
		List<UniformePlanillaUniforme> recursosPlanilla = new ArrayList<UniformePlanillaUniforme>();
		for (int i = 0; i < ltbUniformesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbUniformesAgregados.getItemAtIndex(i);
			String genero = ((Combobox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			String talla = ((Combobox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			int cantidad = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			double precioUnitario = ((Doublespinner) ((listItem.getChildren()
					.get(4))).getFirstChild()).getValue();
			long idUniforme = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Uniforme uniforme = servicioUniforme.buscar(idUniforme);
			UniformePlanillaUniforme uniformePlanilla = new UniformePlanillaUniforme(
					uniforme, planillaUniforme, genero, talla, cantidad,
					precioUnitario);
			recursosPlanilla.add(uniformePlanilla);
		}
		servicioUniformePlanillaUniforme.guardar(recursosPlanilla);
	}

	private void guardarBitacora(PlanillaUniforme planillaUniforme,
			boolean edicion) {

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
			BitacoraUniforme bitacora = new BitacoraUniforme(0,
					planillaUniforme, "Planilla en Edicion", fechaHora,
					fechaHora, horaAuditoria, nombreUsuarioSesion(), imagen);
			servicioBitacoraUniforme.guardar(bitacora);
		} else {
			if (id == 0) {
				BitacoraUniforme bitacora = new BitacoraUniforme(0,
						planillaUniforme, "Planilla en Edicion", fechaHora,
						fechaHora, horaAuditoria, nombreUsuarioSesion(), imagen);
				servicioBitacoraUniforme.guardar(bitacora);
			}
			List<BitacoraUniforme> listaBitacoras = new ArrayList<BitacoraUniforme>();
			BitacoraUniforme bitacora = new BitacoraUniforme(0,
					planillaUniforme, "Planilla Enviada", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraUniforme bitacora2 = new BitacoraUniforme(0,
					planillaUniforme, "Esperando Aprobacion de Planilla",
					fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion(),
					imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraUniforme bitacora3 = new BitacoraUniforme(0,
					planillaUniforme, "Esperando Finalizacion de Planilla",
					fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion(),
					imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraUniforme bitacora4 = new BitacoraUniforme(0,
					planillaUniforme, "Esperando Pago de Planilla", fechaHora,
					fechaHora, horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraUniforme.guardarBitacoras(listaBitacoras);
		}
	}

	protected boolean validar() {
		if (txtCiudad.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtDireccion.getText().compareTo("") == 0
				|| txtCliente.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| txtEMail.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| cmbMarcaSugerida.getText().compareTo("") == 0
				|| cmbActividad.getText().compareTo("") == 0
				|| cmbLogo.getText().compareTo("") == 0
				|| cdtJustificacion.getValue().compareTo("") == 0
				|| dtbActividad.getText().compareTo("") == 0
				|| (!rdoNo.isChecked() && !rdoSi.isChecked())) {
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
				} else {
					if (!validarLista()) {
						msj.mensajeAlerta(Mensaje.faltaCampoLista);
						return false;
					}
					return true;
				}
			}
		}
	}

	private boolean validarLista() {
		for (int i = 0; i < ltbUniformesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbUniformesAgregados.getItemAtIndex(i);
			String genero = ((Combobox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			String talla = ((Combobox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			if (genero.equals("") || talla.equals(""))
				return false;
		}
		return true;
	}

	private void llenarListas() {
		PlanillaUniforme planilla = servicioPlanillaUniforme.buscar(id);
		uniformes = servicioUniforme.buscarTodosOrdenados();
//		uniformes = servicioUniforme.buscarDisponibles(planilla);
		ltbUniformes.setModel(new ListModelList<Uniforme>(uniformes));
		uniformesAgregados = servicioUniformePlanillaUniforme
				.buscarPorPlanilla(planilla);
		ltbUniformesAgregados
				.setModel(new ListModelList<UniformePlanillaUniforme>(
						uniformesAgregados));

		listasMultiples();
	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
			listas.add(ltbUniformes);
			listas.add(ltbUniformesAgregados);
		}
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "03");
		cmbActividad.setModel(new ListModelList<F0005>(udc));
	}

	@Listen("onClick = #pasar1Recurso")
	public void derechaRecurso() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbUniformes.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Uniforme uniforme = listItem.get(i).getValue();
					uniformes.remove(uniforme);
					UniformePlanillaUniforme uniformePlanilla = new UniformePlanillaUniforme();
					uniformePlanilla.setUniforme(uniforme);
					uniformesAgregados.clear();
					for (int j = 0; j < ltbUniformesAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbUniformesAgregados
								.getItemAtIndex(j);
						String genero = ((Combobox) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						String talla = ((Combobox) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						int cantidad = ((Spinner) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						double precioUnitario = ((Doublespinner) ((listItemj
								.getChildren().get(4))).getFirstChild())
								.getValue();
						long idUniforme = ((Spinner) ((listItemj.getChildren()
								.get(6))).getFirstChild()).getValue();
						Uniforme uniformej = servicioUniforme
								.buscar(idUniforme);
						UniformePlanillaUniforme uniformePlanillaj = new UniformePlanillaUniforme(
								uniformej, null, genero, talla, cantidad,
								precioUnitario);
						uniformesAgregados.add(uniformePlanillaj);
					}
					uniformesAgregados.add(uniformePlanilla);
					ltbUniformesAgregados
							.setModel(new ListModelList<UniformePlanillaUniforme>(
									uniformesAgregados));
					ltbUniformesAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
//		for (int i = 0; i < listitemEliminar.size(); i++) {
//			ltbUniformes.removeItemAt(listitemEliminar.get(i).getIndex());
//		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Recurso")
	public void izquierdaRecurso() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbUniformesAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					UniformePlanillaUniforme uniformePlanilla = listItem2
							.get(i).getValue();
					uniformesAgregados.remove(uniformePlanilla);
					uniformes.add(uniformePlanilla.getUniforme());
					ltbUniformes
							.setModel(new ListModelList<Uniforme>(uniformes));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbUniformesAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}

	public ListModelList<F0005> getTallas() {
		tallas = new ListModelList<F0005>(servicioF0005.buscarParaUDCOrdenados(
				"00", "13"));
		return tallas;
	}

	public void buscarCatalogoPropio() {
		final List<PlanillaUniforme> listPlanilla = servicioPlanillaUniforme
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaUniforme>(catalogoUniformes,
				"Planillas de Solicitud de Uniformes", listPlanilla, true,
				"Nombre Actividad", "Ciudad", "Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaUniforme> buscar(List<String> valores) {

				List<PlanillaUniforme> lista = new ArrayList<PlanillaUniforme>();

				for (PlanillaUniforme planilla : listPlanilla) {
					if (planilla.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0).toLowerCase())
							&& planilla.getCiudad().toLowerCase()
									.startsWith(valores.get(1).toLowerCase())
							&& planilla.getMarca().getDescripcion()
									.toLowerCase()
									.startsWith(valores.get(2).toLowerCase())
							&& String
									.valueOf(
											formatoFecha.format(planilla
													.getFechaAuditoria()))
									.toLowerCase().startsWith(valores.get(3))) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaUniforme planillaCata) {
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
		catalogo.setParent(catalogoUniformes);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoUniformes")
	public void seleccionPropia() {
		PlanillaUniforme planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		llenarListas();
		catalogo.setParent(null);
	}

	private void settearCampos(PlanillaUniforme planilla) {
		txtCiudad.setValue(planilla.getCiudad());
		txtNombre.setValue(planilla.getNombrePdv());
		txtCliente.setValue(planilla.getNombreCliente());
		txtRif.setValue(planilla.getRifPdv());
		txtCosto.setValue(planilla.getCosto());
		txtDireccion.setValue(planilla.getDireccionPdv());
		txtEMail.setValue(planilla.getCorreoPdv());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		txtTelefono.setValue(planilla.getTelefonoPdv());
		F0005 f05 = servicioF0005.buscar("00", "03",
				planilla.getTipoActividad());
		cmbActividad.setValue(f05.getDrdl01());
		cmbMarcaSugerida.setValue(planilla.getMarca().getDescripcion());
		cmbLogo.setValue(planilla.getLogo());
		cdtJustificacion.setValue(planilla.getJustificacion());
		dtbActividad.setValue(planilla.getFechaEntrega());
		tabDatos.setSelected(true);
		if (planilla.getContrato().equals("Y"))
			rdoSi.setChecked(true);
		else
			rdoNo.setChecked(true);
		id = planilla.getIdPlanillaUniforme();
	}

	public int getCambio() {
		if (ltbUniformesAgregados.getItemCount() != 0) {
			double total = 0;
			for (int i = 0; i < ltbUniformesAgregados.getItemCount(); i++) {
				Listitem listItem = ltbUniformesAgregados.getItemAtIndex(i);
				int cantidad = ((Spinner) ((listItem.getChildren().get(3)))
						.getFirstChild()).getValue();
				double precioUnitario = ((Doublespinner) ((listItem
						.getChildren().get(4))).getFirstChild()).getValue();
				((Doublespinner) ((listItem.getChildren().get(5)))
						.getFirstChild()).setValue(precioUnitario * cantidad);
				total = total + (precioUnitario * cantidad);
			}
			txtCosto.setValue(total);
		}
		return cambio;
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
