package nttdatacenters.spring;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nttdatacenters.spring.Entities.Client;
import nttdatacenters.spring.Service.ClientService;

@SpringBootApplication
public class NttdatacentersSpringT3CghCristianApplication implements CommandLineRunner {

	@Autowired
	ClientService clientService;

	public static void main(String[] args) {
		SpringApplication.run(NttdatacentersSpringT3CghCristianApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//CREAMOS UN OBJETO CLIENTE
		Client cristian = Client.builder()
						.name("Cristian")
						.lastname("Gonzalez")
						.dni("47343243C")
						.dateBirth(LocalDate.of(1991, 10, 30))
						.build();
		
		//CREAMOS OTRO OBJETO CLIENTE
		Client mcarmen = Client.builder()
				.name("Mari Carmen")
				.lastname("Lopez")
				.dni("12121212C")
				.dateBirth(LocalDate.of(1980, 10, 30))
				.build();
		
		//CREAMOS OTRO OBJETO CLIENTE CON NOMBRE IDENTICO SALVO DNI (PARA FILTRAR LISTA POR NOMBRE)
		Client cristian1 = Client.builder()
						.name("Cristian")
						.lastname("Gonzalez")
						.dni("42343243C")
						.dateBirth(LocalDate.of(1991, 10, 30))
						.build();
		
		//CREAMOS OTRO OBJETO CLIENTE MENOR DE EDAD
				Client mcarmen1 = Client.builder()
						.name("Ana")
						.lastname("Lopez")
						.dni("12131212C")
						.dateBirth(LocalDate.of(2020, 10, 30))
						.build();
		
		//CREAMOS UN OBJETO CLIENTE IGUAL QUE EL PRIMERO, NO SE GUARDARÁ POR TENER EL MISMO DNI
		Client cristian2 = Client.builder()
				.name("Cristian")
				.lastname("Gonzalez")
				.dni("47343243C")
				.dateBirth(LocalDate.of(1991, 10, 30))
				.build();
		
		//CREAMOS UN OBJETO CLIENTE CON DNI LONGITUD INCORRECTA (SE PODRÍA USAR REGEX Y MIL COSAS...
		Client cristian3 = Client.builder()
				.name("Cristian")
				.lastname("Gonzalez")
				.dni("47343243")
				.dateBirth(LocalDate.of(1991, 10, 30))
				.build();
		
		
		
		//SE GUARDA
		clientService.save(cristian);
		
		//SE GUARDA
		clientService.save(mcarmen);
		
		//SE GUARDA
		clientService.save(mcarmen1);
		
		//SE GUARDA
		clientService.save(cristian1);
		
		//DNI ENCONTRADO --> NO SE GUARDA
		clientService.save(cristian2);
		
		//DNI FORMATO INCORRECTO --> NO SE GUARDA
		clientService.save(cristian3);
		
		//--------------------------- FORMAS DE SACAR PARTIDO A UN FINDALL ----------------------------//
		
		//SACAMOS LISTA DE CLIENTES
		System.out.println("\nLista de clientes");
		clientService.findAll().stream()
								.forEach(System.out::println);
		
		
		//SACAMOS LISTA DE CLIENTES POR NOMBRE
		System.out.println("\nLista de clientes obteniendo nombre");
		clientService.findAll().stream()
						.forEach(n-> System.out.println(n.getName()));

		//SACAMOS LISTA DE CLIENTES POR NOMBRE (ÚNICOS) USANDO CONSULTA
		System.out.println("\nLista de clientes, quedarse con los que tengan el nombre que pasamos por parámetro");
		clientService.findAll().stream()
							.filter(n->n.getName().equals("Cristian"))
							.forEach(n-> System.out.println(n.getName()));

		
		//CONTAMOS LISTA DE CLIENTES
		System.out.println("\nLista de clientes, contamos el total de clientes registrados");
		System.out.println(clientService.findAll().stream().count());
		
		//CONTAMOS LISTA DE CLIENTES AÑO DE NACIMIENTO MENOR QUE EL PASADO POR PARÁMETRO
		System.out.println("\nObtener lista de clientes que hayan nacido antes del 2020 y contamos");
		System.out.println(clientService.findAll().stream().filter(n-> n.getDateBirth().getYear()<2020).count());
		
		//SACAMOS LISTA DE CLIENTES QUE COINCIDAN CON NOMBRE Y APELLIDOS SIN USAR CONSULTA
		System.out.println("\nObtener lista de clientes con nombres únicos");
		clientService.findAll().stream().map(n-> n.getName()).distinct().forEach(System.out::println);
		
		
		//----------------------------- FIND BY NAME AND LASTNAME ----------------------------------//
		
		System.out.println("\nBusqueda por nombre y apellido");
		List<Client> client = clientService.findByNameANDLastname("Cristian", "Gonzalez");
		
		if(client.size()!=0)  {
			client.stream().forEach(System.out::println);
		}else {
			System.out.println("Sin clientes por los parámetros pasados");
		}
		
		
		//----------------------------- FIND BY ID POR TIPO LONG----------------------------------//
		
		System.out.println("\nBusqueda por nombre y apellido");
		Optional<Client> clientOptional = clientService.findById(1L);
		
		if(clientOptional.isPresent()) {
			clientOptional.stream().forEach(System.out::println);
		} else {
			System.out.println("Sin cliente por ese ID");
		}
		
		//---------------------------- UPDATE BY ID ------------------------------------------//
		System.out.println("\nActualizo cliente existente con nombre Tellez y muestro lista");
		Optional<Client> clientOptionalUpdate = clientService.findById(1L);
		
		cristian.setName("Tellez");
		clientService.update(cristian);
		
		clientService.findAll().forEach(System.out::println);
		
		//--------------------------- DELETE BY ID -----------------------------------------//
		System.out.println("\nEliminar cliente por ID (Se elimina el id 1, Tellez) y muestro lista");
		
		clientService.deleteById(1L);
		
		clientService.findAll().forEach(System.out::println);
		
		//--------------------------- DELETE ALL -----------------------------------------//
		System.out.println("\nEliminar todos");
				
		clientService.deleteAll();
				
		
	}

}
