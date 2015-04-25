package servicio.seguridad;

import interfacedao.seguridad.IConfiguracionEnvio;
import modelo.seguridad.ConfiguracionEnvio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConfiguracionEnvio")
public class SConfiguracionEnvio {

	@Autowired
	private IConfiguracionEnvio configuracionDAO;

	public ConfiguracionEnvio buscar(Long i) {
		return configuracionDAO.findOne(i);
	}

	public void guardar(ConfiguracionEnvio objeto) {
		configuracionDAO.saveAndFlush(objeto);
	}
}
