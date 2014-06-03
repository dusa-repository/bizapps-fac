package servicio.maestros;

import interfacedao.maestros.ISkuDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SSku")
public class SSku {

	@Autowired
	private ISkuDAO skuDAO;
}
