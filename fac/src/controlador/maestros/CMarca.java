package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Empresa;
import modelo.maestros.Marca;
import modelo.maestros.Sku;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;
import modelo.transacciones.RecursoPlanillaEvento;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CMarca extends CGenerico {

	private static final long serialVersionUID = -6469442861701709721L;
	@Wire
	private Window wdwMarca;
	@Wire
	private Textbox txtCodigoMarca;
	@Wire
	private Textbox txtDescripcionMarca;
	@Wire
	private Radio rdoNo;
	@Wire
	private Radio rdoSi;
	@Wire
	private Div botoneraMarca;
	@Wire
	private Div DivCatalogoMarca;
	@Wire
	private Div divCatalogoEmpresa;
	@Wire
	private Textbox txtNombreEmpresa;
	Catalogo<Empresa> catalogoEmpresa;
	Catalogo<Marca> catalogo;
	String id = "";
	Long idEmpresa = (long) 0;

	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwMarca);
			}

			@Override
			public void buscar() {
				buscarCatalogoPropio();

			}

			@Override
			public void limpiar() {
				txtCodigoMarca.setValue("");
				txtDescripcionMarca.setValue("");
				rdoSi.setChecked(false);
				rdoNo.setChecked(false);
				txtNombreEmpresa.setValue("");
				id = "";
				idEmpresa = (long) 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionMarca.getValue();
					String codigo = txtCodigoMarca.getValue();
					Empresa empresa = servicioEmpresa.buscar(idEmpresa);
					Boolean estado = false;
					if (rdoSi.isChecked())
						estado = true;
					Marca marca = new Marca(codigo, descripcion, fechaHora,
							horaAuditoria, nombreUsuarioSesion(), empresa,
							estado);
					servicioMarca.guardar(marca);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					if (!estado) {
						notificarInactivacion(
								"Las siguientes MARCAS han sido inactivadas: "
										+ descripcion, valor);
					}
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar la Marca?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Marca marca = servicioMarca.buscar(id);
										List<PlanillaArte> planillaArte = servicioPlanillaArte
												.buscarPorMarca(marca);
										List<PlanillaCata> planillaCata = servicioPlanillaCata
												.buscarPorMarca(marca);
										List<PlanillaEvento> planillaEvento = servicioPlanillaEvento
												.buscarPorMarca(marca);
										List<PlanillaFachada> planillaFachada = servicioPlanillaFachada
												.buscarPorMarca(marca);
										List<PlanillaPromocion> planillaPromocion = servicioPlanillaPromocion
												.buscarPorMarca(marca);
										List<PlanillaUniforme> planillaUniforme = servicioPlanillaUniforme
												.buscarPorMarca(marca);
										List<RecursoPlanillaEvento> recursoPlanillaEvento = servicioRecursoPlanillaEvento
												.buscarPorMarca(marca);
										List<Sku> list4 = servicioSku
												.buscarPorMarca(marca);

										if (!planillaArte.isEmpty()
												|| !planillaCata.isEmpty()
												|| !planillaEvento.isEmpty()
												|| !planillaFachada.isEmpty()
												|| !planillaPromocion.isEmpty()
												|| !planillaUniforme.isEmpty()
												|| !recursoPlanillaEvento
														.isEmpty()
												|| !list4.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioMarca.eliminar(id);
											limpiar();
											msj.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}

			@Override
			public void atras() {
				// TODO Auto-generated method stub

			}

			@Override
			public void adelante() {
				// TODO Auto-generated method stub

			}

			@Override
			public void enviar() {
				// TODO Auto-generated method stub

			}
		};
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraMarca.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoMarca.getText().compareTo("") == 0
				|| txtDescripcionMarca.getText().compareTo("") == 0
				|| txtNombreEmpresa.getText().compareTo("") == 0
				|| (!rdoSi.isChecked() && !rdoNo.isChecked())) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public void buscarCatalogoPropio() {
		final List<Marca> listMarca = servicioMarca.buscarTodosOrdenados();
		catalogo = new Catalogo<Marca>(DivCatalogoMarca, "Catalogo de Marcas",
				listMarca, true, "Id", "Descripcion", "Empresa", "Estado") {

			@Override
			protected List<Marca> buscar(List<String> valores) {

				List<Marca> lista = new ArrayList<Marca>();
				for (Marca marca : listMarca) {
					String estado = "Activo";
					if (marca.getEstado() != null)
						if (!marca.getEstado())
							estado = "Inactivo";
					String empresa = "";
					if (marca.getEmpresa() != null)
						empresa = marca.getEmpresa().getNombre();
					if (marca.getIdMarca().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& marca.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& empresa.toLowerCase().contains(
									valores.get(2).toLowerCase())
							&& estado.toLowerCase().contains(
									valores.get(3).toLowerCase())) {
						lista.add(marca);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Marca marca) {
				String estado = "Activo";
				if (marca.getEstado() != null)
					if (!marca.getEstado())
						estado = "Inactivo";
				String empresa = "";
				if (marca.getEmpresa() != null)
					empresa = marca.getEmpresa().getNombre();
				String[] registros = new String[4];
				registros[0] = marca.getIdMarca();
				registros[1] = marca.getDescripcion();
				registros[2] = empresa;
				registros[3] = estado;
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoMarca);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #DivCatalogoMarca")
	public void seleccionPropia() {
		Marca marca = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(marca);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtCodigoMarca")
	public void buscarPorNombrePropio() {
		Marca marca = servicioMarca.buscar(txtCodigoMarca.getValue());
		if (marca != null)
			llenarCamposPropios(marca);
	}

	public void llenarCamposPropios(Marca marca) {
		txtCodigoMarca.setValue(marca.getIdMarca());
		txtDescripcionMarca.setValue(marca.getDescripcion());
		if (marca.getEstado() != null)
			if (marca.getEstado())
				rdoSi.setChecked(true);
			else
				rdoNo.setChecked(true);
		else {
			rdoSi.setChecked(false);
			rdoNo.setChecked(false);
		}
		if (marca.getEmpresa() != null)
			llenarCamposAjenos(marca.getEmpresa());
		id = marca.getIdMarca();
	}

	@Listen("onClick = #btnBuscarEmpresas")
	public void buscarCatalogoAjeno() {
		final List<Empresa> listMarca = servicioEmpresa.buscarTodosOrdenados();
		catalogoEmpresa = new Catalogo<Empresa>(divCatalogoEmpresa,
				"Catalogo de Empresas", listMarca, true, "Nombre",
				"Descripcion") {

			@Override
			protected List<Empresa> buscar(List<String> valores) {

				List<Empresa> lista = new ArrayList<Empresa>();

				for (Empresa marca : listMarca) {
					if (marca.getNombre().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& marca.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(marca);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Empresa marca) {
				String[] registros = new String[2];
				registros[0] = marca.getNombre();
				registros[1] = marca.getDescripcion();
				return registros;
			}
		};
		catalogoEmpresa.setParent(divCatalogoEmpresa);
		catalogoEmpresa.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpresa")
	public void seleccionAjena() {
		Empresa marca = catalogoEmpresa.objetoSeleccionadoDelCatalogo();
		llenarCamposAjenos(marca);
		catalogoEmpresa.setParent(null);
	}

	@Listen("onChange = #txtNombreEmpresa")
	public void buscarPorNombreAjeno() {
		Empresa zona = servicioEmpresa.buscarPorNombre(txtNombreEmpresa
				.getValue());
		if (zona != null)
			llenarCamposAjenos(zona);
		else {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			txtNombreEmpresa.setValue("");
			txtNombreEmpresa.setFocus(true);
			idEmpresa = (long) 0;
		}
	}

	public void llenarCamposAjenos(Empresa marca) {
		txtNombreEmpresa.setValue(marca.getNombre());
		idEmpresa = marca.getIdEmpresa();
	}

}
