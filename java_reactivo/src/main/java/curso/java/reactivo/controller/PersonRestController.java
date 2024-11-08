//package curso.java.reactivo.controller;
//
//import curso.java.reactivo.model.Person;
//import curso.java.reactivo.service.PersonService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/person")
//public class PersonRestController {
//    private final PersonService personService;
//
//    public PersonRestController(PersonService personService) {
//        this.personService = personService;
//    }
//
//    @PostMapping()
//    public Mono<ResponseEntity<Person>> addPerson(@RequestBody Person person) {
//        return personService.addPerson(person)
//                .flatMap(savedPerson -> Mono.just(ResponseEntity.ok(savedPerson)));
//    }
//
//    @GetMapping()
//    public Mono<ResponseEntity<List<Person>>> getPerson() {
//        return personService.getPerson()
//                .switchIfEmpty(Mono.error(new RuntimeException("No persons found")))
//                .collectList()
//                .flatMap(person -> Mono.just(ResponseEntity.ok(person)));
//    }
//
//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<Person>> getPersonById(@PathVariable Long id) {
//        return personService
//                .getPersonById(id)
//                .switchIfEmpty(Mono.error(new RuntimeException("Person not found")))
//                .flatMap(person -> Mono.just(ResponseEntity.ok(person)));
//    }
//
//    @PutMapping("/{id}")
//    public Mono<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
//        return personService.getPersonById(id)
//                .switchIfEmpty(Mono.error(new RuntimeException("Person not found")))
//                .map(existingPerson -> {
//                    if (person.getName() != null) {
//                        existingPerson.setName(person.getName());
//                    }
//                    if (person.getAge() != null) {
//                        existingPerson.setAge(person.getAge());
//                    }
//                    if (person.getGender() != null) {
//                        existingPerson.setGender(person.getGender());
//                    }
//                    if (person.getDateOfBirth() != null) {
//                        existingPerson.setDateOfBirth(person.getDateOfBirth());
//                    }
//                    if (person.getBloodType() != null) {
//                        existingPerson.setBloodType(person.getBloodType());
//                    }
//                    return existingPerson;
//                })
//                .flatMap(personService::updatePerson);
//    }
//
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable Long id) {
//        return personService.getPersonById(id)
//                .switchIfEmpty(Mono.error(new RuntimeException("Person not found")))
//                .then(personService.deletePerson(id))
//                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
//    }
//
//}
