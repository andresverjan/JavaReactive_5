package com.cedesistemas.session7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Session7Application {

    public static void main(String[] args) {

        var persona = Persona.builder()
                .nombre("Juan")
                .ciudad("Bogotá")
                .saldo(1000)
                .direcciones(new String[]{"Calle 1", "Calle 2"})
                .estadoCuenta("Activa")
                .build();

        var persona2 = Persona.builder()
                .nombre("Pedro")
                .ciudad("Medellín")
                .saldo(500)
                .direcciones(new String[]{"Calle 3", "Calle 4"})
                .estadoCuenta("Inactiva")
                .build();

        var persona3 = Persona.builder()
                .nombre("María")
                .ciudad("Cali")
                .saldo(3000)
                .direcciones(new String[]{"Calle 5", "Calle 6"})
                .estadoCuenta("Activa")
                .build();

        var persona4 = Persona.builder()
                .nombre("Luis")
                .ciudad("Barranquilla")
                .saldo(4000)
                .direcciones(new String[]{"Calle 7", "Calle 8"})
                .estadoCuenta("Inactiva")
                .build();

        var persona5 = Persona.builder()
                .nombre("Ana")
                .ciudad("Cartagena")
                .saldo(5000)
                .direcciones(new String[]{"Calle 9", "Calle 10"})
                .estadoCuenta("Activa")
                .build();

        var persona6 = Persona.builder()
                .nombre("Carlos")
                .ciudad("Santa Marta")
                .saldo(6000)
                .direcciones(new String[]{"Calle 11", "Calle 12"})
                .estadoCuenta("Inactiva")
                .build();

        var persona7 = Persona.builder()
                .nombre("Sofía")
                .ciudad("Villavicencio")
                .saldo(100)
                .direcciones(new String[]{"Calle 13", "Calle 14"})
                .estadoCuenta("Activa")
                .build();

        var persona8 = Persona.builder()
                .nombre("Luisa")
                .ciudad("Bogotá")
                .saldo(-5000)
                .direcciones(new String[]{"Calle 15", "Calle 16"})
                .estadoCuenta("Activa")
                .build();

        var persona9 = Persona.builder()
                .nombre("Harold")
                .ciudad("Pereira")
                .saldo(-8000)
                .direcciones(new String[]{"Calle 15", "Calle 16"})
                .estadoCuenta("Activa")
                .build();

        var personas = Flux.just(persona, persona2, persona3, persona4, persona5, persona6,
                persona7, persona8, persona9);

        SpringApplication.run(Session7Application.class, args);
        System.out.println("Ejercicio 1");
        ejercicio1(personas);
        System.out.println("Ejercicio 2");
        ejercicio2(personas);
        System.out.println("Ejercicio 3");
        ejercicio3(personas);
        System.out.println("Ejercicio 4");
        ejercicio4();
        System.out.println("Ejercicio 5");
        ejercicio5(personas);
        System.out.println("Ejercicio 1 Punto 2");
        ejercicio21(personas);
        System.out.println("Ejercicio 2 Punto 2");
        ejercicio22(personas);
        System.out.println("Ejercicio 3 Punto 2");
        ejercicio23(personas);
        System.out.println("Ejercicio 4 Punto 2");
        ejercicio24(personas);
        System.out.println("Ejercicio 5 Punto 2");
        ejercicio25(personas);
        System.out.println("Ejercicio 1 Punto 3");
        ejercicio31(personas);
        System.out.println("Ejercicio 2 Punto 3");
        ejercicio32(personas);
        System.out.println("Ejercicio 3 Punto 3");
        ejercicio33(personas);
        System.out.println("Ejercicio 4 Punto 3");
        ejercicio34(personas);
        System.out.println("Ejercicio 5 Punto 3");
        ejercicio35(personas);
    }
    //1. Manipulación de Flujos de Datos
    // 1.Crea un flujo que contenga una lista de personas. Usa map para convertir sus nombres a mayúsculas y
    // luego usa flatMap para dividir cada nombre en letras individuales. Además, imprime la cantidad total de letras procesadas.

    static void ejercicio1(Flux<Persona> personas) {
        personas
                .map(persona -> persona.getNombre().toUpperCase())
                .flatMap(nombre -> Flux.fromArray(nombre.split("")))
                .count()
                .subscribe(total -> System.out.println("Total de letras procesadas: " + total));
    }

    //2.Crea un flujo que contenga las ciudades donde viven los clientes. Filtra las ciudades que empiezan con la letra
    // "B" y elimina duplicados. Luego, cuenta cuántas ciudades únicas se encontraron.
    static void ejercicio2(Flux<Persona> personas) {
        personas
                .map(Persona::getCiudad)
                .filter(ciudad -> ciudad.startsWith("B"))
                .distinct()
                .count()
                .subscribe(total -> System.out.println("Total de ciudades únicas: " + total));
    }

    //3.Crea un flujo con una lista desordenada de saldos de cuentas bancarias y ordénalos. Luego, toma los primeros
    // cinco saldos más altos y muestra el nombre del cliente junto con su saldo.
    static void ejercicio3(Flux<Persona> personas) {
        personas
                .map(persona -> persona.getNombre() + " - " + persona.getSaldo())
                .sort()
                .take(5)
                .subscribe(result -> System.out.println("Cliente: " + result));
    }

    //4. Crea un flujo que contenga una lista de transacciones (nombres y montos). Filtra las transacciones superiores
    // a 1000 y convierte cada monto a euros (asumiendo una tasa ficticia). Además, suma todos los montos convertidos y muestra el total.
    static void ejercicio4() {
        Flux.just(
                        new Transaccion("Juan", 2000),
                        new Transaccion("Pedro", 500),
                        new Transaccion("María", 3000),
                        new Transaccion("Luis", 4000),
                        new Transaccion("Ana", 5000),
                        new Transaccion("Carlos", 6000),
                        new Transaccion("Sofía", 100)
                )
                .filter(transaccion -> transaccion.getTransaccion() > 1000)
                .map(transaccion -> transaccion.getTransaccion() * 0.00023)
                .reduce(Double::sum)
                .subscribe(total -> System.out.println("Total en euros: " + total));
    }

    //5. Simula una llamada a un servicio externo que devuelve un flujo de datos sobre el estado de las cuentas
    // bancarias por cada dirección del cliente usando flatMap. Imprime el estado junto con el nombre del cliente.
    static void ejercicio5(Flux<Persona> personas) {
        personas
                .flatMap(persona -> Flux.fromArray(persona.getDirecciones())
                        .map(direccion -> persona.getNombre() + " - " + direccion + " - " + persona.getEstadoCuenta()))
                .subscribe(result -> System.out.println("Estado de cuenta: " + result));
    }

    //2. Gestión de Errores en Flujos
    // 1. Crea un flujo que lanza una excepción al procesar un saldo negativo y maneja el error devolviendo un saldo
    // predeterminado, además imprime el nombre del cliente afectado.
    static void ejercicio21(Flux<Persona> personas) {
        personas
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo");
                    }
                    return persona;
                })
                .onErrorReturn(Persona.builder().nombre("Cliente afectado").saldo(0).build())
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }

    //2. Crea un flujo que simula un error al consultar el saldo y maneja el error devolviendo otro flujo con un
    // saldo predeterminado para todos los clientes.
    static void ejercicio22(Flux<Persona> personas) {
        personas
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo");
                    }
                    return persona;
                })
                .onErrorResume(error -> Flux.just(Persona.builder().nombre("Cliente afectado").saldo(0).build()))
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }

    /*
    3. Implementa un flujo que registre el error cuando ocurra usando doOnError, incluyendo detalles sobre el cliente afectado.
    */
    static void ejercicio23(Flux<Persona> personas) {
        personas
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo");
                    }
                    return persona;
                })
                .doOnError(error -> System.out.println("Error en cliente: " + error.getMessage()))
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }

    /*
    4. Crea un flujo que maneje diferentes tipos de excepciones con onErrorResume.
    */
    static void ejercicio24(Flux<Persona> personas) {
        personas
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo");
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    if (error instanceof RuntimeException) {
                        return Flux.just(Persona.builder().nombre("Cliente afectado").saldo(0).build());
                    } else {
                        return Flux.error(error);
                    }
                })
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }

    /*
    Crea un flujo que continúe procesando a pesar de los errores usando onErrorContinue, registrando los errores encontrados.
    */
    static void ejercicio25(Flux<Persona> personas) {
        personas
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo");
                    }
                    return persona;
                })
                .onErrorContinue((error, persona) -> System.out.println("Error en cliente: " + persona.toString() + " - " + error.getMessage()))
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }

    /*
    3. Combinación de Flujos de Datos
    1. Combina dos flujos en uno solo sin importar el orden y muestra el nombre del cliente junto con su ciudad.
    */
    static void ejercicio31(Flux<Persona> personas) {
        var personas2 = Flux.just(
                Persona.builder().nombre("Juan").ciudad("Bogotá").build(),
                Persona.builder().nombre("Pedro").ciudad("Medellín").build(),
                Persona.builder().nombre("María").ciudad("Cali").build(),
                Persona.builder().nombre("Luis").ciudad("Barranquilla").build(),
                Persona.builder().nombre("Ana").ciudad("Cartagena").build(),
                Persona.builder().nombre("Carlos").ciudad("Santa Marta").build(),
                Persona.builder().nombre("Sofía").ciudad("Villavicencio").build(),
                Persona.builder().nombre("Luisa").ciudad("Bogotá").build(),
                Persona.builder().nombre("Harold").ciudad("Pereira").build()
        );

        personas.zipWith(personas2, (persona1, persona2) -> persona1.getNombre() + " - " + persona2.getCiudad())
                .subscribe(result -> System.out.println("Cliente: " + result));
    }

    /*
    2. Combina dos flujos emparejando sus elementos y muestra la suma del saldo total.
     */
    static void ejercicio32(Flux<Persona> personas) {
        var personas2 = Flux.just(
                Persona.builder().nombre("Juan").saldo(1000).build(),
                Persona.builder().nombre("Pedro").saldo(500).build(),
                Persona.builder().nombre("María").saldo(3000).build(),
                Persona.builder().nombre("Luis").saldo(4000).build(),
                Persona.builder().nombre("Ana").saldo(5000).build(),
                Persona.builder().nombre("Carlos").saldo(6000).build(),
                Persona.builder().nombre("Sofía").saldo(100).build(),
                Persona.builder().nombre("Luisa").saldo(-5000).build(),
                Persona.builder().nombre("Harold").saldo(-8000).build()
        );

        personas.zipWith(personas2, (persona1, persona2) -> persona1.getSaldo() + persona2.getSaldo())
                .reduce((saldo1, saldo2) -> saldo1 + saldo2)
                .subscribe(total -> System.out.println("Saldo total: " + total));
    }

    /*
    3. Combina dos flujos tomando el último valor emitido por cada uno y muestra la ciudad junto con su respectivo estado.
    */
    static void ejercicio33(Flux<Persona> personas) {
        var personas2 = Flux.just(
                Persona.builder().ciudad("Bogotá").estadoCuenta("Activa").build(),
                Persona.builder().ciudad("Medellín").estadoCuenta("Inactiva").build(),
                Persona.builder().ciudad("Cali").estadoCuenta("Activa").build(),
                Persona.builder().ciudad("Barranquilla").estadoCuenta("Inactiva").build(),
                Persona.builder().ciudad("Cartagena").estadoCuenta("Activa").build(),
                Persona.builder().ciudad("Santa Marta").estadoCuenta("Inactiva").build(),
                Persona.builder().ciudad("Villavicencio").estadoCuenta("Activa").build(),
                Persona.builder().ciudad("Bogotá").estadoCuenta("Activa").build(),
                Persona.builder().ciudad("Pereira").estadoCuenta("Activa").build()
        );

        personas.zipWith(personas2, (persona1, persona2) -> persona1.getCiudad() + " - " + persona2.getEstadoCuenta())
                .subscribe(result -> System.out.println("Ciudad: " + result));
    }

    /*
    4.Concatenar dos flujos en uno solo manteniendo el orden e imprimiendo todos los nombres.
     */
    static void ejercicio34(Flux<Persona> personas) {
        var personas2 = Flux.just(
                Persona.builder().nombre("Juan").build(),
                Persona.builder().nombre("Pedro").build(),
                Persona.builder().nombre("María").build(),
                Persona.builder().nombre("Luis").build(),
                Persona.builder().nombre("Ana").build(),
                Persona.builder().nombre("Carlos").build(),
                Persona.builder().nombre("Sofía").build(),
                Persona.builder().nombre("Luisa").build(),
                Persona.builder().nombre("Harold").build()
        );

        personas.concatWith(personas2)
                .map(Persona::getNombre)
                .subscribe(result -> System.out.println("Nombre: " + result));
    }

    /*
    5. Proporcionar un flujo alternativo si el original está vacío e imprime un mensaje personalizado.
    */
    static void ejercicio35(Flux<Persona> personas) {
        personas
                .filter(persona -> persona.getSaldo() > 0)
                .switchIfEmpty(Flux.just(Persona.builder().nombre("Cliente afectado").saldo(0).build()))
                .subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + " - Saldo: " + persona.getSaldo()));
    }
}
