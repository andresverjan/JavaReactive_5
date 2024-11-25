package com.artifactory.crud.repository;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.EstudianteNota;
import com.artifactory.crud.model.Materia;
import com.artifactory.crud.model.Person;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, Long> {

    @Query(" Select e.id as id, e.nombre as nombre, e.edad as edad ,  '' as nota " +
            "from estudiante e " +
            "where e.id in((select  estudiante " +
            "from materia m              " +
            "group by estudiante having avg(nota) > 3))")
    Flux<EstudianteNota> findEstudentsAprobados();

    @Query(" Select e.id as id, e.nombre as nombre, e.edad as edad , (select avg(nota)" +
            "from materia m " +
            "group by estudiante " +
            "having avg(nota) <= 3) as nota " +
            "from estudiante e " +
            "where e.id in((select  estudiante " +
            "from materia m              " +
            "group by estudiante having avg(nota) <= 3))")
    Flux<EstudianteNota> findEstudentsReprobados();

}
