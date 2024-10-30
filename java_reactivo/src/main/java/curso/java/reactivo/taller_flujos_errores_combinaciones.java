package curso.java.reactivo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class taller_flujos_errores_combinaciones {
    public static void main(String[] args) {
        List<persona_taller> personas = List.of(
                new persona_taller("Alice", "New York", new String[]{"123 Main St", "456 Park Ave"}, 10000.0),
                new persona_taller("Bob", "Boston", new String[]{"789 Elm St", "101 Oak St"}, 20000.0),
                new persona_taller("Charlie", "Chicago", new String[]{"135 1st St", "246 2nd St"}, 30000.0),
                new persona_taller("Bob's Syster", "Boston", new String[]{"789 Elm St", "101 Oak St"}, 20000.0),
                new persona_taller("Bob's father", "Bogota", new String[]{"789 Elm St", "101 Oak St"}, 20000.0),
                new persona_taller("Cris", "Bogota", new String[]{"789 Elm St", "101 Oak St"}, 200.0),
                new persona_taller("Bob's moma", "Bucaramanga", new String[]{"789 Elm St", "101 Oak St"}, -200.0)

        );

        List<String> estados = List.of("Activa", "Inactiva", "Activa", "Inactiva", "Activa", "Inactiva", "Activa");
        /*Ejercicio 1: Uso de map y flatMap

        Crea un flujo que contenga una lista de personas.
        Usa map para convertir sus nombres a mayúsculas y
        luego usa flatMap para dividir cada nombre en letras individuales.
        Además, imprime la cantidad total de letras procesadas.
        */
        System.out.println("--------------------------- EJERCICIO 1.1 ---------------------------");
        Flux<persona_taller> personasFlux = Flux.fromIterable(personas);
        personasFlux
                .map(persona -> persona.getNombre().toUpperCase())
                .flatMap(nombre -> Flux.fromArray(nombre.split("")))
                .count()
                .doOnNext(System.out::println)
                .subscribe();
        /*Ejercicio 2: Uso de filter y distinct

        Crea un flujo que contenga las ciudades donde viven los clientes.
        Filtra las ciudades que empiezan con la letra "B" y elimina duplicados.
        Luego, cuenta cuántas ciudades únicas se encontraron.*/
        System.out.println("--------------------------- EJERCICIO 1.2 ---------------------------");
        personasFlux
                .map(persona -> persona.getCiudad())
                .filter(ciudad -> ciudad.startsWith("B"))
                .distinct()
                .count()
                .doOnNext(System.out::println)
                .subscribe();

        /*Ejercicio 3: Uso de sort y take

        Crea un flujo con una lista desordenada de saldos de cuentas bancarias y ordénalos.
         Luego, toma los primeros cinco saldos más altos y muestra el nombre del cliente junto con su saldo.*/
        System.out.println("--------------------------- EJERCICIO 1.3 ---------------------------");
        personasFlux
                .sort((p1, p2) -> Double.compare(p2.getSaldo(), p1.getSaldo()))
                .take(5)
                .doOnNext(p -> System.out.println(p.getNombre() + ": " + p.getSaldo()))
                .subscribe();
        /* Ejercicio 4: Combinación de varios operadores

        Crea un flujo que contenga una lista de transacciones (nombres y montos).
        Filtra las transacciones superiores a 1000 y convierte cada monto a euros (asumiendo una tasa ficticia).
        Además, suma todos los montos convertidos y muestra el total.*/
        System.out.println("--------------------------- EJERCICIO 1.4 ---------------------------");

        personasFlux
                .filter(persona -> persona.getSaldo() > 1000)
                .flatMap(persona -> Flux.just(persona.getSaldo() * 4.66730))
                .reduce(0.0, (subtotal, element) -> subtotal + element)
                .doOnNext(System.out::println)
                .subscribe();
        /*Ejercicio 5: Uso avanzado de flatMap

        Simula una llamada a un servicio externo que devuelve un flujo de datos
        sobre el estado de las cuentas bancarias por cada dirección del cliente usando flatMap.
        Imprime el estado junto con el nombre del cliente.*/
        System.out.println("--------------------------- EJERCICIO 1.5 ---------------------------");
        Map<String, String> estadoCuentas = new HashMap<>();
        estadoCuentas.put("123 Main St", "Activa");
        estadoCuentas.put("456 Park Ave", "Inactiva");
        estadoCuentas.put("789 Elm St", "Activa");

        personasFlux
                .flatMap(persona -> Flux.fromArray(persona.getDirecciones())
                        .flatMap(direccion -> {
                            String estado = estadoCuentas.get(direccion);
                            if (estado != null) {
                                return Flux.just(persona.getNombre() + ": " + estado);
                            } else {
                                return Flux.empty();
                            }
                        })
                )
                .subscribe(System.out::println);


        System.out.println("--------------------------- EJERCICIO 2.1 ---------------------------");
        obtenerSaldo(personas)
                .doOnNext(persona -> System.out.println(persona.getNombre() + ": " + persona.getSaldo()))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 2.2 ---------------------------");
        obtenerSaldoPredeterminado(personas)
                .doOnNext(persona -> System.out.println(persona.getNombre() + ": " + persona.getSaldo()))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 2.3 ---------------------------");
        obtenerSaldoDetallado(personas)
                .doOnNext(persona -> System.out.println(persona.getNombre() + ": " + persona.getSaldo()))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 3.1 ---------------------------");
        combinarPersonas(personas, personas)
                .doOnNext(persona -> System.out.println(persona.getNombre() + ": " + persona.getCiudad()))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 3.2 ---------------------------");
        combinarSaldos(personas, personas)
                .doOnNext(saldo -> System.out.println("Saldo total: " + saldo))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 3.3 ---------------------------");
        combinarCiudades(personas, estados)
                .doOnNext(ciudad -> System.out.println(ciudad))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 3.4 ---------------------------");
        concatenarNombres(personas, personas)
                .doOnNext(nombre -> System.out.println(nombre))
                .subscribe();

        System.out.println("--------------------------- EJERCICIO 3.5 ---------------------------");
        obtenerSaldoIgnorarErrores(personas)
                .doOnNext(persona -> System.out.println(persona.getNombre() + ": " + persona.getSaldo()))
                .subscribe();
    }

    /*Ejercicio 1: Uso básico de onErrorReturn

   Crea un flujo que lanza una excepción al procesar un saldo negativo y maneja el error devolviendo un saldo predeterminado,
   además imprime el nombre del cliente afectado.*/
    public static Flux<persona_taller> obtenerSaldo(List<persona_taller> personas) {
        return Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException(persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    return Flux.fromIterable(personas)
                            .filter(persona -> persona.getNombre().equals(error.getMessage()))
                            .map(persona -> new persona_taller(persona.getNombre(), persona.getCiudad(), persona.getDirecciones(), 0.0));
                });
    }

    /* Ejercicio 2: Uso de onErrorResume

    Crea un flujo que simula un error al consultar el saldo y maneja el error
    devolviendo otro flujo con un saldo predeterminado para todos los clientes.*/
    public static Flux<persona_taller> obtenerSaldoPredeterminado(List<persona_taller> personas) {
        return Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() != 0) {
                        throw new RuntimeException(persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    return Flux.fromIterable(personas)
                            .map(persona -> new persona_taller(persona.getNombre(), persona.getCiudad(), persona.getDirecciones(), 0.0));
                });
    }

    /* Ejercicio 3: Manejo específico con doOnError

    Implementa un flujo que registre el error cuando ocurra usando doOnError,
     incluyendo detalles sobre el cliente afectado.*/
    public static Flux<persona_taller> obtenerSaldoDetallado(List<persona_taller> personas) {
        return Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException(persona.getNombre() + ": Saldo negativo");
                    }
                    return persona;
                })
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    /*Ejercicio 4: Uso combinado de errores

    Crea un flujo que maneje diferentes tipos de excepciones con onErrorResume.*/
    public static Flux<persona_taller> obtenerSaldoCombinado(List<persona_taller> personas) {
        return Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException(persona.getNombre() + ": Saldo negativo");
                    } else if (persona.getSaldo() == 0) {
                        throw new IllegalArgumentException(persona.getNombre() + ": Saldo nulo");
                    }
                    return persona;
                })
                .onErrorResume(RuntimeException.class, error -> {
                    return Flux.fromIterable(personas)
                            .filter(persona -> persona.getNombre().equals(error.getMessage().split(":")[0]))
                            .map(persona -> new persona_taller(persona.getNombre(), persona.getCiudad(), persona.getDirecciones(), 0.0));
                })
                .onErrorResume(IllegalArgumentException.class, error -> {
                    return Flux.fromIterable(personas)
                            .filter(persona -> persona.getNombre().equals(error.getMessage().split(":")[0]))
                            .map(persona -> new persona_taller(persona.getNombre(), persona.getCiudad(), persona.getDirecciones(), 1000.0));
                });
    }

    /* Ejercicio 5: Ignorar errores con onErrorContinue

Crea un flujo que continúe procesando a pesar de los errores usando onErrorContinue, registrando los errores encontrados.*/

    public static Flux<persona_taller> obtenerSaldoIgnorarErrores(List<persona_taller> personas) {
        return Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException(persona.getNombre() + ": Saldo negativo");
                    }
                    return persona;
                })
                .onErrorContinue((error, persona) -> System.err.println("Error: " + error.getMessage()));
    }

    /* Ejercicio 1: Uso básico de merge

    Combina dos flujos en uno solo sin importar el orden y muestra el nombre del cliente junto con su ciudad.*/

    public static Flux<persona_taller> combinarPersonas(List<persona_taller> personas1, List<persona_taller> personas2) {
        Flux<persona_taller> personasFlux1 = Flux.fromIterable(personas1);
        Flux<persona_taller> personasFlux2 = Flux.fromIterable(personas2);
        return Flux.merge(personasFlux1, personasFlux2);
    }

    /*Ejercicio 2: Uso básico de zip

    Combina dos flujos emparejando sus elementos y muestra la suma del saldo total.*/
    public static Flux<Double> combinarSaldos(List<persona_taller> personas1, List<persona_taller> personas2) {
        Flux<persona_taller> personasFlux1 = Flux.fromIterable(personas1);
        Flux<persona_taller> personasFlux2 = Flux.fromIterable(personas2);
        return Flux.zip(personasFlux1, personasFlux2, (p1, p2) -> p1.getSaldo() + p2.getSaldo());
    }

    /*Ejercicio 3: Uso avanzado de combineLatest

    Combina dos flujos tomando el último valor emitido por cada uno y muestra la ciudad junto con su respectivo estado.*/
    public static Flux<String> combinarCiudades(List<persona_taller> personas1, List<String> estados) {
        Flux<persona_taller> personasFlux1 = Flux.fromIterable(personas1);
        Flux<String> estadosFlux = Flux.fromIterable(estados);
        return Flux.combineLatest(personasFlux1, estadosFlux, (p1, e1) -> p1.getCiudad() + ": " + e1);
    }

    /* Ejercicio 4: Uso básico de concat

    Concatenar dos flujos en uno solo manteniendo el orden e imprimiendo todos los nombres.*/

    public static Flux<String> concatenarNombres(List<persona_taller> personas1, List<persona_taller> personas2) {
        Flux<persona_taller> personasFlux1 = Flux.fromIterable(personas1);
        Flux<persona_taller> personasFlux2 = Flux.fromIterable(personas2);
        return Flux.concat(personasFlux1, personasFlux2)
                .map(persona_taller::getNombre);
    }

    /* Ejercicio 5: Uso avanzado de switchIfEmpty

    Proporcionar un flujo alternativo si el original está vacío e imprime un mensaje personalizado.*/
    public static Flux<persona_taller> obtenerPersonasPorEdad(List<persona_taller> personas, double saldo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSaldo() == saldo)
                .switchIfEmpty(Flux.error(new RuntimeException("No se encontraron personas con el saldo especificado")));
    }
}
