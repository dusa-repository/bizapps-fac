package controlador.transacciones.notas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.transacciones.notas.DetalleNotaCredito;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controlador.maestros.CGenerico;

public class CRechazoNota extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Listbox ltbRechazo;
	@Wire
	private Window wdwRechazo;
	private Listbox ltbLista;
	private Datebox dtbDesde;
	private Datebox dtbHasta;
	private Combobox cmbEstado;
	private Combobox cmbAliado;
	private Combobox cmbMarca;
	private Combobox cmbZona;
	private Longbox txtCodigo;
	String estado = "";
	CProcesarNota controlador = new CProcesarNota();
	List<DetalleNotaCredito> listaGeneral = new ArrayList<DetalleNotaCredito>();
	List<DetalleNotaCredito> procesadas = new ArrayList<DetalleNotaCredito>();
	boolean certificadas;
	boolean reporte;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("rechazado");
		if (map != null) {
			estado = (String) map.get("estado");
			dtbDesde = (Datebox) map.get("dtbDesde");
			dtbHasta = (Datebox) map.get("dtbHasta");
			ltbLista = (Listbox) map.get("ltbLista");
			cmbEstado = (Combobox) map.get("cmbEstado");
			cmbAliado = (Combobox) map.get("cmbAliado");
			cmbMarca = (Combobox) map.get("cmbMarca");
			cmbZona = (Combobox) map.get("cmbZona");
			txtCodigo = (Longbox) map.get("txtCodigo");
			listaGeneral = (List<DetalleNotaCredito>) map.get("listaGeneral");
			procesadas = (List<DetalleNotaCredito>) map.get("procesadas");
			certificadas = (boolean) map.get("certificadas");
			reporte = (boolean) map.get("reporte");
			map.clear();
			map = null;
		}
		ltbRechazo.setModel(new ListModelList<DetalleNotaCredito>(procesadas));
	}

	@Listen("onClick = #btnSalirRechazo")
	public void cerrarMotivo() {
		cerrarVentana(wdwRechazo);
	}

	@Listen("onClick = #btnAceptarRechazo")
	public void aceptarMotivo() {
		List<DetalleNotaCredito> listaRechazada = new ArrayList<DetalleNotaCredito>();
		if (ltbRechazo.getItemCount() != 0) {
			for (int i = 0; i < ltbRechazo.getItemCount(); i++) {
				Listitem listItem = ltbRechazo.getItemAtIndex(i);
				DetalleNotaCredito detalle = listItem.getValue();
				Textbox text = (Textbox) listItem.getChildren().get(9)
						.getChildren().get(0);
				String observacion = text.getValue();
				detalle.setObservacion(observacion);
				listaRechazada.add(detalle);
			}
		}
		cerrarMotivo();
		controlador.recibirParametros(estado, listaRechazada, dtbDesde,
				dtbHasta, ltbLista, cmbEstado, cmbAliado, cmbMarca, cmbZona,
				txtCodigo, servicioDetalleCredito, listaGeneral, certificadas, reporte);
	}

}
