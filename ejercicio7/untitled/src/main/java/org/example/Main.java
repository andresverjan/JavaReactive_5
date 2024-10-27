package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args)  {
        System.out.println("Ejercicio 1.1:");
        ejercicio1_1();
        System.out.println("Ejercicio 1.2:");
        ejercicio1_2();
        System.out.println("Ejercicio 1.3:");
        ejercicio1_3();
        System.out.println("Ejercicio 1.4:");
        ejercicio1_4(2.0);
        System.out.println("Ejercicio 1.5:");
        String nombreCliente = "Luis";
        ejercicio1_5(nombreCliente);
        nombreCliente = "Juan";
        ejercicio1_5(nombreCliente);
        System.out.println("Ejercicio 2.1:");
        ejercicio2_1("Lulio", 100);
        System.out.println("Ejercicio 2.2:");
        ejercicio2_2( 100);
        System.out.println("Ejercicio 2.3:");
        ejercicio2_3();
        System.out.println("Ejercicio 2.4:");
        ejercicio2_4("Perano");
        ejercicio2_4("Lulio");
        ejercicio2_4("Juan");
        System.out.println("Ejercicio 2.5:");
        ejercicio2_5();
        System.out.println("Ejercicio 3.1:");
        ejercicio3_1();
        System.out.println("Ejercicio 3.2:");
        ejercicio3_2();
        System.out.println("Ejercicio 3.3:");
        ejercicio3_3();
        System.out.println("Ejercicio 3.4:");
        ejercicio3_4();
        System.out.println("Ejercicio 3.5:");
        ejercicio3_5(10);
        ejercicio3_5(35);
    }
    //Proporcionar un flujo alternativo si el original está vacío e imprime un mensaje personalizado.
    private static void ejercicio3_5(double saldo) {
        List<Persona> personas = loadPersonas();
        Flux<Persona> listaPersonasFlux1 = Flux.fromIterable(personas)
                .filter(persona -> persona.getSaldo() == saldo)
                .doOnNext(persona -> {
                    System.out.println("El cliente " + persona.getNombre() + " tiene saldo de " + saldo);
                })
                .switchIfEmpty(Flux.fromIterable(personas)
                        .doOnNext(persona -> {
                            System.out.println("El cliente " + persona.getNombre() + " tiene saldo diferente de " + saldo);
                        })
                        .filter(persona -> persona.getSaldo() != saldo));
        listaPersonasFlux1.subscribe(persona -> System.out.println(persona));



    }

    //Concatenar dos flujos en uno solo manteniendo el orden e imprimiendo todos los nombres.
    private static void ejercicio3_4() {
        Flux<String> listaCiudadFlux1 = Flux.just("Bogota", "Cali", "Medellin");
        Flux<String> listaEstadoFlux2 = Flux.just("Activo", "Inactivo", "Bloqueado");
        Flux<String> mergeSaldosFlux = Flux.concat(listaCiudadFlux1, listaEstadoFlux2).sort();
        mergeSaldosFlux.subscribe(value -> System.out.println(value));
    }

    //Combina dos flujos tomando el último valor emitido por cada uno y muestra la ciudad junto con su respectivo estado.
    private static void ejercicio3_3() {
        Flux<String> listaCiudadFlux1 = Flux.just("Bogota", "Cali", "Medellin");
        Mono<String> listaEstadoFlux2 = Flux.just("Activo", "Inactivo", "Bloqueado").last();
        Flux<String> mergeSaldosFlux = Flux.combineLatest(listaCiudadFlux1, listaEstadoFlux2, (ciudad, estado) -> {
            return ciudad + " " + estado;
        });
        mergeSaldosFlux.subscribe(value -> System.out.println(value));
    }

    //Crea un flujo que simula un error al consultar el saldo y maneja el error devolviendo otro flujo con un saldo predeterminado para todos los clientes.
    private static void ejercicio3_2() {
        List<Persona> personas = loadPersonas();
        Flux<Persona> listaPersonasFlux1 = Flux.fromIterable(personas);
        Flux<Persona> listaPersonasFlux2 = Flux.fromIterable(personas);

        Mono<Double> mergeSaldosFlux = Flux.merge(listaPersonasFlux1, listaPersonasFlux2)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new SaldoMenorCeroException(persona);
                    }
                    return persona.getSaldo();
                })
                .onErrorContinue((error, persona) -> {
                    if (error instanceof SaldoMenorCeroException) {
                        SaldoMenorCeroException exception = (SaldoMenorCeroException) error;
                        System.out.println("El cliente " + exception.getPersona().getNombre()
                                + " tiene saldo negativo y sera descartado");
                    }
                })
                .filter(saldo -> saldo >= 0)
                .reduce(0.0, (subtotal, saldo) -> subtotal + saldo);
        mergeSaldosFlux.subscribe(saldo -> System.out.println("El saldo total de todos los clientes en ambos flujos es "+saldo));

    }

    //Combina dos flujos en uno solo sin importar el orden y muestra el nombre del cliente junto con su ciudad.
    private static void ejercicio3_1() {
        List<Persona> personas = loadPersonas();
        List<String> ciudades = new ArrayList<>();
        Flux<String> listaCiudadesFlux = Flux.fromIterable(personas)
                .map(persona -> persona.getCiudad())
                .distinct();
        Flux<String> listaNombresFlux = Flux.fromIterable(personas)
                .map(persona -> persona.getNombre())
                .distinct();
        Flux<String> mergeCuidadNombres = Flux.merge(listaCiudadesFlux, listaNombresFlux);
        mergeCuidadNombres.subscribe(value -> System.out.println(value));
    }


    //Crea un flujo que contenga una lista de personas.
    // Usa map para convertir sus nombres a mayúsculas y
    // luego usa flatMap para dividir cada nombre en letras individuales.
    // Además, imprime la cantidad total de letras procesadas.
    public static  void ejercicio1_1() {
        List<Persona> personas = loadPersonas();
        Mono<Long> personasFlux = Flux.fromIterable(personas)
                .map(persona -> {
                    persona.setNombre(persona.getNombre().toUpperCase());
                    return persona;
                })
                .flatMap(persona -> {
                    return Flux.fromArray(persona.getNombre().split(""));
                })
                .count();
        personasFlux.subscribe(letras -> System.out.println(letras));
    }

    //Crea un flujo que contenga las ciudades donde viven los clientes.
    // Filtra las ciudades que empiezan con la letra "B"
    // y elimina duplicados.
    // Luego, cuenta cuántas ciudades únicas se encontraron.
    public static  void ejercicio1_2() {
        List<Persona> personas = loadPersonas();
        Mono<Long> ciudadesFlux = Flux.fromIterable(personas)
                .map(persona -> persona.getCiudad())
                .filter(ciudad -> ciudad.startsWith("B"))
                .distinct()
                .count();
        ciudadesFlux.subscribe(ciudades -> System.out.println(ciudades));
    }

    //Crea un flujo con una lista desordenada de saldos de cuentas bancarias
    // y ordénalos.
    // Luego, toma los primeros cinco saldos más altos y
    // muestra el nombre del cliente junto con su saldo.
    public static  void ejercicio1_3() {
        List<Persona> personas = loadPersonas();
        Flux<Persona> personasFlux = Flux.fromIterable(personas)
                .sort((persona1, persona2) -> Double.compare(persona1.getSaldo(), persona2.getSaldo()))
                .take(5);
        personasFlux.subscribe(persona -> {
            System.out.println(persona.getNombre() + " " + persona.getSaldo());

        });
    }

    //Crea un flujo que contenga una lista de transacciones (nombres y montos).
    // Filtra las transacciones superiores a 1000 y
    // convierte cada monto a euros (asumiendo una tasa ficticia).
    // Además, suma todos los montos convertidos y muestra el total.
