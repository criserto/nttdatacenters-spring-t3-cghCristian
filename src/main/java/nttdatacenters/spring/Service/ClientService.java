package nttdatacenters.spring.Service;


import java.util.List;
import java.util.Optional;

import nttdatacenters.spring.Entities.Client;


public interface ClientService {
	
	public void save(Client client);
	
	public Optional<Client> findById(Long id);
	
	public List<Client> findAll();
	
	public List<Client> findByNameANDLastname(String name, String lastname);
	
	public void update(Client client);
	
	public void deleteById(Long id);
	
	public void deleteAll();
	
	
	
}
