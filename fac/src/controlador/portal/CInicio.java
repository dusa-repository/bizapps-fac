package controlador.portal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import modelo.seguridad.Arbol;
import modelo.seguridad.Configuracion;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.image.AImage;
import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
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
	private Button btnNotaCredito;
	@Wire
	private Button btnInBox;
	@Wire
	private Button btnPlan;
	@Wire
	private Button btnCruds;
	@Wire
	private Button btnProcesarNota;
	@Wire
	private Button btnActualizar;
	@Wire
	private Image imagenes;
	@Wire
	private Label lblEntorno;
	@Wire
	private Label lblNombre;
	private String tipo = "";
	List<Button> botonesAgregados = new ArrayList<Button>();
	@Wire
	private Listbox ltbRoles;
	boolean admin = false;
	// @Wire
	// private Include include;
	URL url = getClass().getResource("/controlador/maestros/usuario.png");

	@Override
	public void inicializar() throws IOException {
		Clients.confirmClose("Mensaje de la Aplicacion:");
		Authentication authe = SecurityContextHolder.getContext()
				.getAuthentication();

		Usuario u = servicioUsuario.buscarUsuarioPorNombre(authe.getName());

		List<Grupo> grupos = servicioGrupo.buscarGruposUsuario(u);
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).getNombre().equals("MARCA")
					|| grupos.get(i).getNombre().equals("TRADE MARKETING"))
				admin = true;
		}
		ltbRoles.setModel(new ListModelList<Grupo>(grupos));

		btnCataInduccion.setImage("/public/imagenes/botones/planillaP.png");
		btnEvento.setImage("/public/imagenes/botones/planillaP.png");
		btnFachada.setImage("/public/imagenes/botones/planillaP.png");
		btnNotaCredito.setImage("/public/imagenes/botones/planillaP.png");
		btnPromocion.setImage("/public/imagenes/botones/planillaP.png");
		btnSolicitudArte.setImage("/public/imagenes/botones/planillaP.png");
		btnUniforme.setImage("/public/imagenes/botones/planillaP.png");
		btnCruds.setImage("/public/imagenes/botones/adminP.png");
		btnProcesarNota.setImage("/public/imagenes/botones/checkP.png");
		btnInBox.setImage("/public/imagenes/botones/inboxP.png");
		btnPlan.setImage("/public/imagenes/botones/planP.png");

		Over(btnCruds, "adminG");
		Out(btnCruds, "adminP");
		Over(btnProcesarNota, "checkG");
		Out(btnProcesarNota, "checkP");
		Over(btnInBox, "inboxG");
		Out(btnInBox, "inboxP");
		Over(btnPlan, "planG");
		Out(btnPlan, "planP");
		Over(btnCataInduccion, "planillaG");
		Out(btnCataInduccion, "planillaP");
		Over(btnEvento, "planillaG");
		Out(btnEvento, "planillaP");
		Over(btnFachada, "planillaG");
		Out(btnFachada, "planillaP");
		Over(btnNotaCredito, "planillaG");
		Out(btnNotaCredito, "planillaP");
		Over(btnPromocion, "planillaG");
		Out(btnPromocion, "planillaP");
		Over(btnSolicitudArte, "planillaG");
		Out(btnSolicitudArte, "planillaP");
		Over(btnUniforme, "planillaG");
		Out(btnUniforme, "planillaP");

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
		botones.add(btnUniforme);
		botones.add(btnUniforme);
		botones.add(btnPromocion);
		botones.add(btnCataInduccion);
		botones.add(btnFachada);
		botones.add(btnNotaCredito);
		botones.add(btnSolicitudArte);
		botones.add(btnInBox);
		botones.add(btnPlan);
		botones.add(btnProcesarNota);
		botones.add(btnCruds);

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
						if (!botones.get(i).getId().equals("btnInBox")
								&& !botones.get(i).getId().equals("btnCruds")
								&& !botones.get(i).getId()
										.equals("btnProcesarNota")
								&& !botones.get(i).getId()
										.equals("btnNotaCredito")
								&& !botones.get(i).getId().equals("btnPlan"))
							botonesAgregados.add(botones.get(i));
						botones.get(i).setVisible(true);
						botones.get(i).addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										if (arbol.getNombre().equals("InBox")
												&& admin) {
											recibir(tipo);
											variable = "Generales";
											Window window = (Window) Executions
													.createComponents(
															"/vistas/transacciones/VSolicitud.zul",
															null, null);
											window.doModal();
										} else {
											recibir(tipo);
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
		if (SecurityUtil.isAnyGranted("Solicitante")) {
			continuar();
		} else {
			if (SecurityUtil.isAnyGranted("TRADE MARKETING"))
				trade();
			else {
				if (SecurityUtil.isAnyGranted("MARCA"))
					marca();
				else
					lblEntorno.setValue("Entorno: No especificado");
			}
		}
	}

	private void continuar() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("width", "500px");
		params.put("height", "800px");
		params.put("style", "top:100px;");
		String[] arreglo = { "TRADE MARKETING", "MARCA" };

		Messagebox.Button[] boton = { Messagebox.Button.OK,
				Messagebox.Button.CANCEL };

		Messagebox.show("¿SU SOLICITUD SERA CARGADA A?", "", boton, arreglo,
				"", null, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getData().toString().contains("OK")) {
							trade();
						} else {
							if (evt.getData().toString().contains("CANCEL")) {
								marca();
							}
						}
					}
				}, params);

	}

	public void Over(final Button boton, final String imagen) {
		boton.addEventListener(Events.ON_MOUSE_OVER,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						boton.setImage("/public/imagenes/botones/" + imagen
								+ ".png");
						boton.setStyle("color:black");
					}
				});
	}

	public void Out(final Button boton, final String imagen) {
		boton.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				boton.setImage("/public/imagenes/botones/" + imagen + ".png");
				boton.setStyle("color:white");
			}
		});
	}

	@Listen("onClick = #lblEditarCuenta")
	public void abrirVentana() {
		Window window = (Window) Executions.createComponents(
				"/vistas/seguridad/VEditarUsuario.zul", null, null);
		window.doModal();
	}

	public void marca() {
		List<Configuracion> configuracion = servicioConfiguracion
				.buscar("Marca");
		tipo = "Marca";
		asignarLabel(tipo);
		recorrer(configuracion);

	}

	public void asignarLabel(String tipo2) {
		if (tipo2.equals("Marca")) {
			lblNombre.setValue("Usted se encuentra bajo el entorno de MARCA");
			lblEntorno.setValue("Entorno: MARCA");
			if (btnActualizar != null) {
				btnActualizar.setLabel("Cambiar a TRADEMARKETING");
				btnActualizar
						.setTooltiptext("Presione el boton para cambiar al entorno de TRADEMARKETING");
			}
		} else {
			lblNombre
					.setValue("Usted se encuentra bajo el entorno de TRADEMARKETING");
			lblEntorno.setValue("Entorno: TRADE MARKETING");
			if (btnActualizar != null) {
				btnActualizar.setLabel("Cambiar a MARCA");
				btnActualizar
						.setTooltiptext("Presione el boton para cambiar al entorno de MARCA");
			}
		}
	}

	public void trade() {
		List<Configuracion> configuracion = servicioConfiguracion
				.buscar("TradeMark");
		tipo = "TradeMark";
		asignarLabel(tipo);
		recorrer(configuracion);
	}

	private void recorrer(List<Configuracion> configuracion) {
		for (int i = 0; i < botonesAgregados.size(); i++) {
			String boton = botonesAgregados.get(i).getId();
			boolean entro = false;
			for (int j = 0; j < configuracion.size(); j++) {
				if (boton.equals("btn" + configuracion.get(j).getPlanilla())) {
					entro = true;
				}
			}
			if (!entro)
				botonesAgregados.get(i).setVisible(false);
			else
				botonesAgregados.get(i).setVisible(true);
		}
	}

	@Listen("onClick=#btnActualizar")
	public void actualizar() {
		continuar();
	}
}
