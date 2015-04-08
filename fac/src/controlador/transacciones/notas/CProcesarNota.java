package controlador.transacciones.notas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Aliado;
import modelo.maestros.Marca;
import modelo.maestros.Zona;
import modelo.seguridad.Arbol;
import modelo.transacciones.notas.DetalleNotaCredito;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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
	List<DetalleNotaCredito> listaGeneral = new ArrayList<DetalleNotaCredito>();
	ListModelList<Marca> marcas;
	ListModelList<Aliado> aliados;
	ListModelList<Zona> zonas;

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
				if (arbol.getNombre().equals("Certificar NC"))
					btnCertificar.setVisible(true);
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
									cambiarEstado(siguiente);
									refresh();
								}
							}
						});
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

	protected void cambiarEstado(String siguiente) {
		List<DetalleNotaCredito> listaEditar = new ArrayList<DetalleNotaCredito>();
		for (int i = 0; i < ltbLista.getItemCount(); i++) {
			Listitem listItem = ltbLista.getItemAtIndex(i);
			DetalleNotaCredito detalle = listItem.getValue();
			if (listItem.isSelected()) {
				Textbox text = (Textbox) listItem.getChildren().get(7)
						.getChildren().get(0);
				String observacion = text.getValue();
				detalle.setEstado(siguiente);
				detalle.setObservacion(observacion);
				detalle.setFechaAuditoria(fechaHora);
				detalle.setHoraAuditoria(horaAuditoria);
				detalle.setUsuarioAuditoria(nombreUsuarioSesion());
				if (siguiente.equals("Aprobada"))
					detalle.setFechaAprobacion(fechaHora);
				if (siguiente.equals("Certificada"))
					detalle.setFechaConfirmacion(fechaHora);
				listaEditar.add(detalle);
			}
		}
		servicioDetalleCredito.guardarVarios(listaEditar);
	}

	private boolean validarEstatus(List<DetalleNotaCredito> procesadas,
			String estatus) {
		int contadorAprobada = 0, contadorPendiente = 0, contadorCetificada = 0;
		boolean error = false;
		boolean errorTrans = false;
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
		}
		if (error) {
			msj.mensajeAlerta(Mensaje.estadoIncorrectoRechazada);
			return false;
		}
		if (errorTrans) {
			msj.mensajeAlerta("No puede cambiar de estado las solicitudes transferidas, verifique su seleccion");
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
							codigoMarca, codigoAliado, codigoZona, valor,
							desde, hasta, estado);
		else
			listaGeneral = servicioDetalleCredito
					.buscarLikeCodigoMarcaCodigoAliadoCodigoZonaYCodigoNotaYTipoYFechasEntre(
							codigoMarca, codigoAliado, codigoZona, codigo,
							valor, desde, hasta, estado);
		ltbLista.setModel(new ListModelList<DetalleNotaCredito>(listaGeneral));
		ltbLista.setMultiple(false);
		ltbLista.setMultiple(true);
		ltbLista.setCheckmark(false);
		ltbLista.setCheckmark(true);
	}
}
