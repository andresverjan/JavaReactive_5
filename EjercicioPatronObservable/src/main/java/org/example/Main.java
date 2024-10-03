package org.example;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Main {

    public static int factorial(int num){
        if(num == 0){
            return 1;
        }
        else
            return num * factorial(num-1);
    }
    public static void main(String[] args) {
//Observable
        Observable<Integer> observable = Observable.range(1, 50);
//Observer 1
        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed 2");
            }

            @Override
            public void onNext(Integer value) {
                value = value +1;
                System.out.println("Received 2: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed 2");
            }
        };
//Observer 2
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed 2");
            }

            @Override
            public void onNext(Integer value) {
                int result = factorial(value);
                System.out.println("Received 2: " + result);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed 2");
            }
        };

    //Observer 3
        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed 3");
            }

            @Override
            public void onNext(Integer value) {
                value = value * value;
                System.out.println("Received 3: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed 3");
            }
        };

        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }
}