package com.example.task.flowManipulation;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Workshop {
    public static void main(String[] args) {

        System.out.println("*************** 1 - Manipulación de Flujos de Datos ***************");
        ManipulationFlow manipulationFlow = new ManipulationFlow();

        System.out.println("\n--------------- 1 - Uso de map y flatMap ---------------");
        manipulationFlow.toUpperCaseName();

        System.out.println("\n--------------- 2 - Uso de filter y distinct ---------------");
        manipulationFlow.filterCities();

        System.out.println("\n--------------- 3 - Uso de sort y take ---------------");
        manipulationFlow.sortBalance();

        System.out.println("\n--------------- 4 - Combinación de varios operadores ---------------");
        manipulationFlow.operationValues();

        System.out.println("\n--------------- 5 - Uso avanzado de flatMap ---------------");
        manipulationFlow.statusAccountByAddress();

        System.out.println("*************** 2 - Gestión de Errores en Flujos ***************");
        FlowErrorManagement flowErrorManagement = new FlowErrorManagement();

        System.out.println("\n--------------- 1 - Uso básico de onErrorReturn ---------------");
        flowErrorManagement.proccessNegativeBalance();

        System.out.println("\n--------------- 2 - Uso de onErrorResume ---------------");
        flowErrorManagement.proccessErrorBalance();

        System.out.println("\n--------------- 3 - Manejo específico con doOnError ---------------");
        flowErrorManagement.proccessError();

        System.out.println("\n--------------- 4 - Uso combinado de errores ---------------");
        flowErrorManagement.proccessErrorMix();

        System.out.println("\n--------------- 5 - Ignorar errores con onErrorContinue ---------------");
        flowErrorManagement.ignoreError();

        System.out.println("\n\n*************** 3 - Combinación de Flujos de Datos ***************");
        CombiningDataFlows combiningDataFlows = new CombiningDataFlows();

        System.out.println("\n--------------- 1 - Uso básico de merge ---------------");
        combiningDataFlows.showName();

        System.out.println("\n--------------- 2 - Uso básico de zip ---------------");
        combiningDataFlows.join2Flux();

        System.out.println("\n--------------- 3 - Uso avanzado de combineLatest ---------------");
        combiningDataFlows.joinWithStatus();

        System.out.println("\n--------------- 4 - Uso avanzado de combineLatest ---------------");
        combiningDataFlows.concatFlux();

        System.out.println("\n--------------- 5 - Uso avanzado de switchIfEmpty ---------------");
        combiningDataFlows.alternativeFlux();
 }

    static List<Person> getPeople() {
        String[] addresses1 = new String[]{"Avenida 15", "Calle 10"};
        String[] addresses2 = new String[]{"Carrera 45", "Carrera 50"};
        String[] addresses3 = new String[]{"Calle 1T", "Transversal 3"};

        List<Person> people =  new ArrayList<>();

        people.add(new Person("1 Luisa", "Manchester", addresses1,9785));
        people.add(new Person("2 Andres", "Medellin", addresses2,150846));
        people.add(new Person("3 Cristian", "Bogota", addresses3,789416));
        people.add(new Person("4 Juan", "Cali", addresses1,5984843));
        people.add(new Person("5 Maria", "Madrid", addresses2,-154));
        people.add(new Person("6 Ana", "Lima", addresses3,7894));
        people.add(new Person("7 Pedro", "Londres", addresses3,4873));
        people.add(new Person("8 Pablo", "Bogota", addresses3,89416));
        people.add(new Person("9 Cindy", "Buga", addresses1,3984842));
        people.add(new Person("10 Fernando", "Madrid", addresses2,154));
        people.add(new Person("11 Laura", "Lima", addresses3,9894));
        people.add(new Person("12 Julia", "Barcelona", addresses3,4873));

        return people;
    }
}
