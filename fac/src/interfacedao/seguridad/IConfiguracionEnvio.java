package interfacedao.seguridad;

import modelo.seguridad.ConfiguracionEnvio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfiguracionEnvio extends JpaRepository<ConfiguracionEnvio, Long>{

}
