package pl.piomin.services.repository;

import org.springframework.data.repository.CrudRepository;
import pl.piomin.services.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByNationality(String nationality);
}
