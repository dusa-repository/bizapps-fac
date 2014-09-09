package controlador.transacciones;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.estado.BitacoraFachada;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Recurso;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.RecursoPlanillaFachada;

import org.zkforge.ckez.CKeditor;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Fileupload;
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

public class CFachada extends CGenerico {

	private static final long serialVersionUID = 2808122811363378528L;
	@Wire
	private Window wdwFachada;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabDescripcion;
	@Wire
	private Tab tabJustificacion;
	@Wire
	private Tab tabFachada;
	@Wire
	private Tab tabImagenes;
	@Wire
	private Tab tabRecursos;
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
	private Textbox txtNombre;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Textbox txtRif;
	@Wire
	private Spinner spnPersonas;
	@Wire
	private Combobox cmbMarcaSugerida;
	@Wire
	private Textbox txtContacto;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Combobox cmbNivelEconomico;
	@Wire
	private Textbox txtDuracion;
	@Wire
	private Textbox txtPatente;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private CKeditor cdtDescripcion;
	@Wire
	private CKeditor cdtJustificacion;
	@Wire
	private Combobox cmbDecoracion;
	@Wire
	private Combobox cmbFormato;
	@Wire
	private Combobox cmbArte;
	@Wire
	private Textbox txtEmail;
	@Wire
	private Doublespinner dspAlto;
	@Wire
	private Doublespinner dspLargo;
	@Wire
	private Doublespinner dspAncho;
	@Wire
	private Image imagen1;
	@Wire
	private Fileupload fudImagen1;
	@Wire
	private Media media1;
	@Wire
	private Image imagen2;
	@Wire
	private Fileupload fudImagen2;
	@Wire
	private Media media2;
	@Wire
	private Image imagen3;
	@Wire
	private Fileupload fudImagen3;
	@Wire
	private Media media3;
	@Wire
	private Image imagen4;
	@Wire
	private Fileupload fudImagen4;
	@Wire
	private Media media4;
	@Wire
	private Div botoneraFachada;
	@Wire
	private Div catalogoFachada;
	@Wire
	private Listbox ltbRecursos;
	@Wire
	private Listbox ltbRecursosAgregados;
	Catalogo<PlanillaFachada> catalogo;
	ListModelList<Marca> marcas;
	List<Recurso> recursos = new ArrayList<Recurso>();
	List<RecursoPlanillaFachada> recursosAgregados = new ArrayList<RecursoPlanillaFachada>();
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
			if (grupos.get(i).getNombre().equals("TRADE MARKETING")
					|| grupos.get(i).getNombre().equals("MARCA")) {
				i = grupos.size();
				editar = true;
			}
		}
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwFachada);
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
				txtDuracion.setValue("");
				txtNombreActividad.setValue("");
				txtTelefono.setValue("");
				txtNombre.setValue("");
				txtEmail.setValue("");
				txtPatente.setValue("");
				txtRif.setValue("");
				cmbActividad.setValue("");
				cmbMarcaSugerida.setValue("");
				cmbArte.setValue("");
				cmbNivelEconomico.setValue("");
				cmbFormato.setValue("");
				cmbDecoracion.setValue("");
				spnPersonas.setValue(0);
				dspAlto.setValue((double) 0);
				dspAncho.setValue((double) 0);
				dspLargo.setValue((double) 0);
				cdtDescripcion.setValue("");
				cdtJustificacion.setValue("");
				dtbActividad.setValue(fecha);
				tabDatos.setSelected(true);
				org.zkoss.image.Image imagenUsuario1 = null;
				imagen1.setContent(imagenUsuario1);
				imagen2.setContent(imagenUsuario1);
				imagen3.setContent(imagenUsuario1);
				imagen4.setContent(imagenUsuario1);
				usuarioEditador = null;
				estadoInbox = "";
				tipoInbox = "";
				inbox = false;
				id = 0;
				tabDatos.setSelected(true);
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
										PlanillaFachada planilla = servicioPlanillaFachada
												.buscar(id);
										servicioBitacoraFachada
												.eliminarPorPlanilla(planilla);
										servicioRecursoPlanillaFachada
												.limpiar(planilla);
										servicioPlanillaFachada.eliminar(id);
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
				if (tabJustificacion.isSelected())
					tabDescripcion.setSelected(true);
				if (tabFachada.isSelected())
					tabJustificacion.setSelected(true);
				if (tabImagenes.isSelected())
					tabFachada.setSelected(true);
				if (tabRecursos.isSelected())
					tabImagenes.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabImagenes.isSelected())
					tabRecursos.setSelected(true);
				if (tabFachada.isSelected())
					tabImagenes.setSelected(true);
				if (tabJustificacion.isSelected())
					tabFachada.setSelected(true);
				if (tabDescripcion.isSelected())
					tabFachada.setSelected(true);
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
		botoneraFachada.appendChild(botonera);

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaFachada planilla = servicioPlanillaFachada
						.buscar((Long) map.get("id"));
				estadoInbox = (String) map.get("estadoInbox");
				usuarioEditador = planilla.getUsuario();
				tipoInbox = planilla.getTipo();
				listaGenerica = (List<PlanillaGenerica>) map.get("lista");
				planillaGenerica = (PlanillaGenerica) map.get("planilla");
				catalogoGenerico = (Catalogo<PlanillaGenerica>) map
						.get("catalogo");
				fechaInbox = (Timestamp) map.get("fechaInbox");
				setearCampos(planilla);
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
			PlanillaFachada planilla = servicioPlanillaFachada.buscar(id);
			servicioRecursoPlanillaFachada.limpiar(planilla);
		}
		String nombre, patente, rif, duracion, ciudad, contacto, direccion, mail, nombreActividad, telefono, formato, salidaArte, tipoDecoracion, tipoActividad, nivel, descripcion, justificacion;
		formato = cmbFormato.getSelectedItem().getContext();
		salidaArte = cmbArte.getSelectedItem().getContext();
		tipoActividad = cmbActividad.getSelectedItem().getContext();
		tipoDecoracion = cmbDecoracion.getSelectedItem().getContext();
		nivel = cmbNivelEconomico.getSelectedItem().getContext();
		descripcion = cdtDescripcion.getValue();
		justificacion = cdtJustificacion.getValue();
		nombre = txtNombre.getValue();
		patente = txtPatente.getValue();
		rif = txtRif.getValue();
		duracion = txtDuracion.getValue();
		ciudad = txtCiudad.getValue();
		contacto = txtContacto.getValue();
		direccion = txtDireccion.getValue();
		mail = txtEmail.getValue();
		nombreActividad = txtNombreActividad.getValue();
		telefono = txtTelefono.getValue();
		Marca marca = servicioMarca.buscar(cmbMarcaSugerida.getSelectedItem()
				.getContext());
		Date valorFecha = dtbActividad.getValue();
		int personas = spnPersonas.getValue();
		double costo = txtCosto.getValue();
		double alto = dspAlto.getValue();
		double ancho = dspAncho.getValue();
		double largo = dspLargo.getValue();
		Timestamp fechaActividad = new Timestamp(valorFecha.getTime());
		byte[] imagenUsuario1 = null;
		if (media1 instanceof org.zkoss.image.Image
				&& imagen1.getContent() != null) {
			imagenUsuario1 = imagen1.getContent().getByteData();

		}
		byte[] imagenUsuario2 = null;
		if (media2 instanceof org.zkoss.image.Image
				&& imagen2.getContent() != null) {
			imagenUsuario2 = imagen2.getContent().getByteData();

		}
		byte[] imagenUsuario3 = null;
		if (media3 instanceof org.zkoss.image.Image
				&& imagen3.getContent() != null) {
			imagenUsuario3 = imagen3.getContent().getByteData();

		}
		byte[] imagenUsuario4 = null;
		if (media4 instanceof org.zkoss.image.Image
				&& imagen4.getContent() != null) {
			imagenUsuario4 = imagen4.getContent().getByteData();

		}

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

		PlanillaFachada planillaFachada = new PlanillaFachada(id, usuario,
				marca, nombreActividad, fechaActividad, tipoActividad, ciudad,
				contacto, nombre, rif, telefono, direccion, mail, personas,
				duracion, nivel, patente, costo, descripcion, justificacion,
				tipoDecoracion, formato, salidaArte, alto, largo, ancho,
				imagenUsuario1, imagenUsuario2, imagenUsuario3, imagenUsuario4,
				fechaHora, fechaEnvio, horaAuditoria, nombreUsuarioSesion(),
				string, usuario.getZona().getDescripcion(), tipoConfig, "", 0,
				"");
		servicioPlanillaFachada.guardar(planillaFachada);

		if (id != 0)
			planillaFachada = servicioPlanillaFachada.buscar(id);
		else
			planillaFachada = servicioPlanillaFachada.buscarUltima();

		if (inbox) {
			PlanillaGenerica planillita = new PlanillaGenerica(
					planillaFachada.getIdPlanillaFachada(),
					usuario.getNombre(), marca.getDescripcion(),
					nombreActividad, planillaFachada.getFechaEnvio(), string,
					"Fachada y Decoraciones");
			int indice = listaGenerica.indexOf(planillaGenerica);
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(indice, planillita);
			control.actualizar(listaGenerica, catalogoGenerico);
		}

		if (guardo)
			guardarBitacora(planillaFachada, true);
		if (envio) {
			guardarBitacora(planillaFachada, false);
			enviarEmail(tipoConfig, nombreUsuarioSesion(),
					planillaFachada.getIdPlanillaFachada(),
					"Fachada y Decoraciones", usuario.getMail());
		}

		guardarRecursos(planillaFachada);
	}

	private void guardarRecursos(PlanillaFachada planillaFachada) {
		List<RecursoPlanillaFachada> recursosPlanilla = new ArrayList<RecursoPlanillaFachada>();
		for (int i = 0; i < ltbRecursosAgregados.getItemCount(); i++) {
			Listitem listItem = ltbRecursosAgregados.getItemAtIndex(i);
			Integer solicitado = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Integer aprobado = ((Spinner) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			long recursoId = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Recurso recurso = servicioRecurso.buscar(recursoId);
			RecursoPlanillaFachada recursoPlanilla = new RecursoPlanillaFachada(
					recurso, planillaFachada, solicitado, aprobado);
			recursosPlanilla.add(recursoPlanilla);
		}
		servicioRecursoPlanillaFachada.guardar(recursosPlanilla);
	}

	private void guardarBitacora(PlanillaFachada planillaFachada,
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
			BitacoraFachada bitacora = new BitacoraFachada(0, planillaFachada,
					"Planilla en Edicion", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			servicioBitacoraFachada.guardar(bitacora);
		} else {

			if (id == 0) {
				BitacoraFachada bitacora = new BitacoraFachada(0,
						planillaFachada, "Planilla en Edicion", fechaHora,
						fechaHora, horaAuditoria, nombreUsuarioSesion(), imagen);
				servicioBitacoraFachada.guardar(bitacora);
			}
			List<BitacoraFachada> listaBitacoras = new ArrayList<BitacoraFachada>();
			BitacoraFachada bitacora = new BitacoraFachada(0, planillaFachada,
					"Planilla Enviada", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraFachada bitacora2 = new BitacoraFachada(0, planillaFachada,
					"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraFachada bitacora3 = new BitacoraFachada(0, planillaFachada,
					"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraFachada bitacora4 = new BitacoraFachada(0, planillaFachada,
					"Esperando Pago de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraFachada.guardarBitacoras(listaBitacoras);
		}
	}

	protected boolean validar() {
		if (txtCiudad.getText().compareTo("") == 0
				|| txtContacto.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtDireccion.getText().compareTo("") == 0
				|| txtDuracion.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| txtEmail.getText().compareTo("") == 0
				|| txtPatente.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| cmbMarcaSugerida.getText().compareTo("") == 0
				|| cmbNivelEconomico.getText().compareTo("") == 0
				|| cmbActividad.getText().compareTo("") == 0
				|| cmbArte.getText().compareTo("") == 0
				|| cmbFormato.getText().compareTo("") == 0
				|| cmbDecoracion.getText().compareTo("") == 0
				|| cdtDescripcion.getValue().compareTo("") == 0
				|| cdtJustificacion.getValue().compareTo("") == 0
				|| dtbActividad.getText().compareTo("") == 0
				|| spnPersonas.getText().compareTo("") == 0
				|| dspAlto.getText().compareTo("") == 0
				|| dspAncho.getText().compareTo("") == 0
				|| dspLargo.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarCorreo(txtEmail.getValue())) {
				msj.mensajeAlerta(Mensaje.correoInvalido);
				return false;
			} else {
				if (!Validador.validarTelefono(txtTelefono.getValue())) {
					msj.mensajeAlerta(Mensaje.correoInvalido);
					return false;
				} else
					return true;
			}
		}
	}

	private void llenarListas() {
		PlanillaFachada planilla = servicioPlanillaFachada.buscar(id);

		recursos = servicioRecurso.buscarDisponiblesFachada(planilla);
		ltbRecursos.setModel(new ListModelList<Recurso>(recursos));
		recursosAgregados = servicioRecursoPlanillaFachada
				.buscarPorPlanilla(planilla);
		ltbRecursosAgregados
				.setModel(new ListModelList<RecursoPlanillaFachada>(
						recursosAgregados));

		listasMultiples();
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "00");
		cmbFormato.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "16");
		cmbArte.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "01");
		cmbDecoracion.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "17");
		cmbActividad.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "04");
		cmbNivelEconomico.setModel(new ListModelList<F0005>(udc));
	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
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

	@Listen("onClick = #pasar1Recurso")
	public void derechaRecurso() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbRecursos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Recurso recurso = listItem.get(i).getValue();
					recursos.remove(recurso);
					RecursoPlanillaFachada recursoPlanilla = new RecursoPlanillaFachada();
					recursoPlanilla.setRecurso(recurso);
					recursosAgregados.clear();
					for (int j = 0; j < ltbRecursosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbRecursosAgregados
								.getItemAtIndex(j);
						Integer solicitado = ((Spinner) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						Integer aprobado = ((Spinner) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						long recursoId = ((Spinner) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						Recurso recursoj = servicioRecurso.buscar(recursoId);
						RecursoPlanillaFachada recursoPlanillaj = new RecursoPlanillaFachada(
								recursoj, null, solicitado, aprobado);
						recursosAgregados.add(recursoPlanillaj);
					}
					recursosAgregados.add(recursoPlanilla);
					ltbRecursosAgregados
							.setModel(new ListModelList<RecursoPlanillaFachada>(
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
					RecursoPlanillaFachada recursoPlanilla = listItem2.get(i)
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

	public void buscarCatalogoPropio() {
		final List<PlanillaFachada> listPlanilla = servicioPlanillaFachada
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaFachada>(catalogoFachada,
				"Planillas de Fachadas y Decoraciones", listPlanilla, true,
				"Nombre Actividad", "Ciudad", "Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaFachada> buscar(List<String> valores) {

				List<PlanillaFachada> lista = new ArrayList<PlanillaFachada>();

				for (PlanillaFachada planilla : listPlanilla) {
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
			protected String[] crearRegistros(PlanillaFachada planillaCata) {
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
		catalogo.setParent(catalogoFachada);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoFachada")
	public void seleccionPropia() {
		PlanillaFachada planilla = catalogo.objetoSeleccionadoDelCatalogo();
		setearCampos(planilla);
		llenarListas();
		catalogo.setParent(null);
	}

	private void setearCampos(PlanillaFachada planilla) {
		txtCiudad.setValue(planilla.getCiudad());
		txtNombre.setValue(planilla.getNombrePdv());
		txtPatente.setValue(planilla.getPatente());
		txtRif.setValue(planilla.getRifPdv());
		txtContacto.setValue(planilla.getNombreCliente());
		txtCosto.setValue(planilla.getCosto());
		txtDireccion.setValue(planilla.getDireccionPdv());
		txtDuracion.setValue(planilla.getDuracion());
		txtEmail.setValue(planilla.getCorreoPdv());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		txtTelefono.setValue(planilla.getTelefonoPdv());
		F0005 f05 = servicioF0005.buscar("00", "00", planilla.getFormato());
		cmbFormato.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "16", planilla.getArte());
		cmbArte.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "01", planilla.getTipoDecoracion());
		cmbDecoracion.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "17", planilla.getTipoActividad());
		cmbActividad.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "04", planilla.getNivel());
		cmbNivelEconomico.setValue(f05.getDrdl01());
		cmbMarcaSugerida.setValue(planilla.getMarca().getDescripcion());
		spnPersonas.setValue(planilla.getPersonas());
		dspAlto.setValue(planilla.getAlto());
		dspAncho.setValue(planilla.getAncho());
		dspLargo.setValue(planilla.getLargo());
		cdtDescripcion.setValue(planilla.getDescripcion());
		cdtJustificacion.setValue(planilla.getJustificacion());
		dtbActividad.setValue(planilla.getFechaActividad());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanillaFachada();
		BufferedImage imag1;
		if (planilla.getImagenA() != null) {
			try {
				imag1 = ImageIO.read(new ByteArrayInputStream(planilla
						.getImagenA()));
				imagen1.setContent(imag1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedImage imag2;
		if (planilla.getImagenB() != null) {
			try {
				imag2 = ImageIO.read(new ByteArrayInputStream(planilla
						.getImagenB()));
				imagen2.setContent(imag2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedImage imag3;
		if (planilla.getImagenC() != null) {
			try {
				imag3 = ImageIO.read(new ByteArrayInputStream(planilla
						.getImagenC()));
				imagen3.setContent(imag3);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedImage imag4;
		if (planilla.getImagenD() != null) {
			try {
				imag4 = ImageIO.read(new ByteArrayInputStream(planilla
						.getImagenD()));
				imagen4.setContent(imag4);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Listen("onUpload = #fudImagen1")
	public void processMedia1(UploadEvent event) {
		media1 = event.getMedia();
		if (media1 != null) {
			if (media1.getContentType().equals("image/jpeg")
					|| media1.getContentType().equals("image/png")) {
				if (media1.getByteData().length >= 512000
						&& media1.getByteData().length <= 2048000) {
					imagen1.setContent((org.zkoss.image.Image) media1);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	@Listen("onUpload = #fudImagen2")
	public void processMedia2(UploadEvent event) {
		media2 = event.getMedia();
		if (media2 != null) {
			if (media2.getContentType().equals("image/jpeg")
					|| media2.getContentType().equals("image/png")) {
				if (media2.getByteData().length >= 512000
						&& media2.getByteData().length <= 2048000) {
					imagen2.setContent((org.zkoss.image.Image) media2);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	@Listen("onUpload = #fudImagen3")
	public void processMedia3(UploadEvent event) {
		media3 = event.getMedia();
		if (media3 != null) {
			if (media3.getContentType().equals("image/jpeg")
					|| media3.getContentType().equals("image/png")) {
				if (media3.getByteData().length >= 512000
						&& media3.getByteData().length <= 2048000) {
					imagen3.setContent((org.zkoss.image.Image) media3);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	@Listen("onUpload = #fudImagen4")
	public void processMedia4(UploadEvent event) {
		media4 = event.getMedia();
		if (media4 != null) {
			if (media4.getContentType().equals("image/jpeg")
					|| media4.getContentType().equals("image/png")) {
				if (media4.getByteData().length >= 512000
						&& media4.getByteData().length <= 2048000) {
					imagen4.setContent((org.zkoss.image.Image) media4);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono")
	public void validarTelefono2E() throws IOException {
		if (Validador.validarTelefono(txtTelefono.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtEmail")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtEmail.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	@Listen("onClick = #btnRemover1")
	public void limpiar1() {
		org.zkoss.image.Image imagenUsuario1 = null;
		imagen1.setContent(imagenUsuario1);
	}

	@Listen("onClick = #btnRemover2")
	public void limpiar2() {
		org.zkoss.image.Image imagenUsuario1 = null;
		imagen2.setContent(imagenUsuario1);
	}

	@Listen("onClick = #btnRemover3")
	public void limpiar3() {
		org.zkoss.image.Image imagenUsuario1 = null;
		imagen3.setContent(imagenUsuario1);
	}

	@Listen("onClick = #btnRemover4")
	public void limpiar4() {
		org.zkoss.image.Image imagenUsuario1 = null;
		imagen4.setContent(imagenUsuario1);
	}

}
