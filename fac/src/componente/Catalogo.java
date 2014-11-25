package componente;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public abstract class Catalogo<Clase> extends Window {

	private static final long serialVersionUID = 1L;
	Listbox lsbCatalogo;
	Textbox txtSY;
	Label labelSYNombre;
	Textbox txtRT;
	Label labelRTNombre;
	Label labelBuscado;

	public Catalogo(final Component cGenerico, String titulo,
			List<Clase> lista, boolean emergente, String... campos) {
		super("", "2", false);
		this.setId("cmpCatalogo" + titulo);
		this.setStyle("background-header:#661313; background: #f4f2f2");
		crearLista(lista, campos, emergente, titulo);
		lsbCatalogo.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Events.postEvent(cGenerico, new Event("onSeleccion"));
					}
				});
	}

	public void crearLista(List<Clase> lista, String[] campos,
			final boolean emergente, String titulo) {
		Hbox box = new Hbox();
		final Separator separador1 = new Separator();
		final Separator separador2 = new Separator();
		lsbCatalogo = new Listbox();
		lsbCatalogo.setMold("paging");
		lsbCatalogo.setPagingPosition("top");
		lsbCatalogo.setRows(10);
		lsbCatalogo.setPageSize(20);
		final Auxhead cabecera = new Auxhead();
		Listhead lhdEncabezado = new Listhead();
		lhdEncabezado.setSizable(true);
		for (int i = 0; i < campos.length; i++) {
			final Textbox cajaTexto = new Textbox();
			cajaTexto.setContext(campos[i]);
			cajaTexto.setHflex("1");
			cajaTexto.setWidth("auto");
			cajaTexto.addEventListener(Events.ON_OK,
					new EventListener<KeyEvent>() {
						@Override
						public void onEvent(KeyEvent e) throws Exception {
							List<String> valores = new ArrayList<>();
							for (int i = 0; i < cabecera.getChildren().size(); i++) {
								Auxheader cabeceraFila = (Auxheader) cabecera
										.getChildren().get(i);
								Textbox te = (Textbox) cabeceraFila
										.getChildren().get(0);
								valores.add(te.getValue());
							}
							;
							String valor = cajaTexto.getValue();
							List<Clase> listaNueva = buscar(valores);
							lsbCatalogo.setModel(new ListModelList<Clase>(
									listaNueva));
							if (!emergente) {
								lsbCatalogo.setMultiple(false);
								lsbCatalogo.setCheckmark(false);
								lsbCatalogo.setMultiple(true);
								lsbCatalogo.setCheckmark(true);
							}
							cajaTexto.setValue(valor);
						}
					});
			cajaTexto.setPlaceholder("Buscar....");
			cajaTexto
					.setTooltiptext("Presione Enter para Filtrar la Informacion");
			Auxheader cabeceraFila = new Auxheader();
			cabeceraFila.appendChild(cajaTexto);
			cabecera.appendChild(cabeceraFila);
			Listheader listheader = new Listheader(campos[i]);
			listheader.setHflex("min");
			lhdEncabezado.appendChild(listheader);
		}
		lsbCatalogo.appendChild(cabecera);
		
		if (!emergente)
			lsbCatalogo.setHflex("1");
		else
			lsbCatalogo.setWidth("100%");
		lsbCatalogo.appendChild(lhdEncabezado);
		// lsbCatalogo.setSizedByContent(true);
		lsbCatalogo.setSpan("true");
		cabecera.setVisible(true);
		lhdEncabezado.setVisible(true);
		lsbCatalogo.setModel(new ListModelList<Clase>(lista));
		lsbCatalogo.setItemRenderer(new ListitemRenderer<Clase>() {

			@Override
			public void render(Listitem fila, Clase objeto, int arg2)
					throws Exception {
				fila.setValue(objeto);
				String[] registros = crearRegistros(objeto);
				for (int i = 0; i < registros.length; i++) {
					Listcell celda = new Listcell(registros[i]);
					celda.setParent(fila);
				}
			}
		});

		if (emergente) {
			this.setClosable(true);
			this.setWidth("70em");
			this.setTitle(titulo);
			this.appendChild(separador2);
			this.appendChild(lsbCatalogo);
			lsbCatalogo.setMultiple(true);
			lsbCatalogo.setCheckmark(true);
			lsbCatalogo.setMultiple(false);
			lsbCatalogo.setCheckmark(false);
		} else {
			this.setHflex("1");
			lsbCatalogo.setHeight("450px");
			this.setClosable(false);
			this.appendChild(lsbCatalogo);
			lsbCatalogo.setMultiple(false);
			lsbCatalogo.setCheckmark(false);
			lsbCatalogo.setMultiple(true);
			lsbCatalogo.setCheckmark(true);
		}
	}

	/**
	 * Metodo que permite llamar un servicio dependiendo el controlador que
	 * busque, es decir que haga un filtro dentro del catalogo, ayudando asi al
	 * usuario a encontrar el registro buscado con mayor facilidad
	 */
	protected abstract List<Clase> buscar(List<String> valores);

	/**
	 * Metodo que permite por cada controlador indicar cuales son los registros
	 * que quiere mostrar en su catalogo, formando una matriz de String
	 */
	protected abstract String[] crearRegistros(Clase objeto);

	public Clase objetoSeleccionadoDelCatalogo() {
		return lsbCatalogo.getSelectedItem().getValue();
	}

	public Listbox getListbox() {
		return lsbCatalogo;
	}

	public void actualizarLista(List<Clase> lista) {
		lsbCatalogo.setModel(new ListModelList<Clase>(lista));
		lsbCatalogo.setMultiple(false);
		lsbCatalogo.setCheckmark(false);
		lsbCatalogo.setMultiple(true);
		lsbCatalogo.setCheckmark(true);
	}

	public List<Clase> obtenerSeleccionados() {
		List<Clase> valores = new ArrayList<Clase>();
		boolean entro = false;
		if (lsbCatalogo.getItemCount() != 0) {
			final List<Listitem> list1 = lsbCatalogo.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected()) {
					Clase clase = list1.get(i).getValue();
					entro = true;
					valores.add(clase);
				}
			}
			if (!entro) {
				valores.clear();
				return valores;
			}
			return valores;
		} else
			return null;
	}

	/**
	 * Metodo que permite limpiar los items seleccionados en el catalogo
	 */
	public void limpiarSeleccion() {

		lsbCatalogo.clearSelection();

	}

}