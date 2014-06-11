package controlador.portal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.maestros.Uniforme;
import modelo.seguridad.Arbol;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.image.AImage;
import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import componente.Validador;
import controlador.maestros.CGenerico;

public class CInicio extends CGenerico {

	private static final long serialVersionUID = 944420195285144918L;
	@Wire
	private Window wdwInicio;
	@Wire
	private Button btnEvento;
	@Wire
	private Button btnUniforme;
	@Wire
	private Button btnPromocion;
	@Wire
	private Button btnSolicitudArte;
	@Wire
	private Button btnCataInduccion;
	@Wire
	private Button btnFachada;
	@Wire
	private Button btnInBox;
	@Wire
	private Button btnCruds;
	@Wire
	private Image imagenes;
	@Wire
	private Listbox ltbRoles;
	boolean admin = false;
	// @Wire
	// private Include include;
	URL url = getClass().getResource("/controlador/maestros/usuario.png");

	@Override
	public void inicializar() throws IOException {

		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();

		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());

		// Set<Grupo> roles;
		// roles = u.getGrupos();
		List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getNombre().equals("Administrador")) {
				admin = true;
				grupoDominante = grupos.get(i).getNombre();
				i = grupos.size();
			} else {
				if (grupos.get(i).getNombre().equals("Coordinador")) {
					grupoDominante = grupos.get(i).getNombre();
					i = grupos.size();
				} else {
					if (grupos.get(i).getNombre().equals("Supervisor")) {
						grupoDominante = grupos.get(i).getNombre();
						i = grupos.size();
					}
				}
			}
		}
		ltbRoles.setModel(new ListModelList<Grupo>(grupos));

		btnCataInduccion.setSrc("/public/imagenes/botones/planillaP.png");
		btnEvento.setSrc("/public/imagenes/botones/planillaP.png");
		btnFachada.setSrc("/public/imagenes/botones/planillaP.png");
		btnPromocion.setSrc("/public/imagenes/botones/planillaP.png");
		btnSolicitudArte.setSrc("/public/imagenes/botones/planillaP.png");
		btnUniforme.setSrc("/public/imagenes/botones/planillaP.png");
		btnCruds.setSrc("/public/imagenes/botones/adminP.png");
		btnInBox.setSrc("/public/imagenes/botones/inboxP.png");

		Over(btnCruds, "adminG");
		Out(btnCruds, "adminP");
		Over(btnInBox, "inboxG");
		Out(btnInBox, "inboxP");
		Over(btnCataInduccion, "planillaG");
		Out(btnCataInduccion, "planillaP");
		Over(btnEvento, "planillaG");
		Out(btnEvento, "planillaP");
		Over(btnFachada, "planillaG");
		Out(btnFachada, "planillaP");
		Over(btnPromocion, "planillaG");
		Out(btnPromocion, "planillaP");
		Over(btnSolicitudArte, "planillaG");
		Out(btnSolicitudArte, "planillaP");
		Over(btnUniforme, "planillaG");
		Out(btnUniforme, "planillaP");

		// String ruta = "/vistas/componentes/VPrincipal.zul";
		// include.setSrc(null);

		// include.setSrc("/vistas/componentes/VPrincipal.zul");

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
		botones.add(btnEvento);
		botones.add(btnUniforme);
		botones.add(btnPromocion);
		botones.add(btnCataInduccion);
		botones.add(btnFachada);
		botones.add(btnSolicitudArte);
		botones.add(btnInBox);
		botones.add(btnCruds);

		// if (SecurityUtil.isAllGranted("ROLE_SUPERVISOR")) {
		// }
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
										if (arbol.getNombre().equals("InBox")
												&& admin) {
											variable = "En Edicion";
											Window window = (Window) Executions
													.createComponents(
															"/vistas/transacciones/VSolicitud.zul",
															null, null);
											window.doModal();
										} else {
											Window window = (Window) Executions.createComponents(
													"/vistas/" + arbol.getUrl()
															+ ".zul", null,
													null);
											window.doModal();
										}
									}
								});
					}
				}
			}
		}
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
}
