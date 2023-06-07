package ru.fabrichnaya.rentalcarsapp.services.impL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.repositories.CarsRepository;
import ru.fabrichnaya.rentalcarsapp.services.CarsService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarsServiceImpl implements CarsService {

    private final CarsRepository carsRepository;

    @Autowired
    public CarsServiceImpl(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @Override
    public List<Car> findAll(boolean sortByYear) {
        if (sortByYear)
            return carsRepository.findAll(Sort.by("yearOfIssue"));
        else
            return carsRepository.findAll();
    }

    @Override
    public List<Car> findWithPagination(Integer page, Integer carsPerPage, boolean sortByYear) {
        if (sortByYear)
            return carsRepository.findAll(PageRequest.of(page, carsPerPage, Sort.by("yearOfIssue"))).getContent();
        else
            return carsRepository.findAll(PageRequest.of(page, carsPerPage)).getContent();
    }

    @Override
    public Car findOne(int id) {
        Optional<Car> foundCar = carsRepository.findById(id);
        return foundCar.orElse(null);
    }

    @Override
    public List<Car> searchByName(String query) {
        return carsRepository.findByNameStartingWith(query);
    }

    @Override
    @Transactional
    public void save(Car car) {
        carsRepository.save(car);
    }

    @Override
    @Transactional
    public void update(int id, Car updatedCar) {
        Car carToBeUpdated = carsRepository.findById(id).get();

        updatedCar.setId(id);
        updatedCar.setTenant(carToBeUpdated.getTenant()); // чтобы не терялась связь при обновлении

        carsRepository.save(updatedCar);
    }

    @Override
    @Transactional
    public void delete(int id) {
        carsRepository.deleteById(id);
    }

    @Override
    public Person getCarTenant(int id) {
        // lazy for Person
        return carsRepository.findById(id).map(Car::getTenant).orElse(null);
    }


    @Override
    @Transactional
    public void release(int id) {
        carsRepository.findById(id).ifPresent(
                car -> {
                    car.setTenant(null);
                    car.setTakenAt(null);
                });
    }

    @Override
    @Transactional
    public void assign(int id, Person selectedPerson) {
        carsRepository.findById(id).ifPresent(
                car -> {
                    car.setTenant(selectedPerson);
                    car.setTakenAt(new Date());
                }
        );
    }
}
