package com.lab;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class observableExample {

    public static void main(String[] args) {

        //Array de enteros, obtiene los numeros del rango indicado (1 al 5)
        Observable<Integer> observable = Observable.range(1, 5);

        //Ejemplo Observador que estara atento a la lista de enteros que genera el observable
        Observer<Integer> observer = new Observer<Integer>() {

            //Genera la suscripcion desde el observer al observable
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            //metodo que recibe los datos que emite el observable y lo imprime en consola
            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + value);
                //factorial(integer);
            }

            //Muestra si el observer falla
            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            //muestra que la transaccion se completo ok
            @Override
            public void onComplete() {
                System.out.println("Completed" + "\n------------------------------------------");

            }
        };

        // 1- recibir los numeros y autoincrementarlos
        Observer<Integer> autoIncrement = new Observer<Integer>() {
            private int sum = 0;

            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                System.out.println("Subscribed Autoincrement numbers");
            }

            @Override
            public void onNext(@NonNull Integer num) {
                sum = num + 1;
                //sum += num;
                System.out.println("Received autoIncrement: " + num + " : " + sum);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Autoincrement" + "\n------------------------------------------");
            }
        };

        // 2- factorial de un numero
        Observer<Integer> factorialObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                System.out.println("Subscribed factorial numbers");
            }

            @Override
            public void onNext(@NonNull Integer num) {
                System.out.println("Received factorial: " + num + " : " + factorial(num));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Factorial" + "\n------------------------------------------");
            }
        };

        // 3- cuadrado de un numero
        Observer<Integer> squareObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                System.out.println("Subscribed Square numbers");
            }

            @Override
            public void onNext(@NonNull Integer num) {
                int square = num * num;
                System.out.println("Received Square: " + num + " : " + square);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed square" + "\n------------------------------------------");
            }
        };


        //Inicializacion de el observable y observador
        observable.subscribe(observer);//suscripcion del observador al observado
        observable.subscribe(autoIncrement);
        observable.subscribe(factorialObserver);
        observable.subscribe(squareObserver);
    }

    private static int factorial(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }else {
            return number * factorial(number - 1);
        }
    }
}
