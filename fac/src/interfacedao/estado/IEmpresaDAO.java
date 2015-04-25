package interfacedao.estado;

import java.util.List;

import modelo.maestros.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpresaDAO extends JpaRepository<Empresa, Long>{

	List<Empresa> findByNombre(String value);

}
