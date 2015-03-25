package componente;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Space;

public abstract class Botonera extends Hbox {

	private static final long serialVersionUID = 7264458156730934676L;

	public Botonera() {
		super();
		Button btnGuardar = new Button();
		Button btnEliminar = new Button();
		Button btnLimpiar = new Button();
		Button btnBuscar = new Button();
		Button btnSalir = new Button();
		Button btnAdelante = new Button();
		Button btnAtras = new Button();
		Button btnEnviar = new Button();
		this.appendChild(btnGuardar);
		this.appendChild(btnBuscar);
		this.appendChild(btnLimpiar);
		this.appendChild(btnEliminar);
		this.appendChild(btnAtras);
		this.appendChild(btnAdelante);
		this.appendChild(btnSalir);
		Space espacio = new Space();
		espacio.setWidth("310px");
		this.appendChild(espacio);
		this.appendChild(btnEnviar);

		btnAdelante.setImage("/public/imagenes/botones/adelante.png");
		btnAtras.setImage("/public/imagenes/botones/atras.png");
		btnGuardar.setImage("/public/imagenes/botones/guardar.png");
		btnEliminar.setImage("/public/imagenes/botones/eliminar.png");
		btnLimpiar.setImage("/public/imagenes/botones/limpiar.png");
		btnBuscar.setImage("/public/imagenes/botones/buscar.png");
		btnSalir.setImage("/public/imagenes/botones/salir.png");
		btnEnviar.setImage("/public/imagenes/botones/guardar.png");

		btnAtras.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnEliminar.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnAdelante.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnGuardar.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnLimpiar.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnSalir.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnBuscar.setStyle("font-size: 12px ;width: 93px; height: 30px");
		btnEnviar.setStyle("font-size: 12px ;width: 93px; height: 30px");

		btnAdelante.setLabel("Siguiente");
		btnAtras.setLabel("Anterior");
		btnGuardar.setLabel("Guardar");
		btnEliminar.setLabel("Eliminar");
		btnLimpiar.setLabel("Limpiar");
		btnBuscar.setLabel("Buscar");
		btnSalir.setLabel("Salir");
		btnEnviar.setLabel("Enviar");

		btnAtras.setTooltiptext("Pestaña Anterior");
		btnAdelante.setTooltiptext("Pestaña Siguiente");
		btnGuardar.setTooltiptext("Guardar");
		btnEliminar.setTooltiptext("Eliminar");
		btnLimpiar.setTooltiptext("Limpiar");
		btnBuscar.setTooltiptext("Buscar");
		btnSalir.setTooltiptext("Salir");
		btnEnviar.setTooltiptext("Enviar Solicitud");

		btnAtras.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				atras();
			}
		});
		btnAdelante.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
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
		btnBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				buscar();
			}
		});
		btnSalir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				salir();
			}
		});

		btnEnviar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				enviar();
			}
		});
		
		btnBuscar.setAutodisable("self");
	}

	public abstract void enviar();

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

	public abstract void buscar();

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