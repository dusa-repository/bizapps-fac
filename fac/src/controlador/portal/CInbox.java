package controlador.portal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.seguridad.Arbol;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

import componente.Validador;

import controlador.maestros.CGenerico;

public class CInbox extends CGenerico {

	private static final long serialVersionUID = 8456820954892688693L;
	@Wire
	private Window wdwInbox;
	@Wire
	private Button btnPendiente;
	@Wire
	private Button btnAprobada;
	@Wire
	private Button btnRechazada;
	@Wire
	private Button btnCancelada;
	@Wire
	private Image imagenes;
	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	int pendiente = 0;
	int aprobada = 0;
	int cancelada = 0;
	int finalizada = 0;
	int rechazada = 0;
	
	
	public int getRechazada() {
		rechazada = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "Rechazada");
		return rechazada;
	}

	public int getAprobada() {
		aprobada = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "Aprobada");
		return aprobada;
	}

	public int getCancelada() {
		cancelada = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "Cancelada");
		return cancelada;
	}

	public int getFinalizada() {
		finalizada = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "Finalizada");
		return finalizada;
	}

	public int getPendiente() {
		pendiente = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "Pendiente");
		return pendiente;
	}

	@Override
	public void inicializar() throws IOException {

		btnPendiente.setSrc("/public/imagenes/botones/pendiente.png");
		btnAprobada.setSrc("/public/imagenes/botones/procesada.png");
		btnCancelada.setSrc("/public/imagenes/botones/cancelada.png");
		btnRechazada.setSrc("/public/imagenes/botones/rechazada.png");

		Over(btnCancelada, "canceladaG");
		Out(btnCancelada, "cancelada");
		Over(btnPendiente, "pendienteG");
		Out(btnPendiente, "pendiente");
		Over(btnAprobada, "procesadaG");
		Out(btnAprobada, "procesada");
		Over(btnRechazada, "rechazadaG");
		Out(btnRechazada, "rechazada");

		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();

		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());

		if (u.getImagen() == null) {
			imagenes.setContent(new AImage(url));
		} else {
			try {
				BufferedImage imag;
				imag = ImageIO.read(new ByteArrayInputStream(u.getImagen()));
				imagenes.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final List<Button> botones = new ArrayList<Button>();
		botones.add(btnCancelada);
		botones.add(btnPendiente);
		botones.add(btnAprobada);
		botones.add(btnRechazada);
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(
				auth.getAuthorities());
		for (int k = 0; k < authorities.size(); k++) {
			final Arbol arbol;
			String nombre = authorities.get(k).toString();
			if (Validador.validarNumero(nombre)) {
				arbol = servicioArbol.buscar(Long.parseLong(nombre));
				for (int i = 0; i < botones.size(); i++) {
					if (("btn" + arbol.getNombre()).equals(botones.get(i)
							.getId())) {
						final int j = i;
						botones.get(i).setVisible(true);
						botones.get(i).addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {

										variable = arbol.getNombre();
										Window window = (Window) Executions
												.createComponents("/vistas/"
														+ arbol.getUrl()
														+ ".zul", null, null);
										window.doModal();
									}
								});
					}
				}
			}
		}
	}

	@Listen("onClick = #btnCerrar")
	public void cerrar() {
		cerrarVentana(wdwInbox);
	}

	public void Over(final Button boton, final String imagen) {
		boton.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						boton.setSrc("/public/imagenes/botones/" + imagen
								+ ".png");
						boton.setStyle("color:black");
					}
				});
	}

	public void Out(final Button boton, final String imagen) {
		boton.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				boton.setSrc("/public/imagenes/botones/" + imagen + ".png");
				boton.setStyle("color:white");
			}
		});
	}
	
	public void actualizar(){
		getAprobada();
		getCancelada();
		getPendiente();
		getRechazada();
	}
}
