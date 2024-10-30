package com.example.task.reactiveOperation;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Operation {

    public static List<Person> createList(){
        List<Person> people =  new ArrayList<>();
        people.add(new Person("Juan", "Perez", "123456789", 30, "Aries"));
        people.add(new Person("María", "Gómez", "987654321", 25, "Virgo"));
        people.add(new Person("Carlos", "Martínez", "555444333", 10, "Capricornio"));
        people.add(new Person("Laura", "Rodríguez", "111222333", 15, "Tauro"));
        people.add(new Person("Pedro", "Sánchez", "999888777", 28, "Leo"));
        people.add(new Person("Ana", "Fernández", "666777888", 22, "Acuario"));
        people.add(new Person("David", "López", "333222111", 25, "Cáncer"));
        people.add(new Person("Sofía", "Díaz", "777666555", 22, "Géminis"));
        people.add(new Person("Javier", "Hernández", "888999000", 27, "Escorpio"));
        people.add(new Person("Elena", "García", "112233445", 13, "Libra"));
        people.add(new Person("Pablo", "Muñoz", "554433221", 18, "Piscis"));
        people.add(new Person("Rosa", "Jiménez", "998877665", 29, "Sagitario"));

        return people;
    }

    //1. Crear un Flux a partir de la lista de personas.
    // Crear un Flux a partir de la lista de personas. permite manejar la lista de forma reactiva
    Flux<Person> createFluxPerson() {
        return Flux.fromIterable(createList());
    }

    //2 y 3. Filtrar las personas mayores de 30 años utilizando filter().
    //switchIfEmpty proporciona un flujo alterno cuando el original esta vacio ... flux.just
    public void filterOver30(){
        createFluxPerson()
            .filter(person -> person.getEdad()>30)
            .map(person -> person.getNombre() + " " + person.getApellido() + "  Edad: " + person.getEdad())
            .switchIfEmpty(Mono.error(new RuntimeException("No hay personas mayores a 30")))
            .subscribe(System.out::println,
                    error -> System.err.println(error.getMessage())
            );
    }

    //4.Crear un Mono con la primera persona de la lista.
    Mono<Person> getFirstPerson(){
        return createFluxPerson().next();
    }

    //5.Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
    public void showFirstPerson(){
        getFirstPerson()
            .flatMap(person -> Mono.just(person.getNombre() + " " + person.getApellido()))
            .subscribe(System.out::println);
    }

    //6. Agrupar por signo
    /*
    groupBy -> Agrupa las personas por signo
    flatMap -> se utiliza para transformar cada GroupedFlux en un nuevo flujo.
    groupedFlux -> Dentro de flatMap, groupedFlux representa un grupo de personas con el mismo signo.
    collectList() -> Recoge todas las personas de un grupo específico en una lista.
    doOnNext -> Imprime el signo del zodiaco y el número de personas en ese grupo.
    subscribe -> Se suscribe al flujo para que se ejecute y procese la información.
	*/
    public void groupBySign(){
        createFluxPerson()
            .groupBy(Person::getSigno)//Agrupa por signo
            .flatMap(groupedFlux -> groupedFlux
                    .collectList()//Convierte cada signo en una lista
                    .doOnNext(personas -> System.out.println(groupedFlux.key() + " : " + personas.size())))
            .subscribe();
    }

    //7. Obtener personas por edad
    // onErrorDropped
    public Flux<Person> getPeopleByAge (int age){
        return createFluxPerson()
                .filter(person -> person.getEdad() == age)
                .switchIfEmpty(Mono.error(new RuntimeException("No hay personas con la edad " + age)));
    }

    //8.Obtener persona por signo
    public Flux<Person> getPeopleBysign(String Sign){
        return createFluxPerson()
                .filter(person -> person.getSigno().equals(Sign))
                .switchIfEmpty(Mono.error(new RuntimeException("No hay personas con el signo " + Sign)));
    }

    //9. obtener persona por numero de telefono, devolver un mono con el numero o vacio si no lo encuentra
    public Mono<Person> getPeopleByPhoneNumber(String phoneNumber){
        return createFluxPerson()
                .filter(person -> person.getTelefono().equals(phoneNumber))
                .singleOrEmpty(); //devuelve el mono con los resultados o vacio
    }

    //10. Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas.
    // Devolver un Mono con la persona agregada. (Hacer uso de peek)
    public Mono<Person> addPerson(Person person){
        List<Person> people = createList();
        boolean exists = people.stream().anyMatch(p -> p.getNombre().equals(person.getNombre()));
        if (exists) {
            return Mono.error(new RuntimeException("La persona ya existe en la lista."));
        }
        people.add(person);
        people.forEach(System.out::println);
        return Mono.just(person);
    }


    //11.Eliminar persona
    public Mono<Person> deletePersona(Person person){
        List<Person> people = createList();
        return Mono.create(newPeopleList -> {
            boolean removed = people.removeIf(p->p.getNombre().equals(person.getNombre()));
            people.forEach(System.out::println);
            if(removed){
                newPeopleList.success(person);
            }else{
                newPeopleList.error(new RuntimeException("Persona no encontrada: " + person));
            }
        });
    }
}
