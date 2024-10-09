package org.lambda;

/*import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;*/



import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.math.BigInteger;

public class ExerciseObservable {
    public static void main(String[] args) {


        Observable<Integer> observable = Observable.range(1, 50);


        Observer<Integer> autoincrement = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received increment: num " + value + " --> " + (value + 1));
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



        Observer<Integer> fact = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Factorial: num "+ value + " --> " + factorial(value));

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


        Observer<Integer> squared = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("To squared: num "+ value + " --> " + (value * value));

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

        observable.subscribe(autoincrement);
        observable.subscribe(fact);
        observable.subscribe(squared);


        //RXJAVA3
        Observable<String> observablerx = Observable.just("Hello", "World");
        observablerx.subscribe(
                item -> System.out.println("Received: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed")
        );

        Observable<String> observablerxError = Observable.error(new RuntimeException("Simulated error"));
        observablerxError.subscribe(
                item -> System.out.println("Received: " + item),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Completed")
        );
    }
    private static BigInteger factorial(int n) {
        if (n == 0) {
            return BigInteger.ONE;
        }
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }
}