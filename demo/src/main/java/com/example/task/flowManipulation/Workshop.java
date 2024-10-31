package com.example.task.flowManipulation;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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






    }
}
