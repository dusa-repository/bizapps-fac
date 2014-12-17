package controlador.maestros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.maestros.Zona;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CUsuario extends CGenerico {

	private static final long serialVersionUID = 7879830599305337459L;
	@Wire
	private Window wdwUsuario;
	@Wire
	private Textbox txtCodigoUsuario;
	@Wire
	private Textbox txtNombreUsuario;
	@Wire
	private Textbox txtPasswordUsuario;
	@Wire
	private Textbox txtEmailUsuario;
	@Wire
	private Textbox txtZonaUsuario;
	@Wire
	private Textbox txtSupervisorUsuario;
	@Wire
	private Div botoneraUsuario;
	@Wire
	private Div DivCatalogoZona;
	@Wire
	private Div DivCatalogoUsuario;
	@Wire
	private Image imagen;
	@Wire
	private Fileupload fudImagenUsuario;
	@Wire
	private Media media;
	@Wire
	private Listbox ltbGruposDisponibles;
	@Wire
	private Listbox ltbGruposAgregados;
	List<Grupo> gruposDisponibles = new ArrayList<Grupo>();
	List<Grupo> gruposOcupados = new ArrayList<Grupo>();
	URL url = getClass().getResource("usuario.png");

	Catalogo<Usuario> catalogoUsuario;
	Catalogo<Zona> catalogo;
	String id = "";
	String idBoton = "";
	String idZona = "";

	@Override
	public void inicializar() throws IOException {
		try {
			imagen.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		llenarListas();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwUsuario);
			}

			@Override
			public void buscar() {
				Event e = new Event("");
				buscarCatalogoPropio(e);

			}

			@Override
			public void limpiar() {
				txtCodigoUsuario.setDisabled(false);
				ltbGruposAgregados.getItems().clear();
				ltbGruposDisponibles.getItems().clear();
				txtCodigoUsuario.setValue("");
				txtEmailUsuario.setValue("");
				txtNombreUsuario.setValue("");
				txtPasswordUsuario.setValue("");
				txtSupervisorUsuario.setValue("");
				txtZonaUsuario.setValue("");
				try {
					imagen.setContent(new AImage(url));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				id = "";
				idZona = "";
				idBoton = "";
				llenarListas();
			}

			@Override
			public void guardar() {
				if (validar()) {
					Set<Grupo> gruposUsuario = new HashSet<Grupo>();
					for (int i = 0; i < ltbGruposAgregados.getItemCount(); i++) {
						Grupo grupo = ltbGruposAgregados.getItems().get(i)
								.getValue();
						gruposUsuario.add(grupo);
					}
					String login = txtCodigoUsuario.getValue();
					String nombre = txtNombreUsuario.getValue();
					String email = txtEmailUsuario.getValue();
					String password = txtPasswordUsuario.getValue();
					String supervisor = txtSupervisorUsuario.getValue();
					Zona zona = servicioZona.buscar(idZona);
					byte[] imagenUsuario = null;
					if (media instanceof org.zkoss.image.Image) {
						imagenUsuario = imagen.getContent().getByteData();

					} else {
						try {
							imagen.setContent(new AImage(url));
						} catch (IOException e) {
							e.printStackTrace();
						}
						imagenUsuario = imagen.getContent().getByteData();
					}
					Usuario usuario = new Usuario(login, zona, nombre, email,
							password, supervisor, "1", "Envio", imagenUsuario,
							true, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), gruposUsuario);
					servicioUsuario.guardar(usuario);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar el Usuario?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Usuario usuario = servicioUsuario
												.buscar(id);
										List<PlanillaArte> planillaArte = servicioPlanillaArte
												.buscarPorUsuario(usuario);
										List<PlanillaCata> planillaCata = servicioPlanillaCata
												.buscarPorUsuario(usuario);
										List<PlanillaEvento> planillaEvento = servicioPlanillaEvento
												.buscarPorUsuario(usuario);
										List<PlanillaFachada> planillaFachada = servicioPlanillaFachada
												.buscarPorUsuario(usuario);
										List<PlanillaPromocion> planillaPromocion = servicioPlanillaPromocion
												.buscarPorUsuario(usuario);
										List<PlanillaUniforme> planillaUniforme = servicioPlanillaUniforme
												.buscarPorUsuario(usuario);
										if (!planillaArte.isEmpty()
												|| !planillaCata.isEmpty()
												|| !planillaEvento.isEmpty()
												|| !planillaFachada.isEmpty()
												|| !planillaPromocion.isEmpty()
												|| !planillaUniforme.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioUsuario.eliminar(id);
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
		botoneraUsuario.appendChild(botonera);
	}

	protected void llenarListas() {

		Usuario usuario = servicioUsuario.buscar(id);
		gruposDisponibles = servicioGrupo.buscarDisponibles(usuario);
		ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
				gruposDisponibles));

		if (usuario != null)
			gruposOcupados = servicioGrupo.buscarGruposUsuario(usuario);
		else
			gruposOcupados.clear();

		ltbGruposAgregados.setModel(new ListModelList<Grupo>(gruposOcupados));

		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);

		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	protected boolean validar() {
		if (txtCodigoUsuario.getText().compareTo("") == 0
				|| txtEmailUsuario.getText().compareTo("") == 0
				|| txtNombreUsuario.getText().compareTo("") == 0
				|| txtPasswordUsuario.getText().compareTo("") == 0
				|| txtSupervisorUsuario.getText().compareTo("") == 0
				|| txtZonaUsuario.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	@Listen("onUpload = #fudImagenUsuario")
	public void processMedia(UploadEvent event) {
		media = event.getMedia();
		if (media != null) {
			if (media.getContentType().equals("image/jpeg")
					|| media.getContentType().equals("image/png")) {
				if (media.getByteData().length <= 104000) {
					imagen.setHeight("60px");
					imagen.setWidth("60px");
					imagen.setContent((org.zkoss.image.Image) media);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}
	}

	@Listen("onClick =  #btnBuscarSupervisores")
	public void buscarCatalogoPropio(Event e) {
		Button boton;
		if (e.getTarget() != null) {
			boton = (Button) e.getTarget();
			idBoton = boton.getId();
		} else
			idBoton = "btnBuscarUsuarios";
		final List<Usuario> listUsuario = servicioUsuario
				.buscarTodosOrdenados();
		catalogoUsuario = new Catalogo<Usuario>(DivCatalogoUsuario,
				"Catalogo de Usuarios", listUsuario, true, "Codigo", "Nombre",
				"Email", "Supervisor", "Zona") {

			@Override
			protected List<Usuario> buscar(List<String> valores) {

				List<Usuario> lista = new ArrayList<Usuario>();

				for (Usuario usuario : listUsuario) {
					if (usuario.getIdUsuario().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& usuario.getNombre().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& usuario.getMail().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& usuario.getSupervisor().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& usuario.getZona().getDescripcion().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						lista.add(usuario);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Usuario usuario) {
				String[] registros = new String[5];
				registros[0] = usuario.getIdUsuario();
				registros[1] = usuario.getNombre();
				registros[2] = usuario.getMail();
				registros[3] = usuario.getSupervisor();
				registros[4] = usuario.getZona().getDescripcion();
				return registros;
			}
		};
		catalogoUsuario.setParent(DivCatalogoUsuario);
		catalogoUsuario.doModal();
	}

	@Listen("onChange = #txtCodigoUsuario, #txtSupervisorUsuario")
	public void buscarNombre(Event evento) {
		Textbox txt = (Textbox) evento.getTarget();
		switch (txt.getId()) {
		case "txtCodigoUsuario":
			Usuario usuario1 = servicioUsuario
					.buscarUsuarioPorNombre2(txtCodigoUsuario.getValue());
			if (usuario1 != null) {
				setearUsuario(usuario1);
				txtCodigoUsuario.setDisabled(true);
			} else {
				txtEmailUsuario.setValue("");
				txtNombreUsuario.setValue("");
				txtPasswordUsuario.setValue("");
				txtSupervisorUsuario.setValue("");
				txtZonaUsuario.setValue("");
				id = "";
				idZona = "";
				idBoton = "";
			}
			break;
		case "txtSupervisorUsuario":
			Usuario usuario2 = servicioUsuario.buscar(txtSupervisorUsuario
					.getValue());
			if (usuario2 == null)
				txtSupervisorUsuario.setValue("");
			txtSupervisorUsuario.setFocus(true);
			break;
		default:
			break;
		}
	}

	@Listen("onSeleccion = #DivCatalogoUsuario")
	public void seleccionarCatalogo() {
		Usuario usuario = catalogoUsuario.objetoSeleccionadoDelCatalogo();
		switch (idBoton) {
		case "btnBuscarSupervisores":
			setearSupervisor(usuario);
			break;
		case "btnBuscarUsuarios":
			setearUsuario(usuario);
			txtCodigoUsuario.setDisabled(true);
			break;
		default:
			break;
		}
		catalogoUsuario.setParent(null);
	}

	public void setearUsuario(Usuario usuario) {
		txtCodigoUsuario.setValue(usuario.getIdUsuario());
		txtEmailUsuario.setValue(usuario.getMail());
		txtNombreUsuario.setValue(usuario.getNombre());
		txtPasswordUsuario.setValue(usuario.getPassword());
		txtSupervisorUsuario.setValue(usuario.getSupervisor());
		txtZonaUsuario.setValue(usuario.getZona().getDescripcion());
		BufferedImage imag;
		if (usuario.getImagen() != null) {
			try {
				imag = ImageIO.read(new ByteArrayInputStream(usuario
						.getImagen()));
				imagen.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		idZona = usuario.getZona().getIdZona();
		id = usuario.getIdUsuario();
		llenarListas();
	}

	public void setearSupervisor(Usuario usuario) {
		txtSupervisorUsuario.setValue(usuario.getIdUsuario());
	}

	@Listen("onClick = #btnBuscarZonas")
	public void buscarCatalogoAjeno() {
		final List<Zona> listZona = servicioZona.buscarTodosOrdenados();
		catalogo = new Catalogo<Zona>(DivCatalogoZona, "Catalogo de Zonas",
				listZona, true, "Id", "Descripcion") {

			@Override
			protected List<Zona> buscar(List<String> valores) {

				List<Zona> lista = new ArrayList<Zona>();

				for (Zona zona : listZona) {
					if (zona.getIdZona().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& zona.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(zona);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Zona zona) {
				String[] registros = new String[2];
				registros[0] = zona.getIdZona();
				registros[1] = zona.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(DivCatalogoZona);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #DivCatalogoZona")
	public void seleccionAjena() {
		Zona zona = catalogo.objetoSeleccionadoDelCatalogo();
		txtZonaUsuario.setValue(zona.getDescripcion());
		idZona = zona.getIdZona();
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtZonaUsuario")
	public void buscarPorNombreAjeno() {
		Zona zona = servicioZona.buscar(txtZonaUsuario.getValue());
		if (zona != null) {
			txtZonaUsuario.setValue(zona.getDescripcion());
			idZona = zona.getIdZona();
		} else {
			txtZonaUsuario.setValue("");
			idZona = "";
			txtZonaUsuario.setFocus(true);
		}
	}

	@Listen("onClick = #pasar1")
	public void moverDerecha() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbGruposDisponibles.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Grupo grupo = listItem.get(i).getValue();
					gruposDisponibles.remove(grupo);
					gruposOcupados.add(grupo);
					ltbGruposAgregados.setModel(new ListModelList<Grupo>(
							gruposOcupados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposDisponibles.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);
	}

	@Listen("onClick = #pasar2")
	public void moverIzquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbGruposAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Grupo grupo = listItem2.get(i).getValue();
					gruposOcupados.remove(grupo);
					gruposDisponibles.add(grupo);
					ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
							gruposDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposAgregados.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}
}
