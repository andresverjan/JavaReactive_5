package org.example;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ObservableActivity {

    public static void main(String[] args) {

        Observable<Integer> observable = Observable.range(1, 50);

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 1 - Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 1 - Incremented: " + (value + 1));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 1 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 - Completed");
            }
        };
        // Observer 2
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 2 - Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                long result = 1;
                for (int i = 2; i <= value; i++) {
                    result *= i;
                }

                System.out.println("Observer 2 - Factorial of " + value + ": " + result);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 2 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 2 - Completed");
            }


        };

        // Observer 3: Elevar cada número al cuadrado
        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 3 - Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 3 - Squared: " + (value * value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 3 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 3 - Completed");
            }
        };

        // Suscribir los observers al observable
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }
}