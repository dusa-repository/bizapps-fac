package servicio.transacciones.notas;

import java.util.ArrayList;
import java.util.List;

import interfacedao.maestros.IMarcaDAO;
import interfacedao.transacciones.notas.IConfiguracionMarcaDAO;
import modelo.maestros.Marca;
import modelo.pk.ConfiguracionMarcaId;
import modelo.seguridad.Configuracion;
import modelo.transacciones.notas.ConfiguracionMarca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SConfiguracionMarca")
public class SConfiguracionMarca {

	@Autowired
	private IConfiguracionMarcaDAO configuracionMarcaDAO;
	@Autowired
	private IMarcaDAO marcaDAO;
	private List<String> orden = new ArrayList<String>();
	private Sort o;

	public List<ConfiguracionMarca> buscar(String valor) {
		orden.clear();
		orden.add("id.marcaIdMarca");
		o = new Sort(Sort.Direction.ASC, orden);
		List<ConfiguracionMarca> lista = configuracionMarcaDAO.findByIdTipo(
				valor, o);
		List<String> ids = new ArrayList<String>();
		List<Marca> marcas = new ArrayList<Marca>();
		for (int i = 0; i < lista.size(); i++) {
			ids.add(lista.get(i).getId().getMarca().getIdMarca());
		}
		orden.clear();
		orden.add("idMarca");
		o = new Sort(Sort.Direction.ASC, orden);
		if (!ids.isEmpty())
			marcas = marcaDAO.findByIdMarcaNotIn(ids, o);
		else
			marcas = marcaDAO.findAll(o);
		for (int m = 0; m < marcas.size(); m++) {
			ConfiguracionMarcaId clave = new ConfiguracionMarcaId();
			clave.setMarca(marcas.get(m));
			clave.setTipo(valor);
			ConfiguracionMarca objeto = new ConfiguracionMarca(clave, 0, 0.0,
					0.0, 0.0, null, null, null);
			lista.add(objeto);
		}
		return lista;
	}

	public void guardarVarias(List<ConfiguracionMarca> listaDetalle) {
		configuracionMarcaDAO.save(listaDetalle);
	}

	public List<ConfiguracionMarca> buscarTodosParaPoteYTipo(String valor) {
		Double cero = (double) 0;
		orden.clear();
		orden.add("id.marcaIdMarca");
		o = new Sort(Sort.Direction.ASC, orden);
		return configuracionMarcaDAO.findByIdTipoAndCostoNot(valor, cero, o);
	}

	public List<ConfiguracionMarca> buscarTodosParaDistribucionYTipo(
			String valor) {
		orden.clear();
		orden.add("id.marcaIdMarca");
		o = new Sort(Sort.Direction.ASC, orden);
		return configuracionMarcaDAO.findByIdTipo(valor, o);
	}

	public ConfiguracionMarca buscarClave(ConfiguracionMarcaId claveConfig) {
		return configuracionMarcaDAO.findOne(claveConfig);
	}
}
