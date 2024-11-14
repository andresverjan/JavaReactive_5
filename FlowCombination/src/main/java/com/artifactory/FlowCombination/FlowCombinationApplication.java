package com.artifactory.FlowCombination;

import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
public class FlowCombinationApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpringApplication.run(FlowCombinationApplication.class, args);

        String[] direccion1 = {"cl 200 # 56 -89"};
        String[] direccion2 = {"cl 52 # 58 -8"};
        String[] direccion3 = {"cl 256 # 87 -78"};
        String[] direccion4 = {"cl 100 # 89 - 49"};
        String[] direccion5 = {"cl 56 # 32 -2"};
        String[] direccion6 = {"cl 6 # 9 -3"};

        Persona persona1 = new Persona("Juan", "Bogota", direccion1, 5000, "Active");
        Persona persona2 = new Persona("Pablo", "Cali", direccion2, 900, "Active");
        Persona persona3 = new Persona("Carlos", "Medellin", direccion3, 200, "Active");
        Persona persona4 = new Persona("Felipe", "Bogota", direccion4, 8000, "Close");
        Persona persona5 = new Persona("Rodrigo", "Pasto", direccion5, -300, "Close");
        Persona persona6 = new Persona("Luis", "Cartagena", direccion6, 100, "Active");

        List<Persona> personaList = new ArrayList<Persona>();
        personaList.add(persona1);
        personaList.add(persona2);
        personaList.add(persona3);
        personaList.add(persona4);
        personaList.add(persona5);
        personaList.add(persona6);

  /*      Flux<Persona> flux = Flux.fromIterable(personaList);

        //1#1
        flux
                .map(p -> p.getNombre().toLowerCase())
                .flatMap(n -> Flux.fromArray(n.split("")))
                .count()
                .doOnNext(System.out::println)
                .subscribe();

        //1#2
        flux.map(persona -> persona.getCiudad())
                .filter(p -> !p.contains("B"))
                .distinct()
                .count()
                .subscribe(System.out::println);


        //1#3

        flux.take(5)
                .map(per -> {
                    return per.getSaldo() + "<->" + per.getNombre();
                }).sort()
                .subscribe(System.out::println);


        //1#4

        flux.map(per -> per.getSaldo())
                .filter(p -> p > 1000).toStream()
                .map(p -> p.intValue() * 4688)
                .reduce(Integer::sum)
                .stream().forEach(System.out::println);

        //1#5
        flux
                .flatMap(p -> Flux.fromArray(p.getDirecciones())
                        .map(r -> p.getEstado() + "<->" + p.getNombre()))
                .subscribe(resultado -> System.out.println(resultado));

        //2#1
        flux.map(i -> {
                    if (i.getSaldo() < 0) {

                        throw new RuntimeException("Error Saldo negativo :");
                    }
                    return "Persona :" + i.getNombre() + " saldo :" + i.getSaldo();
                })
                .onErrorReturn("Saldo predeterminado 00000")
                .subscribe(System.out::println);

        //2#2
        flux.map(p -> {
                    if (p.getSaldo() < 0) {
                        p.setSaldo(0);
                        throw new RuntimeException("Error :");
                    }
                    return "Saldo :" + p.getSaldo();
                })
                .onErrorResume(error -> {

                    return flux.map(i -> i.getNombre() + " : Saldo 9999");
                })
                .subscribe(System.out::println);


        //2#3
        flux.flatMap(p -> {
                    if (p.getSaldo() < 0) {

                        throw new RuntimeException("Error : Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                    }
                    return flux.just("Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                })
                .doOnError(e -> {
                    e.getMessage();
                })
                .subscribe(System.out::println,
                        e -> System.err.print(e));


        //2#4

        flux.flatMap(p -> {
                    if (p.getSaldo() < 0) {

                        throw new RuntimeException("Error : Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                    }
                    return flux.just("Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                })
                .onErrorReturn("Saldo predeterminado 00000")
                .onErrorResume(error -> {

                    return flux.map(i -> i.getNombre() + " : Saldo 9999");
                })
                .doOnError(e -> System.err.print(e.getMessage()))
                .subscribe(System.out::println,
                        e -> System.err.print(e));

        //2#5
        flux.flatMap(p -> {
                    if (p.getSaldo() < 0) {

                        throw new RuntimeException("Error : Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                    }
                    return flux.just("Nombre " + p.getNombre() + " Ciudad " + p.getCiudad() + " Saldo :" + p.getSaldo());
                })
                .onErrorContinue((e, p) -> System.err.print(e))
                .subscribe(System.out::println,
                        e -> System.err.print(e));

        Flux<Integer> flujoInPares = Flux.just(1, 3, 5, 7, 9);
        Flux<Integer> flujoPares = Flux.just(2, 4, 6, 8, 10);

        //3#1
        Flux<Integer> merged = Flux.merge(flujoInPares, flujoPares);
        merged.subscribe(System.out::println);

        //3#2
        Flux<Tuple2<Integer, Integer>> zip = Flux.zip(flujoInPares, flujoPares);
        zip.subscribe(tuple -> System.out.println(tuple.getT1() + " sigue " + tuple.getT2()));

        //3#3

        Flux<String> combined = Flux.combineLatest(flujoInPares, flujoPares,
                (first, last) -> first + " " + last);
        combined.subscribe(System.out::println);

        //3#4
        Flux<String> firstNames = Flux.just("John", "Jane");
        Flux<String> lastNames = Flux.just("Doe", "Smith");

        Flux<String> concatenar = (Flux<String>) Flux.concat(firstNames, lastNames);
        concatenar.subscribe(System.out::println);

        //3#5

        Flux<String> names = Flux.just("Alice", "Bob", "Charlie");

        names.filter(name -> name.startsWith("D"))
                .switchIfEmpty(Flux.error(new RuntimeException("No names available")))
                .subscribe(System.out::println);
*/

        Flux<String> ciudades = Flux.just("Medellin", "Bello");Flux<String> barrios = Flux.just("Comunidad de Medellin", "Robledo?");Flux.combineLatest(ciudades, barrios, (ciudad, barrio) -> ciudad + ", " + barrio).subscribe(combinado -> System.out.println("Combinación: " + combinado));



    }

}