public static  void ejercicio1_4(Double tasa) {
    List<Transacion> trasacciones = loadTransacciones();
    Mono<Double> transaccionesFlux = Flux.fromIterable(trasacciones)
            .filter(transacion -> transacion.getMonto() > 1000)
            .map(trasaccion -> trasaccion.getMonto() * tasa)
            .reduce(0.0, (subtotal, saldo) -> subtotal + saldo);
    transaccionesFlux.subscribe(total -> System.out.println(total));
}
//Simula una llamada a un servicio externo que devuelve un flujo de datos sobre el estado de las cuentas bancarias
public static Flux<ReporteClienteEstadoCuentas> ejercicio1_5_1(String nombreCliente){
        List<String> estados = List.of("Activo", "Inactivo", "Bloqueado");
        return Flux.just(new ReporteClienteEstadoCuentas(nombreCliente, estados.get((int) (Math.random() * 3))));
}
// por cada dirección del cliente usando flatMap.
// Imprime el estado junto con el nombre del cliente.
public static void ejercicio1_5_2(ReporteClienteEstadoCuentas estadosFlux){

        List<Persona> personasLista = loadPersonas();
        Flux<GroupedFlux<String, ReporteEstadoCuentas>> reporteEstadoCuentasFlux =
                Flux.fromIterable(personasLista)
                        .filter(personas -> personas.getNombre().equals(estadosFlux.getNombre()))
                        .switchIfEmpty(Mono.error(new RuntimeException("No hay datos para el cliente " + estadosFlux.getNombre())))
                        .flatMap(
                                persona -> Flux.fromArray(persona.getDirecciones())
                                        .flatMap(direccion -> Flux.just(estadosFlux)
                                                .map(estado ->
                                                        new ReporteEstadoCuentas(direccion,
                                                                persona.getNombre(),
                                                                estado.getEstadoCuenta()))
                                        )
            ).groupBy(ReporteEstadoCuentas::getDireccion);
        reporteEstadoCuentasFlux.subscribe(reporteEstadoCuentas -> {
            System.out.println(reporteEstadoCuentas.key());
            reporteEstadoCuentas.subscribe(reporteEstadoCuentas1 -> {
                System.out.println("\t" + reporteEstadoCuentas1.toString());
            });
        }, error -> {
            System.out.println(error.toString());
        });
}

    private static void ejercicio1_5(String nombreCliente) {
        ejercicio1_5_1(nombreCliente).subscribe(
                reporteClienteEstadoCuentas -> {
                    if(reporteClienteEstadoCuentas == null){
                        System.out.println("No hay datos para el cliente " + nombreCliente);
                        return;
                    }
                    ejercicio1_5_2(reporteClienteEstadoCuentas);
                },
                error -> {
                    System.out.println(error.toString());
                },
                () -> System.out.println("Fin del flujo de datos para el cliente " + nombreCliente + ".\n"));
    }
