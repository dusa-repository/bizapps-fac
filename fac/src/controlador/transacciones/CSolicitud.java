package controlador.transacciones;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.estado.BitacoraFachada;
import modelo.estado.BitacoraPromocion;
import modelo.estado.BitacoraUniforme;
import modelo.generico.PlanillaGenerica;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.seguridad.Arbol;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
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
	private Image imagenNo;
	@Wire
	private Image imagenSi;
	public Catalogo<PlanillaGenerica> catalogo;
	String titulo = "";
	private boolean tradeMark = false;
	private List<PlanillaGenerica> listPlanilla = new ArrayList<PlanillaGenerica>();
	private List<PlanillaGenerica> items = new ArrayList<PlanillaGenerica>();
	private List<PlanillaGenerica> items2 = new ArrayList<PlanillaGenerica>();
	ListModelList<F0005> motivos;

	@Override
	public void inicializar() throws IOException {
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				authe.getAuthorities());
		for (int i = 0; i < authorities.size(); i++) {
			if (authorities.get(i).getAuthority().equals("TRADE MARKETING")) {
				tradeMark = true;
				grupoDominante = "Administrador";
				i = authorities.size();
			} else {

				if (authorities.get(i).getAuthority().equals("MARCA")) {
					grupoDominante = "Administrador";
					i = authorities.size();
				} else {
					if (authorities.get(i).getAuthority()
							.equals("Gerente Regional")) {
						grupoDominante = "Gerente Regional";
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
		// Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());
		// List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		// for (int i = 0; i < grupos.size(); i++) {
		// if (grupos.get(i).getNombre().equals("Administrador")) {
		// grupoDominante = grupos.get(i).getNombre();
		// i = grupos.size();
		// // if (servicioConfiguracion.buscarAdministradorTradeMark(u,
		// // "TradeMark") != null)
		// // tradeMark = true;
		// } else {
		// if (grupos.get(i).getNombre().equals("Gerente Regional")) {
		// grupoDominante = grupos.get(i).getNombre();
		// i = grupos.size();
		// } else {
		// if (grupos.get(i).getNombre().equals("Solicitante")) {
		// grupoDominante = grupos.get(i).getNombre();
		// i = grupos.size();
		// }
		// }
		// }
		// }
		if (variable.equals("Generales"))
			variable = "En Edicion";
		buscarCatalogoPropio();

	}

	public void buscarCatalogoPropio() {
		cargarLista();
		final List<PlanillaGenerica> listaCatalogo = listPlanilla;
		catalogo = new Catalogo<PlanillaGenerica>(catalogoSolicitud,
				"PlanillaCata", listaCatalogo, false, "Usuario", "Estado",
				"Fecha de Envio de Planilla", "Marca", "Nombre Actividad",
				"Tipo Planilla") {

			@Override
			protected List<PlanillaGenerica> buscar(List<String> valores) {
				if (listPlanilla.size() == 0) {
					cargarLista();
				}
				List<PlanillaGenerica> lista = new ArrayList<PlanillaGenerica>();

				for (PlanillaGenerica planilla : listPlanilla) {
					if (planilla.getUsuario().toLowerCase()
							.startsWith(valores.get(0).toLowerCase())
							&& planilla.getEstado().toLowerCase()
									.startsWith(valores.get(1).toLowerCase())
							&& String
									.valueOf(
											formatoFecha.format(planilla
													.getFecha())).toLowerCase()
									.startsWith(valores.get(2))
							&& planilla.getMarca().toLowerCase()
									.startsWith(valores.get(3).toLowerCase())
							&& planilla.getNombreActividad().toLowerCase()
									.startsWith(valores.get(4).toLowerCase())
							&& planilla.getTipoPlanilla().toLowerCase()
									.startsWith(valores.get(5).toLowerCase())) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaGenerica planilla) {
				String[] registros = new String[6];
				registros[0] = planilla.getUsuario().toLowerCase();
				registros[1] = planilla.getEstado().toLowerCase();
				registros[2] = String.valueOf(formatoFecha.format(planilla
						.getFecha()));
				registros[3] = planilla.getMarca().toLowerCase();
				registros[4] = planilla.getNombreActividad().toLowerCase();
				registros[5] = planilla.getTipoPlanilla().toLowerCase();
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
				Messagebox.show("¿Desea Aprobar las " + procesadas.size()
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
				Messagebox.show("¿Desea Cancelar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									HashMap<String, Object> mapaRechazo = new HashMap<String, Object>();
									mapaRechazo.put("catalogo", catalogo);
									mapaRechazo.put("estatusViejo", estatus);
									mapaRechazo.put("estatusNuevo", estadoNuevo);
									Sessions.getCurrent().setAttribute(
											"rechazador", mapaRechazo);
									Window window = (Window) Executions
											.createComponents(
													"/vistas/componentes/VMotivo.zul",
													wdwSolicitud, mapaRechazo);
									window.doModal();
									listaMotivo = (Listbox) window.getChildren()
											.get(1).getChildren().get(1)
											.getChildren().get(0);
									listaMotivo.setModel(new ListModelList<PlanillaGenerica>(
											procesadas));
//									cambiarEstado(procesadas, estatus,
//											estadoDefecto, estadoNuevo);
//									cargarLista();
//									catalogo.actualizarLista(listPlanilla);
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
				Messagebox.show("¿Desea Rechazar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									HashMap<String, Object> mapaRechazo = new HashMap<String, Object>();
									mapaRechazo.put("catalogo", catalogo);
									mapaRechazo.put("estatusViejo", estatus);
									mapaRechazo.put("estatusNuevo", estadoNuevo);
									Sessions.getCurrent().setAttribute(
											"rechazador", mapaRechazo);
									Window window = (Window) Executions
											.createComponents(
													"/vistas/componentes/VMotivo.zul",
													wdwSolicitud, mapaRechazo);
									window.doModal();
									listaMotivo = (Listbox) window.getChildren()
											.get(1).getChildren().get(1)
											.getChildren().get(0);
									listaMotivo.setModel(new ListModelList<PlanillaGenerica>(
											procesadas));
//									cambiarEstado(procesadas, estatus,
//											estadoDefecto, estadoNuevo);
//									cargarLista();
//									catalogo.actualizarLista(listPlanilla);
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
				Messagebox.show("¿Desea Finalizar las " + procesadas.size()
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
				Messagebox.show("¿Desea Pagar las " + procesadas.size()
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
		byte[] imagen = imagenSi.getContent().getByteData();

		URL url2 = getClass().getResource("/imagenes/no.png");
		try {
			imagenNo.setContent(new AImage(url2));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] imagenX = imagenNo.getContent().getByteData();

		for (int i = 0; i < procesadas.size(); i++) {
			switch (procesadas.get(i).getTipoPlanilla()) {
			case "Eventos Especiales":
				PlanillaEvento planillaEvento = servicioPlanillaEvento
						.buscar(procesadas.get(i).getId());
				planillaEvento.setEstado(estado);
				planillaEvento.setRefencia(procesadas.get(i).getReferencia());
				planillaEvento.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaEvento.setHoraAuditoria(horaAuditoria);
				planillaEvento.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaEvento.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraEvento bitacoraE = new BitacoraEvento(0,
							planillaEvento, estadoNuevo, fechaHora, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), imagenX);
					listBitacoraEvento.add(bitacoraE);

				} else {
					BitacoraEvento bitacoraEvento = servicioBitacoraEvento
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							nombreUsuarioSesion(), estado,
							planillaEvento.getZona(), "Marca", "",
							planillaEvento.getIdPlanillaEvento(),"");
					servicioPlanillaEvento.guardar(nueva);
					nueva = servicioPlanillaEvento.buscarUltima();

					List<ItemDegustacionPlanillaEvento> itemsDegustacionAgregados = servicioItemDegustacionPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < itemsDegustacionAgregados.size(); j++) {
						itemsDegustacionAgregados.get(j).setPlanillaEvento(
								nueva);
					}
					List<ItemEstimadoPlanillaEvento> itemsEstimacionAgregados = servicioItemEstimadoPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < itemsEstimacionAgregados.size(); j++) {
						itemsEstimacionAgregados.get(j)
								.setPlanillaEvento(nueva);
					}
					List<RecursoPlanillaEvento> recursosAgregados = servicioRecursoPlanillaEvento
							.buscarPorPlanilla(planillaEvento);
					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).setPlanillaEvento(nueva);
					}

					servicioItemEstimadoPlanillaEvento
							.guardar(itemsEstimacionAgregados);
					servicioItemDegustacionPlanillaEvento
							.guardar(itemsDegustacionAgregados);
					servicioRecursoPlanillaEvento.guardar(recursosAgregados);
				}

				break;

			case "Uniformes":
				PlanillaUniforme planillaUniforme = servicioPlanillaUniforme
						.buscar(procesadas.get(i).getId());
				planillaUniforme.setEstado(estado);
				planillaUniforme.setRefencia(procesadas.get(i).getReferencia());
				planillaUniforme.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaUniforme.setHoraAuditoria(horaAuditoria);
				planillaUniforme.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaUniforme.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraUniforme bitacoraU = new BitacoraUniforme(0,
							planillaUniforme, estadoNuevo, fechaHora,
							fechaHora, horaAuditoria, nombreUsuarioSesion(),
							imagenX);
					listBitacoraUniforme.add(bitacoraU);

				} else {
					BitacoraUniforme bitacoraUniforme = servicioBitacoraUniforme
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							nombreUsuarioSesion(), estado, planillaUniforme
									.getUsuario().getZona().getDescripcion(),
							"Marca", "",
							planillaUniforme.getIdPlanillaUniforme(),"");
					servicioPlanillaUniforme.guardar(nueva);
					nueva = servicioPlanillaUniforme.buscarUltima();
					List<UniformePlanillaUniforme> uniformesAgregados = servicioUniformePlanillaUniforme
							.buscarPorPlanilla(planillaUniforme);
					for (int j = 0; j < uniformesAgregados.size(); j++) {
						uniformesAgregados.get(j).setPlanillaUniforme(nueva);
					}
					servicioUniformePlanillaUniforme
							.guardar(uniformesAgregados);
				}

				break;

			case "Promociones de Marca":
				PlanillaPromocion planillaPromocion = servicioPlanillaPromocion
						.buscar(procesadas.get(i).getId());
				planillaPromocion.setEstado(estado);
				planillaPromocion
						.setRefencia(procesadas.get(i).getReferencia());
				planillaPromocion.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaPromocion.setHoraAuditoria(horaAuditoria);
				planillaPromocion.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaPromocion.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraPromocion bitacoraP = new BitacoraPromocion(0,
							planillaPromocion, estadoNuevo, fechaHora,
							fechaHora, horaAuditoria, nombreUsuarioSesion(),
							imagenX);
					listBitacoraPromocion.add(bitacoraP);

				} else {
					BitacoraPromocion bitacoraPromocion = servicioBitacoraPromocion
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							planillaPromocion.getMarcaA(),
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
							horaAuditoria, nombreUsuarioSesion(), estado,
							planillaPromocion.getZona(), "Marca", "",
							planillaPromocion.getIdPlanillaPromocion(),"");
					servicioPlanillaPromocion.guardar(nueva);

					nueva = servicioPlanillaPromocion.buscarUltima();
				}

				break;

			case "Solicitud de Arte y Publicaciones":
				PlanillaArte planillaArte = servicioPlanillaArte
						.buscar(procesadas.get(i).getId());
				planillaArte.setEstado(estado);
				planillaArte.setRefencia(procesadas.get(i).getReferencia());
				planillaArte.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaArte.setHoraAuditoria(horaAuditoria);
				planillaArte.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaArte.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraArte bitacoraA = new BitacoraArte(0, planillaArte,
							estadoNuevo, fechaHora, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), imagenX);
					listBitacoraArte.add(bitacoraA);

				} else {
					BitacoraArte bitacoraArte = servicioBitacoraArte
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							nombreUsuarioSesion(), estado,
							planillaArte.getZona(), "Marca", "",
							planillaArte.getIdPlanillaArte(),"");
					servicioPlanillaArte.guardar(nueva);

					nueva = servicioPlanillaArte.buscarUltima();
				}

				break;

			case "Cata Induccion":
				PlanillaCata planillaCata = servicioPlanillaCata
						.buscar(procesadas.get(i).getId());
				planillaCata.setEstado(estado);
				planillaCata.setRefencia(procesadas.get(i).getReferencia());
				planillaCata.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaCata.setHoraAuditoria(horaAuditoria);
				planillaCata.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaCata.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraCata bitacoraC = new BitacoraCata(0, planillaCata,
							estadoNuevo, fechaHora, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), imagenX);
					listBitacoraCata.add(bitacoraC);

				} else {
					BitacoraCata bitacoraCata = servicioBitacoraCata
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							nombreUsuarioSesion(), estado,
							planillaCata.getZona(), "Marca", "",
							planillaCata.getIdPlanillaCata(),"");
					servicioPlanillaCata.guardar(nueva);
					nueva = servicioPlanillaCata.buscarUltima();

					List<ItemPlanillaCata> itemsAgregados = servicioItemPlanillaCata
							.buscarPorPlanilla(planillaCata);
					for (int j = 0; j < itemsAgregados.size(); j++) {
						itemsAgregados.get(j).setPlanillaCata(nueva);
					}

					List<RecursoPlanillaCata> recursosAgregados = servicioRecursoPlanillaCata
							.buscarPorPlanilla(planillaCata);

					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).setPlanillaCata(nueva);
					}

					servicioItemPlanillaCata.guardar(itemsAgregados);
					servicioRecursoPlanillaCata.guardar(recursosAgregados);
				}

				break;

			case "Fachada y Decoraciones":
				PlanillaFachada planillaFachada = servicioPlanillaFachada
						.buscar(procesadas.get(i).getId());
				planillaFachada.setEstado(estado);
				planillaFachada.setRefencia(procesadas.get(i).getReferencia());
				planillaFachada.setMotivoCancelacion(procesadas.get(i).getMotivo());
				planillaFachada.setHoraAuditoria(horaAuditoria);
				planillaFachada.setUsuarioAuditoria(nombreUsuarioSesion());
				planillaFachada.setFechaAuditoria(fechaHora);
				if (estado.equals("Rechazada") || estado.equals("Cancelada")) {
					BitacoraFachada bitacoraF = new BitacoraFachada(0,
							planillaFachada, estadoNuevo, fechaHora, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), imagenX);
					listBitacoraFachada.add(bitacoraF);

				} else {
					BitacoraFachada bitacoraFachada = servicioBitacoraFachada
							.buscarPorEstadoYPlanilla(estadoDefecto,
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
							nombreUsuarioSesion(), estado,
							planillaFachada.getZona(), "Marca", "",
							planillaFachada.getIdPlanillaFachada(), "");
					servicioPlanillaFachada.guardar(nueva);
					nueva = servicioPlanillaFachada.buscarUltima();
					List<RecursoPlanillaFachada> recursosAgregados = servicioRecursoPlanillaFachada
							.buscarPorPlanilla(planillaFachada);
					for (int j = 0; j < recursosAgregados.size(); j++) {
						recursosAgregados.get(j).setPlanillaFachada(nueva);
					}
					servicioRecursoPlanillaFachada.guardar(recursosAgregados);
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
			listCata = servicioPlanillaCata.buscarAdminEstado(variable,
					tipoConfig, usuarioSesion(nombreUsuarioSesion()));
			listEvento = servicioPlanillaEvento.buscarAdminEstado(variable,
					tipoConfig, usuarioSesion(nombreUsuarioSesion()));
			listPromocion = servicioPlanillaPromocion.buscarAdminEstado(
					variable, tipoConfig, usuarioSesion(nombreUsuarioSesion()));
			listArte = servicioPlanillaArte.buscarAdminEstado(variable,
					tipoConfig, usuarioSesion(nombreUsuarioSesion()));
			listUniforme = servicioPlanillaUniforme.buscarAdminEstado(variable,
					tipoConfig, usuarioSesion(nombreUsuarioSesion()));
			listFachada = servicioPlanillaFachada.buscarAdminEstado(variable,
					tipoConfig, usuarioSesion(nombreUsuarioSesion()));
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
		for (int i = 0; i < listCata.size(); i++) {
			long id = listCata.get(i).getIdPlanillaCata();
			String usuario = listCata.get(i).getUsuario().getNombre();
			String marca = listCata.get(i).getMarca().getDescripcion();
			String nombreActividad = listCata.get(i).getNombreActividad();
			String estado = listCata.get(i).getEstado();
			String tipoPlanilla = "Cata Induccion";
			Timestamp fecha = listCata.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listEvento.size(); i++) {
			long id = listEvento.get(i).getIdPlanillaEvento();
			String usuario = listEvento.get(i).getUsuario().getNombre();
			String marca = listEvento.get(i).getMarca().getDescripcion();
			String nombreActividad = listEvento.get(i).getNombreActividad();
			String estado = listEvento.get(i).getEstado();
			String tipoPlanilla = "Eventos Especiales";
			Timestamp fecha = listEvento.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listFachada.size(); i++) {
			long id = listFachada.get(i).getIdPlanillaFachada();
			String usuario = listFachada.get(i).getUsuario().getNombre();
			String marca = listFachada.get(i).getMarca().getDescripcion();
			String nombreActividad = listFachada.get(i).getNombreActividad();
			String estado = listFachada.get(i).getEstado();
			String tipoPlanilla = "Fachada y Decoraciones";
			Timestamp fecha = listFachada.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listPromocion.size(); i++) {
			long id = listPromocion.get(i).getIdPlanillaPromocion();
			String usuario = listPromocion.get(i).getUsuario().getNombre();
			String marca = listPromocion.get(i).getMarcaA().getDescripcion();
			String nombreActividad = listPromocion.get(i).getNombreActividad();
			String estado = listPromocion.get(i).getEstado();
			String tipoPlanilla = "Promociones de Marca";
			Timestamp fecha = listPromocion.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listArte.size(); i++) {
			long id = listArte.get(i).getIdPlanillaArte();
			String usuario = listArte.get(i).getUsuario().getNombre();
			String marca = listArte.get(i).getMarca().getDescripcion();
			String nombreActividad = listArte.get(i).getNombreActividad();
			String estado = listArte.get(i).getEstado();
			String tipoPlanilla = "Solicitud de Arte y Publicaciones";
			Timestamp fecha = listArte.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
		}
		for (int i = 0; i < listUniforme.size(); i++) {
			long id = listUniforme.get(i).getIdPlanillaUniforme();
			String usuario = listUniforme.get(i).getUsuario().getNombre();
			String marca = listUniforme.get(i).getMarca().getDescripcion();
			String nombreActividad = listUniforme.get(i).getNombreActividad();
			String estado = listUniforme.get(i).getEstado();
			String tipoPlanilla = "Uniformes";
			Timestamp fecha = listUniforme.get(i).getFechaEnvio();
			PlanillaGenerica plani = new PlanillaGenerica(id, usuario, marca,
					nombreActividad, fecha, estado, tipoPlanilla);
			listPlanilla.add(plani);
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
		String estatusNuevo="";
		String estatusViejo="";
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
				planilla.setMotivo(motivo);
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
}
