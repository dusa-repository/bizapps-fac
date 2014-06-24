package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.estado.BitacoraFachada;
import modelo.estado.BitacoraPromocion;
import modelo.estado.BitacoraUniforme;
import modelo.generico.PlanillaGenerica;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;
import controlador.portal.CInbox;

public class CSolicitud extends CGenerico {

	private static final long serialVersionUID = 1572485553927529288L;
	@Wire
	private Window wdwSolicitud;
	@Wire
	private Div catalogoSolicitud;
	Catalogo<PlanillaGenerica> catalogo;
	String titulo = "";
	private boolean tradeMark = false;
	final List<PlanillaGenerica> listPlanilla = new ArrayList<PlanillaGenerica>();

	@Override
	public void inicializar() throws IOException {
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());
		List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getNombre().equals("Administrador")) {
				grupoDominante = grupos.get(i).getNombre();
				i = grupos.size();
				if (servicioConfiguracion.buscarAdministradorTradeMark(u,
						"TradeMark") != null)
					tradeMark = true;
			} else {
				if (grupos.get(i).getNombre().equals("Coordinador")) {
					grupoDominante = grupos.get(i).getNombre();
					i = grupos.size();
				} else {
					if (grupos.get(i).getNombre().equals("Supervisor")) {
						grupoDominante = grupos.get(i).getNombre();
						i = grupos.size();
					}
				}
			}
		}
		System.out.println("Grupo" + grupoDominante + "  Variable" + variable);
		buscarCatalogoPropio();

	}

	public void buscarCatalogoPropio() {
		cargarLista();
		System.out.println(listPlanilla.size());
		catalogo = new Catalogo<PlanillaGenerica>(catalogoSolicitud,
				"PlanillaCata", listPlanilla, false, "Usuario", "Estado",
				"Fecha", "Marca", "Nombre Actividad", "Tipo Planilla") {

			@Override
			protected List<PlanillaGenerica> buscar(List<String> valores) {

				List<PlanillaGenerica> lista = new ArrayList<PlanillaGenerica>();

				for (PlanillaGenerica planilla : listPlanilla) {
					if (planilla.getUsuario().toLowerCase()
							.startsWith(valores.get(0))
							&& planilla.getEstado().toLowerCase()
									.startsWith(valores.get(1))
							&& String
									.valueOf(
											formatoFecha.format(planilla
													.getFecha())).toLowerCase()
									.startsWith(valores.get(2))
							&& planilla.getMarca().toLowerCase()
									.startsWith(valores.get(3))
							&& planilla.getNombreActividad().toLowerCase()
									.startsWith(valores.get(4))
							&& planilla.getTipoPlanilla().toLowerCase()
									.startsWith(valores.get(5))) {
						lista.add(planilla);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(PlanillaGenerica planilla) {
				String[] registros = new String[6];
				registros[0] = planilla.getUsuario();
				registros[1] = planilla.getEstado();
				registros[2] = String.valueOf(formatoFecha.format(planilla
						.getFecha()));
				registros[3] = planilla.getMarca();
				registros[4] = planilla.getNombreActividad();
				registros[5] = planilla.getTipoPlanilla();
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
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("¿Desea Aprobar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus);
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
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("¿Desea Cancelar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus);
									cargarLista();
									catalogo.actualizarLista(listPlanilla);
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
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("¿Desea Rechazar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus);
									cargarLista();
									catalogo.actualizarLista(listPlanilla);
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
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estatus)) {
				Messagebox.show("¿Desea Finalizar las " + procesadas.size()
						+ " Planillas?", "Alerta", Messagebox.OK
						| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									cambiarEstado(procesadas, estatus);
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
									cambiarEstado(procesadas, estatus);
									cargarLista();
									catalogo.actualizarLista(listPlanilla);
								}
							}
						});
			}
		}
	}

	@Listen("onClick = #btnVer")
	public void verPlanilla() {
		final List<PlanillaGenerica> procesadas = catalogo
				.obtenerSeleccionados();
		if (validarSeleccion(procesadas)) {
			if (procesadas.size() == 1) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", procesadas.get(0).getId());
				map.put("estadoInbox", procesadas.get(0).getEstado());
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
				errorEdicion=true;
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
			if (contadorAprobada != 0 || contadorFinalizada != 0 || contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Finalizada":
			if (contadorPendiente != 0 || contadorFinalizada != 0 || contadorPagada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Pagada":
			if (contadorPendiente != 0 || contadorAprobada != 0 || contadorPagada != 0) {
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

	public void cambiarEstado(List<PlanillaGenerica> procesadas, String estado) {
		List<PlanillaCata> listCata = new ArrayList<PlanillaCata>();
		List<PlanillaFachada> listFachada = new ArrayList<PlanillaFachada>();
		List<PlanillaPromocion> listPromocion = new ArrayList<PlanillaPromocion>();
		List<PlanillaEvento> listEvento = new ArrayList<PlanillaEvento>();
		List<PlanillaArte> listArte = new ArrayList<PlanillaArte>();
		List<PlanillaUniforme> listUniforme = new ArrayList<PlanillaUniforme>();
		for (int i = 0; i < procesadas.size(); i++) {
			switch (procesadas.get(i).getTipoPlanilla()) {
			case "Eventos Especiales":
				PlanillaEvento planillaEvento = servicioPlanillaEvento
						.buscar(procesadas.get(i).getId());
				planillaEvento.setEstado(estado);
//				BitacoraEvento bitacoraEvento = new BitacoraEvento(0,
//						planillaEvento, estado, fechaHora, fechaHora,
//						horaAuditoria, nombreUsuarioSesion());
//				servicioBitacoraEvento.guardar(bitacoraEvento);
				listEvento.add(planillaEvento);
				break;

			case "Uniformes":
				PlanillaUniforme planillaUniforme = servicioPlanillaUniforme
						.buscar(procesadas.get(i).getId());
				planillaUniforme.setEstado(estado);
//				BitacoraUniforme bitacoraUniforme = new BitacoraUniforme(0,
//						planillaUniforme, estado, fechaHora, fechaHora,
//						horaAuditoria, nombreUsuarioSesion());
//				servicioBitacoraUniforme.guardar(bitacoraUniforme);
				listUniforme.add(planillaUniforme);
				break;

			case "Promociones de Marca":
				PlanillaPromocion planillaPromocion = servicioPlanillaPromocion
						.buscar(procesadas.get(i).getId());
				planillaPromocion.setEstado(estado);
//				BitacoraPromocion bitacora = new BitacoraPromocion(0,
//						planillaPromocion, estado, fechaHora, fechaHora,
//						horaAuditoria, nombreUsuarioSesion());
//				servicioBitacoraPromocion.guardar(bitacora);
				listPromocion.add(planillaPromocion);
				break;

			case "Solicitud de Arte y Publicaciones":
				PlanillaArte planillaArte = servicioPlanillaArte
						.buscar(procesadas.get(i).getId());
				planillaArte.setEstado(estado);
//				BitacoraArte bitacoraArte = new BitacoraArte(0, planillaArte,
//						estado, fechaHora, fechaHora, horaAuditoria,
//						nombreUsuarioSesion());
//				servicioBitacoraArte.guardar(bitacoraArte);
				listArte.add(planillaArte);
				break;

			case "Cata Induccion":
				PlanillaCata planillaCata = servicioPlanillaCata
						.buscar(procesadas.get(i).getId());
				planillaCata.setEstado(estado);
//				BitacoraCata bitacoraCata = new BitacoraCata(0, planillaCata,
//						estado, fechaHora, fechaHora, horaAuditoria,
//						nombreUsuarioSesion());
//				servicioBitacoraCata.guardar(bitacoraCata);
				listCata.add(planillaCata);
				break;

			case "Fachada y Decoraciones":
				PlanillaFachada planillaFachada = servicioPlanillaFachada
						.buscar(procesadas.get(i).getId());
				planillaFachada.setEstado(estado);
//				BitacoraFachada bitacoraFachada = new BitacoraFachada(0,
//						planillaFachada, estado, fechaHora, fechaHora,
//						horaAuditoria, nombreUsuarioSesion());
//				servicioBitacoraFachada.guardar(bitacoraFachada);
				listFachada.add(planillaFachada);
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
		if (!listUniforme.isEmpty())
			servicioPlanillaUniforme.guardarVarias(listUniforme);
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

		case "Coordinador":
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

		case "Supervisor":
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
			Timestamp fecha = listCata.get(i).getFechaAuditoria();
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
			Timestamp fecha = listEvento.get(i).getFechaAuditoria();
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
			Timestamp fecha = listPromocion.get(i).getFechaAuditoria();
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
			Timestamp fecha = listArte.get(i).getFechaAuditoria();
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
			Timestamp fecha = listUniforme.get(i).getFechaAuditoria();
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
			Timestamp fecha = listFachada.get(i).getFechaAuditoria();
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

		default:
			break;
		}
		return titulo;
	}
}
