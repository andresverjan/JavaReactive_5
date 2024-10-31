package com.example.task.flowManipulation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.example.task.flowManipulation.Workshop.getPeople;

public class ManipulationFlow {

    public List<Person> createPeopleList() {
        return getPeople();
    }

    public  Flux<Person> createFluxPerson() {
        return Flux.fromIterable(createPeopleList());
    }

    public Flux<Transaction> createFluxTransaction() {
        return Flux.just(
                new Transaction("transferecia", 1550),
                new Transaction("compra fisica",1000),
                new Transaction("Compra en linea",890),
                new Transaction("transferecia", 139),
                new Transaction("compra fisica",6980),
                new Transaction("Compra en linea",486)
        );
    }

    public Mono<String> getStatusAccountByAddress(String address) {
        var random = new Random();
        var status = random.nextBoolean() ? "Activa" : "Inactiva";

        return Mono.just(status);
    }

    /**
     * 1. Manipulación de Flujos de Datos
     */

    //1.  Uso de map y flatMap
    // Usa map para convertir sus nombres a mayúsculas
    //flatMap para dividir cada nombre en letras individuales.
    //imprime la cantidad total de letras procesadas.
    public void toUpperCaseName() {
        createFluxPerson()
                .map(p -> p.getName().toUpperCase())//extrae y convierte los nombre en mayuscula
                .flatMap(name -> Flux.fromArray(name.split(""))) //divide cada letra de cada nombre
                .count()//cuenta el total de letras procesadas
                .subscribe(numLettters -> System.out.println("Cantidad total de letras procesadas: " + numLettters));
    }

    // 2: Uso de filter y distinct
    // Crea un flujo que contenga las ciudades donde viven los clientes.
    // Filtra las ciudades que empiezan con la letra "B" y elimina duplicados.
    // cuenta cuántas ciudades únicas se encontraron.
    public void filterCities(){
        createFluxPerson()
                .map(Person::getCity)//saca la ciudad por persona
                .filter(city -> city.startsWith("B"))
                .distinct()//elimina ciudades repetidos
                .count()
                .subscribe(uniqueCities -> System.out.println("Cantidad de ciudades unicas encontradas: " + uniqueCities));
    }

    //3: Uso de sort y take
    //Crea un flujo con una lista desordenada de saldos de cuentas bancarias y ordénalos.
    //toma los primeros cinco saldos más altos y muestra el nombre del cliente junto con su saldo.
    public void sortBalance(){
        createFluxPerson()
                .sort(Comparator.comparing(Person::getBalance).reversed())
                .take(5)
                .subscribe(person -> System.out.println("cliente: " + person.getName() + " | Saldo: " + person.getBalance()));
    }

    //4: Combinación de varios operadores
    // Crea un flujo que contenga una lista de transacciones (nombres y montos).
    // Filtra las transacciones superiores a 1000 y convierte cada monto a euros (asumiendo una tasa ficticia).
    // suma todos los montos convertidos y muestra el total.
    public void operationValues(){
        double exchangeRate = 436;
        createFluxTransaction()
                .filter(transaction -> transaction.getValue() > 1000)
                .map(transaction -> transaction.getValue() * exchangeRate)
                .doOnNext(System.out::println)
                .reduce(0.0, Double::sum)//realiza la sula de los montos
                .subscribe(totalSum -> System.out.println("Total de montos en EUR " + totalSum));
    }

    //5: Uso avanzado de flatMap
    //Simula una llamada a un servicio externo que devuelve un flujo de datos sobre el estado de las cuentas bancarias
    // por cada dirección del cliente usando flatMap.
    // Imprime el estado junto con el nombre del cliente.
    public void statusAccountByAddress(){
        createFluxPerson()
                .flatMap(person ->
                        Flux.fromArray(person.getAddresses())//convierto en un flux el array de personas
                                //aplico flatmap para ir a cada elemento del flux de direcciones e ir por el estado de la cuenta de cada direccion
                                .flatMap(address -> getStatusAccountByAddress(address)
                                        .map(status -> "Cliente: " + person.getName() + " | Dirección: " +
                                                address + " | Estado Cuenta: " + status)))
                .subscribe(System.out::println);
    }







}
