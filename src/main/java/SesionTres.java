import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SesionTres {
    public static void main(String[] args){
        Observable<Integer> observable = Observable.range(1, 10);

        // Ejemplo:
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        /* Observer 1: Este observer debe recibir los números y
        autoincrementarlos (es decir, sumar 1 a cada número recibido).*/
        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received " + (integer + 1));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        /* Observer 2: Este observer debe calcular el factorial
        de cada número recibido.*/
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                int factorial = 1;
                for (int i = 1; i <= integer; i++) {
                    factorial *= i;
                }
                System.out.println("Received " + factorial);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        /* Observer 3: Este observer debe realizar un cálculo diferente,
         como elevar cada número al cuadrado.*/
        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received " + (integer * integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };


        observable.subscribe(observer);
        System.out.println("=========================================================================================");
        observable.subscribe(observer1);
        System.out.println("=========================================================================================");
        observable.subscribe(observer2);
        System.out.println("=========================================================================================");
        observable.subscribe(observer3);
        System.out.println("=========================================================================================");
    }
}