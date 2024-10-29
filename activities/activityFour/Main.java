package org.example.activityFour;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        PartOne partOne = new PartOne();
        PartTwo partTwo = new PartTwo();
        PartThree partThree = new PartThree();

        System.out.println("\n1. Manipulación de Flujos de Datos");

        System.out.println("\nPunto 1");
        partOne.toUpperAndPrint();

        System.out.println("\nPunto 2");
        partOne.filterCities();

        System.out.println("\nPunto 3");
        partOne.sortAndTakeTop5();

        System.out.println("\nPunto 4");
        partOne.processTransactions();

        System.out.println("\nPunto 5");
        partOne.getBankAccountStatus().blockLast();


        System.out.println("\n2. Gestión de Errores en Flujos");

        System.out.println("\nPunto 1: onErrorReturn");
        partTwo.oneOneErrorReturn();

        System.out.println("\nPunto 2: onErrorResume");
        partTwo.twoOnErrorResume();

        System.out.println("\nPunto 3: doOnError");
        partTwo.threeDoOnError();

        System.out.println("\nPunto 4: combinado de errores");
        partTwo.fourDifferentErrors();

        System.out.println("\nPunto 5: onErrorContinue");
        partTwo.fiveContinueWithErrors();


        System.out.println("\n3. Combinación de Flujos de Datos");

        System.out.println("\nPunto 1: Uso basico de merge:");
        partThree.oneMerge();

        System.out.println("\nPunto 2: Uso basico de zip");
        partThree.twoZip();

        System.out.println("\nPunto 3: Uso avanzado de combineLatest");
        partThree.threeCombineLatest();

        System.out.println("\nPunto 4: Uso básico de concat");
        partThree.fourConcat();

        System.out.println("\nPunto 5: Uso avanzado de switchIfEmpty");
        partThree.fiveSwitchIfEmtpy();
    }
}