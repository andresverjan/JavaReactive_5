package com.example.task.flowManipulation;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static com.example.task.flowManipulation.Workshop.getPeople;

public class CombiningDataFlows {

    public List<Person> createPeopleList1() {
         return getPeople().subList(0,6);
    }

    public List<Person> createPeopleList2() {
        return getPeople().subList(6,12);

    }

    public Flux<Person> createFluxPerson1() {
        return Flux.fromIterable(createPeopleList1());
    }

    public Flux<Person> createFluxPerson2() {
        return Flux.fromIterable(createPeopleList2());
    }

    private Flux<String> createDocumentNumberPersonFlux(){
        List<String> documentNumberPersonList = new ArrayList<>(
                List.of("11111111", "9999999", "3333333333", "88888888","66666666"));
        return Flux.fromIterable(documentNumberPersonList);
    }

    private Flux<String> createStatusFlux(){
        List<String> documentNumberPersonList = new ArrayList<>(
                List.of("INACTIVO", "ACTIVO", "BLOQUEADO", "TERMINADO"));
        return Flux.fromIterable(documentNumberPersonList);
    }

    static Flux<String> createFluxEmpty() {
        return Flux.empty();
    }

    //1: Uso básico de merge
    //Combina dos flujos en uno solo sin importar el orden y muestra el nombre del cliente junto con su ciudad.
    public void showName(){
        Flux<Person> fluxMerged = Flux.merge(createFluxPerson1(),createFluxPerson2());
        fluxMerged.subscribe(p -> System.out.println("Nombre: " + p.getName() + "| Ciudad: " + p.getCity()));
    }

    //2: Uso básico de zip
    //Combina dos flujos emparejando sus elementos y muestra la suma del saldo total.
    public void join2Flux(){
        Flux<Tuple2<Person, String>> combinedZip = Flux.zip(createFluxPerson1(), createDocumentNumberPersonFlux());

        combinedZip
                .doOnNext(p -> System.out.println("Nombre: " + p.getT1().getName() + " Documento: " + p.getT2() + " Saldo: " +p.getT1().getBalance()))
                .map(p-> p.getT1().getBalance())
                .reduce(0.0,Double::sum)
                .subscribe(sum -> System.out.println( "Suma del saldo total: " + sum));
    }

    //3: Uso avanzado de combineLatest
    // Combina dos flujos tomando el último valor emitido por cada uno y
    // muestra la ciudad junto con su respectivo estado.
    public void joinWithStatus(){
        Flux<String> combinedFlow = Flux.combineLatest(createFluxPerson1().map(Person::getCity),
                createStatusFlux().map(s -> s),
               (city, status) -> city + " " + status);

        combinedFlow.subscribe(System.out::println);
    }

    // 4: Uso básico de concat
    //Concatenar dos flujos en uno solo manteniendo el orden e imprimiendo todos los nombres.
    public void concatFlux(){
        Flux<String> concatPersonFlux = Flux.concat(createFluxPerson1().map(Person::getName),
                createFluxPerson2().map(Person::getName));
        concatPersonFlux.subscribe(System.out::println);
    }

    //5: Uso avanzado de switchIfEmpty
    //Proporcionar un flujo alternativo si el original está vacío e imprime un mensaje personalizado.
    public void alternativeFlux(){
        createFluxEmpty()
                .map(s->s)
                .switchIfEmpty(Flux.just("Flux Original vacio"))
        .subscribe(System.out::println);
    }

}
