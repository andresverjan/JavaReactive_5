package com.lab;

import io.reactivex.rxjava3.core.Observable;

public class observableExampleNew {

    public static void main(String[] args) {

        Observable<String> observable = Observable.just("Hello", "World");

        observable.subscribe(item -> System.out.println("Received: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed"));
    }
}
