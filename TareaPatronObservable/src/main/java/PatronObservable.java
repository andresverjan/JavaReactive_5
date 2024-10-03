import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PatronObservable {

    public static Long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1L;
        }
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 50);

        // Primer Observer: Autoincrementa el número recibido
        Observer<Integer> observer_N1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Observer N°1 suscrito");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println("Observer N°1 recibió: " + value + " -> Incrementado: " + (value + 1));
            }
            @Override
            public void onError(Throwable e) {
                System.err.println("Observer N°1 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer N°1 completado");
            }
        };
        // Segundo Observer: Calcula el factorial del número recibido
        Observer<Integer> observer_N2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Observer N°2 suscrito");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println("Observer N°2 recibió: " + value + " -> Factorial: " + factorial(value));
            }
            @Override
            public void onError(Throwable e) {
                System.err.println("Observer N°2 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer N°2 completado");
            }
        };

        // Tercer Observer: Eleva cada número al cuadrado
        Observer<Integer> observer_N3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Observer N°3 suscrito");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println("Observer N°3 recibió: " + value + " -> Cuadrado: " + (value * value));
            }
            @Override
            public void onError(Throwable e) {
                System.err.println("Observer N°3 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer N°3 completado");
            }
        };
        // Suscribimos los tres observers al observable
        observable.subscribe(observer_N1);
        observable.subscribe(observer_N2);
        observable.subscribe(observer_N3);
    }
}
