package componente;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;

public abstract class Botonera extends Hbox {
	
	private static final long serialVersionUID = 7264458156730934676L;

	public Botonera() {
		super();
		Button btnGuardar = new Button();
		Button btnEliminar = new Button();
		Button btnLimpiar = new Button();
		Button btnReporte = new Button();
		Button btnSalir = new Button();
		Button btnAdelante = new Button();
		Button btnAtras = new Button();

		this.appendChild(btnGuardar);
		this.appendChild(btnLimpiar);
		this.appendChild(btnEliminar);
		this.appendChild(btnAdelante);
		this.appendChild(btnAtras);
		this.appendChild(btnReporte);
		this.appendChild(btnSalir);

		btnAdelante.setSrc("/public/imagenes/botones/adelante.png");
		btnAtras.setSrc("/public/imagenes/botones/atras.png");
		btnGuardar.setSrc("/public/imagenes/botones/guardar.png");
		btnEliminar.setSrc("/public/imagenes/botones/eliminar.png");
		btnLimpiar.setSrc("/public/imagenes/botones/limpiar.png");
		btnReporte.setSrc("/public/imagenes/botones/reporte.png");
		btnSalir.setSrc("/public/imagenes/botones/salir.png");
	
		btnAtras.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnEliminar.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnAdelante.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnGuardar.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnLimpiar.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnSalir.setStyle("font-size: 11px ;width: 87px; height: 25px");
		btnReporte.setStyle("font-size: 11px ;width: 87px; height: 25px");
		
		btnAdelante.setLabel("Siguiente");
		btnAtras.setLabel("Anterior");
		btnGuardar.setLabel("Guardar");
		btnEliminar.setLabel("Eliminar");
		btnLimpiar.setLabel("Limpiar");
		btnReporte.setLabel("Reporte");
		btnSalir.setLabel("Salir");

		btnAtras.setTooltiptext("Pestaña Anterior");
		btnAdelante.setTooltiptext("Pestaña Siguiente");
		btnGuardar.setTooltiptext("Guardar");
		btnEliminar.setTooltiptext("Eliminar");
		btnLimpiar.setTooltiptext("Limpiar");
		btnReporte.setTooltiptext("Reporte");
		btnSalir.setTooltiptext("Salir");


		btnAtras.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						atras();
					}
				});
		btnAdelante.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				adelante();
			}

		});
		btnGuardar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						guardar();
					}

				});

		btnEliminar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						eliminar();
					}
				});
		btnLimpiar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						limpiar();
					}
				});
		btnReporte.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						reporte();
					}
				});
		btnSalir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				salir();
			}
		});
	}

	/**
	 * Metodo que guarda un registro nuevo si no a sido guardado con
	 * anterioridad, de ser asÃ­ se modifica aquellos datos que el usuario
	 * cambie
	 */
	public abstract void atras();

	public abstract void adelante();

	public abstract void guardar();

	/**
	 * Metodo que limpia todos los campos para darle la libertad al usuario de
	 * aÃ±adir un nuevo registro o seleccionar uno ya existente
	 */
	public abstract void limpiar();

	public abstract void reporte();

	/**
	 * Metodo que permite cerrar el div que se genera al entrar a la vista
	 */
	public abstract void salir();

	/**
	 * Metodo que permite la eliminacion de un registro siempre y cuando no este
	 * asociado a otra entidad
	 */
	public abstract void eliminar();
}