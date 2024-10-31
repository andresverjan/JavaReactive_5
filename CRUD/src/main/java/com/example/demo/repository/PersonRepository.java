package com.example.demo.repository;

import com.example.demo.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class PersonRepository {
    private final R2dbcEntityTemplate template;

    public Mono<Person> save(Person person) {
        return template.insert(Person.class).using(person);
    }

    public Mono<Person> findById(Long id) {
        return template.select(Person.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one();
    }

    public Flux<Person> findAll() {
        return template.select(Person.class).all();
    }

    public Mono<Person> update(Person person) {
        return template.update(Person.class)
                .matching(Query.query(Criteria.where("id").is(person.getId())))
                .apply(Update.update("name", person.getName())
                        .set("age", person.getAge())
                        .set("gander", person.getGander())
                        .set("date_of_birth", person.getDateOfBirth())
                        .set("blood_type", person.getBloodType()))
                .then(Mono.just(person));
    }

    public Mono<Void> deleteById(Long id) {
        return template.delete(Person.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .all()
                .then();
    }
}
