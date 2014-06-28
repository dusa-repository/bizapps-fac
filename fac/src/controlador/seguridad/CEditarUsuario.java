package controlador.seguridad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import modelo.seguridad.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CEditarUsuario extends CGenerico {

	@Wire
	private Textbox txtNombreUsuarioEditar;
	@Wire
	private Textbox txtClaveUsuarioNueva;
	@Wire
	private Textbox txtClaveUsuarioConfirmar;
	@Wire
	private Image imgUsuario;
	@Wire
	private Fileupload fudImagenUsuario;
	@Wire
	private Div botoneraEditarUsuario;
	@Wire
	private Window wdwEditarUsuario;
	private String id = "";
	private Media media;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	private static final long serialVersionUID = 2439502647179786175L;

	@Override
	public void inicializar() throws IOException {

		Usuario usuario = servicioUsuario.buscar(nombreUsuarioSesion());
		id = usuario.getIdUsuario();
		txtNombreUsuarioEditar.setValue(usuario.getIdUsuario());
		if (usuario.getImagen() == null) {
			imgUsuario.setContent(new AImage(url));
		} else {
			try {
				BufferedImage imag;
				imag = ImageIO.read(new ByteArrayInputStream(usuario
						.getImagen()));
				imgUsuario.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {

				cerrarVentana(wdwEditarUsuario);
			}

			@Override
			public void buscar() {

			}

			@Override
			public void limpiar() {
				Usuario usuario = servicioUsuario
						.buscarUsuarioPorNombre(nombreUsuarioSesion());
				id = usuario.getIdUsuario();
				txtNombreUsuarioEditar.setValue(usuario.getIdUsuario());
				txtClaveUsuarioConfirmar.setValue("");
				txtClaveUsuarioNueva.setValue("");
				if (usuario.getImagen() == null) {
					try {
						imgUsuario.setContent(new AImage(url));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						BufferedImage imag;
						imag = ImageIO.read(new ByteArrayInputStream(usuario
								.getImagen()));
						imgUsuario.setContent(imag);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void guardar() {

				if (validar()) {
					if (txtClaveUsuarioNueva.getValue().equals(
							txtClaveUsuarioConfirmar.getValue())) {
						Usuario usuario = servicioUsuario
								.buscarUsuarioPorNombre(id);
						byte[] imagenUsuario = null;
						imagenUsuario = imgUsuario.getContent().getByteData();
						String password = txtClaveUsuarioConfirmar.getValue();
						usuario.setPassword(password);
						usuario.setImagen(imagenUsuario);
						servicioUsuario.guardar(usuario);
						msj.mensajeInformacion(Mensaje.guardado);
						limpiar();
					} else {
						msj.mensajeAlerta(Mensaje.contrasennasNoCoinciden);
					}
				}
			}

			@Override
			public void enviar() {

			}

			@Override
			public void eliminar() {

			}

			@Override
			public void atras() {

			}

			@Override
			public void adelante() {

			}

		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraEditarUsuario.appendChild(botonera);

	}

	protected boolean validar() {
		if (txtClaveUsuarioConfirmar.getValue().equals("")
				|| txtClaveUsuarioNueva.getValue().equals("")) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	@Listen("onUpload = #fudImagenUsuario")
	public void processMedia(UploadEvent event) throws IOException {
		media = event.getMedia();
		if (media != null) {
			if (media.getContentType().equals("image/jpeg")
					|| media.getContentType().equals("image/png")) {
				if (media.getByteData().length <= 104000) {
					imgUsuario.setContent(new AImage(url));
					imgUsuario.setHeight("60px");
					imgUsuario.setWidth("60px");
					imgUsuario.setContent((org.zkoss.image.Image) media);
					imgUsuario.setVisible(true);
				} else {
					msj.mensajeAlerta(Mensaje.tamanioMuyGrande);
				}
			} else {
				msj.mensajeAlerta(Mensaje.noPermitido);
			}
		}

	}

}
