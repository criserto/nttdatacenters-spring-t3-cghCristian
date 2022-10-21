package nttdatacenters.spring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nttdatacenters.spring.Entities.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	List<Client> findByNameAndLastname(String name, String lastname);
	
	Boolean existsByDni(String dni);

}
