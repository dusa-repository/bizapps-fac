package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import servicio.maestros.SAliado;
import servicio.maestros.SF0004;
import servicio.maestros.SF0005;
import servicio.maestros.SMarca;
import servicio.maestros.SRecurso;
import servicio.maestros.SSku;
import servicio.maestros.SUniforme;
import servicio.maestros.SZona;
import servicio.seguridad.SArbol;
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

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2971748198417518812L;
	@WireVariable("SAliado")
	protected SAliado servicioAliado;
	@WireVariable("SArbol")
	protected SArbol servicioArbol;
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
	
	protected DateFormat df = new SimpleDateFormat("HH:mm:ss");
	public final Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.SECOND));
	public java.util.Date fecha = new Date();
	public Timestamp fechaHora = new Timestamp(fecha.getTime());
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
//		Selectors.wireComponents( comp, this, false );
		inicializar();
	}

	public abstract void inicializar() throws IOException;
	
	public void cerrarVentana(Window win) {
		win.onClose();
	}
	
	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}
}
