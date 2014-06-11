package controlador.seguridad;

import java.io.IOException;

import modelo.seguridad.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CReinicioPassword extends CGenerico {

	@Wire
	private Textbox txtCorreoUsuario;
	@Wire
	private Textbox txtCedulaUsuario;
	@Wire
	private Label lblNombreUsuario;
	@Wire
	private Div botoneraReinicio;
	@Wire
	private Window wdwRecordar;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final long serialVersionUID = 6988038390488496987L;

	@Override
	public void inicializar() throws IOException {
		
		txtCedulaUsuario.setFocus(true);
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				wdwRecordar.onClose();

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				txtCorreoUsuario.setValue("");
				txtCedulaUsuario.setValue("");
				txtCedulaUsuario.setFocus(true);

			}

			@Override
			public void guardar() {
				String password = KeyGenerators.string().generateKey();
				String correo;
				if (validar()) {
					Usuario usuario = servicioUsuario
							.buscarUsuarioPorNombre(txtCedulaUsuario.getValue());
					if (usuario != null) {
						if (usuario.getMail() != null) {
							correo = usuario.getMail();
						} else {
							correo = txtCorreoUsuario.getValue();
						}
						usuario.setPassword(password);
						servicioUsuario.guardar(usuario);
						enviarEmailNotificacion(
								correo,
								"Ha Solicitado Reiniciar su Password, sus nuevos datos para el inicio de sesion son: "
										+ " Usuario: "
										+ usuario.getNombre()
										+ "  " + " Password: " + password);
						limpiar();
			
					} else {
						Messagebox
								.show("El Nombre de Usuario no Existe",
										"Informacion", Messagebox.OK,
										Messagebox.INFORMATION);
					}
				}

			}

			@Override
			public void enviar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

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
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraReinicio.appendChild(botonera);

	}

	protected boolean validar() {
		if (txtCedulaUsuario.getText().compareTo("") == 0
				|| txtCorreoUsuario.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/*
	 * Metodo que permite validar el correo electronico
	 */
	@Listen("onChange = #txtCorreoUsuario")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtCorreoUsuario.getValue()) == false) {
			Messagebox.show("Correo Invalido", "Informacion", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	/* Valida la cedula */
	@Listen("onChange = #txtCedulaUsuario")
	public void validarCedula() {
		if (!Validador.validarNumero(txtCedulaUsuario.getValue())) {
			Messagebox.show("Cedula Invalida", "Informacion", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}
}
