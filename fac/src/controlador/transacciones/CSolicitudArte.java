package controlador.transacciones;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.estado.BitacoraArte;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;

import org.zkforge.ckez.CKeditor;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CSolicitudArte extends CGenerico {

	private static final long serialVersionUID = 2423234035533735428L;
	@Wire
	private Window wdwArte;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabLineamientos;
	@Wire
	private Tab tabImagenes;
	@Wire
	private Textbox txtNombreActividad;
	@Wire
	private Textbox txtRespActividad;
	@Wire
	private Textbox txtRespZona;
	@Wire
	private Combobox cmbMarcaSugerida;
	@Wire
	private Combobox cmbArte;
	@Wire
	private Combobox cmbFormato;
	@Wire
	private Textbox txtNombreLocal;
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
	private Textbox txtRif;
	@Wire
	private Textbox txtPatente;
	@Wire
	private CKeditor cdtLineamiento;
	@Wire
	private Div botoneraSolicitudArte;
	@Wire
	private Div catalogoSolicitudArte;
	Catalogo<PlanillaArte> catalogo;
	ListModelList<Marca> marcas;
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
				cerrarVentana(wdwArte);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtNombreLocal.setValue("");
				txtNombreActividad.setValue("");
				txtPatente.setValue("");
				txtRif.setValue("");
				cmbMarcaSugerida.setValue("");
				cmbArte.setValue("");
				cmbFormato.setValue("");
				dspAlto.setValue((double) 0);
				dspAncho.setValue((double) 0);
				dspLargo.setValue((double) 0);
				cdtLineamiento.setValue("");
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
					Messagebox.show("¿Esta Seguro de Eliminar la Planilla?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										PlanillaArte planilla = servicioPlanillaArte
												.buscar(id);
										servicioBitacoraArte
												.eliminarPorPlanilla(planilla);
										servicioPlanillaArte.eliminar(id);
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
				if (tabLineamientos.isSelected())
					tabDatos.setSelected(true);
				if (tabImagenes.isSelected())
					tabLineamientos.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabLineamientos.isSelected())
					tabImagenes.setSelected(true);
				if (tabDatos.isSelected())
					tabLineamientos.setSelected(true);
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
		botoneraSolicitudArte.appendChild(botonera);
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaArte planilla = servicioPlanillaArte.buscar((Long) map
						.get("id"));
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
	}

	protected void guardarDatos(String string) {

		boolean envio = false;
		boolean guardo = false;

		String nombreLocal, patente, rif, nombreActividad, formato, salidaArte, lineamiento;
		formato = cmbFormato.getSelectedItem().getContext();
		salidaArte = cmbArte.getSelectedItem().getContext();
		lineamiento = cdtLineamiento.getValue();
		nombreLocal = txtNombreLocal.getValue();
		patente = txtPatente.getValue();
		rif = txtRif.getValue();
		nombreActividad = txtNombreActividad.getValue();
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
		double alto = dspAlto.getValue();
		double ancho = dspAncho.getValue();
		double largo = dspLargo.getValue();
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
		PlanillaArte planillaArte = new PlanillaArte(id, usuario, marca,
				nombreActividad, nombreLocal, salidaArte, rif, patente,
				formato, alto, largo, ancho, imagenUsuario1, imagenUsuario2,
				imagenUsuario3, imagenUsuario4, lineamiento, fechaHora,
				fechaEnvio, horaAuditoria, nombreUsuarioSesion(), string,
				usuario.getZona().getDescripcion(), tipoConfig, "", 0, "");
		servicioPlanillaArte.guardar(planillaArte);
		if (id != 0)
			planillaArte = servicioPlanillaArte.buscar(id);
		else
			planillaArte = servicioPlanillaArte.buscarUltima();

		if (inbox) {
			PlanillaGenerica planillita = new PlanillaGenerica(
					planillaArte.getIdPlanillaArte(), usuario.getNombre(),
					marca.getDescripcion(), nombreActividad,
					planillaArte.getFechaEnvio(), string,
					"Solicitud de Arte y Publicaciones");
			int indice = listaGenerica.indexOf(planillaGenerica);
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(indice, planillita);
			control.actualizar(listaGenerica, catalogoGenerico);
		}

		if (guardo)
			guardarBitacora(planillaArte, true);
		if (envio)
			guardarBitacora(planillaArte, false);
	}

	private void guardarBitacora(PlanillaArte planillaArte, boolean edicion) {

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
			BitacoraArte bitacora = new BitacoraArte(0, planillaArte,
					"Planilla en Edicion", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			servicioBitacoraArte.guardar(bitacora);
		} else {
			if (id == 0) {
				BitacoraArte bitacora = new BitacoraArte(0, planillaArte,
						"Planilla en Edicion", fechaHora, fechaHora,
						horaAuditoria, nombreUsuarioSesion(), imagen);
				servicioBitacoraArte.guardar(bitacora);
			}
			List<BitacoraArte> listaBitacoras = new ArrayList<BitacoraArte>();
			BitacoraArte bitacora = new BitacoraArte(0, planillaArte,
					"Planilla Enviada", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraArte bitacora2 = new BitacoraArte(0, planillaArte,
					"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraArte bitacora3 = new BitacoraArte(0, planillaArte,
					"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraArte bitacora4 = new BitacoraArte(0, planillaArte,
					"Esperando Pago de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraArte.guardarBitacoras(listaBitacoras);
		}
	}

	protected boolean validar() {
		if (txtNombreLocal.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtPatente.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| cmbMarcaSugerida.getText().compareTo("") == 0
				|| cmbArte.getText().compareTo("") == 0
				|| cmbFormato.getText().compareTo("") == 0
				|| cdtLineamiento.getValue().compareTo("") == 0
				|| dspAlto.getText().compareTo("") == 0
				|| dspAncho.getText().compareTo("") == 0
				|| dspLargo.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "00");
		cmbFormato.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "01");
		cmbArte.setModel(new ListModelList<F0005>(udc));
	}

	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}

	public void buscarCatalogoPropio() {
		final List<PlanillaArte> listPlanilla = servicioPlanillaArte
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaArte>(catalogoSolicitudArte,
				"Planillas de Arte y Publicaciones", listPlanilla, true,
				"Nombre Actividad", "Marca", "Nombre de Cliente",
				"Fecha Edicion") {

			@Override
			protected List<PlanillaArte> buscar(List<String> valores) {

				List<PlanillaArte> lista = new ArrayList<PlanillaArte>();

				for (PlanillaArte planilla : listPlanilla) {
					if (planilla.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0).toLowerCase())
							&& planilla.getMarca().getDescripcion()
									.toLowerCase()
									.startsWith(valores.get(1).toLowerCase())
							&& planilla.getNombreCliente().toLowerCase()
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
			protected String[] crearRegistros(PlanillaArte planillaCata) {
				String[] registros = new String[4];
				registros[0] = planillaCata.getNombreActividad().toLowerCase();
				registros[1] = planillaCata.getMarca().getDescripcion()
						.toLowerCase();
				registros[2] = planillaCata.getNombreCliente().toLowerCase();
				registros[3] = String.valueOf(formatoFecha.format(planillaCata
						.getFechaAuditoria()));
				return registros;
			}
		};
		catalogo.setParent(catalogoSolicitudArte);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoSolicitudArte")
	public void seleccionPropia() {
		PlanillaArte planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		catalogo.setParent(null);
	}

	private void settearCampos(PlanillaArte planilla) {
		txtPatente.setValue(planilla.getPatente());
		txtRif.setValue(planilla.getRif());
		txtNombreLocal.setValue(planilla.getNombreCliente());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		F0005 f05 = servicioF0005.buscar("00", "00", planilla.getFormato());
		cmbFormato.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "01", planilla.getTipoArte());
		cmbArte.setValue(f05.getDrdl01());
		cmbMarcaSugerida.setValue(planilla.getMarca().getDescripcion());
		dspAlto.setValue(planilla.getAlto());
		dspAncho.setValue(planilla.getAncho());
		dspLargo.setValue(planilla.getLargo());
		cdtLineamiento.setValue(planilla.getLineamiento());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanillaArte();
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
				if (media1.getByteData().length <= 104000) {
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
				if (media2.getByteData().length <= 104000) {
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
				if (media3.getByteData().length <= 104000) {
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
				if (media4.getByteData().length <= 104000) {
					imagen4.setContent((org.zkoss.image.Image) media4);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	@Listen("onClick =#btnModeloArte")
	public void modelo() {
		String ruta = "/vistas/transacciones/VModeloArte.zul";
		Window win = (Window) Executions.createComponents(ruta, null, null);
		win.doModal();
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
