package ru.fabrichnaya.rentalcarsapp.services.impL;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.repositories.PeopleRepository;
import ru.fabrichnaya.rentalcarsapp.services.PeopleService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleServiceImpl implements PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Override
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


    @Override
    public Optional<Person> getPersonByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    @Override
    public List<Car> getCarsByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getCars());

            // Проверка срока просрочки машины
            person.get().getCars().forEach(car -> {
                long diffInMillies = Math.abs(car.getTakenAt().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (diffInMillies > 864000000)
                    car.setExpired(true); // срок аренды машины просрочен
            });

            return person.get().getCars();
        }
        else {
            return Collections.emptyList();
        }
    }
}