//Crea un flujo que lanza una excepción al procesar un saldo negativo y
// maneja el error devolviendo un saldo predeterminado, además imprime el nombre del cliente afectado.
private static void ejercicio2_1(String nombre, double saldoPredefinido){
    List<Persona> personasLista = loadPersonas();
    Flux<Object> personasFlux = Flux.fromIterable(personasLista)
            .filter(persona -> persona.getNombre().equals(nombre) && persona.getSaldo() < 0)
            .map(persona -> {
                System.out.println("El cliente " + persona.getNombre() + " tiene saldo negativo");
                persona.setSaldo(saldoPredefinido);
                throw new SaldoMenorCeroException(persona);
            }).onErrorReturn(saldoPredefinido);

    personasFlux.subscribe(value -> System.out.println("su nuevo saldo es " + value));
    }

    //Crea un flujo que simula un error al consultar el saldo y
    // maneja el error devolviendo otro flujo con un saldo predeterminado para todos los clientes.
    private static void ejercicio2_2(double saldoPredefinido) {
        List<Persona> personas = new ArrayList<Persona>();
        Mono<Object> personasFlux = Mono.empty()
                .switchIfEmpty(Mono.error(new RuntimeException("Error al consultar el saldo")))
                .onErrorResume(error -> {
                    System.out.println("Error al consultar el saldo: " + error.getMessage());
                    return Mono.just(loadPersonas())
                            .flatMapMany(Flux::fromIterable)
                            .map(persona -> {
                                persona.setSaldo(saldoPredefinido);
                                return persona;
                            })
                            .collectList();
                });
        personasFlux.subscribe(res -> System.out.println(res));
    }

    //Implementa un flujo que registre el error cuando ocurra usando doOnError, incluyendo detalles sobre el cliente afectado.
    private static void ejercicio2_3() {
        List<Persona> personas = loadPersonas();
        Flux<Persona> personasFlux = Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new SaldoMenorCeroException(persona);
                    }
                    return persona;
                })
                .doOnError(error -> {
                    if (error instanceof SaldoMenorCeroException) {
                        SaldoMenorCeroException exception = (SaldoMenorCeroException) error;
                    }
                });
        personasFlux.subscribe(persona -> System.out.println(persona),
                error -> {
                    if (error instanceof SaldoMenorCeroException) {
                        SaldoMenorCeroException exception = (SaldoMenorCeroException) error;
                        System.out.println("El cliente " + exception.getPersona()
                                + " tiene saldo negativo");
                    }
                });
    }

    //Crea un flujo que maneje diferentes tipos de excepciones con onErrorResume.
    public static void ejercicio2_4(String nombre) {
        List<Persona> personas = loadPersonas();
        Flux<Persona> personasFlux = Flux.fromIterable(personas)
                .filter(persona -> persona.getNombre().equals(nombre))
                .switchIfEmpty(Mono.error(new NoExisteClienteException(nombre)))
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new SaldoMenorCeroException(persona);
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    if (error instanceof SaldoMenorCeroException) {
                        SaldoMenorCeroException exception = (SaldoMenorCeroException) error;
                        System.out.println("El cliente " + exception.getPersona()
                                + " tiene saldo negativo");
                        return Flux.just(exception.getPersona());
                    }
                    if (error instanceof NoExisteClienteException) {
                        NoExisteClienteException exception = (NoExisteClienteException) error;
                        System.out.println("El cliente " + exception.getPersona()
                                + " no existe");
                        return Flux.empty();
                    }
                    return Flux.empty();
                });
        personasFlux.subscribe(persona -> System.out.println(persona));
    }

    //Crea un flujo que continúe procesando a pesar de los errores usando onErrorContinue, registrando los errores encontrados.
    public static void ejercicio2_5() {
        List<Persona> personas = loadPersonas();
        Flux<Persona> personasFlux = Flux.fromIterable(personas)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new SaldoMenorCeroException(persona);
                    }
                    return persona;
                })
                .onErrorContinue((error, persona) -> {
                    if (error instanceof SaldoMenorCeroException) {
                        SaldoMenorCeroException exception = (SaldoMenorCeroException) error;
                        System.out.println("El cliente " + exception.getPersona()
                                + " tiene saldo negativo");
                    }
                });
        personasFlux.subscribe(persona -> System.out.println(persona));
    }

    private static List<Persona> loadPersonas() {
        return List.of(
                new Persona("Juan", "Baranquilla", new String[]{"Direccion 1", "Direccion 3", "Direccion 2"}, 25),
                new Persona("Mario", "Bogota", new String[]{"Direccion 1", "Direccion 3", "Direccion 2"}, 25),
                new Persona("Pedro", "Cali", new String[]{"Direccion 1", "Direccion 3", "Direccion 2"}, 35),
                new Persona("Lulio", "Cali", new String[]{"Direccion 1", "Direccion 3", "Direccion 2"}, -35));
    }

    private static List<Transacion> loadTransacciones() {
        return List.of(
                new Transacion("Juan", 100),
                new Transacion("Mario", 2000),
                new Transacion("Pedro", 300),
                new Transacion("Juan", 100));
    }
}