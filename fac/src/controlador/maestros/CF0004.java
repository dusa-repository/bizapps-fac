package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.F0004;
import modelo.pk.F0004PK;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CF0004 extends CGenerico {

	private static final long serialVersionUID = -8680709288839148461L;
	@Wire
	private Textbox txtSYF0004;
	@Wire
	private Textbox txtRTF0004;
	@Wire
	private Textbox txtLNF0004;
	@Wire
	private Textbox txtNUMF0004;
	@Wire
	private Textbox txtDL01F0004;
	@Wire
	private Doublespinner dblCDLF0004;
	@Wire
	private Window wdwF0004;
	@Wire
	private Div botoneraF0004;
	@Wire
	private Div divCatalogoF0004;
	Catalogo<F0004> catalogo;
	F0004PK clave = null;

	@Override
	public void inicializar() throws IOException {

		txtSYF0004.setFocus(true);

		Botonera botonera = new Botonera() {
			
			@Override
			public void salir() {
				cerrarVentana(wdwF0004);

			}

			@Override
			public void buscar() {
				mostrarCatalogo();
			}

			@Override
			public void limpiar() {
				limpiarCampos();
				habilitarTextClave();
				clave = null;
			}

			@Override
			public void guardar() {
				boolean guardar = true;
				if (clave == null)
					guardar = validar();
				if (guardar) {
					double a =0;
					String rt = txtSYF0004.getValue();
					String sy = txtRTF0004.getValue();
					String dl = txtDL01F0004.getValue();
					String ln = txtLNF0004.getValue();
					if(dblCDLF0004.getText().compareTo("")!= 0)
					a = dblCDLF0004.getValue();
					String num = txtNUMF0004.getValue();
					F0004PK clave = new F0004PK();
					clave.setDtsy(rt);
					clave.setDtrt(sy);
					F0004 fooo4 = new F0004();
					fooo4.setId(clave);
					fooo4.setDtdl01(dl);
					fooo4.setDtln2(ln);
					fooo4.setDtcnum(num);			
					fooo4.setDtcdl(a);
					fooo4.setDtjobn("5");
					fooo4.setUsuarioAuditoria(nombreUsuarioSesion());
					fooo4.setFechaAuditoria(fechaHora);
					fooo4.setHoraAuditoria(horaAuditoria);
					servicioF0004.guardar(fooo4);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void eliminar() {

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
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraF0004.appendChild(botonera);

	}

	public void limpiarCampos() {
		clave = null;
		txtDL01F0004.setValue("");
		txtLNF0004.setValue("");
		txtNUMF0004.setValue("");
		txtRTF0004.setValue("");
		txtSYF0004.setValue("");
		dblCDLF0004.setValue((double) 0);
		txtSYF0004.setFocus(true);
	}

	public void habilitarTextClave() {
		if (txtRTF0004.isDisabled())
			txtRTF0004.setDisabled(false);
		if (txtSYF0004.isDisabled())
			txtSYF0004.setDisabled(false);
	}

	protected boolean validar() {
		if (claveSYExiste() || claveRTExiste()) {
			return false;
		} else {
			if (!camposLLenos()) {
				msj.mensajeError(Mensaje.camposVacios);
				return false;
			} else
				return true;
		}
	}

	@Listen("onChange = #txtSYF0004")
	public boolean claveSYExiste() {
		if (servicioF0004.buscar(txtSYF0004.getValue(), txtRTF0004.getValue()) != null) {
			msj.mensajeAlerta(Mensaje.claveUsada);
			txtSYF0004.setFocus(true);
			return true;
		} else
			return false;
	}

	@Listen("onChange = #txtRTF0004")
	public boolean claveRTExiste() {
		if (servicioF0004.buscar(txtSYF0004.getValue(), txtRTF0004.getValue()) != null) {
			msj.mensajeAlerta(Mensaje.claveUsada);
			txtRTF0004.setFocus(true);
			return true;
		} else
			return false;
	}

	public boolean camposLLenos() {
		if (txtDL01F0004.getText().compareTo("") == 0
				|| txtRTF0004.getText().compareTo("") == 0
				|| txtSYF0004.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}


	public void mostrarCatalogo() {
		final List<F0004> listF0004 = servicioF0004.buscarTodosOrdenados();
		catalogo = new Catalogo<F0004>(divCatalogoF0004, "Catalogo de F0004", listF0004,true, "SY",
				"RT", "Descripcion", "Codigo", "2 Linea", "Numerico") {

			@Override
			protected List<F0004> buscar(List<String> valores) {

				List<F0004> lista = new ArrayList<F0004>();

				for (F0004 f0004 : listF0004) {
					if (f0004.getId().getDtsy().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& f0004.getId().getDtrt().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& f0004.getDtdl01().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& String.valueOf(f0004.getDtcdl()).toLowerCase()
									.contains(valores.get(3))
							&& f0004.getDtln2().toLowerCase()
									.contains(valores.get(4))
							&& f0004.getDtcnum().toLowerCase()
									.contains(valores.get(5))) {
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
		catalogo.setParent(divCatalogoF0004);
		catalogo.doModal();
	}
	
	@Listen("onSeleccion = #divCatalogoF0004")
	public void seleccion() {
		F0004 f04 = catalogo.objetoSeleccionadoDelCatalogo();
		clave = f04.getId();
		txtRTF0004.setValue(f04.getId().getDtrt());
		txtRTF0004.setDisabled(true);
		txtSYF0004.setValue(f04.getId().getDtsy());
		txtSYF0004.setDisabled(true);
		txtDL01F0004.setValue(f04.getDtdl01());
		txtLNF0004.setValue(f04.getDtln2());
		txtNUMF0004.setValue(f04.getDtcnum());
		dblCDLF0004.setValue(f04.getDtcdl());
		txtDL01F0004.setFocus(true);
		catalogo.setParent(null);
	}
}