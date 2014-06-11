package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Recurso;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;

public class CRecurso extends CGenerico {

	private static final long serialVersionUID = 2959392250770869756L;

	@Wire
	private Window wdwRecurso;
	@Wire
	private Textbox txtDescripcionRecurso;
	@Wire
	private Div botoneraRecurso;
	@Wire
	private Div DivCatalogoRecurso;
	Catalogo<Recurso> catalogo;
	long id = 0;
	
	@Override
	public void inicializar() throws IOException {
		Botonera botonera = new Botonera() {
			
			@Override
			public void salir() {
				cerrarVentana(wdwRecurso);
			}
			
			@Override
			public void buscar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void limpiar() {
				txtDescripcionRecurso.setValue("");
				id = 0;
			}
			
			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionRecurso.getValue();
					Recurso recurso = new Recurso(id, descripcion, fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioRecurso.guardar(recurso);
					Messagebox.show("Registro Guardado Exitosamente",
							"Informacion", Messagebox.OK,
							Messagebox.INFORMATION);

					limpiar();
				}
			}
			
			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Recurso?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										servicioRecurso.eliminar(id);
										limpiar();
										Messagebox
												.show("Registro Eliminado Exitosamente",
														"Informacion",
														Messagebox.OK,
														Messagebox.INFORMATION);
									}
								}
							});
				} else {
					Messagebox.show("No ha Seleccionado Ningun Registro",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
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
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraRecurso.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtDescripcionRecurso.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnBuscarRecursos")
	public void buscarCatalogoPropio() {
		final List<Recurso> listRecurso = servicioRecurso.buscarTodosOrdenados();
		catalogo = new Catalogo<Recurso>(DivCatalogoRecurso, "Recurso",
				listRecurso, true, "Descripcion") {

			@Override
			protected List<Recurso> buscar(List<String> valores) {

				List<Recurso> lista = new ArrayList<Recurso>();

				for (Recurso recurso : listRecurso) {
					if (recurso.getDescripcion().toLowerCase()
							.startsWith(valores.get(0))) {
						lista.add(recurso);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Recurso recurso) {
				String[] registros = new String[1];
				registros[0] = recurso.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoRecurso);
		catalogo.doModal();
	}
	
	@Listen("onSeleccion = #DivCatalogoRecurso")
	public void seleccionPropia() {
		Recurso recurso = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCamposPropios(recurso);
		catalogo.setParent(null);
	}
	
	@Listen("onChange = #txtDescripcionRecurso")
	public void buscarPorNombrePropio() {
		Recurso recurso = servicioRecurso
				.buscarPorDescripcion(txtDescripcionRecurso.getValue());
		if (recurso != null)
			llenarCamposPropios(recurso);
	}
	
	public void llenarCamposPropios(Recurso recurso) {
		txtDescripcionRecurso.setValue(recurso.getDescripcion());
		id = recurso.getIdRecurso();
	}
}
