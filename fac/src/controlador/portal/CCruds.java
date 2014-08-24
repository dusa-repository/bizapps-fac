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

public class CCruds extends CGenerico {

	@Wire
	private Window wdwCruds;
	@Wire
	private Button btnUsuario;
	@Wire
	private Button btnRecurso;
	@Wire
	private Button btnZona;
	@Wire
	private Button btnSku;
	@Wire
	private Button btnAliado;
	@Wire
	private Button btnMarca;
	@Wire
	private Button btnUnif;
	@Wire
	private Button btnTiposUDC;
	@Wire
	private Button btnUDC;
	@Wire
	private Button btnConfiguracion;
	@Wire
	private Button btnFuncionalidad;
	@Wire
	private Button btnGrupo;
	@Wire
	private Image imagenes;
	@Wire
	private Listbox ltbRoles;
	
	URL url = getClass().getResource("/controlador/maestros/usuario.png");
	
	
	private static final long serialVersionUID = 4411078183725965182L;

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub
		if(Executions.getCurrent().getBrowser().equals("gecko")){
			wdwCruds.setWidth("106em");
			wdwCruds.setHeight("50em");
		}
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();

		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());

		List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		ltbRoles.setModel(new ListModelList<Grupo>(grupos));
		
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
		botones.add(btnAliado);
		botones.add(btnMarca);
		botones.add(btnRecurso);
		botones.add(btnSku);
		botones.add(btnUsuario);
		botones.add(btnZona);
		botones.add(btnUnif);
		botones.add(btnTiposUDC);
		botones.add(btnUDC);
		botones.add(btnGrupo);
		botones.add(btnFuncionalidad);
		botones.add(btnConfiguracion);
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
								"/public/imagenes/botones/crudP.png");
						botones.get(i).addEventListener(Events.ON_MOUSE_OVER,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										botones.get(j)
												.setSrc("/public/imagenes/botones/crudG.png");
										botones.get(j).setStyle("color:black");
									}
								});
						botones.get(i).addEventListener(Events.ON_MOUSE_OUT,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										botones.get(j)
												.setSrc("/public/imagenes/botones/crudP.png");
										botones.get(j).setStyle("color:white");
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
		cerrarVentana(wdwCruds);
	}
	
	@Listen("onClick = #lblEditarCuenta")
	public void abrirVentana(){
		Window window = (Window) Executions.createComponents(
				"/vistas/seguridad/VEditarUsuario.zul", null, null);
				window.doModal();
	}

}
