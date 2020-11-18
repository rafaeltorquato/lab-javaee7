package study.domain.model;

import java.util.List;

public interface PersonDao {

    void save(Person person);

    void delete(Person person);

    List<Person> list();

    Person findById(Long id);

}
