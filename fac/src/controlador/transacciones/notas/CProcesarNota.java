package controlador.transacciones.notas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.generico.PlanillaGenerica;
import modelo.maestros.Aliado;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Zona;
import modelo.seguridad.Arbol;
import modelo.transacciones.notas.DetalleNotaCredito;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelElement;

import servicio.transacciones.notas.SDetalleNotaCredito;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.maestros.CGenerico;

public class CProcesarNota extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwProcesar;
	@Wire
	private Listbox ltbLista;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Combobox cmbEstado;
	@Wire
	private Combobox cmbAliado;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbZona;
	@Wire
	private Longbox txtCodigo;
	@Wire
	private Button btnAprobar;
	@Wire
	private Button btnCertificar;
	@Wire
	private Button btnRechazar;
	@Wire
	private Label lblFooter;
	List<DetalleNotaCredito> listaGeneral = new ArrayList<DetalleNotaCredito>();
	ListModelList<Marca> marcas;
	ListModelList<Aliado> aliados;
	ListModelList<Zona> zonas;
	boolean certificadas = false;
	boolean reporte = false;

	@Override
	public void inicializar() throws IOException {
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				authe.getAuthorities());
		for (int i = 0; i < authorities.size(); i++) {
			Arbol arbol;
			String nombre = authorities.get(i).toString();
			if (Validador.validarNumero(nombre)) {
				arbol = servicioArbol.buscar(Long.parseLong(nombre));
				if (arbol.getNombre().equals("Aprobar NC"))
					btnAprobar.setVisible(true);
				if (arbol.getNombre().equals("Certificar NC")) {
					certificadas = true;
					btnCertificar.setVisible(true);
				}
				if (arbol.getNombre().equals("Rechazar NC"))
					btnRechazar.setVisible(true);
			}
		}
		refresh();
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

	public ListModelList<Zona> getZonas() {
		Zona marca = new Zona();
		marca.setDescripcion("TODAS");
		marca.setIdZona("%");
		List<Zona> marcasLista = new ArrayList<Zona>();
		marcasLista.add(marca);
		marcasLista.addAll(servicioZona.buscarTodosOrdenados());
		zonas = new ListModelList<Zona>(marcasLista);
		return zonas;
	}

	public ListModelList<Aliado> getAliados() {
		Aliado marca = new Aliado();
		marca.setNombre("TODOS");
		marca.setIdAliado(0);
		List<Aliado> marcasLista = new ArrayList<Aliado>();
		marcasLista.add(marca);
		marcasLista.addAll(servicioAliado.buscarTodosOrdenados());
		aliados = new ListModelList<Aliado>(marcasLista);
		return aliados;
	}

	@Listen("onClick= #btnCerrar")
	public void salir() {
		cerrarVentana(wdwProcesar);
	}

	@Listen("onClick= #btnVer")
	public void ver() {
		List<DetalleNotaCredito> procesadas = obtenerSeleccionados();
		if (validarSeleccion(procesadas)) {
			if (procesadas.size() == 1) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", procesadas.get(0).getId().getNotaCredito()
						.getIdNotaCredito());
				Sessions.getCurrent().setAttribute("consulta", map);
				Window window = (Window) Executions.createComponents(
						"/vistas/transacciones/notas/VNotaCredito.zul", null,
						map);
				window.doModal();
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);

		}
	}

	@Listen("onClick= #btnAprobar, #btnCertificar, #btnRechazar")
	public void procesar(Event evento) {
		final List<DetalleNotaCredito> procesadas = obtenerSeleccionados();
		String estadoSiguiente = "";
		String nombre = "";
		switch (evento.getTarget().getId()) {
		case "btnAprobar":
			estadoSiguiente = "Aprobada";
			nombre = "Aprobar";
			break;
		case "btnCertificar":
			estadoSiguiente = "Certificada";
			nombre = "Certificar";
			break;
		case "btnRechazar":
			estadoSiguiente = "Rechazada";
			nombre = "Rechazar";
			break;
		}
		final String siguiente = estadoSiguiente;
		if (validarSeleccion(procesadas)) {
			if (validarEstatus(procesadas, estadoSiguiente)) {
				Messagebox.show(
						"¿Desea " + nombre + " las " + procesadas.size()
								+ " Planillas?", "Alerta", Messagebox.OK
								| Messagebox.CANCEL, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									if (siguiente.equals("Rechazada"))
										abrirVentanaRechazo(siguiente,
												procesadas);
									else {
										if (siguiente.equals("Aprobada"))
											verificarSaldo(siguiente,
													procesadas);
										else
											cambiarEstado(siguiente, procesadas);
									}

								}
							}
						});
			}
		}

	}

	protected void verificarSaldo(String estado,
			List<DetalleNotaCredito> procesadas) {
		List<DetalleNotaCredito> reales = new ArrayList<DetalleNotaCredito>();
		String zonas = "";
		for (int w = 0; w < procesadas.size(); w++) {
			DetalleNotaCredito detalle = procesadas.get(w);
			if (detalle.getId().getNotaCredito().getAliado().getZona() != null) {
				Zona zona = detalle.getId().getNotaCredito().getAliado()
						.getZona();
				if (zona.getSaldo() != null) {
					if (zona.getSaldo() - detalle.getCosto() >= 0) {
						zona.setConsumido(zona.getConsumido()
								+ detalle.getCosto());
						zona.setSaldo(zona.getSaldo() - detalle.getCosto());
						servicioZona.guardar(zona);
						reales.add(detalle);
					} else
						zonas += zona.getDescripcion() + ". ";
				} else
					zonas += zona.getDescripcion() + ". ";
			}
		}
		if (reales.isEmpty())
			msj.mensajeAlerta("Las solicitudes no pudieron ser aprobadas, ya que las siguientes zonas no cuentan con saldo disponible: "
					+ zonas);
		else {
			if (zonas.equals(""))
				cambiarEstado(estado, reales);
			else {
				msj.mensajeAlerta("Algunas solicitudes no pudieron ser aprobadas, ya que las siguientes zonas no cuentan con saldo disponible: "
						+ zonas
						+ "El resto de las mismas fueron aprobadas correctamente");
				cambiarEstado(estado, reales);
			}

		}
	}

	private List<DetalleNotaCredito> obtenerSeleccionados() {
		List<DetalleNotaCredito> valores = new ArrayList<DetalleNotaCredito>();
		if (ltbLista.getItemCount() != 0) {
			List<Listitem> list1 = ltbLista.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected()) {
					DetalleNotaCredito clase = list1.get(i).getValue();
					valores.add(clase);
				}
			}
			return valores;
		} else
			return null;
	}

	protected void cambiarEstado(String siguiente,
			List<DetalleNotaCredito> procesadas) {
		List<DetalleNotaCredito> listaEditar = new ArrayList<DetalleNotaCredito>();
		for (int i = 0; i < procesadas.size(); i++) {
			DetalleNotaCredito detalle = procesadas.get(i);
			detalle.setEstado(siguiente);
			detalle.setFechaAuditoria(fechaHora);
			detalle.setHoraAuditoria(horaAuditoria);
			detalle.setUsuarioAuditoria(nombreUsuarioSesion());
			if (siguiente.equals("Aprobada"))
				detalle.setFechaAprobacion(fechaHora);
			if (siguiente.equals("Certificada"))
				detalle.setFechaConfirmacion(fechaHora);
			listaEditar.add(detalle);
		}
		servicioDetalleCredito.guardarVarios(listaEditar);
		refresh();
	}

	private boolean validarEstatus(List<DetalleNotaCredito> procesadas,
			String estatus) {
		int contadorAprobada = 0, contadorPendiente = 0, contadorCetificada = 0;
		boolean error = false;
		boolean errorTrans = false;
		boolean errorEdicion = false;
		for (int i = 0; i < procesadas.size(); i++) {
			if (procesadas.get(i).getEstado().equals("Enviada"))
				contadorPendiente++;
			if (procesadas.get(i).getEstado().equals("Aprobada"))
				contadorAprobada++;
			if (procesadas.get(i).getEstado().equals("Certificada"))
				contadorCetificada++;
			if (procesadas.get(i).getEstado().equals("Rechazada"))
				error = true;
			if (procesadas.get(i).getEstado().equals("Transferida"))
				errorTrans = true;
			if (procesadas.get(i).getEstado().equals("En Edicion"))
				errorEdicion = true;
		}
		if (error) {
			msj.mensajeAlerta(Mensaje.estadoIncorrectoRechazada);
			return false;
		}
		if (errorTrans) {
			msj.mensajeAlerta("No puede cambiar de estado las solicitudes transferidas, verifique su seleccion");
			return false;
		}
		if (errorEdicion) {
			msj.mensajeAlerta("No puede cambiar de estado las solicitudes en edicion, verifique su seleccion");
			return false;
		}
		switch (estatus) {
		case "Aprobada":
			if (contadorAprobada != 0 || contadorCetificada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Certificada":
			if (contadorPendiente != 0 || contadorCetificada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		case "Rechazada":
			if (contadorCetificada != 0) {
				msj.mensajeAlerta(Mensaje.estadoIncorrecto);
				return false;
			} else
				return true;
		default:
			return true;
		}
	}

	private boolean validarSeleccion(List<DetalleNotaCredito> procesadas) {
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

	@Listen("onClick = #btnRefrescar")
	public void refresh() {
		String tipo = "%";
		if (!certificadas)
			tipo = valor;
		Date desde = new Date();
		if (dtbDesde.getValue() != null)
			desde = dtbDesde.getValue();
		desde = restarDia(desde);
		Date hasta = new Date();
		if (dtbHasta.getValue() != null)
			hasta = dtbHasta.getValue();
		hasta = agregarDia(hasta);
		String codigoMarca = "%";
		if (cmbMarca.getSelectedItem() != null)
			codigoMarca = cmbMarca.getSelectedItem().getContext();
		String codigoAliado = "%";
		if (cmbAliado.getSelectedItem() != null)
			if (!cmbAliado.getSelectedItem().getContext().equals("0"))
				codigoAliado = cmbAliado.getValue();
		String codigoZona = "%";
		if (cmbZona.getSelectedItem() != null)
			if (!cmbEstado.getValue().equals("TODAS"))
				codigoZona = cmbZona.getSelectedItem().getContext();
		long codigo = 0;
		if (txtCodigo.getText().compareTo("") != 0)
			codigo = txtCodigo.getValue();
		String estado = "%";
		if (cmbEstado.getSelectedItem() != null)
			if (!cmbEstado.getValue().equals("TODOS"))
				estado = cmbEstado.getValue();
		listaGeneral.clear();
		if (codigo == 0)
			listaGeneral = servicioDetalleCredito
					.buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYTipoYFechasEntre(
							codigoMarca, codigoAliado, codigoZona, tipo, desde,
							hasta, estado, reporte);
		else
			listaGeneral = servicioDetalleCredito
					.buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYCodigoNotaYTipoYFechasEntre(
							codigoMarca, codigoAliado, codigoZona, codigo,
							tipo, desde, hasta, estado, reporte);
		ltbLista.setModel(new ListModelList<DetalleNotaCredito>(listaGeneral));
		ltbLista.setMultiple(false);
		ltbLista.setMultiple(true);
		ltbLista.setCheckmark(false);
		ltbLista.setCheckmark(true);
	}

	@Listen("onSelect = #ltbLista")
	public void actualizarCosto() {
		if (ltbLista != null)
			if (ltbLista.getItemCount() != 0) {
				Double costo = (double) 0;
				for (int i = 0; i < ltbLista.getItemCount(); i++) {
					Listitem listItem = ltbLista.getItemAtIndex(i);
					if (listItem.isSelected()) {
						Listcell celda = (Listcell) listItem.getChildren().get(
								8);
						costo = costo + Double.valueOf(celda.getLabel());
					}
				}
				lblFooter.setValue(String.valueOf(costo));
			}
	}

	// Ventana de Rechazo
	protected void abrirVentanaRechazo(String siguiente,
			List<DetalleNotaCredito> procesadas) {
		HashMap<String, Object> mapaRechazo = new HashMap<String, Object>();
		mapaRechazo.put("estado", siguiente);
		mapaRechazo.put("listaGeneral", listaGeneral);
		mapaRechazo.put("procesadas", procesadas);
		mapaRechazo.put("dtbDesde", dtbDesde);
		mapaRechazo.put("dtbHasta", dtbHasta);
		mapaRechazo.put("ltbLista", ltbLista);
		mapaRechazo.put("cmbEstado", cmbEstado);
		mapaRechazo.put("cmbAliado", cmbAliado);
		mapaRechazo.put("cmbMarca", cmbMarca);
		mapaRechazo.put("cmbZona", cmbZona);
		mapaRechazo.put("txtCodigo", txtCodigo);
		mapaRechazo.put("certificadas", certificadas);
		mapaRechazo.put("reporte", reporte);
		Sessions.getCurrent().setAttribute("rechazado", mapaRechazo);
		Window window = (Window) Executions.createComponents(
				"/vistas/transacciones/notas/VRechazoNota.zul", wdwProcesar,
				mapaRechazo);
		window.doModal();
	}

	public void recibirParametros(String estado,
			List<DetalleNotaCredito> listaRechazada, Datebox dtbDesde2,
			Datebox dtbHasta2, Listbox ltbLista2, Combobox cmbEstado2,
			Combobox cmbAliado2, Combobox cmbMarca2, Combobox cmbZona2,
			Longbox txtCodigo2, SDetalleNotaCredito servicioDetalleCredito2,
			List<DetalleNotaCredito> listaGeneral2, boolean certificadas2,
			boolean reporte2) {
		dtbDesde = dtbDesde2;
		dtbHasta = dtbHasta2;
		ltbLista = ltbLista2;
		cmbEstado = cmbEstado2;
		cmbAliado = cmbAliado2;
		cmbMarca = cmbMarca2;
		cmbZona = cmbZona2;
		txtCodigo = txtCodigo2;
		listaGeneral = listaGeneral2;
		servicioDetalleCredito = servicioDetalleCredito2;
		certificadas = certificadas2;
		reporte = reporte2;
		cambiarEstado(estado, listaRechazada);
	}

	@Listen("onClick = #btnReporte")
	public void reporte() {
		if (ltbLista.getItemCount() != 0) {
			reporte = true;
			refresh();
			ltbLista.renderAll();
			String s = ";";
			final StringBuffer sb = new StringBuffer();

			for (Object head : ltbLista.getHeads()) {
				String h = "";
				if (head instanceof Listhead) {
					for (Object header : ((Listhead) head).getChildren()) {
						h += ((Listheader) header).getLabel() + s;
					}
					sb.append(h + "\n");
				}
			}
			for (Object item : ltbLista.getItems()) {
				String i = "";
				for (Object cell : ((Listitem) item).getChildren()) {
					i += ((Listcell) cell).getLabel() + s;
				}
				sb.append(i + "\n");
			}
			Messagebox.show(Mensaje.exportar, "Alerta", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								Filedownload.save(sb.toString().getBytes(),
										"text/plain", "datos.csv");
							}
						}
					});
			reporte = false;
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

}
