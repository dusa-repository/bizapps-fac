package controlador.transacciones.notas;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import modelo.generico.PlanillaGenerica;
import modelo.maestros.Aliado;
import modelo.maestros.F0005;
import modelo.maestros.Marca;
import modelo.maestros.Zona;
import modelo.pk.ConfiguracionMarcaId;
import modelo.pk.CostoNotaCreditoId;
import modelo.pk.DetalleNotaCreditoId;
import modelo.seguridad.ConfiguracionEnvio;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.notas.ConfiguracionMarca;
import modelo.transacciones.notas.CostoNotaCredito;
import modelo.transacciones.notas.DetalleNotaCredito;
import modelo.transacciones.notas.NotaCredito;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
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

public class CNotaCredito extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwNotaCredito;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabDistribucion;
	@Wire
	private Tab tabPote;
	@Wire
	private Textbox txtAliado;
	@Wire
	private Textbox txtRef;
	@Wire
	private Label lblNombre;
	@Wire
	private Label lblZona;
	@Wire
	private Datebox dtbFecha;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbActividad;
	@Wire
	private Textbox txtDescripcion;
	@Wire
	private Spinner spnCantidad;
	@Wire
	private Doublespinner spnCostoLinea;
	@Wire
	private Div botoneraNota;
	@Wire
	private Div catalogoNota;
	@Wire
	private Div divCatalogoAliado;
	@Wire
	private Div divCatalogoDistribucion;
	@Wire
	private Div divCatalogoPote;
	@Wire
	private Listbox ltbLista;
	@Wire
	private Intbox txtPlan;
	@Wire
	private Doublebox txtDistribuir;
	@Wire
	private Doublebox txtPlanPote;
	@Wire
	private Doublebox txtDistribuirPote;
	Botonera botonera;
	Catalogo<NotaCredito> catalogo;
	Catalogo<Aliado> catalogoAliado;
	Catalogo<ConfiguracionMarca> catalogoDistribucion;
	Catalogo<ConfiguracionMarca> catalogoPote;
	List<DetalleNotaCredito> listaDetalle = new ArrayList<DetalleNotaCredito>();
	long contador = 0;
	boolean editar = true;
	private Long id = null;
	private Long idAliado = null;
	private Zona zonaFiltro = null;

	@Override
	public void inicializar() throws IOException {
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();
		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());
		if (u.getZona() != null)
			zonaFiltro = u.getZona();
		txtAliado.setFocus(true);
		List<F0005> udc = new ArrayList<F0005>();
		if (valor.equals("Marca"))
			udc = servicioF0005
					.buscarParaUDCOrdenadosYTipo("00", "18", "Marca");
		else
			udc = servicioF0005.buscarParaUDCOrdenadosYTipo("00", "18",
					"Trademark");
		cmbActividad.setModel(new ListModelList<F0005>(udc));
		dtbFecha.setValue(fechaHora);
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Marca");
		strings.add("Plan Caja");
		strings.add("Peso %");
		strings.add("Consolidado");
		for (int i = 0; i < udc.size(); i++) {
			if (!udc.get(i).getDrdl01().toUpperCase().contains("POTE"))
				strings.add(udc.get(i).getDrdl01());

		}
		String[] stockArr = new String[strings.size()];
		stockArr = strings.toArray(stockArr);
		mostrarCatalogoPote();
		mostrarCatalogoDistribucion(stockArr);
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwNotaCredito);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
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
			public void enviar() {
				if (validar()) {
					if (validarMarcasActivas()) {
						guardarDatos("Enviada");
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
						salir();
					}
				}
			}

			@Override
			public void eliminar() {
				if (id != null) {
					if (editar)
						Messagebox
								.show("¿Esta Seguro de Eliminar la Planilla?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													NotaCredito planilla = servicioNotaCredito
															.buscar(id);
													servicioCostoNotaCredito
															.limpiar(planilla);
													servicioDetalleCredito
															.limpiar(planilla);
													servicioNotaCredito
															.eliminar(id);
													limpiar();
													msj.mensajeInformacion(Mensaje.eliminado);
												}
											}
										});
					else
						msj.mensajeAlerta("No puede eliminar Planillas que han sido enviadas");
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();
			}

			@Override
			public void atras() {
				if (tabDistribucion.isSelected())
					tabDatos.setSelected(true);
				if (tabPote.isSelected())
					tabDistribucion.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabDistribucion.isSelected())
					tabPote.setSelected(true);
				if (tabDatos.isSelected())
					tabDistribucion.setSelected(true);

			}
		};
		botoneraNota.appendChild(botonera);
		ConfiguracionEnvio objeto = servicioConfiguracionEnvio.buscar((long) 1);
		if (objeto != null)
			if (objeto.getEstado())
				validarBotonEnviar(objeto, botonera);

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("consulta");
		if (map != null) {
			if (map.get("id") != null) {
				NotaCredito nota = servicioNotaCredito.buscar((Long) map
						.get("id"));
				llenarCamposPropios(nota);
				botonera.getChildren().get(0).setVisible(false);
				botonera.getChildren().get(1).setVisible(false);
				botonera.getChildren().get(2).setVisible(false);
				botonera.getChildren().get(3).setVisible(false);
				botonera.getChildren().get(7).setVisible(false);
				botonera.getChildren().get(8).setVisible(false);
				map.clear();
				map = null;
			}
		}
	}

	protected boolean validarMarcasActivas() {
		String marcas = "";
		for (int i = 0; i < listaDetalle.size(); i++) {
			if (listaDetalle.get(i).getMarca().getEstado() != null)
				if (!listaDetalle.get(i).getMarca().getEstado()) {
					marcas += listaDetalle.get(i).getMarca().getDescripcion()
							+ ". ";
				}

		}
		if (marcas.equals(""))
			return true;
		else {
			msj.mensajeAlerta("La solicitud no pudo ser enviada, porque las siguientes marcas se encuentran inactivas: "
					+ marcas + "Por favor contacte al administrador");
			return false;
		}
	}

	private void mostrarCatalogoPote() {
		final List<ConfiguracionMarca> listAliado = servicioConfiguracionMarca
				.buscarTodosParaPoteYTipo(valor);
		catalogoPote = new Catalogo<ConfiguracionMarca>(divCatalogoPote,
				"Catalogo de Pote", listAliado, false, false, "Marca", "Bs",
				"Peso %", "Suma Pote Comercial", "Monto a Distribuir") {

			@Override
			protected List<ConfiguracionMarca> buscar(List<String> valores) {
				return listAliado;
			}

			@Override
			protected String[] crearRegistros(ConfiguracionMarca aliado) {
				String[] registros = new String[5];
				registros[4] = String.valueOf(0);
				registros[3] = String.valueOf(0);
				registros[0] = aliado.getId().getMarca().getDescripcion();
				registros[1] = String.valueOf(aliado.getCosto());
				registros[2] = String.valueOf(round(
						aliado.getPorcentajeCosto(), 2));
				return registros;
			}
		};
		catalogoPote.setParent(divCatalogoPote);
	}

	private void mostrarCatalogoDistribucion(final String[] strings) {
		final List<ConfiguracionMarca> listAliado = servicioConfiguracionMarca
				.buscarTodosParaDistribucionYTipo(valor);
		catalogoDistribucion = new Catalogo<ConfiguracionMarca>(
				divCatalogoDistribucion, "Catalogo de Distribucion",
				listAliado, false, false, strings) {

			@Override
			protected List<ConfiguracionMarca> buscar(List<String> valores) {
				return listAliado;
			}

			@Override
			protected String[] crearRegistros(ConfiguracionMarca aliado) {
				String[] registros = new String[strings.length];
				for (int i = 0; i < strings.length; i++) {
					switch (strings[i]) {
					case "Plan Caja":
						registros[i] = String.valueOf(aliado.getCajas());
						break;
					case "Peso %":
						registros[i] = String.valueOf(round(
								aliado.getPorcentajePlan(), 2));
						break;
					case "Marca":
						registros[i] = aliado.getId().getMarca()
								.getDescripcion();
						break;
					default:
						registros[i] = String.valueOf(0);
						break;
					}

				}
				return registros;
			}
		};
		catalogoDistribucion.setParent(divCatalogoDistribucion);

	}

	protected void limpiarCampos() {
		botonera.getChildren().get(0).setVisible(true);
		botonera.getChildren().get(3).setVisible(true);
		botonera.getChildren().get(8).setVisible(true);
		txtAliado.setValue("");
		txtCosto.setValue(0);
		limpiarCamposItem();
		limpiarColores(txtAliado, txtCosto, txtDescripcion, cmbActividad,
				cmbMarca, spnCantidad, spnCostoLinea, txtRef);
		lblNombre.setValue("");
		lblZona.setValue("");
		editar = true;
		id = null;
		idAliado = null;
		listaDetalle.clear();
		ltbLista.renderAll();
		ltbLista.getItems().clear();
		catalogoPote.setId(null);
		catalogoPote.setParent(null);
		catalogoPote = null;
		catalogoDistribucion.setId(null);
		catalogoDistribucion.setParent(null);
		catalogoDistribucion = null;
		List<F0005> udc = new ArrayList<F0005>();
		if (valor.equals("Marca"))
			udc = servicioF0005
					.buscarParaUDCOrdenadosYTipo("00", "18", "Marca");
		else
			udc = servicioF0005.buscarParaUDCOrdenadosYTipo("00", "18",
					"Trademark");
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Marca");
		strings.add("Plan Caja");
		strings.add("Peso %");
		strings.add("Consolidado");
		for (int i = 0; i < udc.size(); i++) {
			if (!udc.get(i).getDrdl01().toUpperCase().contains("POTE"))
				strings.add(udc.get(i).getDrdl01());

		}
		String[] stockArr = new String[strings.size()];
		stockArr = strings.toArray(stockArr);
		mostrarCatalogoPote();
		mostrarCatalogoDistribucion(stockArr);
	}

	protected void guardarDatos(String estado) {
		if (id != null) {
			NotaCredito planilla = servicioNotaCredito.buscar(id);
			servicioDetalleCredito.limpiar(planilla);
		} else
			id = (long) 0;
		Aliado aliado = servicioAliado.buscar(idAliado);
		NotaCredito nota = new NotaCredito(id, aliado, new Timestamp(dtbFecha
				.getValue().getTime()), fechaHora, horaAuditoria,
				nombreUsuarioSesion(), valor, txtCosto.getValue(), estado);
		servicioNotaCredito.guardar(nota);

		if (id != 0)
			nota = servicioNotaCredito.buscar(id);
		else
			nota = servicioNotaCredito.buscarUltima();

		servicioCostoNotaCredito.limpiar(nota);
		DetalleNotaCreditoId clave = new DetalleNotaCreditoId();
		clave.setNotaCredito(nota);
		Integer contador = 0;
		for (int i = 0; i < listaDetalle.size(); i++) {
			contador++;
			clave.setIdDetalleCredito(contador.longValue());
			DetalleNotaCredito detalle = listaDetalle.get(i);
			detalle.setId(clave);
			detalle.setEstado(estado);
			detalle.setFechaCreacion(new Timestamp(dtbFecha.getValue()
					.getTime()));
			detalle.setFechaAuditoria(fechaHora);
			detalle.setHoraAuditoria(horaAuditoria);
			detalle.setUsuarioAuditoria(nombreUsuarioSesion());
			detalle.setPorcentajeAplicado(null);
			if (detalle.getTipoActividad().toUpperCase().contains("POTE"))
				guardarCostos(nota, detalle.getCosto(), contador.longValue());
			else {
				ConfiguracionMarcaId claveConfig = new ConfiguracionMarcaId();
				claveConfig.setMarca(detalle.getMarca());
				claveConfig.setTipo(valor);
				ConfiguracionMarca objeto = servicioConfiguracionMarca
						.buscarClave(claveConfig);
				detalle.setPorcentajeAplicado(objeto.getPorcentajePlan());
			}
			servicioDetalleCredito.guardar(detalle);
		}
		Usuario usuario = servicioUsuario
				.buscarUsuarioPorNombre(nombreUsuarioSesion());
		if (estado.equals("Enviada")) {
			F0005 udc = servicioF0005.buscar("00", "20", "00");
			if (udc == null)
				msj.mensajeAlerta("No se ha enviado el correo debido a que no se ha configurado un correo para el grupo de nota de credito");
			else
				enviarEmailNota(valor, nombreUsuarioSesion(),
						nota.getIdNotaCredito(), usuario.getMail(),
						udc.getDrdl01());
		}

	}

	private void guardarCostos(NotaCredito nota, Double costo, long cont) {
		List<ConfiguracionMarca> listaFija = servicioConfiguracionMarca
				.buscarTodosParaPoteYTipo(valor);
		CostoNotaCreditoId clave = new CostoNotaCreditoId();
		clave.setNotaCredito(nota);
		clave.setIdLinea(cont);
		for (Iterator<ConfiguracionMarca> iterator = listaFija.iterator(); iterator
				.hasNext();) {
			ConfiguracionMarca configuracionMarca = (ConfiguracionMarca) iterator
					.next();
			clave.setMarca(configuracionMarca.getId().getMarca());
			CostoNotaCredito objeto = new CostoNotaCredito(clave, costo
					* configuracionMarca.getPorcentajeCosto() / 100,
					configuracionMarca.getPorcentajeCosto(), fechaHora,
					horaAuditoria, nombreUsuarioSesion());
			servicioCostoNotaCredito.guardar(objeto);
		}
	}

	protected boolean validar() {
		if (lblNombre.getValue().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0) {
			aplicarColores(txtAliado, txtCosto);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!editar) {
				msj.mensajeAlerta("No puede Editar notas que ya han sido enviadas");
				return false;
			} else
				return true;
		}
	}

	public void buscarCatalogoPropio() {
		final List<NotaCredito> listAliado = servicioNotaCredito
				.buscarTodosOrdenadosPorTipo(valor);
		catalogo = new Catalogo<NotaCredito>(catalogoNota,
				"Catalogo de Aliados", listAliado, true, "Aliado",
				"Codigo Aliado", "Zona", "Fecha", "Estado") {

			@Override
			protected List<NotaCredito> buscar(List<String> valores) {

				List<NotaCredito> lista = new ArrayList<NotaCredito>();

				for (NotaCredito aliado : listAliado) {
					String codigo = "";
					if (aliado.getAliado().getCodigo() != null)
						codigo = aliado.getAliado().getCodigo();
					if (aliado.getAliado().getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& codigo.toLowerCase().contains(
									valores.get(1).toLowerCase())
							&& aliado.getAliado().getZona().getDescripcion()
									.toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& formatoFecha.format(aliado.getFechaNota())
									.toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& aliado.getEstado().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						lista.add(aliado);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(NotaCredito aliado) {
				String codigo = "";
				if (aliado.getAliado().getCodigo() != null)
					codigo = aliado.getAliado().getCodigo();
				String[] registros = new String[5];
				registros[0] = aliado.getAliado().getNombre();
				registros[1] = codigo;
				registros[2] = aliado.getAliado().getZona().getDescripcion();
				registros[3] = formatoFecha.format(aliado.getFechaNota());
				registros[4] = aliado.getEstado();
				return registros;
			}
		};
		catalogo.setParent(catalogoNota);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoNota")
	public void seleccionPropia() {
		NotaCredito aliado = catalogo.objetoSeleccionadoDelCatalogo();
		limpiarCampos();
		llenarCamposPropios(aliado);
		catalogo.setParent(null);
	}

	@Listen("onClick = #btnBuscarAliado")
	public void buscarCatalogoAjeno() {
		List<Aliado> aliados = new ArrayList<Aliado>();
		if (zonaFiltro != null)
			aliados = servicioAliado.buscarPorZona(zonaFiltro);
		final List<Aliado> listZona = aliados;
		catalogoAliado = new Catalogo<Aliado>(divCatalogoAliado,
				"Catalogo de Aliados", listZona, true, "Codigo", "Nombre",
				"Zona") {

			@Override
			protected List<Aliado> buscar(List<String> valores) {

				List<Aliado> lista = new ArrayList<Aliado>();

				for (Aliado aliado : listZona) {
					String codigo = "";
					if (aliado.getCodigo() != null)
						codigo = aliado.getCodigo();
					if (codigo.toLowerCase().contains(
							valores.get(0).toLowerCase())
							&& aliado.getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& aliado.getZona().getDescripcion().toLowerCase()
									.contains(valores.get(2).toLowerCase())) {
						lista.add(aliado);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Aliado aliado) {
				String codigo = "";
				if (aliado.getCodigo() != null)
					codigo = aliado.getCodigo();
				String[] registros = new String[3];
				registros[0] = codigo;
				registros[1] = aliado.getNombre();
				registros[2] = aliado.getZona().getDescripcion();
				return registros;
			}
		};
		catalogoAliado.setParent(divCatalogoAliado);
		catalogoAliado.doModal();
	}

	@Listen("onSeleccion = #divCatalogoAliado")
	public void seleccionAjena() {
		Aliado zona = catalogoAliado.objetoSeleccionadoDelCatalogo();
		llenarCamposAjenos(zona);
		catalogoAliado.setParent(null);
	}

	@Listen("onSelect=#cmbActividad")
	public void seleccionarCombo() {
		cmbMarca.setValue("");
		if (cmbActividad.getValue().toUpperCase().contains("POTE")) {
			List<Marca> marcas = new ArrayList<Marca>();
			List<ConfiguracionMarca> configuracion = servicioConfiguracionMarca
					.buscarTodosParaPoteYTipo(valor);
			for (Iterator<ConfiguracionMarca> iterator = configuracion
					.iterator(); iterator.hasNext();) {
				ConfiguracionMarca configuracionMarca = (ConfiguracionMarca) iterator
						.next();
				marcas.add(configuracionMarca.getId().getMarca());
			}
			cmbMarca.setModel(new ListModelList<Marca>(marcas));
		} else
			cmbMarca.setModel(new ListModelList<Marca>(servicioMarca
					.buscarTodosOrdenados()));
	}

	@Listen("onChange = #txtAliado; onOK = #txtAliado")
	public void buscarPorNombrePropio() {
		Aliado aliado = servicioAliado.buscarPorCodigo(txtAliado.getValue());
		if (aliado != null)
			llenarCamposAjenos(aliado);
		else {
			txtAliado.setValue("");
			lblNombre.setValue("");
			lblZona.setValue("");
			txtAliado.setFocus(true);
			msj.mensajeAlerta(Mensaje.noHayRegistros);
		}
	}

	public void llenarCamposAjenos(Aliado aliado) {
		idAliado = aliado.getIdAliado();
		txtAliado.setValue(aliado.getCodigo());
		lblNombre.setValue(aliado.getNombre());
		lblZona.setValue(aliado.getZona().getDescripcion());
	}

	public void llenarCamposPropios(NotaCredito nota) {
		id = nota.getIdNotaCredito();
		llenarCamposAjenos(nota.getAliado());
		txtCosto.setValue(nota.getCosto());
		dtbFecha.setValue(nota.getFechaNota());
		if (!nota.getEstado().contains("En Edicion")) {
			botonera.getChildren().get(0).setVisible(false);
			botonera.getChildren().get(3).setVisible(false);
			botonera.getChildren().get(8).setVisible(false);
			editar = false;
		}
		listaDetalle = servicioDetalleCredito.buscarPorNota(nota);
		ltbLista.setModel(new ListModelList<DetalleNotaCredito>(listaDetalle));
		ltbLista.renderAll();
		for (int i = 0; i < listaDetalle.size(); i++) {
			if (listaDetalle.get(i).getTipoActividad().toUpperCase()
					.contains("POTE"))
				modificarPote(listaDetalle.get(i).getCosto(),
						listaDetalle.get(i).getMarca());
			else
				modificarDistribucion(listaDetalle.get(i).getCosto(),
						listaDetalle.get(i).getMarca(), listaDetalle.get(i)
								.getTipoActividad());
		}
	}

	private void limpiarCamposItem() {
		spnCantidad.setValue(0);
		spnCostoLinea.setValue((double) 0);
		txtDescripcion.setValue("");
		txtRef.setValue("");
		cmbActividad.setValue("");
		cmbMarca.setValue("");
	}

	private boolean validarItems() {
		if (cmbMarca.getText().compareTo("") == 0
				|| cmbActividad.getText().compareTo("") == 0
				|| dtbFecha.getText().compareTo("") == 0
				|| txtDescripcion.getText().compareTo("") == 0) {
			aplicarColores(cmbMarca, cmbActividad, txtDescripcion, txtRef);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnAgregar")
	public void annadirLista() {
		if (validarItems()) {
			Marca marca = servicioMarca.buscar(cmbMarca.getSelectedItem()
					.getContext());
			DetalleNotaCredito objeto = new DetalleNotaCredito(null, marca,
					cmbActividad.getValue(), txtDescripcion.getValue(),
					spnCantidad.getValue(), spnCostoLinea.getValue(),
					"En Edicion", "", fechaHora, null, null, fechaHora,
					horaAuditoria, nombreUsuarioSesion(), null,
					txtRef.getValue());
			listaDetalle.add(objeto);
			ltbLista.setModel(new ListModelList<DetalleNotaCredito>(
					listaDetalle));
			ltbLista.renderAll();
			calcularCostoTotal(spnCostoLinea.getValue(), marca,
					cmbActividad.getValue());
			limpiarCamposItem();
		} else
			msj.mensajeError("Debe llenar todos los campos de la seccion de detalle");
	}

	private void calcularCostoTotal(Double double1, Marca marca, String string) {
		double costo = 0;
		for (int i = 0; i < listaDetalle.size(); i++) {
			costo = costo + listaDetalle.get(i).getCosto();
		}
		txtCosto.setValue(costo);
		if (string.toUpperCase().contains("POTE"))
			modificarPote(double1, marca);
		else
			modificarDistribucion(double1, marca, string);
	}

	@Listen("onClick = #btnVer")
	public void seleccionarItemLista() {
		if (ltbLista.getItemCount() != 0) {
			if (ltbLista.getSelectedItems().size() == 1) {
				Listitem listItem = ltbLista.getSelectedItem();
				DetalleNotaCredito modelo = listItem.getValue();
				spnCantidad.setValue(modelo.getBotellas());
				txtDescripcion.setValue(modelo.getDescripcion());
				txtRef.setValue(modelo.getIdReferencia());
				cmbActividad.setValue(modelo.getTipoActividad());
				spnCostoLinea.setValue(modelo.getCosto());
				cmbMarca.setValue(modelo.getMarca().getDescripcion());
				ltbLista.removeItemAt(listItem.getIndex());
				listaDetalle.remove(modelo);
				ltbLista.renderAll();
				calcularCostoTotal(-modelo.getCosto(), modelo.getMarca(),
						modelo.getTipoActividad());
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	@Listen("onClick = #btnRemover")
	public void removerItem() {
		if (ltbLista.getItemCount() != 0) {
			if (ltbLista.getSelectedItems().size() == 1) {
				Listitem listItem = ltbLista.getSelectedItem();
				DetalleNotaCredito modelo = listItem.getValue();
				int i = listItem.getIndex();
				ltbLista.removeItemAt(i);
				listaDetalle.remove(modelo);
				ltbLista.renderAll();
				calcularCostoTotal(-modelo.getCosto(), modelo.getMarca(),
						modelo.getTipoActividad());
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	public void modificarPote(Double precio, Marca marca) {
		Double cantidad = (double) 0;
		Double total = (double) 0;
		Listbox list = (Listbox) catalogoPote.getChildren().get(0);
		list.renderAll();
		for (int i = 0; i < list.getItemCount(); i++) {
			Listitem listItem = list.getItemAtIndex(i);
			ConfiguracionMarca marca2 = listItem.getValue();
			Listcell celda = (Listcell) listItem.getChildren().get(1);
			total = total + Double.valueOf(celda.getLabel());
			celda = (Listcell) listItem.getChildren().get(2);
			Double porcentaje = Double.valueOf(celda.getLabel());
			celda = (Listcell) listItem.getChildren().get(4);
			Double distribuido = Double.valueOf(celda.getLabel());
			distribuido = distribuido + round(precio * porcentaje / 100, 2);
			celda.setLabel(String.valueOf(distribuido));
			if (marca2.getId().getMarca().getIdMarca()
					.equals(marca.getIdMarca())) {
				celda = (Listcell) listItem.getChildren().get(3);
				Double suma = Double.valueOf(celda.getLabel());
				suma = suma + precio;
				celda.setLabel(String.valueOf(suma));
			}
		}
		for (int i = 0; i < list.getItemCount(); i++) {
			Listitem listItem = list.getItemAtIndex(i);
			Listcell celda = (Listcell) listItem.getChildren().get(4);
			Double suma = Double.valueOf(celda.getLabel());
			cantidad = cantidad + suma;
		}
		txtDistribuirPote.setValue(cantidad);
		txtPlanPote.setValue(total);
	}

	public void modificarDistribucion(Double precio, Marca marca,
			String tipoActividad) {
		Double cantidad = (double) 0;
		Integer total = 0;
		Listbox list = (Listbox) catalogoDistribucion.getChildren().get(0);
		list.renderAll();
		List<Component> lista = list.getChildren().get(1).getChildren();
		int index = 0;
		for (int i = 0; i < lista.size(); i++) {
			Component component = lista.get(i);
			if (component instanceof Listheader) {
				Listheader cabeza1 = (Listheader) component;
				if (cabeza1.getLabel().equals(tipoActividad)) {
					index = i;
					i = lista.size();
				}
			}
		}

		for (int i = 0; i < list.getItemCount(); i++) {
			Listitem listItem = list.getItemAtIndex(i);
			ConfiguracionMarca marca2 = listItem.getValue();
			Listcell celda = (Listcell) listItem.getChildren().get(1);
			total = total + Integer.valueOf(celda.getLabel());
			if (marca2.getId().getMarca().getIdMarca()
					.equals(marca.getIdMarca())) {
				celda = (Listcell) listItem.getChildren().get(3);
				Double suma = Double.valueOf(celda.getLabel());
				suma = suma + precio;
				celda.setLabel(String.valueOf(suma));
				if (index != 0) {
					celda = (Listcell) listItem.getChildren().get(index);
					suma = Double.valueOf(celda.getLabel());
					suma = suma + precio;
					celda.setLabel(String.valueOf(suma));
				}
			}
		}
		for (int i = 0; i < list.getItemCount(); i++) {
			Listitem listItem = list.getItemAtIndex(i);
			Listcell celda = (Listcell) listItem.getChildren().get(3);
			Double suma = Double.valueOf(celda.getLabel());
			cantidad = cantidad + suma;
		}
		txtDistribuir.setValue(cantidad);
		txtPlan.setValue(total);

	}
}
