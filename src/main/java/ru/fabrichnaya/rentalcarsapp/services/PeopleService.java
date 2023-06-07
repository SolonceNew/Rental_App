package ru.fabrichnaya.rentalcarsapp.services;

import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleService {

    List<Person> findAll();
    Person findOne(int id);
    void save(Person person);
    void update(int id, Person updatedPerson);
    void delete(int id);
    Optional<Person> getPersonByEmail(String email);
    List<Car> getCarsByPersonId(int id);

}
