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

	@Wire
	private Window wdwInbox;
	@Wire
	private Button btnPendiente;
	@Wire
	private Button btnProcesada;
	@Wire
	private Button btnRechazada;
	@Wire
	private Button btnCancelada;
	@Wire
	private Image imagenes;
	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	String rechazada = "";
	
	public String getRechazada() {
		return rechazada = "4";
	}
	
	@Override
	public void inicializar() throws IOException {
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
		botones.add(btnProcesada);
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
						botones.get(i).setSrc(
								"/public/imagenes/botones/adelante.png");
						botones.get(i).addEventListener(Events.ON_MOUSE_OVER,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										botones.get(j)
												.setSrc("/public/imagenes/botones/agregar.png");
									}
								});
						botones.get(i).addEventListener(Events.ON_MOUSE_OUT,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										botones.get(j)
												.setSrc("/public/imagenes/botones/adelante.png");
									}
								});
						botones.get(i).addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
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
	public void cerrar(){
		cerrarVentana(wdwInbox);
	}
}
