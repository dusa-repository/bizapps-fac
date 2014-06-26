package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraPromocion;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaPromocion;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CPromocion extends CGenerico {

	private static final long serialVersionUID = 3943872144535865912L;
	@Wire
	private Window wdwPromocion;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabInformacion;
	@Wire
	private Textbox txtNombreActividad;
	@Wire
	private Textbox txtRespActividad;
	@Wire
	private Textbox txtRespZona;
	@Wire
	private Combobox cmbActividad;
	@Wire
	private Textbox txtLocal;
	@Wire
	private Textbox txtCiudad;
	@Wire
	private Textbox txtEstado;
	@Wire
	private Textbox txtNombreLocal;
	@Wire
	private Textbox txtRif;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Datebox dtbInicio;
	@Wire
	private Datebox dtbFin;
	@Wire
	private Textbox txtResponsablePDV;
	@Wire
	private Textbox txtEMail;
	@Wire
	private Combobox cmbMarca1;
	@Wire
	private Combobox cmbMarca2;
	@Wire
	private Combobox cmbFrecuencia;
	@Wire
	private Combobox cmbModalidad;
	@Wire
	private Combobox cmbPop;
	@Wire
	private Combobox cmbExtra;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private CKeditor cdtMarca1;
	@Wire
	private CKeditor cdtMarca2;
	@Wire
	private Div botoneraPromocion;
	@Wire
	private Div catalogoPromocion;
	Catalogo<PlanillaPromocion> catalogo;
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

	@Override
	public void inicializar() throws IOException {
		cargarCombos();
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
				cerrarVentana(wdwPromocion);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtNombreLocal.setValue("");
				txtNombreActividad.setValue("");
				txtCiudad.setValue("");
				txtRif.setValue("");
				txtCosto.setValue(null);
				txtDireccion.setValue("");
				txtEMail.setValue("");
				txtEstado.setValue("");
				txtLocal.setValue("");
				txtResponsablePDV.setValue("");
				txtTelefono.setValue("");
				cmbActividad.setValue("");
				cmbExtra.setValue("");
				cmbFrecuencia.setValue("");
				cmbMarca1.setValue("");
				cmbMarca2.setValue("");
				cmbModalidad.setValue("");
				cmbPop.setValue("");
				cdtMarca1.setValue("");
				cdtMarca2.setValue("");
				tabDatos.setSelected(true);
				usuarioEditador = null;
				estadoInbox = "";
				tipoInbox = "";
				inbox = false;
				id = 0;
				listaGenerica.clear();
				planillaGenerica = null;
				catalogoGenerico = null;
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
										PlanillaPromocion planilla = servicioPlanillaPromocion
												.buscar(id);
										servicioBitacoraPromocion
												.eliminarPorPlanilla(planilla);
										servicioPlanillaPromocion.eliminar(id);
										limpiar();
										msj.mensajeInformacion(Mensaje.eliminado);
									}
								}
							});
				} else {
					msj.mensajeInformacion(Mensaje.noSeleccionoRegistro);
				}
			}

			@Override
			public void atras() {
				if (tabInformacion.isSelected())
					tabDatos.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabDatos.isSelected())
					tabInformacion.setSelected(true);
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
		botoneraPromocion.appendChild(botonera);
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				PlanillaPromocion planilla = servicioPlanillaPromocion
						.buscar((Long) map.get("id"));
				estadoInbox = (String) map.get("estadoInbox");
				usuarioEditador = planilla.getUsuario();
				tipoInbox = planilla.getTipo();
				listaGenerica = (List<PlanillaGenerica>) map.get("lista");
				planillaGenerica = (PlanillaGenerica) map.get("planilla");
				catalogoGenerico =  (Catalogo<PlanillaGenerica>) map.get("catalogo");
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
		
		String nombreLocal, local, rif, nombreActividad, tipoActividad, modalidad, material, frecuencia, ciudad, email, estado, responsable, telefono, extra, descripcion1, descripcion2, direccion;
		tipoActividad = cmbActividad.getSelectedItem().getContext();
		extra = cmbExtra.getSelectedItem().getContext();
		frecuencia = cmbFrecuencia.getSelectedItem().getContext();
		modalidad = cmbModalidad.getSelectedItem().getContext();
		material = cmbPop.getSelectedItem().getContext();
		descripcion1 = cdtMarca1.getValue();
		descripcion2 = cdtMarca2.getValue();
		nombreLocal = txtNombreLocal.getValue();
		ciudad = txtCiudad.getValue();
		direccion = txtDireccion.getValue();
		email = txtEMail.getValue();
		estado = txtEstado.getValue();
		responsable = txtResponsablePDV.getValue();
		local = txtLocal.getValue();
		telefono = txtTelefono.getValue();
		rif = txtRif.getValue();
		nombreActividad = txtNombreActividad.getValue();
		Usuario usuario = new Usuario();
		if (inbox)
			usuario = usuarioEditador;
		else
			usuario = usuarioSesion(nombreUsuarioSesion());
		
		if (!estadoInbox.equals("Pendiente") && string.equals("Pendiente"))
			envio = true;

		if (!estadoInbox.equals("Pendiente") && string.equals("En Edicion"))
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
		Marca marca1 = servicioMarca.buscar(cmbMarca1.getSelectedItem()
				.getContext());
		Marca marca2 = servicioMarca.buscar(cmbMarca2.getSelectedItem()
				.getContext());
		double costo = txtCosto.getValue();
		Date valorFecha1 = dtbInicio.getValue();
		Timestamp fechaInicio = new Timestamp(valorFecha1.getTime());
		Date valorFecha2 = dtbFin.getValue();
		Timestamp fechaFin = new Timestamp(valorFecha2.getTime());
		PlanillaPromocion planillaPromocion = new PlanillaPromocion(id,
				usuario, marca1, marca2, nombreActividad, tipoActividad, local,
				ciudad, estado, responsable, nombreLocal, rif, telefono, email,
				direccion, fechaInicio, fechaFin, modalidad, frecuencia,
				material, extra, costo, descripcion1, descripcion2, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), string, usuario.getZona()
						.getDescripcion(), tipoConfig, "", 0);
		servicioPlanillaPromocion.guardar(planillaPromocion);
		if (id != 0)
			planillaPromocion = servicioPlanillaPromocion.buscar(id);
		else
			planillaPromocion = servicioPlanillaPromocion.buscarUltima();
		
		if (inbox) {
			PlanillaGenerica planillita = new PlanillaGenerica(
					planillaPromocion.getIdPlanillaPromocion(), usuario.getNombre(),
					marca1.getDescripcion(), nombreActividad, fechaHora, string,
					"Promociones de Marca");
			listaGenerica.remove(planillaGenerica);
			listaGenerica.add(planillita);
			control.actualizar(listaGenerica,catalogoGenerico);
		}
		
		if (guardo)
			guardarBitacora(planillaPromocion, true);
		if (envio)
			guardarBitacora(planillaPromocion, false);
		
		if (tipoConfig.equals("TradeMark") && string.equals("Pendiente")
				&& !inbox) {
			Configuracion con = servicioConfiguracion
					.buscarTradeMark("TradeMark");
			Usuario usuarioAdmin = new Usuario();
			if (con != null)
				usuarioAdmin = con.getUsuario();
			PlanillaPromocion planillaAdmin = new PlanillaPromocion(0,
					usuarioAdmin, marca1, marca2, nombreActividad,
					tipoActividad, local, ciudad, estado, responsable,
					nombreLocal, rif, telefono, email, direccion, fechaInicio,
					fechaFin, modalidad, frecuencia, material, extra, costo,
					descripcion1, descripcion2, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), string, usuario.getZona()
							.getDescripcion(), "Marca", "",
					planillaPromocion.getIdPlanillaPromocion());
			servicioPlanillaPromocion.guardar(planillaAdmin);
			planillaAdmin = servicioPlanillaPromocion.buscarUltima();
			guardarBitacora(planillaAdmin, false);
		}
	}

	private void guardarBitacora(PlanillaPromocion planillaPromocion,
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
			BitacoraPromocion bitacora = new BitacoraPromocion(0, planillaPromocion,
					"Planilla en Edicion", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			servicioBitacoraPromocion.guardar(bitacora);
		} else {
			List<BitacoraPromocion> listaBitacoras = new ArrayList<BitacoraPromocion>();
			BitacoraPromocion bitacora = new BitacoraPromocion(0, planillaPromocion,
					"Planilla Enviada", fechaHora, fechaHora, horaAuditoria,
					nombreUsuarioSesion(), imagen);
			listaBitacoras.add(bitacora);

			BitacoraPromocion bitacora2 = new BitacoraPromocion(0, planillaPromocion,
					"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora2);

			BitacoraPromocion bitacora3 = new BitacoraPromocion(0, planillaPromocion,
					"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora3);

			BitacoraPromocion bitacora4 = new BitacoraPromocion(0, planillaPromocion,
					"Esperando Pago de Planilla", fechaHora, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), imagenX);
			listaBitacoras.add(bitacora4);

			servicioBitacoraPromocion.guardarBitacoras(listaBitacoras);
		}
	}

	protected boolean validar() {
		if (txtNombreLocal.getText().compareTo("") == 0
				|| txtNombreActividad.getText().compareTo("") == 0
				|| txtCiudad.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtDireccion.getText().compareTo("") == 0
				|| txtEMail.getText().compareTo("") == 0
				|| txtEstado.getText().compareTo("") == 0
				|| txtLocal.getText().compareTo("") == 0
				|| txtResponsablePDV.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0
				|| cmbPop.getText().compareTo("") == 0
				|| cmbModalidad.getText().compareTo("") == 0
				|| cmbMarca2.getText().compareTo("") == 0
				|| cmbActividad.getText().compareTo("") == 0
				|| cmbExtra.getText().compareTo("") == 0
				|| cmbFrecuencia.getText().compareTo("") == 0
				|| cmbMarca1.getText().compareTo("") == 0
				|| cdtMarca1.getValue().compareTo("") == 0
				|| cdtMarca2.getValue().compareTo("") == 0) {
			msj.mensajeInformacion(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarCorreo(txtEMail.getValue())) {
				Messagebox.show("Formato de Correo No Valido", "Alerta",
						Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			} else {
				if (!Validador.validarTelefono(txtTelefono.getValue())) {
					Messagebox.show("Formato de Telefono No Valido", "Alerta",
							Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				} else
					return true;
			}
		}
	}

	private void cargarCombos() {
		List<F0005> udc = servicioF0005.buscarParaUDCOrdenados("00", "03");
		cmbActividad.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "10");
		cmbExtra.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "07");
		cmbFrecuencia.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "09");
		cmbModalidad.setModel(new ListModelList<F0005>(udc));

		udc = servicioF0005.buscarParaUDCOrdenados("00", "08");
		cmbPop.setModel(new ListModelList<F0005>(udc));
	}

	public ListModelList<Marca> getMarcas() {
		marcas = new ListModelList<Marca>(servicioMarca.buscarTodosOrdenados());
		return marcas;
	}

	
	public void buscarCatalogoPropio() {
		final List<PlanillaPromocion> listPlanilla = servicioPlanillaPromocion
				.buscarTodosOrdenados(usuarioSesion(nombreUsuarioSesion()));
		catalogo = new Catalogo<PlanillaPromocion>(catalogoPromocion,
				"Planillas de Promociones de Marca", listPlanilla, true, "Nombre Actividad",
				"Marca", "Fecha Edicion") {

			@Override
			protected List<PlanillaPromocion> buscar(List<String> valores) {

				List<PlanillaPromocion> lista = new ArrayList<PlanillaPromocion>();

				for (PlanillaPromocion planilla : listPlanilla) {
					if (planilla.getNombreActividad().toLowerCase()
							.startsWith(valores.get(0))
							&& planilla.getMarcaA().getDescripcion()
									.toLowerCase().startsWith(valores.get(1))
							&& String
									.valueOf(
											formatoFecha.format(planilla
													.getFechaAuditoria()))
									.toLowerCase().startsWith(valores.get(2))) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaPromocion planillaCata) {
				String[] registros = new String[3];
				registros[0] = planillaCata.getNombreActividad();
				registros[1] = planillaCata.getMarcaA().getDescripcion();
				registros[2] = String.valueOf(formatoFecha.format(planillaCata
						.getFechaAuditoria()));
				return registros;
			}
		};
		catalogo.setParent(catalogoPromocion);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoPromocion")
	public void seleccionPropia() {
		PlanillaPromocion planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		catalogo.setParent(null);
	}

	private void settearCampos(PlanillaPromocion planilla) {
		txtCiudad.setValue(planilla.getCiudad());
		txtRif.setValue(planilla.getRifPdv());
		txtEMail.setValue(planilla.getCorreoPdv());
		txtNombreLocal.setValue(planilla.getNombreCliente());
		txtNombreActividad.setValue(planilla.getNombreActividad());
		txtRespActividad.setValue(planilla.getUsuario().getIdUsuario());
		txtRespZona.setValue(planilla.getUsuario().getSupervisor());
		txtCosto.setValue(planilla.getCosto());
		txtDireccion.setValue(planilla.getDireccionPdv());
		txtEstado.setValue(planilla.getEstadoPdv());
		txtLocal.setValue(planilla.getTipoLocal());
		txtResponsablePDV.setValue(planilla.getNombrePdv());
		txtTelefono.setValue(planilla.getTelefonoPdv());
		F0005 f05 = servicioF0005
				.buscar("00", "10", planilla.getComunicacion());
		cmbExtra.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "07", planilla.getFrecuenciaPago());
		cmbFrecuencia.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "03", planilla.getTipoActividad());
		cmbActividad.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "09", planilla.getModalidadPago());
		cmbModalidad.setValue(f05.getDrdl01());
		f05 = servicioF0005.buscar("00", "08", planilla.getMaterial());
		cmbPop.setValue(f05.getDrdl01());
		dtbFin.setValue(planilla.getFechaHasta());
		dtbInicio.setValue(planilla.getFechaDesde());
		cmbMarca1.setValue(planilla.getMarcaA().getDescripcion());
		cmbMarca2.setValue(planilla.getMarcaB().getDescripcion());
		cdtMarca1.setValue(planilla.getDescripcionMarcaA());
		cdtMarca2.setValue(planilla.getDescripcionMarcaB());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanillaPromocion();
	}
	

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono")
	public void validarTelefono2E() throws IOException {
		if (Validador.validarTelefono(txtTelefono.getValue()) == false) {
			Messagebox.show("Formato de Telefono No Valido", "Alerta",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtEMail")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtEMail.getValue()) == false) {
			Messagebox.show("Correo Electronico Invalido", "Alerta",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

}
