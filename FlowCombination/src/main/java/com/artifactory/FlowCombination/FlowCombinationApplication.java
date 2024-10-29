package com.artifactory.FlowCombination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
public class FlowCombinationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowCombinationApplication.class, args);

		String[] direccion1 = {"cl 200 # 56 -89"};
		String[] direccion2 = {"cl 52 # 58 -8"};
		String[] direccion3 = {"cl 256 # 87 -78"};
		String[] direccion4 = {"cl 100 # 89 - 49"};
		String[] direccion5 = {"cl 56 # 32 -2"};
		String[] direccion6 = {"cl 6 # 9 -3"};

		Persona persona1 = new Persona("Juan", "Bogota", direccion1, 5000,"Active");
		Persona persona2 = new Persona("Pablo", "Cali", direccion2, 900, "Active");
		Persona persona3 = new Persona("Carlos", "Medellin", direccion3, 200, "Active");
		Persona persona4 = new Persona("Felipe", "Bogota", direccion4, 8000, "Close");
		Persona persona5 = new Persona("Rodrigo", "Pasto", direccion5, 300, "Close");
		Persona persona6 = new Persona("Luis", "Cartagena", direccion6, 100, "Active");

		List<Persona> personaList  = new ArrayList<Persona>();
		personaList.add(persona1);
		personaList.add(persona2);
		personaList.add(persona3);
		personaList.add(persona4);
		personaList.add(persona5);
		personaList.add(persona6);

		Flux<Persona> flux = Flux.fromIterable(personaList);
/*
	//#1
		flux
				.map(p->p.getNombre().toLowerCase())
				.flatMap(n -> Flux.fromArray(n.split("")))
				.count()
				.doOnNext(System.out::println)
				.subscribe();

	//#2
		flux.map(persona -> persona.getCiudad())
				.filter(p->!p.contains("B"))
				.distinct()
				.count()
				.subscribe(System.out::println);




		//#3

		flux.take(5)
			.map(per-> {
				return per.getSaldo() +"<->" + per.getNombre();
			}).sort()
		.subscribe(System.out::println);


		//#4

        flux.map(per -> per.getSaldo())
                .filter(p->p>1000).toStream()
				.map(p->p.intValue() * 4688)
                .reduce(Integer::sum)
                .stream().forEach(System.out::println);
*/
        //#5
        flux
                .flatMap(p->Flux.fromArray(p.getDirecciones())
                        .map(r -> p.getEstado()+"<->" + p.getNombre()))
                .subscribe(resultado->System.out.println(resultado));



	}


}
