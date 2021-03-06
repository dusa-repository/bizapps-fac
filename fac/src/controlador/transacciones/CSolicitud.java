package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.estado.BitacoraFachada;
import modelo.estado.BitacoraPromocion;
import modelo.estado.BitacoraUniforme;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.seguridad.Usuario;
import modelo.transacciones.ItemDegustacionPlanillaEvento;
import modelo.transacciones.ItemEstimadoPlanillaEvento;
import modelo.transacciones.ItemPlanillaCata;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.RecursoPlanillaCata;
import modelo.transacciones.RecursoPlanillaEvento;
import modelo.transacciones.RecursoPlanillaFachada;
import modelo.transacciones.UniformePlanillaUniforme;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CSolicitud extends CGenerico {

	private static final long serialVersionUID = 1572485553927529288L;
	@Wire("#wdwPagar #lista")
	private Listbox lista;
	@Wire("#wdwPagar")
	private Window wdwPagar;
	@Wire("#wdwMotivo #listaMotivo")
	private Listbox listaMotivo;
	@Wire("#wdwMotivo")
	private Window wdwMotivo;
	@Wire
	private Window wdwSolicitud;
	@Wire
	private Div catalogoSolicitud;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Combobox cmbEstado;
	@Wire
	private Combobox cmbTipo;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbUsuario;
	@Wire
	private Longbox txtCodigo;
	@Wire
	private Image imagenNo;
	@Wire
	private Image imagenSi;
	public Catalogo<PlanillaGenerica> catalogo;
	String titulo = "";
	private boolean tradeMark = false;
	private List<PlanillaGenerica> listPlanilla = new ArrayList<PlanillaGenerica>();
	private List<PlanillaGenerica> items = new ArrayList<PlanillaGenerica>();
	private List<PlanillaGenerica> items2 = new ArrayList<PlanillaGenerica>();
	ListModelList<Marca> marcas;
	ListModelList<F0005> motivos;
	byte[] imagen;
	byte[] imagenX;
	boolean pendiente = true;
	boolean buscado = false;

	@Override
	public void inicializar() throws IOException {
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				authe.getAuthorities());
		Usuario user = new Usuario();
		user.setIdUsuario("%");
		user.setNombre("TODOS");
		List<Usuario> marcasUsers = new ArrayList<Usuario>();
		marcasUsers.add(user);
		for (int i = 0; i < authorities.size(); i++) {
			if (authorities.get(i).getAuthority().equals("TRADE MARKETING")) {
				tradeMark = true;
				grupoDominante = "Administrador";
				i = authorities.size();
			} else {

				if (authorities.get(i).getAuthority().equals("MARCA")) {
					grupoDominante = "Administrador";
					marcasUsers.addAll(servicioUsuario.buscarTodosOrdenados());
					i = authorities.size();
				} else {
					if (authorities.get(i).getAuthority()
							.equals("Gerente Regional")) {
						grupoDominante = "Gerente Regional";
						marcasUsers.addAll(servicioUsuario
								.buscarSupervisados(nombreUsuarioSesion()));
						i = authorities.size();
					} else {
						if (authorities.get(i).getAuthority()
								.equals("Solicitante")) {
							grupoDominante = "Solicitante";
							i = authorities.size();
						}
					}
				}
			}
		}
		if (cmbUsuario != null)
			cmbUsuario.setModel(new ListModelList<Usuario>(marcasUsers));
		if (variable.equals("Generales"))
			variable = "En Edicion";
		buscarCatalogoPropio();

	}

	public ListModelList<Marca> getMarcas() {
		Marca marca = new Marca();
		marca.setDescripcion("TODAS");
		marca.setIdMarca("%");
		List<Marca> marcasLista = new ArrayList<Marca>();
		marcasLista.add(marca);
		marcasLista.addAll(servicioMarca.buscarTodosOrdenados());
		marcas = new ListModelList<Marca>(marcasLista);
		return marcas;
	}

	public void buscarCatalogoPropio() {
		cargarLista();
		final List<PlanillaGenerica> listaCatalogo = listPlanilla;
		catalogo = new Catalogo<PlanillaGenerica>(catalogoSolicitud,
				"PlanillaCata", listaCatalogo, false, "Usuario", "Estado",
				"Codigo Planilla", "Fecha de Envio de Planilla", "Marca",
				"Nombre Actividad", "Tipo Planilla", "Origen") {

			@Override
			protected List<PlanillaGenerica> buscar(List<String> valores) {
				if (listPlanilla.size() == 0) {
					cargarLista();
				}
				if (!pendiente && !buscado) {
					cargarLista();
				}
				List<PlanillaGenerica> lista = new ArrayList<PlanillaGenerica>();

				for (PlanillaGenerica planilla : listPlanilla) {
					if (planilla.getUsuario().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& planilla.getEstado().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& String.valueOf(planilla.getId()).toLowerCase()
									.contains(valores.get(2))
							&& String
									.valueOf(
											formatoFecha.format(planilla
													.getFecha())).toLowerCase()
									.contains(valores.get(3))
							&& planilla.getMarca().toLowerCase()
									.contains(valores.get(4).toLowerCase())
							&& planilla.getNombreActividad().toLowerCase()
									.contains(valores.get(5).toLowerCase())
							&& planilla.getTipoPlanilla().toLowerCase()
									.contains(valores.get(6).toLowerCase())
							&& planilla.getOrigen().toLowerCase()
									.contains(valores.get(7).toLowerCase())) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaGenerica planilla) {
				String[] registros = new String[8];
				registros[0] = planilla.getUsuario();
				registros[1] = planilla.getEstado();
				registros[2] = String.valueOf(planilla.getId());
				registros[3] = String.valueOf(formatoFecha.format(planilla
						.getFecha()));
				registros[4] = planilla.getMarca();
				registros[5] = planilla.getNombreActividad();
				registros[6] = planilla.getTipoPlanilla();
				registros[7] = planilla.getOrigen();
				return registros;
			}
		};
		catalogo.setParent(catalogoSolicitud);
	}

	@Listen("onClick= #btnCerrar")
	public void salir() {
		cerrarVentana(wdwSolicitud);
	}

	@Listen("onClick= #btnProcesar")
	public void procesar() {

		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		final String estatus = "Aprobada";
		final String estadoDefecto = "Esperando Aprobacion de Planilla";
		final String estadoNuevo = "Planilla Aprobada";
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("�Desea Aprobar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus,
											estadoDefecto, estadoNuevo);
									cargarLista();
									catalogo.actualizarLista(listPlanilla);
								}
							}
						});
			}
		}

	}

	@Listen("onClick= #btnCancelar")
	public void cancelar() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		final String estatus = "Cancelada";
		final String estadoDefecto = "";
		final String estadoNuevo = "Planilla Cancelada";
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("�Desea Cancelar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									HashMap<String, Object> mapaRechazo = new HashMap<String, Object>();
									mapaRechazo.put("catalogo", catalogo);
									mapaRechazo.put("estatusViejo", estatus);
									mapaRechazo
											.put("estatusNuevo", estadoNuevo);
									Sessions.getCurrent().setAttribute(
											"rechazador", mapaRechazo);
									Window window = (Window) Executions
											.createComponents(
													"/vistas/componentes/VMotivo.zul",
													wdwSolicitud, mapaRechazo);
									window.doModal();
									listaMotivo = (Listbox) window
											.getChildren().get(1).getChildren()
											.get(1).getChildren().get(0);
									listaMotivo
											.setModel(new ListModelList<PlanillaGenerica>(
													procesadas));
								}
							}
						});
			}
		}

	}

	@Listen("onClick= #btnRechazar")
	public void rechazar() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		final String estatus = "Rechazada";
		final String estadoDefecto = "";
		final String estadoNuevo = "Planilla Rechazada";
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("�Desea Rechazar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									HashMap<String, Object> mapaRechazo = new HashMap<String, Object>();
									mapaRechazo.put("catalogo", catalogo);
									mapaRechazo.put("estatusViejo", estatus);
									mapaRechazo
											.put("estatusNuevo", estadoNuevo);
									Sessions.getCurrent().setAttribute(
											"rechazador", mapaRechazo);
									Window window = (Window) Executions
											.createComponents(
													"/vistas/componentes/VMotivo.zul",
													wdwSolicitud, mapaRechazo);
									window.doModal();
									listaMotivo = (Listbox) window
											.getChildren().get(1).getChildren()
											.get(1).getChildren().get(0);
									listaMotivo
											.setModel(new ListModelList<PlanillaGenerica>(
													procesadas));
								}
							}
						});
			}
		}

	}

	@Listen("onClick= #btnFinalizar")
	public void finalizar() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		final String estatus = "Finalizada";
		final String estadoDefecto = "Esperando Finalizacion de Planilla";
		final String estadoNuevo = "Planilla Finalizada";
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("�Desea Finalizar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus,
											estadoDefecto, estadoNuevo);
									cargarLista();
									catalogo.actualizarLista(listPlanilla);
								}
							}
						});
			}
		}

	}

	@Listen("onClick = #btnPagar")
	public void pagar() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		final String estatus = "Pagada";
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("�Desea Pagar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									HashMap<String, Object> mapita = new HashMap<String, Object>();
									mapita.put("catalogo", catalogo);
									Sessions.getCurrent().setAttribute(
											"pagador", mapita);
									Window window = (Window) Executions
											.createComponents(
													"/vistas/componentes/VPrincipal.zul",
													wdwSolicitud, mapita);
									window.doModal();
									lista = (Listbox) window.getChildren()
											.get(1).getChildren().get(1)
											.getChildren().get(0);
									lista.setModel(new ListModelList<PlanillaGenerica>(
											procesadas));
								}
							}
						});
			}
		}
	}

	@Listen("onClick = #btnVer")
	public void verPlanilla() {
		if (listPlanilla.size() == 0) {
			cargarLista();
		}
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		if (validarSeleccion(procesadas)) {
			if (procesadas.size() == 1) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", procesadas.get(0).getId());
				map.put("estadoInbox", procesadas.get(0).getEstado());
				map.put("lista", listPlanilla);
				map.put("planilla", procesadas.get(0));
				map.put("catalogo", catalogo);
				map.put("fechaInbox", procesadas.get(0).getFecha());
				Sessions.getCurrent().setAttribute("inbox", map);
				Window window = new Window();
				switch (procesadas.get(0).getTipoPlanilla()) {
				case "Eventos Especiales":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VEvento.zul", null, null);
					window.doModal();
					break;
				case "Uniformes":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VUniforme.zul", null, null);
					window.doModal();
					break;
				case "Promociones de Marca":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VPromocion.zul", null, null);
					window.doModal();
					break;
				case "Solicitud de Arte y Publicaciones":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VSolicitudArte.zul", null,
							null);
					window.doModal();
					break;
				case "Cata Induccion":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VCataInduccion.zul", null,
							null);
					window.doModal();
					break;
				case "Fachada y Decoraciones":
					window = (Window) Executions.createComponents(
							"/vistas/transacciones/VFachada.zul", null, null);
					window.doModal();
					break;
				default:
					break;
				}
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		}
	}

	public boolean validarSeleccion(List<PlanillaGenerica> procesadas) {
		if (procesadas == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (procesadas.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean validarEstatus(List<PlanillaGenerica> procesadas,
			String estatus) {
		int contadorAprobada = 0, contadorPendiente = 0, contadorFinalizada = 0, contadorPagada = 0;
		boolean error = false;
		boolean errorEdicion = false;
		for (int i = 0; i < procesadas.size(); i++) {
			if (procesadas.get(i).getEstado().equals("Pendiente"))
				contadorPendiente++;
			if (procesadas.get(i).getEstado().equals("Aprobada"))
				contadorAprobada++;
			if (procesadas.get(i).getEstado().equals("Finalizada"))
				contadorFinalizada++;
			if (procesadas.get(i).getEstado().equals("En Edicion"))
				errorEdicion = true;
			if (procesadas.get(i).getEstado().equals("Pagada"))
				contadorPagada++;
			if (procesadas.get(i).getEstado().equals("Rechazada")
					|| procesadas.get(i).getEstado().equals("Cancelada"))
				error = true;
		}
		if (error) {
			msj.mensajeAlerta(Mensaje.estadoIncorrectoRechazada);
			return false;
		}
		if (errorEdicion) {
			msj.mensajeAlerta(Mensaje.estadoIncorrectoEdicion);
			return false;
		}
		switch (estatus) {
		case "Aprobada":
			if (contadorAprobada != 0 || contadorFinalizada != 0
					|| contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Finalizada":
			if (contadorPendiente != 0 || contadorFinalizada != 0
					|| contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Pagada":
			if (contadorPendiente != 0 || contadorAprobada != 0
					|| contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Rechazada":
			if (contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Cancelada":
			if (contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		default:
			return true;
		}
	}

	public void cambiarEstado(List<PlanillaGenerica> procesadas, String estado,
			String estadoDefecto, String estadoNuevo) {
		pendiente = true;
		buscado = false;
		String tipoConfig = "";
		if (tradeMark)
			tipoConfig = "TradeMark";
		else
			tipoConfig = "Marca";
		List<PlanillaCata> listCata = new ArrayList<PlanillaCata>();
		List<PlanillaFachada> listFachada = new ArrayList<PlanillaFachada>();
		List<PlanillaPromocion> listPromocion = new ArrayList<PlanillaPromocion>();
		List<PlanillaEvento> listEvento = new ArrayList<PlanillaEvento>();
		List<PlanillaArte> listArte = new ArrayList<PlanillaArte>();
		List<PlanillaUniforme> listUniforme = new ArrayList<PlanillaUniforme>();
		List<BitacoraCata> listBitacoraCata = new ArrayList<BitacoraCata>();
		List<BitacoraFachada> listBitacoraFachada = new ArrayList<BitacoraFachada>();
		List<BitacoraPromocion> listBitacoraPromocion = new ArrayList<BitacoraPromocion>();
		List<BitacoraEvento> listBitacoraEvento = new ArrayList<BitacoraEvento>();
		List<BitacoraArte> listBitacoraArte = new ArrayList<BitacoraArte>();
		List<BitacoraUniforme> listBitacoraUniforme = new ArrayList<BitacoraUniforme>();

		URL url = getClass().getResource("/imagenes/si.png");
		try {
			imagenSi.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imagen = imagenSi.getContent().getByteData();

		URL url2 = getClass().getResource("/imagenes/no.png");
		try {
			imagenNo.setContent(new AImage(url2));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imagenX = imagenNo.getContent().getByteData();

		for (int i = 0; i < procesadas.size(); i++) {
			switch (procesadas.get(i).getTipoPlanilla()) {
			case "Eventos Especiales":
				PlanillaEvento planillaEvento = servicioPlanillaEvento
						.buscar(procesadas.get(i).getId());
				planillaEvento.setEstado(estado);
				planillaEvento.setRefencia(procesadas.get(i).getReferencia());
				planillaEvento.setMotivoCancelacion(procesadas.get(i)
						.getMotivo());
				planillaEvento.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaEvento.setHoraAuditoria(horaAuditoria);
				planillaEvento.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaEvento.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraEvento bitacoraE = new BitacoraEvento(0,
							planillaEvento, estadoNuevo, fechaHora, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), imagenX);
					listBitacoraEvento.add(bitacoraE);
					enviarEmailError(tipoConfig, planillaEvento.getUsuario()
							.getIdUsuario(),
							planillaEvento.getIdPlanillaEvento(),
							planillaEvento.getTipo(), planillaEvento
									.getUsuario().getMail(), estado, procesadas
									.get(i).getMotivo(), procesadas.get(i)
									.getDescripcion());

				} else {
					BitacoraEvento bitacoraEvento = servicioBitacoraEvento
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaEvento);
					if (bitacoraEvento == null)
						bitacoraEvento = servicioBitacoraEvento
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaEvento);
					bitacoraEvento.setEstado(estadoNuevo);
					bitacoraEvento.setFechaCambio(fechaHora);
					bitacoraEvento.setHoraAuditoria(horaAuditoria);
					bitacoraEvento.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraEvento.setFechaAuditoria(fechaHora);
					bitacoraEvento.setImagen(imagen);
					listBitacoraEvento.add(bitacoraEvento);
				}

				listEvento.add(planillaEvento);

				if (estado.equals("Aprobada")
						&& planillaEvento.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaEvento nueva = new PlanillaEvento(0, user,
							planillaEvento.getMarca(),
							planillaEvento.getNombreActividad(),
							planillaEvento.getFechaDesde(),
							planillaEvento.getFechaHasta(),
							planillaEvento.getCiudad(),
							planillaEvento.getRegion(),
							planillaEvento.getHoraEvento(),
							planillaEvento.getDireccion(),
							planillaEvento.getPersonas(),
							planillaEvento.getPersonaContacto(),
							planillaEvento.getTelefono(),
							planillaEvento.getNivel(),
							planillaEvento.getEdadTarget(),
							planillaEvento.getMedio(),
							planillaEvento.getVenta(),
							planillaEvento.getCosto(),
							planillaEvento.getDescripcion(),
							planillaEvento.getMecanica(), fechaHora,
							planillaEvento.getFechaEnvio(), horaAuditoria,
							nombreUsuarioSesion(), "Pendiente",
							planillaEvento.getZona(), "Marca", "",
							planillaEvento.getIdPlanillaEvento(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaEvento.guardar(nueva);
					nueva = servicioPlanillaEvento.buscarUltima();
					bitacoraEventos(nueva);

					List<ItemDegustacionPlanillaEvento> itemsDegustacionAgregados = servicioItemDegustacionPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < itemsDegustacionAgregados.size(); j++) {
						itemsDegustacionAgregados.get(j).getId().setPlanillaEvento(
								nueva);
					}
					List<ItemEstimadoPlanillaEvento> itemsEstimacionAgregados = servicioItemEstimadoPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < itemsEstimacionAgregados.size(); j++) {
						itemsEstimacionAgregados.get(j).getId()
								.setPlanillaEvento(nueva);
					}
					List<RecursoPlanillaEvento> recursosAgregados = servicioRecursoPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).getId().setPlanillaEvento(nueva);
					}

					servicioItemEstimadoPlanillaEvento
							.guardar(itemsEstimacionAgregados);
					servicioItemDegustacionPlanillaEvento
							.guardar(itemsDegustacionAgregados);
					servicioRecursoPlanillaEvento.guardar(recursosAgregados);

					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaEvento(), "Eventos Especiales",
							nueva.getUsuario().getMail());
				}

				break;

			case "Uniformes":
				PlanillaUniforme planillaUniforme = servicioPlanillaUniforme
						.buscar(procesadas.get(i).getId());
				planillaUniforme.setEstado(estado);
				planillaUniforme.setRefencia(procesadas.get(i).getReferencia());
				planillaUniforme.setMotivoCancelacion(procesadas.get(i)
						.getMotivo());
				planillaUniforme.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaUniforme.setHoraAuditoria(horaAuditoria);
				planillaUniforme.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaUniforme.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraUniforme bitacoraU = new BitacoraUniforme(0,
							planillaUniforme, estadoNuevo, fechaHora,
							fechaHora, horaAuditoria, nombreUsuarioSesion(),
							imagenX);
					listBitacoraUniforme.add(bitacoraU);
					enviarEmailError(tipoConfig, planillaUniforme.getUsuario()
							.getIdUsuario(),
							planillaUniforme.getIdPlanillaUniforme(),
							planillaUniforme.getTipo(), planillaUniforme
									.getUsuario().getMail(), estado, procesadas
									.get(i).getMotivo(), procesadas.get(i)
									.getDescripcion());

				} else {
					BitacoraUniforme bitacoraUniforme = servicioBitacoraUniforme
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaUniforme);
					if (bitacoraUniforme == null)
						bitacoraUniforme = servicioBitacoraUniforme
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaUniforme);
					bitacoraUniforme.setEstado(estadoNuevo);
					bitacoraUniforme.setFechaCambio(fechaHora);
					bitacoraUniforme.setHoraAuditoria(horaAuditoria);
					bitacoraUniforme.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraUniforme.setFechaAuditoria(fechaHora);
					bitacoraUniforme.setImagen(imagen);
					listBitacoraUniforme.add(bitacoraUniforme);
				}

				listUniforme.add(planillaUniforme);
				if (estado.equals("Aprobada")
						&& planillaUniforme.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaUniforme nueva = new PlanillaUniforme(0, user,
							planillaUniforme.getMarca(),
							planillaUniforme.getNombreActividad(),
							planillaUniforme.getFechaEntrega(),
							planillaUniforme.getTipoActividad(),
							planillaUniforme.getCiudad(),
							planillaUniforme.getNombreCliente(),
							planillaUniforme.getNombrePdv(),
							planillaUniforme.getRifPdv(),
							planillaUniforme.getTelefonoPdv(),
							planillaUniforme.getCorreoPdv(),
							planillaUniforme.getDireccionPdv(),
							planillaUniforme.getLogo(),
							planillaUniforme.getCosto(),
							planillaUniforme.getJustificacion(),
							planillaUniforme.getContrato(), fechaHora,
							planillaUniforme.getFechaEnvio(), horaAuditoria,
							nombreUsuarioSesion(), "Pendiente",
							planillaUniforme.getUsuario().getZona()
									.getDescripcion(), "Marca", "",
							planillaUniforme.getIdPlanillaUniforme(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaUniforme.guardar(nueva);
					nueva = servicioPlanillaUniforme.buscarUltima();
					bitacoraUniformes(nueva);
					List<UniformePlanillaUniforme> uniformesAgregados = servicioUniformePlanillaUniforme
							.buscarPorPlanilla(planillaUniforme);
					for (int j = 0; j < uniformesAgregados.size(); j++) {
						uniformesAgregados.get(j).getId().setPlanillaUniforme(nueva);
					}
					servicioUniformePlanillaUniforme
							.guardar(uniformesAgregados);
					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaUniforme(), "Uniformes", nueva
									.getUsuario().getMail());
				}

				break;

			case "Promociones de Marca":
				PlanillaPromocion planillaPromocion = servicioPlanillaPromocion
						.buscar(procesadas.get(i).getId());
				planillaPromocion.setEstado(estado);
				planillaPromocion
						.setRefencia(procesadas.get(i).getReferencia());
				planillaPromocion.setMotivoCancelacion(procesadas.get(i)
						.getMotivo());
				planillaPromocion.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaPromocion.setHoraAuditoria(horaAuditoria);
				planillaPromocion.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaPromocion.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraPromocion bitacoraP = new BitacoraPromocion(0,
							planillaPromocion, estadoNuevo, fechaHora,
							fechaHora, horaAuditoria, nombreUsuarioSesion(),
							imagenX);
					listBitacoraPromocion.add(bitacoraP);
					enviarEmailError(tipoConfig, planillaPromocion.getUsuario()
							.getIdUsuario(),
							planillaPromocion.getIdPlanillaPromocion(),
							planillaPromocion.getTipo(), planillaPromocion
									.getUsuario().getMail(), estado, procesadas
									.get(i).getMotivo(), procesadas.get(i)
									.getDescripcion());

				} else {
					BitacoraPromocion bitacoraPromocion = servicioBitacoraPromocion
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaPromocion);
					if (bitacoraPromocion == null)
						bitacoraPromocion = servicioBitacoraPromocion
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaPromocion);
					bitacoraPromocion.setEstado(estadoNuevo);
					bitacoraPromocion.setFechaCambio(fechaHora);
					bitacoraPromocion.setHoraAuditoria(horaAuditoria);
					bitacoraPromocion
							.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraPromocion.setFechaAuditoria(fechaHora);
					bitacoraPromocion.setImagen(imagen);
					listBitacoraPromocion.add(bitacoraPromocion);
				}
				listPromocion.add(planillaPromocion);

				if (estado.equals("Aprobada")
						&& planillaPromocion.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaPromocion nueva = new PlanillaPromocion(0, user,
							planillaPromocion.getMarca(),
							planillaPromocion.getMarcaB(),
							planillaPromocion.getNombreActividad(),
							planillaPromocion.getTipoActividad(),
							planillaPromocion.getTipoLocal(),
							planillaPromocion.getCiudad(),
							planillaPromocion.getEstadoPdv(),
							planillaPromocion.getNombreCliente(),
							planillaPromocion.getNombrePdv(),
							planillaPromocion.getRifPdv(),
							planillaPromocion.getTelefonoPdv(),
							planillaPromocion.getCorreoPdv(),
							planillaPromocion.getDireccionPdv(),
							planillaPromocion.getFechaDesde(),
							planillaPromocion.getFechaHasta(),
							planillaPromocion.getModalidadPago(),
							planillaPromocion.getFrecuenciaPago(),
							planillaPromocion.getMaterial(),
							planillaPromocion.getComunicacion(),
							planillaPromocion.getCosto(),
							planillaPromocion.getDescripcionMarcaA(),
							planillaPromocion.getDescripcionMarcaB(),
							fechaHora, planillaPromocion.getFechaEnvio(),
							horaAuditoria, nombreUsuarioSesion(), "Pendiente",
							planillaPromocion.getZona(), "Marca", "",
							planillaPromocion.getIdPlanillaPromocion(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaPromocion.guardar(nueva);

					nueva = servicioPlanillaPromocion.buscarUltima();
					bitacoraPromociones(nueva);
					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaPromocion(),
							"Promociones de Marca", nueva.getUsuario()
									.getMail());
				}

				break;

			case "Solicitud de Arte y Publicaciones":
				PlanillaArte planillaArte = servicioPlanillaArte
						.buscar(procesadas.get(i).getId());
				planillaArte.setEstado(estado);
				planillaArte.setRefencia(procesadas.get(i).getReferencia());
				planillaArte
						.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaArte.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaArte.setHoraAuditoria(horaAuditoria);
				planillaArte.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaArte.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraArte bitacoraA = new BitacoraArte(0, planillaArte,
							estadoNuevo, fechaHora, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), imagenX);
					listBitacoraArte.add(bitacoraA);
					enviarEmailError(tipoConfig, planillaArte.getUsuario()
							.getIdUsuario(), planillaArte.getIdPlanillaArte(),
							planillaArte.getTipo(), planillaArte.getUsuario()
									.getMail(), estado, procesadas.get(i)
									.getMotivo(), procesadas.get(i)
									.getDescripcion());

				} else {
					BitacoraArte bitacoraArte = servicioBitacoraArte
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaArte);
					if (bitacoraArte == null)
						bitacoraArte = servicioBitacoraArte
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaArte);
					bitacoraArte.setEstado(estadoNuevo);
					bitacoraArte.setFechaCambio(fechaHora);
					bitacoraArte.setHoraAuditoria(horaAuditoria);
					bitacoraArte.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraArte.setFechaAuditoria(fechaHora);
					bitacoraArte.setImagen(imagen);
					listBitacoraArte.add(bitacoraArte);
				}
				listArte.add(planillaArte);

				if (estado.equals("Aprobada")
						&& planillaArte.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaArte nueva = new PlanillaArte(0, user,
							planillaArte.getMarca(),
							planillaArte.getNombreActividad(),
							planillaArte.getNombreCliente(),
							planillaArte.getTipoArte(), planillaArte.getRif(),
							planillaArte.getPatente(),
							planillaArte.getFormato(), planillaArte.getAlto(),
							planillaArte.getLargo(), planillaArte.getAncho(),
							planillaArte.getImagenA(),
							planillaArte.getImagenB(),
							planillaArte.getImagenC(),
							planillaArte.getImagenD(),
							planillaArte.getLineamiento(), fechaHora,
							planillaArte.getFechaEnvio(), horaAuditoria,
							nombreUsuarioSesion(), "Pendiente",
							planillaArte.getZona(), "Marca", "",
							planillaArte.getIdPlanillaArte(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaArte.guardar(nueva);

					nueva = servicioPlanillaArte.buscarUltima();
					bitacoraArte(nueva);
					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaArte(),
							"Solicitud de Arte y Publicaciones", nueva
									.getUsuario().getMail());
				}

				break;

			case "Cata Induccion":
				PlanillaCata planillaCata = servicioPlanillaCata
						.buscar(procesadas.get(i).getId());
				planillaCata.setEstado(estado);
				planillaCata.setRefencia(procesadas.get(i).getReferencia());
				planillaCata
						.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaCata.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaCata.setHoraAuditoria(horaAuditoria);
				planillaCata.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaCata.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraCata bitacoraC = new BitacoraCata(0, planillaCata,
							estadoNuevo, fechaHora, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), imagenX);
					listBitacoraCata.add(bitacoraC);
					enviarEmailError(tipoConfig, planillaCata.getUsuario()
							.getIdUsuario(), planillaCata.getIdPlanillaCata(),
							planillaCata.getTipo(), planillaCata.getUsuario()
									.getMail(), estado, procesadas.get(i)
									.getMotivo(), procesadas.get(i)
									.getDescripcion());

				} else {
					BitacoraCata bitacoraCata = servicioBitacoraCata
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaCata);
					if (bitacoraCata == null)
						bitacoraCata = servicioBitacoraCata
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaCata);
					bitacoraCata.setEstado(estadoNuevo);
					bitacoraCata.setFechaCambio(fechaHora);
					bitacoraCata.setHoraAuditoria(horaAuditoria);
					bitacoraCata.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraCata.setFechaAuditoria(fechaHora);
					bitacoraCata.setImagen(imagen);
					listBitacoraCata.add(bitacoraCata);
				}
				listCata.add(planillaCata);

				if (estado.equals("Aprobada")
						&& planillaCata.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaCata nueva = new PlanillaCata(0, user,
							planillaCata.getMarca(),
							planillaCata.getNombreActividad(),
							planillaCata.getFechaActividad(),
							planillaCata.getCata(), planillaCata.getCiudad(),
							planillaCata.getNombreCliente(),
							planillaCata.getTelefonoPdv(),
							planillaCata.getCorreoPdv(),
							planillaCata.getDireccionPdv(),
							planillaCata.getPersonas(),
							planillaCata.getMotivo(), planillaCata.getNivel(),
							planillaCata.getEdadTarget(),
							planillaCata.getCosto(),
							planillaCata.getDescripcion(),
							planillaCata.getMecanica(), fechaHora,
							planillaCata.getFechaEnvio(), horaAuditoria,
							nombreUsuarioSesion(), "Pendiente",
							planillaCata.getZona(), "Marca", "",
							planillaCata.getIdPlanillaCata(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaCata.guardar(nueva);
					nueva = servicioPlanillaCata.buscarUltima();
					bitacoraCata(nueva);
					List<ItemPlanillaCata> itemsAgregados = servicioItemPlanillaCata
							.buscarPorPlanilla(planillaCata);
					for (int j = 0; j < itemsAgregados.size(); j++) {
						itemsAgregados.get(j).getId().setPlanillaCata(nueva);
					}

					List<RecursoPlanillaCata> recursosAgregados = servicioRecursoPlanillaCata
							.buscarPorPlanilla(planillaCata);

					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).getId().setPlanillaCata(nueva);
					}

					servicioItemPlanillaCata.guardar(itemsAgregados);
					servicioRecursoPlanillaCata.guardar(recursosAgregados);
					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaCata(), "Cata Induccion", nueva
									.getUsuario().getMail());
				}

				break;

			case "Fachada y Decoraciones":
				PlanillaFachada planillaFachada = servicioPlanillaFachada
						.buscar(procesadas.get(i).getId());
				planillaFachada.setEstado(estado);
				planillaFachada.setRefencia(procesadas.get(i).getReferencia());
				planillaFachada.setMotivoCancelacion(procesadas.get(i)
						.getMotivo());
				planillaFachada.setDescripcionMotivo(procesadas.get(i)
						.getDescripcion());
				planillaFachada.setHoraAuditoria(horaAuditoria);
				planillaFachada.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaFachada.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraFachada bitacoraF = new BitacoraFachada(0,
							planillaFachada, estadoNuevo, fechaHora, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), imagenX);
					listBitacoraFachada.add(bitacoraF);
					enviarEmailError(tipoConfig, planillaFachada.getUsuario()
							.getIdUsuario(),
							planillaFachada.getIdPlanillaFachada(),
							planillaFachada.getTipo(), planillaFachada
									.getUsuario().getMail(), estado, procesadas
									.get(i).getMotivo(), procesadas.get(i)
									.getDescripcion());
				} else {
					BitacoraFachada bitacoraFachada = servicioBitacoraFachada
							.buscarPorEstadoYPlanilla(estadoDefecto,
									planillaFachada);
					if (bitacoraFachada == null)
						bitacoraFachada = servicioBitacoraFachada
								.buscarPorEstadoYPlanilla("Planilla Aprobada",
										planillaFachada);
					bitacoraFachada.setEstado(estadoNuevo);
					bitacoraFachada.setFechaCambio(fechaHora);
					bitacoraFachada.setHoraAuditoria(horaAuditoria);
					bitacoraFachada.setUsuarioAuditoria(nombreUsuarioSesion());
					bitacoraFachada.setFechaAuditoria(fechaHora);
					bitacoraFachada.setImagen(imagen);
					listBitacoraFachada.add(bitacoraFachada);
				}
				listFachada.add(planillaFachada);

				if (estado.equals("Aprobada")
						&& planillaFachada.getTipo().equals("TradeMark")) {
					Usuario user = usuarioSesion(nombreUsuarioSesion());
					PlanillaFachada nueva = new PlanillaFachada(0, user,
							planillaFachada.getMarca(),
							planillaFachada.getNombreActividad(),
							planillaFachada.getFechaActividad(),
							planillaFachada.getTipoActividad(),
							planillaFachada.getCiudad(),
							planillaFachada.getNombreCliente(),
							planillaFachada.getNombrePdv(),
							planillaFachada.getRifPdv(),
							planillaFachada.getTelefonoPdv(),
							planillaFachada.getDireccionPdv(),
							planillaFachada.getCorreoPdv(),
							planillaFachada.getPersonas(),
							planillaFachada.getDuracion(),
							planillaFachada.getNivel(),
							planillaFachada.getPatente(),
							planillaFachada.getCosto(),
							planillaFachada.getDescripcion(),
							planillaFachada.getJustificacion(),
							planillaFachada.getTipoDecoracion(),
							planillaFachada.getFormato(),
							planillaFachada.getArte(),
							planillaFachada.getAlto(),
							planillaFachada.getLargo(),
							planillaFachada.getAncho(),
							planillaFachada.getImagenA(),
							planillaFachada.getImagenB(),
							planillaFachada.getImagenC(),
							planillaFachada.getImagenD(), fechaHora,
							planillaFachada.getFechaEnvio(), horaAuditoria,
							nombreUsuarioSesion(), "Pendiente",
							planillaFachada.getZona(), "Marca", "",
							planillaFachada.getIdPlanillaFachada(), "");
					nueva.setOrigen("Trade Marketing");
					servicioPlanillaFachada.guardar(nueva);
					nueva = servicioPlanillaFachada.buscarUltima();
					bitacoraFachada(nueva);
					List<RecursoPlanillaFachada> recursosAgregados = servicioRecursoPlanillaFachada
							.buscarPorPlanilla(planillaFachada);
					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).getId().setPlanillaFachada(nueva);
					}
					servicioRecursoPlanillaFachada.guardar(recursosAgregados);
					enviarEmail("Marca", nombreUsuarioSesion(),
							nueva.getIdPlanillaFachada(),
							"Fachada y Decoraciones", nueva.getUsuario()
									.getMail());
				}

				break;

			default:
				break;
			}
		}
		if (!listArte.isEmpty())
			servicioPlanillaArte.guardarVarias(listArte);
		if (!listCata.isEmpty())
			servicioPlanillaCata.guardarVarias(listCata);
		if (!listEvento.isEmpty())
			servicioPlanillaEvento.guardarVarias(listEvento);
		if (!listFachada.isEmpty())
			servicioPlanillaFachada.guardarVarias(listFachada);
		if (!listPromocion.isEmpty())
			servicioPlanillaPromocion.guardarVarias(listPromocion);
		if (!listUniforme.isEmpty()) {
			servicioPlanillaUniforme.guardarVarias(listUniforme);
		}
		// Listas de Bitacora
		if (!listBitacoraArte.isEmpty())
			servicioBitacoraArte.guardarVarias(listBitacoraArte);
		if (!listBitacoraCata.isEmpty())
			servicioBitacoraCata.guardarVarias(listBitacoraCata);
		if (!listBitacoraEvento.isEmpty())
			servicioBitacoraEvento.guardarVarias(listBitacoraEvento);
		if (!listBitacoraFachada.isEmpty())
			servicioBitacoraFachada.guardarVarias(listBitacoraFachada);
		if (!listBitacoraPromocion.isEmpty())
			servicioBitacoraPromocion.guardarVarias(listBitacoraPromocion);
		if (!listBitacoraUniforme.isEmpty())
			servicioBitacoraUniforme.guardarVarias(listBitacoraUniforme);
	}

	public void cargarLista() {
		listPlanilla.clear();
		List<PlanillaCata> listCata = new ArrayList<PlanillaCata>();
		List<PlanillaEvento> listEvento = new ArrayList<PlanillaEvento>();
		List<PlanillaPromocion> listPromocion = new ArrayList<PlanillaPromocion>();
		List<PlanillaArte> listArte = new ArrayList<PlanillaArte>();
		List<PlanillaUniforme> listUniforme = new ArrayList<PlanillaUniforme>();
		List<PlanillaFachada> listFachada = new ArrayList<PlanillaFachada>();
		String tipoConfig = "";
		if (tradeMark)
			tipoConfig = "TradeMark";
		else
			tipoConfig = "Marca";
		switch (grupoDominante) {
		case "Administrador":
			if (pendiente) {
				pendiente = false;
				listCata = servicioPlanillaCata.buscarAdminPendientes(
						tipoConfig, "Pendiente");
				listEvento = servicioPlanillaEvento.buscarAdminPendientes(
						tipoConfig, "Pendiente");
				listPromocion = servicioPlanillaPromocion
						.buscarAdminPendientes(tipoConfig, "Pendiente");
				listArte = servicioPlanillaArte.buscarAdminPendientes(
						tipoConfig, "Pendiente");
				listUniforme = servicioPlanillaUniforme.buscarAdminPendientes(
						tipoConfig, "Pendiente");
				listFachada = servicioPlanillaFachada.buscarAdminPendientes(
						tipoConfig, "Pendiente");
			} else {
				buscado = true;
				listCata = servicioPlanillaCata.buscarAdminEstado(variable,
						tipoConfig, usuarioSesion(nombreUsuarioSesion()));
				listEvento = servicioPlanillaEvento.buscarAdminEstado(variable,
						tipoConfig, usuarioSesion(nombreUsuarioSesion()));
				listPromocion = servicioPlanillaPromocion.buscarAdminEstado(
						variable, tipoConfig,
						usuarioSesion(nombreUsuarioSesion()));
				listArte = servicioPlanillaArte.buscarAdminEstado(variable,
						tipoConfig, usuarioSesion(nombreUsuarioSesion()));
				listUniforme = servicioPlanillaUniforme.buscarAdminEstado(
						variable, tipoConfig,
						usuarioSesion(nombreUsuarioSesion()));
				listFachada = servicioPlanillaFachada.buscarAdminEstado(
						variable, tipoConfig,
						usuarioSesion(nombreUsuarioSesion()));
			}
			break;

		case "Gerente Regional":
			listCata = servicioPlanillaCata.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			listEvento = servicioPlanillaEvento.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			listPromocion = servicioPlanillaPromocion.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			listArte = servicioPlanillaArte.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			listUniforme = servicioPlanillaUniforme.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			listFachada = servicioPlanillaFachada.buscarSupervisorYEstado(
					nombreUsuarioSesion(), variable);
			break;

		case "Solicitante":
			listCata = servicioPlanillaCata.buscarUsuarioSessionYEstado(
					usuarioSesion(nombreUsuarioSesion()), variable);
			listEvento = servicioPlanillaEvento.buscarUsuarioSessionYEstado(
					usuarioSesion(nombreUsuarioSesion()), variable);
			listPromocion = servicioPlanillaPromocion
					.buscarUsuarioSessionYEstado(
							usuarioSesion(nombreUsuarioSesion()), variable);
			listArte = servicioPlanillaArte.buscarUsuarioSessionYEstado(
					usuarioSesion(nombreUsuarioSesion()), variable);
			listUniforme = servicioPlanillaUniforme
					.buscarUsuarioSessionYEstado(
							usuarioSesion(nombreUsuarioSesion()), variable);
			listFachada = servicioPlanillaFachada.buscarUsuarioSessionYEstado(
					usuarioSesion(nombreUsuarioSesion()), variable);
			break;

		default:
			break;
		}
		String origen = "TRADE MARKETING";
		for (int i = 0; i < listCata.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listCata.get(i).getIdPlanillaCata();
			String usuario = listCata.get(i).getUsuario().getNombre();
			String marca = listCata.get(i).getMarca().getDescripcion();
			String nombreActividad = listCata.get(i).getNombreActividad();
			String estado = listCata.get(i).getEstado();
			String tipoPlanilla = "Cata Induccion";
			Timestamp fecha = listCata.get(i).getFechaEnvio();
			if (listCata.get(i).getOrigen() != null)
				origen = listCata.get(i).getOrigen();
			else {
				if (listCata.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listEvento.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listEvento.get(i).getIdPlanillaEvento();
			String usuario = listEvento.get(i).getUsuario().getNombre();
			String marca = listEvento.get(i).getMarca().getDescripcion();
			String nombreActividad = listEvento.get(i).getNombreActividad();
			String estado = listEvento.get(i).getEstado();
			String tipoPlanilla = "Eventos Especiales";
			Timestamp fecha = listEvento.get(i).getFechaEnvio();
			if (listEvento.get(i).getOrigen() != null)
				origen = listEvento.get(i).getOrigen();
			else {
				if (listEvento.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listFachada.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listFachada.get(i).getIdPlanillaFachada();
			String usuario = listFachada.get(i).getUsuario().getNombre();
			String marca = listFachada.get(i).getMarca().getDescripcion();
			String nombreActividad = listFachada.get(i).getNombreActividad();
			String estado = listFachada.get(i).getEstado();
			String tipoPlanilla = "Fachada y Decoraciones";
			Timestamp fecha = listFachada.get(i).getFechaEnvio();
			if (listFachada.get(i).getOrigen() != null)
				origen = listFachada.get(i).getOrigen();
			else {
				if (listFachada.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listPromocion.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listPromocion.get(i).getIdPlanillaPromocion();
			String usuario = listPromocion.get(i).getUsuario().getNombre();
			String marca = listPromocion.get(i).getMarca().getDescripcion();
			String nombreActividad = listPromocion.get(i).getNombreActividad();
			String estado = listPromocion.get(i).getEstado();
			String tipoPlanilla = "Promociones de Marca";
			Timestamp fecha = listPromocion.get(i).getFechaEnvio();
			if (listPromocion.get(i).getOrigen() != null)
				origen = listPromocion.get(i).getOrigen();
			else {
				if (listPromocion.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listArte.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listArte.get(i).getIdPlanillaArte();
			String usuario = listArte.get(i).getUsuario().getNombre();
			String marca = listArte.get(i).getMarca().getDescripcion();
			String nombreActividad = listArte.get(i).getNombreActividad();
			String estado = listArte.get(i).getEstado();
			String tipoPlanilla = "Solicitud de Arte y Publicaciones";
			Timestamp fecha = listArte.get(i).getFechaEnvio();
			if (listArte.get(i).getOrigen() != null)
				origen = listArte.get(i).getOrigen();
			else {
				if (listArte.get(i).getTipo() != null)
					if (listArte.get(i).getTipo().equals("Marca"))
						origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listUniforme.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listUniforme.get(i).getIdPlanillaUniforme();
			String usuario = listUniforme.get(i).getUsuario().getNombre();
			String marca = listUniforme.get(i).getMarca().getDescripcion();
			String nombreActividad = listUniforme.get(i).getNombreActividad();
			String estado = listUniforme.get(i).getEstado();
			String tipoPlanilla = "Uniformes";
			Timestamp fecha = listUniforme.get(i).getFechaEnvio();
			if (listUniforme.get(i).getOrigen() != null)
				origen = listUniforme.get(i).getOrigen();
			else {
				if (listUniforme.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		if (!listPlanilla.isEmpty()) {
			ordenarPorFecha(listPlanilla);
		}
	}

	@Listen("onClick = #btnBitacora")
	public void verBitacora() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		if (validarSeleccion(procesadas)) {
			if (procesadas.size() == 1) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", procesadas.get(0).getId());
				map.put("tipoPlanilla", procesadas.get(0).getTipoPlanilla());
				Sessions.getCurrent().setAttribute("inbox", map);
				Window window = new Window();
				window = (Window) Executions.createComponents(
						"/vistas/transacciones/VBitacora.zul", null, null);
				window.doModal();
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		}
	}

	public String getTitulo() {
		switch (variable) {
		case "Aprobada":
			titulo = "Aprobadas";
			break;
		case "Cancelada":
			titulo = "Canceladas";
			break;
		case "Rechazada":
			titulo = "Rechazadas";
			break;
		case "Pendiente":
			titulo = "Pendientes por Aprobar";
			break;
		case "En Edicion":
			titulo = "En Edicion";
			break;
		case "Generales":
			titulo = "Generales";
			break;
		case "Pagada":
			titulo = "Pagadas";
			break;

		default:
			break;
		}
		return titulo;
	}

	public void actualizar(List<PlanillaGenerica> listilla,
			Catalogo<PlanillaGenerica> catalogoGenerico) {
		catalogo = catalogoGenerico;
		listPlanilla = listilla;
		catalogo.actualizarLista(listPlanilla);
	}

	// Ventana de Pago

	@Listen("onClick = #btnAceptar")
	public void aceptar() {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("pagador");
		if (map != null) {
			if (map.get("catalogo") != null) {
				catalogo = (Catalogo<PlanillaGenerica>) map.get("catalogo");
				map.clear();
				map = null;
			}
		}
		items.clear();
		boolean error = false;
		if (lista.getItemCount() != 0) {
			for (int i = 0; i < lista.getItemCount(); i++) {
				Listitem listItem = lista.getItemAtIndex(i);
				PlanillaGenerica planilla = listItem.getValue();
				String referencia = ((Textbox) ((listItem.getChildren().get(5)))
						.getFirstChild()).getValue();
				if (referencia.equals(""))
					error = true;
				planilla.setReferencia(referencia);
				items.add(planilla);
			}
		}
		if (error)
			msj.mensajeAlerta(Mensaje.listaVacia);
		else {
			String estatus = "Pagada";
			String estadoDefecto = "Esperando Pago de Planilla";
			String estadoNuevo = "Planilla Pagada";
			cambiarEstado(items, estatus, estadoDefecto, estadoNuevo);
			cargarLista();
			catalogo.actualizarLista(listPlanilla);
			cerrarVentana(wdwPagar);
		}
	}

	@Listen("onClick = #btnSalir")
	public void cerrar() {
		cerrarVentana(wdwPagar);
	}

	// Ventana Motivo

	public ListModelList<F0005> getMotivos() {
		motivos = new ListModelList<F0005>(
				servicioF0005.buscarParaUDCOrdenados("00", "14"));
		return motivos;
	}

	@Listen("onClick = #btnSalirMotivo")
	public void cerrarMotivo() {
		cerrarVentana(wdwMotivo);
	}

	@Listen("onClick = #btnAceptarMotivo")
	public void aceptarMotivo() {
		String estatusNuevo = "";
		String estatusViejo = "";
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("rechazador");
		if (map != null) {
			if (map.get("catalogo") != null) {
				catalogo = (Catalogo<PlanillaGenerica>) map.get("catalogo");
				estatusViejo = (String) map.get("estatusViejo");
				estatusNuevo = (String) map.get("estatusNuevo");
				map.clear();
				map = null;
			}
		}
		items2.clear();
		boolean error = false;
		if (listaMotivo.getItemCount() != 0) {
			for (int i = 0; i < listaMotivo.getItemCount(); i++) {
				Listitem listItem = listaMotivo.getItemAtIndex(i);
				PlanillaGenerica planilla = listItem.getValue();
				String motivo = "";
				if (((Combobox) ((listItem.getChildren().get(5)))
						.getFirstChild()).getSelectedItem() != null)
					motivo = ((Combobox) ((listItem.getChildren().get(5)))
							.getFirstChild()).getSelectedItem().getContext();
				if (motivo.equals(""))
					error = true;
				String descripcion = ((Textbox) ((listItem.getChildren().get(6)))
						.getFirstChild()).getValue();
				planilla.setMotivo(motivo);
				planilla.setDescripcion(descripcion);
				items2.add(planilla);
			}
		}
		if (error)
			msj.mensajeAlerta(Mensaje.listaVaciaMotivo);
		else {
			String estatus = estatusViejo;
			String estadoDefecto = "";
			String estadoNuevo = estatusNuevo;
			cambiarEstado(items2, estatus, estadoDefecto, estadoNuevo);
			cargarLista();
			catalogo.actualizarLista(listPlanilla);
			cerrarVentana(wdwMotivo);
		}
	}

	public void bitacoraEventos(PlanillaEvento planillaEvento) {
		List<BitacoraEvento> listaBitacoras = new ArrayList<BitacoraEvento>();
		BitacoraEvento bitacora = new BitacoraEvento(0, planillaEvento,
				"Planilla Enviada", planillaEvento.getFechaEnvio(), fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraEvento bitacora2 = new BitacoraEvento(0, planillaEvento,
				"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
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

	public void bitacoraUniformes(PlanillaUniforme planillaUniforme) {
		List<BitacoraUniforme> listaBitacoras = new ArrayList<BitacoraUniforme>();
		BitacoraUniforme bitacora = new BitacoraUniforme(0, planillaUniforme,
				"Planilla Enviada", planillaUniforme.getFechaEnvio(),
				fechaHora, horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraUniforme bitacora2 = new BitacoraUniforme(0, planillaUniforme,
				"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora2);

		BitacoraUniforme bitacora3 = new BitacoraUniforme(0, planillaUniforme,
				"Esperando Finalizacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagenX);
		listaBitacoras.add(bitacora3);

		BitacoraUniforme bitacora4 = new BitacoraUniforme(0, planillaUniforme,
				"Esperando Pago de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagenX);
		listaBitacoras.add(bitacora4);

		servicioBitacoraUniforme.guardarBitacoras(listaBitacoras);
	}

	public void bitacoraPromociones(PlanillaPromocion planillaPromocion) {

		List<BitacoraPromocion> listaBitacoras = new ArrayList<BitacoraPromocion>();
		BitacoraPromocion bitacora = new BitacoraPromocion(0,
				planillaPromocion, "Planilla Enviada",
				planillaPromocion.getFechaEnvio(), fechaHora, horaAuditoria,
				nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraPromocion bitacora2 = new BitacoraPromocion(0,
				planillaPromocion, "Esperando Aprobacion de Planilla",
				fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion(),
				imagen);
		listaBitacoras.add(bitacora2);

		BitacoraPromocion bitacora3 = new BitacoraPromocion(0,
				planillaPromocion, "Esperando Finalizacion de Planilla",
				fechaHora, fechaHora, horaAuditoria, nombreUsuarioSesion(),
				imagenX);
		listaBitacoras.add(bitacora3);

		BitacoraPromocion bitacora4 = new BitacoraPromocion(0,
				planillaPromocion, "Esperando Pago de Planilla", fechaHora,
				fechaHora, horaAuditoria, nombreUsuarioSesion(), imagenX);
		listaBitacoras.add(bitacora4);

		servicioBitacoraPromocion.guardarBitacoras(listaBitacoras);

	}

	public void bitacoraArte(PlanillaArte planillaArte) {
		List<BitacoraArte> listaBitacoras = new ArrayList<BitacoraArte>();
		BitacoraArte bitacora = new BitacoraArte(0, planillaArte,
				"Planilla Enviada", planillaArte.getFechaEnvio(), fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraArte bitacora2 = new BitacoraArte(0, planillaArte,
				"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
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

	public void bitacoraCata(PlanillaCata planillaCata) {

		List<BitacoraCata> listaBitacoras = new ArrayList<BitacoraCata>();
		BitacoraCata bitacora = new BitacoraCata(0, planillaCata,
				"Planilla Enviada", planillaCata.getFechaEnvio(), fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraCata bitacora2 = new BitacoraCata(0, planillaCata,
				"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
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

	public void bitacoraFachada(PlanillaFachada planillaFachada) {
		List<BitacoraFachada> listaBitacoras = new ArrayList<BitacoraFachada>();
		BitacoraFachada bitacora = new BitacoraFachada(0, planillaFachada,
				"Planilla Enviada", planillaFachada.getFechaEnvio(), fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
		listaBitacoras.add(bitacora);

		BitacoraFachada bitacora2 = new BitacoraFachada(0, planillaFachada,
				"Esperando Aprobacion de Planilla", fechaHora, fechaHora,
				horaAuditoria, nombreUsuarioSesion(), imagen);
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

	@Listen("onClick = #btnRefrescar")
	public void refresh() {
		Usuario user = servicioUsuario.buscar(nombreUsuarioSesion());
		Date fecha1 = dtbDesde.getValue();
		Date fecha2 = dtbHasta.getValue();
		String codigoMarca = "%";
		if (cmbMarca.getSelectedItem() != null)
			codigoMarca = cmbMarca.getSelectedItem().getContext();
		String codigoUsuario = "%";
		if (cmbUsuario != null)
			if (cmbUsuario.getSelectedItem() != null)
				codigoUsuario = cmbUsuario.getSelectedItem().getContext();
		String codigoTipo = "%";
		if (!cmbTipo.getValue().equals("TODOS"))
			codigoTipo = cmbTipo.getValue();
		long codigo = 0;
		if (txtCodigo.getText().compareTo("") != 0)
			codigo = txtCodigo.getValue();
		listPlanilla.clear();
		List<PlanillaCata> listCata = new ArrayList<PlanillaCata>();
		List<PlanillaEvento> listEvento = new ArrayList<PlanillaEvento>();
		List<PlanillaPromocion> listPromocion = new ArrayList<PlanillaPromocion>();
		List<PlanillaArte> listArte = new ArrayList<PlanillaArte>();
		List<PlanillaUniforme> listUniforme = new ArrayList<PlanillaUniforme>();
		List<PlanillaFachada> listFachada = new ArrayList<PlanillaFachada>();
		String tipoP = "Marca";
		if (tradeMark)
			tipoP = "TradeMark";
		switch (grupoDominante) {
		case "Administrador":
			String estadoBuscar = cmbEstado.getValue();
			if (estadoBuscar.equals("TODOS"))
				estadoBuscar = "%";
			if (estadoBuscar.equals("En Edicion")) {
				switch (codigoTipo) {
				case "Cata Induccion":
					listCata = servicioPlanillaCata.buscarUsuarioEntreFechas(
							user, estadoBuscar, fecha1, fecha2, codigoMarca,
							codigoUsuario, codigo);
					break;
				case "Eventos Especiales":
					listEvento = servicioPlanillaEvento
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					break;
				case "Fachada y Decoraciones":
					listFachada = servicioPlanillaFachada
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					break;
				case "Promociones de Marca":
					listPromocion = servicioPlanillaPromocion
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					break;
				case "Solicitud de Arte y Publicaciones":
					listArte = servicioPlanillaArte.buscarUsuarioEntreFechas(
							user, estadoBuscar, fecha1, fecha2, codigoMarca,
							codigoUsuario, codigo);
					break;
				case "Uniformes":
					listUniforme = servicioPlanillaUniforme
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					break;
				default:
					listCata = servicioPlanillaCata.buscarUsuarioEntreFechas(
							user, estadoBuscar, fecha1, fecha2, codigoMarca,
							codigoUsuario, codigo);
					listEvento = servicioPlanillaEvento
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					listPromocion = servicioPlanillaPromocion
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					listArte = servicioPlanillaArte.buscarUsuarioEntreFechas(
							user, estadoBuscar, fecha1, fecha2, codigoMarca,
							codigoUsuario, codigo);
					listUniforme = servicioPlanillaUniforme
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					listFachada = servicioPlanillaFachada
							.buscarUsuarioEntreFechas(user, estadoBuscar,
									fecha1, fecha2, codigoMarca, codigoUsuario,
									codigo);
					break;
				}
			} else {
				switch (codigoTipo) {
				case "Cata Induccion":
					listCata = servicioPlanillaCata
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				case "Eventos Especiales":
					listEvento = servicioPlanillaEvento
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				case "Fachada y Decoraciones":
					listFachada = servicioPlanillaFachada
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				case "Promociones de Marca":
					listPromocion = servicioPlanillaPromocion
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				case "Solicitud de Arte y Publicaciones":
					listArte = servicioPlanillaArte
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				case "Uniformes":
					listUniforme = servicioPlanillaUniforme
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				default:
					listCata = servicioPlanillaCata
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					listEvento = servicioPlanillaEvento
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					listPromocion = servicioPlanillaPromocion
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					listArte = servicioPlanillaArte
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					listUniforme = servicioPlanillaUniforme
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					listFachada = servicioPlanillaFachada
							.buscarAdminPendientesEntreFechas(tipoP,
									estadoBuscar, fecha1, fecha2, codigoMarca,
									codigoUsuario, codigo);
					break;
				}
			}
			break;
		case "Solicitante":
			switch (codigoTipo) {
			case "Cata Induccion":
				listCata = servicioPlanillaCata.buscarUsuarioEntreFechas(user,
						variable, fecha1, fecha2, codigoMarca, codigoUsuario,
						codigo);
				break;
			case "Eventos Especiales":
				listEvento = servicioPlanillaEvento.buscarUsuarioEntreFechas(
						user, variable, fecha1, fecha2, codigoMarca,
						codigoUsuario, codigo);
				break;
			case "Fachada y Decoraciones":
				listFachada = servicioPlanillaFachada.buscarUsuarioEntreFechas(
						user, variable, fecha1, fecha2, codigoMarca,
						codigoUsuario, codigo);
				break;
			case "Promociones de Marca":
				listPromocion = servicioPlanillaPromocion
						.buscarUsuarioEntreFechas(user, variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Solicitud de Arte y Publicaciones":
				listArte = servicioPlanillaArte.buscarUsuarioEntreFechas(user,
						variable, fecha1, fecha2, codigoMarca, codigoUsuario,
						codigo);
				break;
			case "Uniformes":
				listUniforme = servicioPlanillaUniforme
						.buscarUsuarioEntreFechas(user, variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			default:
				listCata = servicioPlanillaCata.buscarUsuarioEntreFechas(user,
						variable, fecha1, fecha2, codigoMarca, codigoUsuario,
						codigo);
				listEvento = servicioPlanillaEvento.buscarUsuarioEntreFechas(
						user, variable, fecha1, fecha2, codigoMarca,
						codigoUsuario, codigo);
				listPromocion = servicioPlanillaPromocion
						.buscarUsuarioEntreFechas(user, variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listArte = servicioPlanillaArte.buscarUsuarioEntreFechas(user,
						variable, fecha1, fecha2, codigoMarca, codigoUsuario,
						codigo);
				listUniforme = servicioPlanillaUniforme
						.buscarUsuarioEntreFechas(user, variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listFachada = servicioPlanillaFachada.buscarUsuarioEntreFechas(
						user, variable, fecha1, fecha2, codigoMarca,
						codigoUsuario, codigo);
				break;
			}
			break;
		case "Gerente Regional":
			switch (codigoTipo) {
			case "Cata Induccion":
				listCata = servicioPlanillaCata
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Eventos Especiales":
				listEvento = servicioPlanillaEvento
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Fachada y Decoraciones":
				listFachada = servicioPlanillaFachada
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Promociones de Marca":
				listPromocion = servicioPlanillaPromocion
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Solicitud de Arte y Publicaciones":
				listArte = servicioPlanillaArte
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			case "Uniformes":
				listUniforme = servicioPlanillaUniforme
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			default:
				listCata = servicioPlanillaCata
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listEvento = servicioPlanillaEvento
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listPromocion = servicioPlanillaPromocion
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listArte = servicioPlanillaArte
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listUniforme = servicioPlanillaUniforme
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				listFachada = servicioPlanillaFachada
						.buscarSupervisorYEstadoEntreFechas(
								nombreUsuarioSesion(), variable, fecha1,
								fecha2, codigoMarca, codigoUsuario, codigo);
				break;
			}
			break;
		}
		String origen = "TRADE MARKETING";
		for (int i = 0; i < listCata.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listCata.get(i).getIdPlanillaCata();
			String usuario = listCata.get(i).getUsuario().getNombre();
			String marca = listCata.get(i).getMarca().getDescripcion();
			String nombreActividad = listCata.get(i).getNombreActividad();
			String estado = listCata.get(i).getEstado();
			String tipoPlanilla = "Cata Induccion";
			Timestamp fecha = listCata.get(i).getFechaEnvio();
			if (listCata.get(i).getOrigen() != null)
				origen = listCata.get(i).getOrigen();
			else {
				if (listCata.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listEvento.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listEvento.get(i).getIdPlanillaEvento();
			String usuario = listEvento.get(i).getUsuario().getNombre();
			String marca = listEvento.get(i).getMarca().getDescripcion();
			String nombreActividad = listEvento.get(i).getNombreActividad();
			String estado = listEvento.get(i).getEstado();
			String tipoPlanilla = "Eventos Especiales";
			Timestamp fecha = listEvento.get(i).getFechaEnvio();
			if (listEvento.get(i).getOrigen() != null)
				origen = listEvento.get(i).getOrigen();
			else {
				if (listEvento.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listFachada.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listFachada.get(i).getIdPlanillaFachada();
			String usuario = listFachada.get(i).getUsuario().getNombre();
			String marca = listFachada.get(i).getMarca().getDescripcion();
			String nombreActividad = listFachada.get(i).getNombreActividad();
			String estado = listFachada.get(i).getEstado();
			String tipoPlanilla = "Fachada y Decoraciones";
			Timestamp fecha = listFachada.get(i).getFechaEnvio();
			if (listFachada.get(i).getOrigen() != null)
				origen = listFachada.get(i).getOrigen();
			else {
				if (listFachada.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listPromocion.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listPromocion.get(i).getIdPlanillaPromocion();
			String usuario = listPromocion.get(i).getUsuario().getNombre();
			String marca = listPromocion.get(i).getMarca().getDescripcion();
			String nombreActividad = listPromocion.get(i).getNombreActividad();
			String estado = listPromocion.get(i).getEstado();
			String tipoPlanilla = "Promociones de Marca";
			Timestamp fecha = listPromocion.get(i).getFechaEnvio();
			if (listPromocion.get(i).getOrigen() != null)
				origen = listPromocion.get(i).getOrigen();
			else {
				if (listPromocion.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listArte.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listArte.get(i).getIdPlanillaArte();
			String usuario = listArte.get(i).getUsuario().getNombre();
			String marca = listArte.get(i).getMarca().getDescripcion();
			String nombreActividad = listArte.get(i).getNombreActividad();
			String estado = listArte.get(i).getEstado();
			String tipoPlanilla = "Solicitud de Arte y Publicaciones";
			Timestamp fecha = listArte.get(i).getFechaEnvio();
			if (listArte.get(i).getOrigen() != null)
				origen = listArte.get(i).getOrigen();
			else {
				if (listArte.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listUniforme.size(); i++) {
			origen = "TRADE MARKETING";
			long id = listUniforme.get(i).getIdPlanillaUniforme();
			String usuario = listUniforme.get(i).getUsuario().getNombre();
			String marca = listUniforme.get(i).getMarca().getDescripcion();
			String nombreActividad = listUniforme.get(i).getNombreActividad();
			String estado = listUniforme.get(i).getEstado();
			String tipoPlanilla = "Uniformes";
			Timestamp fecha = listUniforme.get(i).getFechaEnvio();
			if (listUniforme.get(i).getOrigen() != null)
				origen = listUniforme.get(i).getOrigen();
			else {
				if (listUniforme.get(i).getTipo().equals("Marca"))
					origen = "MARCA";
			}
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla, origen);
			listPlanilla.add(plani);
		}
		if (!listPlanilla.isEmpty()) {
			ordenarPorFecha(listPlanilla);
		}
		catalogo.actualizarLista(listPlanilla);

	}

	private void ordenarPorFecha(List<PlanillaGenerica> listPlanilla2) {
		List<PlanillaGenerica> listAux = new ArrayList<PlanillaGenerica>();

		listAux.add(listPlanilla2.get(0));
		int cont = 0;
		for (Iterator<PlanillaGenerica> iterator = listPlanilla2.iterator(); iterator
				.hasNext();) {
			PlanillaGenerica planillaGenerica = (PlanillaGenerica) iterator
					.next();
			if (cont != 0) {
				Timestamp tiempo = planillaGenerica.getFecha();
				boolean entro = false;
				for (int i = 0; i < listAux.size(); i++) {
					if (tiempo.before(listAux.get(i).getFecha())) {
						entro = true;
						listAux.add(i, planillaGenerica);
						i = listAux.size();
					}
				}
				if (!entro) {
					planillaGenerica = listAux.set(listAux.size() - 1,
							planillaGenerica);
					listAux.add(listAux.size() - 1, planillaGenerica);
				}
			}
			cont++;
		}
		listPlanilla.clear();
		listPlanilla = listAux;
	}

}
