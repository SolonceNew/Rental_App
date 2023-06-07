package ru.fabrichnaya.rentalcarsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fabrichnaya.rentalcarsapp.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByName(String name);
    Optional<Person> findBySurname(String surname);
    Optional<Person> findByEmail(String email);
}
