package controlador.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.seguridad.Arbol;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CConfiguracion extends CGenerico {

	private static final long serialVersionUID = -2800409643154176860L;
	@Wire
	private Listbox ltbTradeMark;
	@Wire
	private Listbox ltbMarca;
	@Wire
	private Window wdwConfiguracion;
	@Wire
	private Textbox txtAdminTrade;
	@Wire
	private Textbox txtAdminMarca;
	@Wire
	private Button btnBuscarTrade;
	@Wire
	private Button btnBuscarMarca;
	@Wire
	private Div botoneraConfiguracion;
	@Wire
	private Div catalogoUsuario;
	Catalogo<Usuario> catalogo;
	ListModelList<Arbol> configuracion;
	List<Listbox> listas = new ArrayList<Listbox>();
	String idBoton = "";

	@Override
	public void inicializar() throws IOException {

		llenarListas();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwConfiguracion);
			}

			@Override
			public void limpiar() {

			}

			@Override
			public void guardar() {
				if (validar()) {
					servicioConfiguracion.limpiar();
					Usuario adminMarca = servicioUsuario.buscar(txtAdminMarca
							.getValue());
					Usuario adminTrade = servicioUsuario.buscar(txtAdminTrade
							.getValue());
					List<Configuracion> lista = new ArrayList<Configuracion>();
					if (ltbMarca.getItemCount() != 0) {
						for (int i = 0; i < ltbMarca.getItemCount(); i++) {
							Listitem listItem = ltbMarca.getItemAtIndex(i);
							if (listItem.isSelected()) {
								Arbol arbol = listItem.getValue();
								Configuracion configuracion = new Configuracion(
										0, arbol.getNombre(), "Marca",
										adminMarca, fechaHora, horaAuditoria,
										nombreUsuarioSesion());
								lista.add(configuracion);
							}
						}
					}
					if (ltbTradeMark.getItemCount() != 0) {
						for (int i = 0; i < ltbTradeMark.getItemCount(); i++) {
							Listitem listItem = ltbTradeMark.getItemAtIndex(i);
							if (listItem.isSelected()) {
								Arbol arbol = listItem.getValue();
								Configuracion configuracion = new Configuracion(
										0, arbol.getNombre(), "TradeMark",
										adminTrade, fechaHora, horaAuditoria,
										nombreUsuarioSesion());
								lista.add(configuracion);
							}
						}
					}
					servicioConfiguracion.guardar(lista);
					msj.mensajeInformacion(Mensaje.guardado);
				}
			}

			@Override
			public void enviar() {

			}

			@Override
			public void eliminar() {

			}

			@Override
			public void buscar() {

			}

			@Override
			public void atras() {

			}

			@Override
			public void adelante() {

			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraConfiguracion.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtAdminMarca.getText().compareTo("") == 0
				|| txtAdminTrade.getText().compareTo("") == 0) {
			msj.mensajeAlerta(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	private void llenarListas() {
		List<Configuracion> configuracionesTrade = servicioConfiguracion
				.buscarTipo("TradeMark");
		if (!configuracionesTrade.isEmpty()) {
			txtAdminTrade.setValue(configuracionesTrade.get(0).getUsuario()
					.getIdUsuario());
		}
		List<Configuracion> configuracionesMarca = servicioConfiguracion
				.buscarTipo("Marca");
		if (!configuracionesMarca.isEmpty()) {
			txtAdminMarca.setValue(configuracionesMarca.get(0).getUsuario()
					.getIdUsuario());
		}
		listasMultiples();
		ltbMarca.renderAll();
		ltbTradeMark.renderAll();
		if (!configuracionesMarca.isEmpty()) {
			for (int i = 0; i < configuracionesMarca.size(); i++) {
				String id = configuracionesMarca.get(i).getPlanilla();
				for (int j = 0; j < ltbMarca.getItemCount(); j++) {
					Listitem listItem = ltbMarca.getItemAtIndex(j);
					Arbol a = listItem.getValue();
					String id2 = a.getNombre();
					if (id.equals(id2)) {
						listItem.setSelected(true);
						j = ltbMarca.getItemCount();
					}
				}

			}
		}
		if (!configuracionesTrade.isEmpty()) {
			for (int i = 0; i < configuracionesTrade.size(); i++) {
				String id = configuracionesTrade.get(i).getPlanilla();
				for (int j = 0; j < ltbTradeMark.getItemCount(); j++) {
					Listitem listItem = ltbTradeMark.getItemAtIndex(j);
					Arbol a = listItem.getValue();
					String id2 = a.getNombre();
					if (id.equals(id2)) {
						listItem.setSelected(true);
						j = ltbTradeMark.getItemCount();
					}
				}

			}
		}

	}

	private void listasMultiples() {
		if (listas.isEmpty()) {
			listas.add(ltbMarca);
			listas.add(ltbTradeMark);
		}
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

	public ListModelList<Arbol> getConfiguracion() {
		configuracion = new ListModelList<Arbol>(
				servicioArbol.buscarParaConfiguracion());
		return configuracion;
	}

	@Listen("onClick =  #btnBuscarMarca,#btnBuscarTrade")
	public void buscarCatalogo(Event e) {
		Button boton;
		if (e.getTarget() != null) {
			boton = (Button) e.getTarget();
			idBoton = boton.getId();
		}
		final List<Usuario> listUsuario = servicioUsuario
				.buscarAdministradores();
		catalogo = new Catalogo<Usuario>(catalogoUsuario,
				"Catalogo de Usuarios de Tipo Administrador", listUsuario,
				true, "Codigo", "Nombre", "Email", "Supervisor", "Zona") {

			@Override
			protected List<Usuario> buscar(List<String> valores) {

				List<Usuario> lista = new ArrayList<Usuario>();

				for (Usuario usuario : listUsuario) {
					if (usuario.getIdUsuario().toLowerCase()
							.startsWith(valores.get(0).toLowerCase())
							&& usuario.getNombre().toLowerCase()
									.startsWith(valores.get(1).toLowerCase())
							&& usuario.getMail().toLowerCase()
									.startsWith(valores.get(2).toLowerCase())
							&& usuario.getSupervisor().toLowerCase()
									.startsWith(valores.get(3).toLowerCase())
							&& usuario.getZona().getDescripcion().toLowerCase()
									.startsWith(valores.get(4).toLowerCase())) {
						lista.add(usuario);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Usuario usuario) {
				String[] registros = new String[5];
				registros[0] = usuario.getIdUsuario().toLowerCase();
				registros[1] = usuario.getNombre().toLowerCase();
				registros[2] = usuario.getMail().toLowerCase();
				registros[3] = usuario.getSupervisor().toLowerCase();
				registros[4] = usuario.getZona().getDescripcion().toLowerCase();
				return registros;
			}
		};
		catalogo.setParent(catalogoUsuario);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoUsuario")
	public void seleccionarCatalogo() {
		Usuario usuario = catalogo.objetoSeleccionadoDelCatalogo();
		switch (idBoton) {
		case "btnBuscarMarca":
			setearMarca(usuario);
			break;
		case "btnBuscarTrade":
			setearTrade(usuario);
			break;
		default:
			break;
		}
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtAdminMarca, #txtAdminTrade")
	public void buscarNombre(Event evento) {
		Usuario usuario1 = servicioUsuario.buscar(txtAdminMarca.getValue());
		Usuario usuario2 = servicioUsuario.buscar(txtAdminTrade.getValue());
		Textbox txt = (Textbox) evento.getTarget();
		switch (txt.getId()) {
		case "txtAdminMarca":
			if (usuario1 != null)
				setearMarca(usuario1);
			else {
				txtAdminMarca.setFocus(true);
				msj.mensajeAlerta(Mensaje.noHayRegistros);
				txtAdminMarca.setValue("");
				idBoton = "";
			}
			break;
		case "txtAdminTrade":
			if (usuario2 != null)
				setearTrade(usuario2);
			else {
				txtAdminTrade.setFocus(true);
				msj.mensajeAlerta(Mensaje.noHayRegistros);
				txtAdminTrade.setValue("");
				idBoton = "";
			}
			break;
		default:
			break;
		}
	}

	private void setearMarca(Usuario usuario1) {
		txtAdminMarca.setValue(usuario1.getIdUsuario());
	}

	private void setearTrade(Usuario usuario2) {
		txtAdminTrade.setValue(usuario2.getIdUsuario());
	}
}
