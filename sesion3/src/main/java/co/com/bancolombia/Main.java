package co.com.bancolombia;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        /*
        * EJERCICIO 2 : PATRON OBSERVABLE
        * Crear un Observable:Implementa un observable que emita los números del 1 al 50 utilizando Observable.range().
            Crear Tres Observers:
            Observer 1: Este observer debe recibir los números y autoincrementarlos (es decir, sumar 1 a cada número recibido).
            Observer 2: Este observer debe calcular el factorial de cada número recibido.
            Observer 3: Este observer debe realizar un cálculo diferente, como elevar cada número al cuadrado.
            Suscribir los Observers:
            Suscribe cada uno de los observers al observable creado. Asegúrate de que cada observer imprima su resultado en la consola.
            Ejecutar el Código:
            Ejecuta tu programa y verifica que todos los observers funcionen correctamente y muestren los resultados esperados.
        *
        * */

        Observable<Integer> observable = Observable.range(1, 50);

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 1 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                value += 1;
                System.out.println("Autoincrement: " + value);
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
        //Suscribir el observador 1 al observable
        observable.subscribe(observer1);


        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 2 Subscribed");
                System.out.println("**************************");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println(factorial(value));

            }
            public BigInteger factorial(int n) {
                if (n == 0) {
                    return BigInteger.ONE;
                } else {
                    return BigInteger.valueOf(n).multiply(factorial(n - 1));
                    /*1*1*1 = 1
                     * 2*1*1 = 2
                     * 3*2*1*1 = 6
                     * 4*3*2*1*1 = 24
                     * 5*4*3*2*1*1 = 120
                     * */
                }
            }
            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Observer 2");
                System.out.println("**************************");
            }
        };
        //Suscribir el observador 2 al observable
        observable.subscribe(observer2);


        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 3 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                Double result = Math.pow(value, 2);
                System.out.println("Square: " + value + " = " + result);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed Observer 3");
                System.out.println("**************************");
            }
        };
        //Suscribir el observador 3 al observable
        observable.subscribe(observer3);
    }
}