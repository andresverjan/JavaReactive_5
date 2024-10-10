package com.example.sesion3.reactivex;

import io.reactivex.rxjava3.core.Observable;

public class Example {

    public String observable() {
        Observable<String> observable = Observable.just("Hello", "World");
        observable.subscribe(item -> System.out.println("Received: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed"));
        return "Observable";
    }
}
