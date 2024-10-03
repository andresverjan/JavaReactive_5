package com.example.sesion3;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */

@SpringBootApplication
public class Sesion3Application {

    public static void main(String[] args) {
        SpringApplication.run(Sesion3Application.class, args);

        //Ejemplo clase:
        Observable<Integer> observable = Observable.range(1, 5);

        Observer<Integer> observer = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + value);
                //factorial(value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
// Suscribir el observable al observador
        observable.subscribe(observer);

        //Tarea sesion 3:
        //Punto 1. Implementa un observable que emita los números del 1 al 50 utilizando Observable.range().
        Observable<Integer> observableExcercise = Observable.range(1, 50);
        //Punto 2. Crear Tres Observers:
        //Observer 1: Este observer debe recibir los números y autoincrementarlos (es decir, sumar 1 a cada número recibido).
        Observer<Integer> observer1 = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed Observer 1");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received Observer 1: " + (value + 1));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Observer 1");
            }
        };
        observableExcercise.subscribe(observer1);

        //Observer 2: Este observer debe calcular el factorial de cada número recibido.
        Observer<Integer> observer2 = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed Observer 2");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + factorial(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Observer 2");
            }
        };
        observableExcercise.subscribe(observer2);

        //Observer 3: Este observer debe realizar un cálculo diferente, como elevar cada número al cuadrado.

        Observer<Integer> observer3 = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed Observer 3");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + square(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Observer 3");
            }
        };
        observableExcercise.subscribe(observer3);
    }

    private static Integer factorial(Integer value) {
        if (value == 0) {
            return 1;
        }
        return value * factorial(value - 1);
    }

    private static int square(int number) {
        return number * number;
    }
}
