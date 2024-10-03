package org.example;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Main {
    static Observable<Integer> observable = Observable.range(1, 50);
    static Observer<Integer> observer1 = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Subscribed  Observable 1");
        }

        @Override
        public void onNext(Integer value) {
            System.out.println("Observer1 Received: "+value+" incremento 1 " + value + 1);
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

    static Observer<Integer> observer2 = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Subscribed Observable 2");
        }

        @Override
        public void onNext(Integer value) {
            System.out.println("Observer2  Received: "+value+" factorial " + factorial((double) value));
        }
        private double factorial(double value) {
            if (value == 0) {
                return 1;
            } else {
                return value * factorial(value - 1);
            }
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

    static Observer<Integer> observer3 = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Subscribed Observable 3");
        }

        @Override
        public void onNext(Integer value) {
            System.out.println("Observer2  Received: "+value+" cuadrado " + value * value);
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

    public static void main(String[]args){
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }
}