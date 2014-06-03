package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.F0004;
import modelo.maestros.F0005;
import modelo.pk.F0005PK;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CF0005 extends CGenerico {


	private static final long serialVersionUID = 4858496432716297913L;
	@Wire
	private Textbox txtSYF0005;
	@Wire
	private Textbox txtRTF0005;
	@Wire
	private Textbox txtKYF0005;
	@Wire
	private Textbox txtSPHDF0005;
	@Wire
	private Textbox txtHRDCF0005;
	@Wire
	private Textbox txtDL01F0005;
	@Wire
	private Textbox txtDL02F0005;
	@Wire
	private Window wdwF0005;
	@Wire
	private Div botoneraF0005;
	@Wire
	private Div DivCatalogoF0005;
	@Wire
	private Div divCatalogoF0004;
	@Wire
	private Label lblDescripcionF0004;
	@Wire
	private Button btnBuscarF0004;
	Catalogo<F0005> catalogo;
	Catalogo<F0004> catalogoF0004;
	F0005PK clave = null;

	@Override
	public void inicializar() throws IOException {

		txtSYF0005.setFocus(true);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwF0005);

			}

			@Override
			public void reporte() {
			}

			@Override
			public void limpiar() {
				clave = null;
				limpiarCampos();
				habilitarTextClave();
			
			}

			@Override
			public void guardar() {
				boolean guardar = true;
				if (clave == null)
					guardar = validar();
				if (guardar) {
					String rt = txtRTF0005.getValue();
					String sy = txtSYF0005.getValue();
					String ky = txtKYF0005.getValue();
					String dl01 = txtDL01F0005.getValue();
					String sphd = txtSPHDF0005.getValue();
					String dl02 = txtDL02F0005.getValue();
					String hrdc = txtHRDCF0005.getValue();
					F0005PK clave = new F0005PK();
					clave.setDrrt(rt);
					clave.setDrsy(sy);
					clave.setDrky(ky);
					F0005 fooo5 = new F0005();
					fooo5.setId(clave);
					fooo5.setDrdl01(dl01);
					fooo5.setDrdl02(dl02);
					fooo5.setDrhrdc(hrdc);
					fooo5.setDrsphd(sphd);
					fooo5.setUsuarioAuditoria(nombreUsuarioSesion());
					fooo5.setFechaAuditoria(fechaHora);
					fooo5.setHoraAuditoria(horaAuditoria);
					servicioF0005.guardar(fooo5);
//					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					catalogo.actualizarLista(servicioF0005
							.buscarTodosOrdenados());
				}

			}

			@Override
			public void eliminar() {
					/* Elimina un solo registro */
					if (clave != null) {
						Messagebox
								.show(Mensaje.deseaEliminar,
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioF0005
															.eliminarUno(clave);
//													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(servicioF0005
															.buscarTodosOrdenados());
												}
											}
										});
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

		};
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraF0005.appendChild(botonera);

	}


	public void limpiarCampos() {
		clave = null;
		txtDL01F0005.setValue("");
		txtSPHDF0005.setValue("");
		txtHRDCF0005.setValue("");
		txtRTF0005.setValue("");
		txtSYF0005.setValue("");
		txtDL02F0005.setValue("");
		txtKYF0005.setValue("");
		txtSYF0005.setFocus(true);
		lblDescripcionF0004.setValue("");
		btnBuscarF0004.setVisible(true);
	}

	public void habilitarTextClave() {
		if (txtRTF0005.isDisabled())
			txtRTF0005.setDisabled(false);
		if (txtSYF0005.isDisabled())
			txtSYF0005.setDisabled(false);
		if (txtKYF0005.isDisabled())
			txtKYF0005.setDisabled(false);
	}

	protected boolean validar() {
		if (claveSYExiste() || claveRTExiste()) {
			return false;
		} else {
			if (!camposLLenos()) {
//				msj.mensajeError(Mensaje.camposVacios);
				return false;
			} else
				return true;
		}
	}

	@Listen("onChange = #txtSYF0005")
	public boolean claveSYExiste() {
		if (servicioF0004.buscarSY(txtSYF0005.getValue()).isEmpty()) {
//			msj.mensajeAlerta(Mensaje.claveSYNoEsta);
			txtSYF0005.setFocus(true);
			return true;
		} else {
			if (txtRTF0005.getText().compareTo("") != 0) {
				if (servicioF0004.buscar(txtSYF0005.getValue(),
						txtRTF0005.getValue()) == null) {
//					msj.mensajeAlerta(Mensaje.claveRTNoEsta);
					lblDescripcionF0004.setValue("");
					txtRTF0005.setFocus(true);
					return true;
				}
				else
					lblDescripcionF0004.setValue(servicioF0004.buscar(txtSYF0005.getValue(),txtRTF0005.getValue()).getDtdl01());
			}
		}
		return false;
	}

	@Listen("onChange = #txtRTF0005")
	public boolean claveRTExiste() {
		if (txtSYF0005.getText().compareTo("") != 0) {
			if (servicioF0004.buscar(txtSYF0005.getValue(),
					txtRTF0005.getValue()) == null) {
//				msj.mensajeAlerta(Mensaje.claveRTNoEsta);
				txtRTF0005.setFocus(true);
				lblDescripcionF0004.setValue("");
				return true;
			}
			else 
				lblDescripcionF0004.setValue(servicioF0004.buscar(txtSYF0005.getValue(),txtRTF0005.getValue()).getDtdl01());
		}
		return false;
	}

	@Listen("onChange = #txtKYF0005")
	public boolean claveKYExiste() {
		if (txtSYF0005.getText().compareTo("") == 0) {
			if (txtSYF0005.getText().compareTo("") == 0) {
				if (servicioF0005.buscar(txtSYF0005.getValue(),
						txtRTF0005.getValue(), txtKYF0005.getValue()) != null) {
//					msj.mensajeAlerta(Mensaje.claveUsada);
					txtKYF0005.setFocus(true);
					return true;
				}
			}
		}
		return false;
	}

	public boolean camposLLenos() {
		if (txtDL01F0005.getText().compareTo("") == 0
				|| txtRTF0005.getText().compareTo("") == 0
				|| txtKYF0005.getText().compareTo("") == 0
				|| txtSYF0005.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	public boolean camposEditando() {
		if (txtDL01F0005.getText().compareTo("") != 0
				|| txtSPHDF0005.getText().compareTo("") != 0
				|| txtHRDCF0005.getText().compareTo("") != 0
				|| txtRTF0005.getText().compareTo("") != 0
				|| txtSYF0005.getText().compareTo("") != 0
				|| txtKYF0005.getText().compareTo("") != 0
				|| txtDL02F0005.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	public void mostrarCatalogo() {
		final List<F0005> listF0005 = servicioF0005.buscarTodosOrdenados();
		catalogo = new Catalogo<F0005>(DivCatalogoF0005, "F0005", listF0005,true, "SY",
				"RT", "KY", "Descripcion 01", "Descripcion 02",
				"Gestion Especial", "Codificacion Fija") {

			@Override
			protected List<F0005> buscar(List<String> valores) {

				List<F0005> listF0005_2 = new ArrayList<F0005>();

				for (F0005 f0005 : listF0005) {
					if (f0005.getId().getDrsy().toLowerCase()
							.startsWith(valores.get(0))
							&& f0005.getId().getDrrt().toLowerCase()
									.startsWith(valores.get(1))
							&& f0005.getId().getDrky().toLowerCase()
									.startsWith(valores.get(2))
							&& f0005.getDrdl01().toLowerCase()
									.startsWith(valores.get(3))
							&& f0005.getDrdl02().toLowerCase()
									.startsWith(valores.get(4))
							&& f0005.getDrsphd().toLowerCase()
									.startsWith(valores.get(5))
							&& f0005.getDrhrdc().toLowerCase()
									.startsWith(valores.get(6))) {
						listF0005_2.add(f0005);
					}
				}
				return listF0005_2;
			}

			@Override
			protected String[] crearRegistros(F0005 f0005) {
				String[] registros = new String[7];
				registros[0] = f0005.getId().getDrsy();
				registros[1] = f0005.getId().getDrrt();
				registros[2] = f0005.getId().getDrky();
				registros[3] = f0005.getDrdl01();
				registros[4] = f0005.getDrdl02();
				registros[5] = f0005.getDrsphd();
				registros[6] = f0005.getDrhrdc();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoF0005);
	}
	

	@Listen("onClick = #btnBuscarF0004")
	public void mostrarCatalogoF0004() {
		final List<F0004> listF0004 = servicioF0004.buscarTodosOrdenados();
		catalogoF0004 = new Catalogo<F0004>(divCatalogoF0004, "F0004", listF0004, true, "SY",
				"RT", "Descripcion", "Codigo", "2 Linea", "Numerico") {

			@Override
			protected List<F0004> buscar(List<String> valores) {

				List<F0004> lista = new ArrayList<F0004>();

				for (F0004 f0004 : listF0004) {
					if (f0004.getId().getDtsy().toLowerCase()
							.startsWith(valores.get(0))
							&& f0004.getId().getDtrt().toLowerCase()
									.startsWith(valores.get(1))
							&& f0004.getDtdl01().toLowerCase()
									.startsWith(valores.get(2))
							&& String.valueOf(f0004.getDtcdl()).toLowerCase()
									.startsWith(valores.get(3))
							&& f0004.getDtln2().toLowerCase()
									.startsWith(valores.get(4))
							&& f0004.getDtcnum().toLowerCase()
									.startsWith(valores.get(5))) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(F0004 f0004) {
				String[] registros = new String[6];
				registros[0] = f0004.getId().getDtsy();
				registros[1] = f0004.getId().getDtrt();
				registros[2] = f0004.getDtdl01();
				registros[3] = String.valueOf(f0004.getDtcdl());
				registros[4] = f0004.getDtln2();
				registros[5] = f0004.getDtcnum();
				return registros;
			}
		};
		catalogoF0004.setClosable(true);
		catalogoF0004.setWidth("80%");
		catalogoF0004.setTitle("Registros");
		catalogoF0004.setParent(divCatalogoF0004);
		catalogoF0004.doModal();
	}

	@Listen("onSeleccion = #divCatalogoF0004")
	public void seleccion() {
		F0004 f0004 = catalogoF0004.objetoSeleccionadoDelCatalogo();
		txtSYF0005.setValue(f0004.getId().getDtsy());
		txtRTF0005.setValue(f0004.getId().getDtrt());
		lblDescripcionF0004.setValue(servicioF0004.buscar(f0004.getId().getDtsy(),f0004.getId().getDtrt()).getDtdl01());
		catalogoF0004.setParent(null);
	}
}