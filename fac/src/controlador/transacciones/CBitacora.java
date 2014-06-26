package controlador.transacciones;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.estado.BitacoraArte;
import modelo.estado.BitacoraCata;
import modelo.estado.BitacoraEvento;
import modelo.estado.BitacoraFachada;
import modelo.estado.BitacoraPromocion;
import modelo.estado.BitacoraUniforme;
import modelo.transacciones.PlanillaArte;
import modelo.transacciones.PlanillaCata;
import modelo.transacciones.PlanillaEvento;
import modelo.transacciones.PlanillaFachada;
import modelo.transacciones.PlanillaPromocion;
import modelo.transacciones.PlanillaUniforme;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import componente.Botonera;

import controlador.maestros.CGenerico;

public class CBitacora extends CGenerico {

	@Wire
	private Window wdwBitacora;
	@Wire
	private Div botoneraBitacora;
	@Wire
	private Label lblNombre;
	@Wire
	private Label lblUsuario;
	@Wire
	private Label lblMarca;
	@Wire
	private Label lblEstado;
	@Wire
	private Label lblTipo;
	@Wire
	private Listbox ltbBitacora;

	@Override
	public void inicializar() throws IOException {

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(wdwBitacora);

			}

			@Override
			public void limpiar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

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
			public void buscar() {
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
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(7).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraBitacora.appendChild(botonera);

		String tipo = "";
		// Llenar Datos
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("inbox");
		if (map != null) {
			if (map.get("id") != null) {
				tipo = (String) map.get("tipoPlanilla");
				switch (tipo) {
				case "Eventos Especiales": {
					PlanillaEvento planilla = servicioPlanillaEvento
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla.getMarca()
									.getDescripcion(), "Eventos Especiales",
							planilla.getEstado());
					List<BitacoraEvento> listaBitacoras = servicioBitacoraEvento
							.buscarPorPlanilla(planilla);
					ltbBitacora.setModel(new ListModelList<BitacoraEvento>(
							listaBitacoras));
				}
					break;
				case "Uniformes": {
					PlanillaUniforme planilla = servicioPlanillaUniforme
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla.getMarca()
									.getDescripcion(), "Uniformes",
							planilla.getEstado());
					List<BitacoraUniforme> listaBitacoras = servicioBitacoraUniforme
							.buscarPorPlanilla(planilla);
					ltbBitacora.setModel(new ListModelList<BitacoraUniforme>(
							listaBitacoras));
				}
					break;
				case "Promociones de Marca": {
					PlanillaPromocion planilla = servicioPlanillaPromocion
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla
									.getMarcaA().getDescripcion(),
									"Promociones de Marca", planilla.getEstado());
					List<BitacoraPromocion> listaBitacoras = servicioBitacoraPromocion
							.buscarPorPlanilla(planilla);
					ltbBitacora.setModel(new ListModelList<BitacoraPromocion>(
							listaBitacoras));
				}
					break;
				case "Solicitud de Arte y Publicaciones": {
					PlanillaArte planilla = servicioPlanillaArte
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla.getMarca()
									.getDescripcion(),"Solicitud de Arte y Publicaciones",
							planilla.getEstado());
					List<BitacoraArte> listaBitacoras = servicioBitacoraArte
							.buscarPorPlanilla(planilla);
					ltbBitacora.setModel(new ListModelList<BitacoraArte>(
							listaBitacoras));
				}
					break;
				case "Cata Induccion": {
					PlanillaCata planilla = servicioPlanillaCata
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla.getMarca()
									.getDescripcion(),"Cata Induccion",
							planilla.getEstado());
					List<BitacoraCata> listaBitacoras = servicioBitacoraCata
							.buscarPorPlanilla(planilla);
					ltbBitacora.setModel(new ListModelList<BitacoraCata>(
							listaBitacoras));
				}
					break;
				case "Fachada y Decoraciones": {
					PlanillaFachada planilla = servicioPlanillaFachada
							.buscar((Long) map.get("id"));
					settear(planilla.getNombreActividad(),
							planilla.getUsuarioAuditoria(), planilla.getMarca()
									.getDescripcion(), "Fachada y Decoraciones",
							planilla.getEstado());
					List<BitacoraFachada> listaBitacoras = servicioBitacoraFachada
							.buscarPorPlanilla(planilla);
					
					ltbBitacora.setModel(new ListModelList<BitacoraFachada>(
							listaBitacoras));
				}
					break;
				default:
					break;
				}

			}
		}
		map.clear();
		map = null;

	}

	public void settear(String nombre, String usuario, String marca,
			String tipo, String estado) {
		lblNombre.setValue(nombre);
		lblUsuario.setValue(usuario);
		lblEstado.setValue(estado);
		lblMarca.setValue(marca);
		lblTipo.setValue(tipo);
	}

}
