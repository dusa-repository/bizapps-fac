package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.seguridad.Configuracion;
import modelo.seguridad.Usuario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import servicio.estado.SBitacoraArte;
import servicio.estado.SBitacoraCata;
import servicio.estado.SBitacoraEvento;
import servicio.estado.SBitacoraFachada;
import servicio.estado.SBitacoraPromocion;
import servicio.estado.SBitacoraUniforme;
import servicio.generico.SPlanillaGenerica;
import servicio.maestros.SAliado;
import servicio.maestros.SF0004;
import servicio.maestros.SF0005;
import servicio.maestros.SMarca;
import servicio.maestros.SRecurso;
import servicio.maestros.SSku;
import servicio.maestros.SUniforme;
import servicio.maestros.SZona;
import servicio.seguridad.SArbol;
import servicio.seguridad.SConfiguracion;
import servicio.seguridad.SGrupo;
import servicio.seguridad.SUsuario;
import servicio.transacciones.SItemDegustacionPlanillaEvento;
import servicio.transacciones.SItemEstimadoPlanillaEvento;
import servicio.transacciones.SItemPlanillaCata;
import servicio.transacciones.SPlanillaArte;
import servicio.transacciones.SPlanillaCata;
import servicio.transacciones.SPlanillaEvento;
import servicio.transacciones.SPlanillaFachada;
import servicio.transacciones.SPlanillaPromocion;
import servicio.transacciones.SPlanillaUniforme;
import servicio.transacciones.SRecursoPlanillaCata;
import servicio.transacciones.SRecursoPlanillaEvento;
import servicio.transacciones.SRecursoPlanillaFachada;
import servicio.transacciones.SUniformePlanillaUniforme;
import servicio.transacciones.notas.SConfiguracionMarca;
import servicio.transacciones.notas.SCostoNotaCredito;
import servicio.transacciones.notas.SDetalleNotaCredito;
import servicio.transacciones.notas.SNotaCredito;
import servicio.transacciones.notas.SPlanificacion;
import componente.Mensaje;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2971748198417518812L;
	@WireVariable("SAliado")
	protected SAliado servicioAliado;
	@WireVariable("SArbol")
	protected SArbol servicioArbol;
	@WireVariable("SBitacoraArte")
	protected SBitacoraArte servicioBitacoraArte;
	@WireVariable("SBitacoraCata")
	protected SBitacoraCata servicioBitacoraCata;
	@WireVariable("SBitacoraEvento")
	protected SBitacoraEvento servicioBitacoraEvento;
	@WireVariable("SBitacoraFachada")
	protected SBitacoraFachada servicioBitacoraFachada;
	@WireVariable("SBitacoraPromocion")
	protected SBitacoraPromocion servicioBitacoraPromocion;
	@WireVariable("SBitacoraUniforme")
	protected SBitacoraUniforme servicioBitacoraUniforme;
	@WireVariable("SConfiguracion")
	protected SConfiguracion servicioConfiguracion;
	@WireVariable("SGrupo")
	protected SGrupo servicioGrupo;
	@WireVariable("SItemDegustacionPlanillaEvento")
	protected SItemDegustacionPlanillaEvento servicioItemDegustacionPlanillaEvento;
	@WireVariable("SItemEstimadoPlanillaEvento")
	protected SItemEstimadoPlanillaEvento servicioItemEstimadoPlanillaEvento;
	@WireVariable("SItemPlanillaCata")
	protected SItemPlanillaCata servicioItemPlanillaCata;
	@WireVariable("SMarca")
	protected SMarca servicioMarca;
	@WireVariable("SPlanillaArte")
	protected SPlanillaArte servicioPlanillaArte;
	@WireVariable("SPlanillaCata")
	protected SPlanillaCata servicioPlanillaCata;
	@WireVariable("SPlanillaEvento")
	protected SPlanillaEvento servicioPlanillaEvento;
	@WireVariable("SPlanillaFachada")
	protected SPlanillaFachada servicioPlanillaFachada;
	@WireVariable("SPlanillaPromocion")
	protected SPlanillaPromocion servicioPlanillaPromocion;
	@WireVariable("SPlanillaUniforme")
	protected SPlanillaUniforme servicioPlanillaUniforme;
	@WireVariable("SRecurso")
	protected SRecurso servicioRecurso;
	@WireVariable("SRecursoPlanillaCata")
	protected SRecursoPlanillaCata servicioRecursoPlanillaCata;
	@WireVariable("SRecursoPlanillaEvento")
	protected SRecursoPlanillaEvento servicioRecursoPlanillaEvento;
	@WireVariable("SRecursoPlanillaFachada")
	protected SRecursoPlanillaFachada servicioRecursoPlanillaFachada;
	@WireVariable("SSku")
	protected SSku servicioSku;
	@WireVariable("SUniforme")
	protected SUniforme servicioUniforme;
	@WireVariable("SUniformePlanillaUniforme")
	protected SUniformePlanillaUniforme servicioUniformePlanillaUniforme;
	@WireVariable("SUsuario")
	protected SUsuario servicioUsuario;
	@WireVariable("SZona")
	protected SZona servicioZona;
	@WireVariable("SF0004")
	protected SF0004 servicioF0004;
	@WireVariable("SF0005")
	protected SF0005 servicioF0005;
	@WireVariable("SPlanillaGenerica")
	protected SPlanillaGenerica servicioPlanillaGenerica;
	@WireVariable("SNotaCredito")
	protected SNotaCredito servicioNotaCredito;
	@WireVariable("SDetalleNotaCredito")
	protected SDetalleNotaCredito servicioDetalleCredito;
	@WireVariable("SPlanificacion")
	protected SPlanificacion servicioPlanificacion;
	@WireVariable("SConfiguracionMarca")
	protected SConfiguracionMarca servicioConfiguracionMarca;
	@WireVariable("SCostoNotaCredito")
	protected SCostoNotaCredito servicioCostoNotaCredito;
	static public String variable = "";
	static public String grupoDominante = "";
	protected DateFormat df = new SimpleDateFormat("HH:mm:ss");
	protected DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	public final Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.SECOND));
	public java.util.Date fecha = new Date();
	public Timestamp fechaHora = new Timestamp(fecha.getTime());
	public Mensaje msj = new Mensaje();
	static public String valor;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// Selectors.wireComponents( comp, this, false );
		inicializar();
	}

	public abstract void inicializar() throws IOException;

	public void cerrarVentana(Window win) {
		win.onClose();
	}

	public void recibir(String string) {
		valor = string;
	}

	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}

	public Usuario usuarioSesion(String valor) {
		return servicioUsuario.buscar(valor);
	}

	/* Metodo que permite enviar un correo electronico a cualquier destinatario */
	public boolean enviarEmailNotificacion(String correo, String mensajes) {
		try {

			String cc = "NOTIFICACION SISTEMA FAC";
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			String destino = correo;
			String mensaje = mensajes;
			String destinos[] = destino.split(",");
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);

			Transport.send(message);

			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean enviarEmail(String grupo, String usuario, long id,
			String tipo, String emailUsuario) {
		try {
			List<Configuracion> grupos = servicioConfiguracion
					.buscarTipo(grupo);
			String destinatario = "";
			String cc = "NOTIFICACION DE NUEVA SOLICITUD DE FORMATO DE ACTIVIDAD";
			String mensaje = "El USUARIO: " + usuario
					+ " HA ENVIADO UNA SOLICITUD DE " + tipo + " CON EL ID: "
					+ id;
			Usuario user = servicioUsuario.buscar(usuario);
			user = servicioUsuario.buscar(user.getSupervisor());
			String correoSupervisor = "";
			if (user != null)
				correoSupervisor = user.getMail();
			if (!grupos.isEmpty()) {
				destinatario = grupos.get(0).getCorreo() + "," + emailUsuario
						+ "," + correoSupervisor;
			}
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			// String contrasena = "Equipo.2";

			String destinos[] = destinatario.split(",");
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);

			Transport.send(message);
			// Transport t = session.getTransport("smtp");
			// t.connect(remitente, contrasena);
			// t.sendMessage(message,
			// message.getRecipients(Message.RecipientType.TO));
			//
			// t.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean enviarEmailNota(String tipo, String usuario, long id,
			String... mails) {
		try {
			String destinatario = "";
			String cc = "NOTIFICACION DE NUEVA SOLICITUD DE FORMATO DE ACTIVIDAD";
			String mensaje = "El USUARIO: " + usuario
					+ " HA ENVIADO UNA SOLICITUD DE NOTA DE CREDITO DE "
					+ tipo.toUpperCase() + ", CON EL ID: " + id;
			Usuario user = servicioUsuario.buscar(usuario);
			user = servicioUsuario.buscar(user.getSupervisor());
			String correoSupervisor = "";
			if (user != null)
				correoSupervisor = user.getMail();
			for (int i = 0; i < mails.length; i++) {
				destinatario += mails[i] + ",";
			}
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			String destinos[] = destinatario.split(",");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente));
			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}
			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean enviarEmailError(String grupo, String usuario, long id,
			String tipo, String emailUsuario, String rechazo, String motivo,
			String descripcion) {
		try {
			List<Configuracion> grupos = servicioConfiguracion
					.buscarTipo(grupo);
			String destinatario = "";
			String cc = "PLANILLA DE FORMATO DE ACTIVIDAD "
					+ rechazo.toUpperCase();
			String mensaje = "La planilla de: " + tipo + ", con el ID " + id
					+ "; ha sido " + rechazo.toUpperCase() + ". Motivado a: "
					+ motivo + ". " + descripcion;
			Usuario user = servicioUsuario.buscar(usuario);
			user = servicioUsuario.buscar(user.getSupervisor());
			String correoSupervisor = "";
			if (user != null)
				correoSupervisor = user.getMail();
			if (!grupos.isEmpty()) {
				destinatario = grupos.get(0).getCorreo() + "," + emailUsuario
						+ "," + correoSupervisor;
			}
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			// String contrasena = "Equipo.2";

			String destinos[] = destinatario.split(",");
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);

			Transport.send(message);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("cdusa", "cartucho");
		}
	}

	public void limpiarColores(InputElement... cajas) {
		for (int i = 0; i < cajas.length; i++) {
			cajas[i].setStyle("border-color:default");
			if (cajas[i] instanceof Combobox)
				cajas[i].setStyle("border: 1px solid default");
		}
	}

	public void aplicarColores(InputElement... cajas) {
		limpiarColores(cajas);
		for (int i = 0; i < cajas.length; i++) {
			if (cajas[i].getText().compareTo("") == 0)
				cajas[i].setStyle("border-color:red");
			if (cajas[i] instanceof Combobox)
				if (((Combobox) cajas[i]).getSelectedItem() == null)
					cajas[i].setStyle("border: 1px solid red");
		}
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
