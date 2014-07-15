package controlador.portal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
	private Button btnEdicion;
	@Wire
	private Button btnPagada;
	
	@Wire
	private Image imagenes;
	@Wire
	private Listbox ltbRoles;

	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	int pendiente = 0;
	int aprobada = 0;
	int cancelada = 0;
	int finalizada = 0;
	int rechazada = 0;
	int edicion = 0;
	int pagadas = 0;
	

	boolean coordinador = false;

	public int getEdicion() {
		edicion = servicioPlanillaGenerica.buscarCantidadPorUsuarioYEstado(
				usuarioSesion(nombreUsuarioSesion()), "En Edicion");
		return edicion;
	}

	public int getPagadas() {
		if (buscarCoordinador())
			pagadas = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Pagada");
		else
			pagadas = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Pagada");
		return pagadas;
	}
	
	public int getRechazada() {
		if (buscarCoordinador())
			rechazada = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Rechazada");
		else
			rechazada = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Rechazada");
		return rechazada;
	}

	public int getAprobada() {
		if (buscarCoordinador())
			aprobada = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Aprobada");
		else
			aprobada = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Aprobada");
		return aprobada;
	}

	public int getCancelada() {
		if (buscarCoordinador())
			cancelada = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Cancelada");
		else
			cancelada = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Cancelada");
		return cancelada;
	}

	public int getFinalizada() {
		if (buscarCoordinador())
			finalizada = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Finalizada");
		else
			finalizada = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Finalizada");
		return finalizada;
	}

	public int getPendiente() {
		if (buscarCoordinador())
			pendiente = servicioPlanillaGenerica
					.buscarCantidadPorCoordinadorYEstado(nombreUsuarioSesion(),
							"Pendiente");
		else
			pendiente = servicioPlanillaGenerica
					.buscarCantidadPorUsuarioYEstado(
							usuarioSesion(nombreUsuarioSesion()), "Pendiente");
		return pendiente;
	}

	public boolean buscarCoordinador() {
		List<Grupo> grupos = servicioGrupo
				.buscarGruposUsuario(usuarioSesion(nombreUsuarioSesion()));
		for (int w = 0; w < grupos.size(); w++) {
			if (grupos.get(w).getNombre().equals("Gerente Regional")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void inicializar() throws IOException {

		btnPendiente.setSrc("/public/imagenes/botones/pendiente.png");
		btnAprobada.setSrc("/public/imagenes/botones/procesada.png");
		btnCancelada.setSrc("/public/imagenes/botones/cancelada.png");
		btnRechazada.setSrc("/public/imagenes/botones/rechazada.png");
		btnEdicion.setSrc("/public/imagenes/botones/planillaP.png");
		btnPagada.setSrc("/public/imagenes/botones/pagada.png");

		Over(btnCancelada, "canceladaG");
		Out(btnCancelada, "cancelada");
		Over(btnPendiente, "pendienteG");
		Out(btnPendiente, "pendiente");
		Over(btnAprobada, "procesadaG");
		Out(btnAprobada, "procesada");
		Over(btnRechazada, "rechazadaG");
		Out(btnRechazada, "rechazada");
		Over(btnEdicion, "planillaG");
		Out(btnEdicion, "planillaP");
		Over(btnPagada, "pagadaG");
		Out(btnPagada, "pagada");

		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();

		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());

		List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		ltbRoles.setModel(new ListModelList<Grupo>(grupos));
		for (int w = 0; w < grupos.size(); w++) {
			if (grupos.get(w).getNombre().equals("Gerente Regional")) {
				coordinador = true;
				w = grupos.size();
			}
		}
		getRechazada();
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
		botones.add(btnEdicion);
		botones.add(btnPagada);
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

										if (arbol.getNombre().equals("Edicion"))
											variable = "En Edicion";
										else
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

	public void actualizar() {
		getAprobada();
		getCancelada();
		getPendiente();
		getRechazada();
	}

	@Listen("onClick = #lblEditarCuenta")
	public void abrirVentana() {
		Window window = (Window) Executions.createComponents(
				"/vistas/seguridad/VEditarUsuario.zul", null, null);
		window.doModal();
	}
}
