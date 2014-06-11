package servicio.generico;

import interfacedao.transacciones.IPlanillaArteDAO;
import interfacedao.transacciones.IPlanillaCataDAO;
import interfacedao.transacciones.IPlanillaEventoDAO;
import interfacedao.transacciones.IPlanillaFachadaDAO;
import interfacedao.transacciones.IPlanillaPromocionDAO;
import interfacedao.transacciones.IPlanillaUniformeDAO;

import modelo.seguridad.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanillaGenerica")
public class SPlanillaGenerica {

	@Autowired
	private IPlanillaArteDAO planillaArteDAO;
	@Autowired
	private IPlanillaCataDAO planillaCataDAO;
	@Autowired
	private IPlanillaEventoDAO planillaEventoDAO;
	@Autowired
	private IPlanillaFachadaDAO planillaFachadaDAO;
	@Autowired
	private IPlanillaPromocionDAO planillaPromocionDAO;
	@Autowired
	private IPlanillaUniformeDAO planillaUniformeDAO;

	public int buscarCantidadPorUsuarioYEstado(Usuario usuario, String estado) {
		int contador = planillaArteDAO.findByUsuarioAndEstado(usuario, estado)
				.size()
				+ planillaCataDAO.findByUsuarioAndEstado(usuario, estado)
						.size()
				+ planillaEventoDAO.findByUsuarioAndEstado(usuario, estado)
						.size()
				+ planillaFachadaDAO.findByUsuarioAndEstado(usuario, estado)
						.size()
				+ planillaPromocionDAO.findByUsuarioAndEstado(usuario, estado)
						.size()
				+ planillaUniformeDAO.findByUsuarioAndEstado(usuario, estado)
						.size();
		return contador;
	}
}
