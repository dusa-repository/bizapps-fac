package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Marca;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;

public class CMarca extends CGenerico {

	private static final long serialVersionUID = -6469442861701709721L;
	@Wire
	private Window wdwMarca;
	@Wire
	private Textbox txtCodigoMarca;
	@Wire
	private Textbox txtDescripcionMarca;
	@Wire
	private Div botoneraMarca;
	@Wire
	private Div DivCatalogoMarca;
	Catalogo<Marca> catalogo;
	String id = "";
	
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
				id = "";
			}
			
			@Override
			public void guardar() {
				if (validar()) {
					String descripcion = txtDescripcionMarca.getValue();
					String codigo = txtCodigoMarca.getValue();
					Marca marca = new Marca(codigo, descripcion, fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioMarca.guardar(marca);
					Messagebox.show("Registro Guardado Exitosamente",
							"Informacion", Messagebox.OK,
							Messagebox.INFORMATION);

					limpiar();
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
										servicioMarca.eliminar(id);
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
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraMarca.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCodigoMarca.getText().compareTo("") == 0
				|| txtDescripcionMarca.getText().compareTo("") == 0) {
			Messagebox.show("Debe Llenar Todos los Campos", "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
			return false;
		} else
			return true;
	}
	

	public void buscarCatalogoPropio() {
		final List<Marca> listMarca = servicioMarca.buscarTodosOrdenados();
		catalogo = new Catalogo<Marca>(DivCatalogoMarca, "Catalogo de Marcas",
				listMarca, true, "Id", "Descripcion") {

			@Override
			protected List<Marca> buscar(List<String> valores) {

				List<Marca> lista = new ArrayList<Marca>();

				for (Marca marca : listMarca) {
					if (marca.getIdMarca().toLowerCase()
							.startsWith(valores.get(0))
							&& marca.getDescripcion().toLowerCase()
									.startsWith(valores.get(1))) {
						lista.add(marca);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Marca marca) {
				String[] registros = new String[2];
				registros[0] = marca.getIdMarca();
				registros[1] = marca.getDescripcion();
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
		Marca marca = servicioMarca
				.buscar(txtCodigoMarca.getValue());
		if (marca != null)
			llenarCamposPropios(marca);
	}
	
	public void llenarCamposPropios(Marca marca) {
		txtCodigoMarca.setValue(marca.getIdMarca());
		txtDescripcionMarca.setValue(marca.getDescripcion());
		id = marca.getIdMarca();
	}

}
