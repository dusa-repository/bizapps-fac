package controlador.transacciones;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraFachada;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaFachada;

import org.zkforge.ckez.CKeditor;
import org.zkoss.util.media.Media;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
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

	@Override
	public void inicializar() throws IOException {
		cargarCombos();
		txtRespActividad.setValue(nombreUsuarioSesion());
		txtRespZona.setValue(usuarioSesion(nombreUsuarioSesion())
				.getSupervisor());
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwArte);
			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub

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
				id = 0;
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
										PlanillaArte planilla = servicioPlanillaArte
												.buscar(id);
										servicioBitacoraArte
												.eliminarPorPlanilla(planilla);
										servicioPlanillaArte.eliminar(id);
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
				}
			}
		};
		botoneraSolicitudArte.appendChild(botonera);
	}

	protected void guardarDatos(String string) {
		String nombreLocal, patente, rif, nombreActividad, formato, salidaArte, lineamiento;
		formato = cmbFormato.getSelectedItem().getContext();
		salidaArte = cmbArte.getSelectedItem().getContext();
		lineamiento = cdtLineamiento.getValue();
		nombreLocal = txtNombreLocal.getValue();
		patente = txtPatente.getValue();
		rif = txtRif.getValue();
		nombreActividad = txtNombreActividad.getValue();
		Usuario usuario = usuarioSesion(nombreUsuarioSesion());
		Marca marca = servicioMarca.buscar(cmbMarcaSugerida.getSelectedItem()
				.getContext());
		double alto = dspAlto.getValue();
		double ancho = dspAncho.getValue();
		double largo = dspLargo.getValue();
		byte[] imagenUsuario1 = null;
		if (media1 instanceof org.zkoss.image.Image) {
			imagenUsuario1 = imagen1.getContent().getByteData();
		}
		byte[] imagenUsuario2 = null;
		if (media2 instanceof org.zkoss.image.Image) {
			imagenUsuario2 = imagen2.getContent().getByteData();
		}
		byte[] imagenUsuario3 = null;
		if (media3 instanceof org.zkoss.image.Image) {
			imagenUsuario3 = imagen3.getContent().getByteData();
		}
		byte[] imagenUsuario4 = null;
		if (media4 instanceof org.zkoss.image.Image) {
			imagenUsuario4 = imagen4.getContent().getByteData();
		}
		PlanillaArte planillaArte = new PlanillaArte(id, usuario, marca,
				nombreActividad, nombreLocal, salidaArte, rif, patente,
				formato, alto, largo, ancho, imagenUsuario1, imagenUsuario2,
				imagenUsuario3, imagenUsuario4, lineamiento, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), string, usuario.getZona()
						.getDescripcion());
		servicioPlanillaArte.guardar(planillaArte);
		if (id != 0)
			planillaArte = servicioPlanillaArte.buscar(id);
		else
			planillaArte = servicioPlanillaArte.buscarUltima();
		guardarBitacora(planillaArte, string);
	}

	private void guardarBitacora(PlanillaArte planillaArte, String string) {
		BitacoraArte bitacora = new BitacoraArte(0, planillaArte, string,
				fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion());
		servicioBitacoraArte.guardar(bitacora);
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
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
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
	
	@Listen("onClick = #btnBuscarPlanillas")
	public void buscarCatalogoPropio() {
		final List<PlanillaArte> listPlanilla = servicioPlanillaArte
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaArte>(catalogoSolicitudArte,
				"PlanillaCata", listPlanilla, true, "Nombre Actividad",
				"Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaArte> buscar(List<String> valores) {

				List<PlanillaArte> lista = new ArrayList<PlanillaArte>();

				for (PlanillaArte planilla : listPlanilla) {
					if (planilla.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0))
							&& planilla.getMarca().getDescripcion()
									.toLowerCase().startsWith(valores.get(1))
							&& String
									.valueOf(
											planilla.getFechaAuditoria()
													.getTime()).toLowerCase()
									.startsWith(valores.get(2))) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaArte planillaCata) {
				String[] registros = new String[3];
				registros[0] = planillaCata.getNombreActividad();
				registros[1] = planillaCata.getMarca().getDescripcion();
				registros[2] = String.valueOf(planillaCata.getFechaAuditoria()
						.getTime());
				return registros;
			}
		};
		catalogo.setParent(catalogoSolicitudArte);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoSolicitudArte")
	public void seleccionPropia() {
		PlanillaArte planilla = catalogo.objetoSeleccionadoDelCatalogo();
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
		catalogo.setParent(null);
	}

	@Listen("onUpload = #fudImagen1")
	public void processMedia1(UploadEvent event) {
		media1 = event.getMedia();
		imagen1.setContent((org.zkoss.image.Image) media1);
	}

	@Listen("onUpload = #fudImagen2")
	public void processMedia2(UploadEvent event) {
		media2 = event.getMedia();
		imagen2.setContent((org.zkoss.image.Image) media2);
	}

	@Listen("onUpload = #fudImagen3")
	public void processMedia3(UploadEvent event) {
		media3 = event.getMedia();
		imagen3.setContent((org.zkoss.image.Image) media3);
	}

	@Listen("onUpload = #fudImagen4")
	public void processMedia4(UploadEvent event) {
		media4 = event.getMedia();
		imagen4.setContent((org.zkoss.image.Image) media4);
	}
}
