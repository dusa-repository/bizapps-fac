package controlador.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CGrupo extends CGenerico {

	private static final long serialVersionUID = 3771289676166008495L;

	@Wire
	private Listbox ltbFuncionalidades;
	@Wire
	private Textbox txtNombreGrupo;
	@Wire
	private Listbox ltbFuncionalidadesSeleccionados;
	@Wire
	private Div botoneraGrupo;
	@Wire
	private Div catalogoGrupo;
	@Wire
	private Window wdwGrupo;
	long id = 0;
	Catalogo<Grupo> catalogo;
	ListModelList<Arbol> modelArbol;

	public static List<String> funcionalidades = new ArrayList<String>();

	@Override
	public void inicializar() throws IOException {
		ltbFuncionalidades.setMultiple(false);
		ltbFuncionalidades.setCheckmark(false);
		ltbFuncionalidades.setMultiple(true);
		ltbFuncionalidades.setCheckmark(true);
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwGrupo);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {
					Set<Arbol> arboles = new HashSet<Arbol>();
					if (ltbFuncionalidades.getItemCount() != 0) {
						for (int i = 0; i < ltbFuncionalidades.getItemCount(); i++) {
							Listitem listItem = ltbFuncionalidades
									.getItemAtIndex(i);
							if (listItem.isSelected()) {
								Arbol arbol = listItem.getValue();
								arboles.add(arbol);
							}
						}
					}
					Boolean estatus = true;
					String nombre = txtNombreGrupo.getValue();
					Grupo grupo = new Grupo(id, estatus, nombre, arboles);
					servicioGrupo.guardarGrupo(grupo);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void enviar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void eliminar() {
				if (id != 0) {
					if (validarEliminar()) {
						Messagebox
								.show("¿Esta Seguro de Eliminar el Grupo?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													Grupo grupo = servicioGrupo
															.buscar(id);
													List<Usuario> usuario = servicioUsuario
															.buscarPorGrupo(grupo);
													if (!usuario.isEmpty())
														msj.mensajeError(Mensaje.noEliminar);
													else {
														servicioGrupo
																.eliminar(id);
														limpiar();
														msj.mensajeInformacion(Mensaje.eliminado);
													}
												}
											}
										});
					}
				} else
					msj.mensajeAlerta(Mensaje.noSeleccionoItem);
			}

			@Override
			public void buscar() {
				final List<Grupo> listGrupo = servicioGrupo.buscarTodos();
				catalogo = new Catalogo<Grupo>(catalogoGrupo, "Grupos",
						listGrupo, true, "Nombre") {

					@Override
					protected List<Grupo> buscar(List<String> valores) {

						List<Grupo> lista = new ArrayList<Grupo>();

						for (Grupo grupo : listGrupo) {
							if (grupo.getNombre().toLowerCase()
									.startsWith(valores.get(0).toLowerCase())) {
								lista.add(grupo);
							}
						}
						return lista;
					}

					@Override
					protected String[] crearRegistros(Grupo grupo) {
						String[] registros = new String[1];
						registros[0] = grupo.getNombre().toLowerCase();
						return registros;
					}
				};
				catalogo.setParent(catalogoGrupo);
				catalogo.doModal();
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
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraGrupo.appendChild(botonera);
	}

	protected boolean validarEliminar() {
		Grupo grupo = servicioGrupo.buscar(id);
		if (grupo != null) {
			if (grupo.getNombre().equals("MARCA")
					|| grupo.getNombre().equals("TRADE MARKETING")
					|| grupo.getNombre().equals("Gerente Regional")
					|| grupo.getNombre().equals("Solicitante")) {
				msj.mensajeAlerta(Mensaje.eliminacionFallida);
				return false;
			} else
				return true;
		} else
			return false;
	}

	public void llenarLista() {
		funcionalidades.clear();
		Grupo grupo = servicioGrupo.buscar(id);
		List<Arbol> arboles = servicioArbol.buscarFuncionalidadesGrupo(grupo);
		ltbFuncionalidades.setMultiple(false);
		ltbFuncionalidades.setCheckmark(false);
		ltbFuncionalidades.setMultiple(true);
		ltbFuncionalidades.setCheckmark(true);
		if (!arboles.isEmpty()) {
			for (int i = 0; i < arboles.size(); i++) {
				long id = arboles.get(i).getIdArbol();
				for (int j = 0; j < ltbFuncionalidades.getItemCount(); j++) {
					Listitem listItem = ltbFuncionalidades.getItemAtIndex(j);
					Arbol a = listItem.getValue();
					long id2 = a.getIdArbol();
					if (id == id2) {
						listItem.setSelected(true);
						funcionalidades.add(a.getNombreBoton());
						j = ltbFuncionalidades.getItemCount();
					}
				}

			}
			ltbFuncionalidadesSeleccionados.setModel(new ListModelList<String>(
					funcionalidades));
		}
	}

	public boolean validar() {
		if (txtNombreGrupo.getText().compareTo("") == 0) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public ListModelList<Arbol> getModelArbol() {
		modelArbol = new ListModelList<Arbol>(servicioArbol.buscarTodos());
		return modelArbol;
	}

	@Listen("onSeleccion = #catalogoGrupo")
	public void seleccionGrupo() {
		limpiarCampos();
		Grupo grupo = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(grupo);
		catalogo.setParent(null);
	}

	private void limpiarCampos() {
		txtNombreGrupo.setValue("");
		txtNombreGrupo.setDisabled(false);
		for (int i = 0; i < ltbFuncionalidades.getItemCount(); i++) {
			Listitem listItem = ltbFuncionalidades.getItemAtIndex(i);
			if (listItem.isSelected()) {
				listItem.setSelected(false);
			}
		}
		id = 0;
		funcionalidades.clear();
		ltbFuncionalidadesSeleccionados.getItems().clear();
		if (txtNombreGrupo.isDisabled())
			txtNombreGrupo.setDisabled(false);
	}

	private void llenarCampos(Grupo grupo) {
		txtNombreGrupo.setValue(grupo.getNombre());
		if (grupo.getNombre().equals("MARCA")
				|| grupo.getNombre().equals("TRADE MARKETING")
				|| grupo.getNombre().equals("Gerente Regional")
				|| grupo.getNombre().equals("Solicitante"))
			txtNombreGrupo.setDisabled(true);
		id = grupo.getIdGrupo();
		llenarLista();
	}

	@Listen("onChange = #txtNombreGrupo")
	public void buscarPorNombre() {
		Grupo grupo = servicioGrupo.buscarPorNombre(txtNombreGrupo.getValue());
		if (grupo != null)
			llenarCampos(grupo);
	}

	@Listen("onSelect = #ltbFuncionalidades")
	public void mostrarFuncionalidad() {
		ltbFuncionalidadesSeleccionados.getItems().clear();
		funcionalidades.clear();
		for (int i = 0; i < ltbFuncionalidades.getItemCount(); i++) {
			Listitem listItem = ltbFuncionalidades.getItemAtIndex(i);
			if (listItem.isSelected()) {
				funcionalidades.add(listItem.getLabel());
				ltbFuncionalidadesSeleccionados
						.setModel(new ListModelList<String>(funcionalidades));
			}
		}
	}
}
