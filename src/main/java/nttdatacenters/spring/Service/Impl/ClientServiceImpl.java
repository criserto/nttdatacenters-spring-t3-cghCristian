package nttdatacenters.spring.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdatacenters.spring.Entities.Client;
import nttdatacenters.spring.Repository.ClientRepository;
import nttdatacenters.spring.Service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
		
    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Override
	public void save(Client client) {
	
		if (clientRepository.existsByDni(client.getDni())) {
			log.warn("[ClientServiceImpl - save] DNI ya estaba registrado");
		} else {
			
			if(client.getDni().length()!=9) {
				log.info("[ClientServiceImpl - save] Formato DNI incorrecto " + client);
			} else {
				clientRepository.save(client);
				log.info("[ClientServiceImpl - save] Cliente registrado " + client);
			}

		}

	}

	@Override
	public Optional<Client> findById(Long id) {

		log.info("[ClientServiceImpl - findById] Obteniendo cliente por Id");

		return clientRepository.findById(id);
	}

	
	@Override
	public List<Client> findAll() {
		log.info("[ClientServiceImpl - findAll] Obteniendo lista de clientes");

		return clientRepository.findAll();
	}

	@Override
	public List<Client> findByNameANDLastname(String name, String lastname) {
		
		log.info("[ClientServiceImpl - findByNameANDLastname] Localizando cliente por  " + name + " y " + lastname);

		return clientRepository.findByNameAndLastname(name, lastname);
		
	}

	@Override
	public void update(Client client) {
		log.info("[ClientServiceImpl - update] Entrando en update");
		
		if(client.getId()==null) {
			log.info("[ClientServiceImpl - update] - Intentando actualizar un cliente que no existe");
		}
		
		if(!clientRepository.existsById(client.getId())) {
			log.info("[ClientServiceImpl - update] Intentando actualizar un cliente con un ID que no existe");
		} else {
			clientRepository.save(client);
			log.info("[ClientServiceImpl - update] Cliente actualizado");

		}
		
		

	}

	@Override
	public void deleteById(Long id) {
		log.info("[ClientServiceImpl - deleteById] Eliminar un cliente por ID");
		if(!clientRepository.existsById(id)) {
			log.info("[ClientServiceImpl - deleteById] Intentando eliminar un cliente con un ID que no existe");
		} else {
			clientRepository.deleteById(id);
			log.info("[ClientServiceImpl - deleteById] Cliente eliminado correctamente");
		}
	}

	@Override
	public void deleteAll() {
		log.info("[ClientServiceImpl - deleteAll] Entrando en método");
		
		clientRepository.deleteAll();
		
		log.info("[ClientServiceImpl - deleteAll] Todos los clientes eliminados todos");

	}



}
