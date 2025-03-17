package pl.piomin.services.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.execution.DefaultToolCallResultConverter;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Person;
import pl.piomin.services.repository.PersonRepository;

import java.util.List;

@Service
public class PersonTools {

    private PersonRepository personRepository;

    public PersonTools(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Tool(description = "Find person by ID")
    public Person getPersonById(
            @ToolParam(description = "Person ID") Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Tool(description = "Find all persons by nationality")
    public List<Person> getPersonsByNationality(
            @ToolParam(description = "Nationality") String nationality) {
        return personRepository.findByNationality(nationality);
    }

}
