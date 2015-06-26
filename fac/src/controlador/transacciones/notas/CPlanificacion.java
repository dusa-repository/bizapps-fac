package controlador.transacciones.notas;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Marca;
import modelo.maestros.Zona;
import modelo.transacciones.notas.Planificacion;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
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

public class CPlanificacion extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window wdwPlanificacion;
	@Wire
	private Div botoneraPlan;
	@Wire
	private Div catalogoPlan;
	@Wire
	private Tab tabDatos;
	@Wire
	private Tab tabPdv;
	@Wire
	private Tab tabActividad;
	@Wire
	private Textbox txtNombrePdv;
	@Wire
	private Textbox txtActividad;
	@Wire
	private Textbox txtTiposActividad;
	@Wire
	private Doublebox txtCosto;
	@Wire
	private Doublebox txtCostoInversion;
	@Wire
	private Doublebox txtCostoInversionReal;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbZona;
	@Wire
	private Combobox cmbMes;
	@Wire
	private Combobox cmbInversion;
	@Wire
	private Combobox cmbPDV;
	@Wire
	private Spinner spnPDV;
	@Wire
	private Spinner spnActividades;
	@Wire
	private Spinner spnPagar;
	Catalogo<Planificacion> catalogo;
	ListModelList<Marca> marcas;
	ListModelList<Zona> zonas;
	Botonera botonera;
	Long id = (long) 0;

	@Override
	public void inicializar() throws IOException {
		dtbHasta.setValue(fecha);
		dtbDesde.setValue(fecha);
		cmbMarca.setFocus(true);
		botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwPlanificacion);
			}

			@Override
			public void limpiar() {
				txtActividad.setValue("");
				txtCosto.setValue(0);
				txtCostoInversion.setValue(0);
				txtCostoInversionReal.setValue(0);
				txtNombrePdv.setValue("");
				txtTiposActividad.setValue("");
				cmbInversion.setValue("");
				cmbMarca.setValue("");
				cmbMes.setValue("");
				cmbPDV.setValue("");
				cmbZona.setValue("");
				spnActividades.setValue(0);
				spnPagar.setValue(0);
				spnPDV.setValue(0);
				dtbDesde.setValue(fecha);
				dtbHasta.setValue(fecha);
				tabDatos.setSelected(true);
				id = (long) 0;
				limpiarColores(txtActividad, txtCosto, txtCostoInversion,
						txtCostoInversionReal, txtNombrePdv, txtTiposActividad,
						cmbInversion, cmbMarca, cmbMes, cmbPDV, cmbZona,
						spnActividades, spnPagar, spnPDV);
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
					guardarDatos("Enviado");
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
										servicioPlanificacion.eliminar(id);
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
			public void buscar() {
				buscarCatalogoPropio();
			}

			@Override
			public void atras() {
				if (tabPdv.isSelected())
					tabDatos.setSelected(true);
				if (tabActividad.isSelected())
					tabPdv.setSelected(true);
			}

			@Override
			public void adelante() {
				if (tabPdv.isSelected())
					tabActividad.setSelected(true);
				if (tabDatos.isSelected())
					tabPdv.setSelected(true);
			}
		};
		botoneraPlan.appendChild(botonera);
	}

	protected void guardarDatos(String string) {
		String tipoInversion = cmbInversion.getValue();
		String mes = cmbMes.getValue();
		String pdvTipo = cmbPDV.getValue();
		String actividadDescripcion = txtActividad.getValue();
		String pdvNombre = txtNombrePdv.getValue();
		String actividades = txtTiposActividad.getValue();
		Double costoActividades = txtCosto.getValue();
		Double costoInversion = txtCosto.getValue() * spnPagar.getValue();
		Double costoReal = txtCostoInversionReal.getValue();
		Marca marca = servicioMarca.buscar(cmbMarca.getSelectedItem()
				.getContext());
		Zona zona = servicioZona.buscar(cmbZona.getSelectedItem().getContext());
		Date fechaI = dtbDesde.getValue();
		Date fechaF = dtbHasta.getValue();
		int cantidadActividades = spnActividades.getValue();
		int cantidadActividadesPagar = spnPagar.getValue();
		int pdvCantidad = spnPDV.getValue();
		Timestamp fechaInicio = new Timestamp(fechaI.getTime());
		Timestamp fechaFin = new Timestamp(fechaF.getTime());
		Planificacion planificacion = new Planificacion(id, marca, zona,
				fechaInicio, fechaFin, mes, tipoInversion, pdvNombre, pdvTipo,
				pdvCantidad, actividadDescripcion, actividades,
				cantidadActividades, cantidadActividadesPagar,
				costoActividades, costoInversion, string, valor, fechaHora,
				horaAuditoria, nombreUsuarioSesion());
		servicioPlanificacion.guardar(planificacion);

	}

	protected boolean validar() {
		if (txtActividad.getText().compareTo("") == 0
				|| txtCosto.getText().compareTo("") == 0
				|| txtCostoInversion.getText().compareTo("") == 0
				|| txtCostoInversionReal.getText().compareTo("") == 0
				|| txtNombrePdv.getText().compareTo("") == 0
				|| txtTiposActividad.getText().compareTo("") == 0
				|| cmbInversion.getText().compareTo("") == 0
				|| cmbMarca.getText().compareTo("") == 0
				|| cmbMes.getText().compareTo("") == 0
				|| cmbPDV.getText().compareTo("") == 0
				|| cmbZona.getText().compareTo("") == 0
				|| dtbDesde.getText().compareTo("") == 0
				|| dtbHasta.getText().compareTo("") == 0) {
			tabDatos.setSelected(true);
			aplicarColores(txtActividad, txtCosto, txtCostoInversion,
					txtCostoInversionReal, txtNombrePdv, txtTiposActividad,
					cmbInversion, cmbMarca, cmbMes, cmbPDV, cmbZona,
					spnActividades, spnPagar, spnPDV);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public ListModelList<Marca> getMarcas() {
		List<Marca> marcasLista = servicioMarca.buscarTodosOrdenados();
		marcas = new ListModelList<Marca>(marcasLista);
		return marcas;
	}

	public ListModelList<Zona> getZonas() {
		List<Zona> marcasLista = servicioZona.buscarTodosOrdenados();
		zonas = new ListModelList<Zona>(marcasLista);
		return zonas;
	}

	protected void buscarCatalogoPropio() {
		final List<Planificacion> listPlanilla = servicioPlanificacion
				.buscarTodosOrdenados();
		catalogo = new Catalogo<Planificacion>(catalogoPlan, "Planificaciones",
				listPlanilla, true, "Zona", "Marca", "Mes", "Desde", "Hasta",
				"Estado") {

			@Override
			protected List<Planificacion> buscar(List<String> valores) {

				List<Planificacion> lista = new ArrayList<Planificacion>();

				for (Planificacion planillaEvento : listPlanilla) {
					if (planillaEvento.getZona().getDescripcion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& planillaEvento.getMarca().getDescripcion()
									.toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& planillaEvento.getMes().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& String
									.valueOf(
											formatoFecha.format(planillaEvento
													.getFechaDesde()))
									.toLowerCase().contains(valores.get(3))
							&& String
									.valueOf(
											formatoFecha.format(planillaEvento
													.getFechaHasta()))
									.toLowerCase().contains(valores.get(4))
							&& planillaEvento.getEstado().toLowerCase()
									.contains(valores.get(5).toLowerCase())) {
						lista.add(planillaEvento);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Planificacion planillaEvento) {
				String[] registros = new String[6];
				registros[0] = planillaEvento.getZona().getDescripcion();
				registros[1] = planillaEvento.getMarca().getDescripcion();
				registros[2] = planillaEvento.getMes();
				registros[3] = String.valueOf(formatoFecha
						.format(planillaEvento.getFechaDesde()));
				registros[4] = String.valueOf(formatoFecha
						.format(planillaEvento.getFechaHasta()));
				registros[5] = planillaEvento.getEstado();
				return registros;
			}
		};
		catalogo.setParent(catalogoPlan);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoPlan")
	public void seleccionPropia() {
		Planificacion planilla = catalogo.objetoSeleccionadoDelCatalogo();
		settearCampos(planilla);
		catalogo.setParent(null);
	}

	private void settearCampos(Planificacion planilla) {
		Integer cantidad = planilla.getActividadesAPagar();
		Double costo = planilla.getActividadCosto();
		txtActividad.setValue(planilla.getActividaDetalles());
		txtCosto.setValue(planilla.getActividadCosto());
		txtCostoInversion.setValue(cantidad * costo);
		txtCostoInversionReal.setValue(planilla.getInversionReal());
		txtNombrePdv.setValue(planilla.getPdvCliente());
		txtTiposActividad.setValue(planilla.getActividades());
		cmbInversion.setValue(planilla.getTipoInversion());
		cmbMarca.setValue(planilla.getMarca().getDescripcion());
		cmbMes.setValue(planilla.getMes());
		cmbPDV.setValue(planilla.getPdvTipo());
		cmbZona.setValue(planilla.getZona().getDescripcion());
		spnActividades.setValue(planilla.getActividadCantidad());
		spnPagar.setValue(planilla.getActividadesAPagar());
		spnPDV.setValue(planilla.getCantidadPdv());
		dtbDesde.setValue(planilla.getFechaDesde());
		dtbHasta.setValue(planilla.getFechaHasta());
		tabDatos.setSelected(true);
		id = planilla.getIdPlanificacion();
	}

	@Listen("onChange = #txtCosto,#spnPagar; onOK = #txtCosto,#spnPagar ")
	public void seleccionCalcular() {
		if (spnPagar.getValue() != null && txtCosto.getValue() != null) {
			txtCostoInversion.setValue(spnPagar.getValue()
					* txtCosto.getValue());
		}
	}

}
