package ru.fabrichnaya.rentalcarsapp.services;

import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;

import java.util.List;

public interface CarsService {

    List<Car> findAll(boolean sortByYear);
    List<Car> findWithPagination(Integer page, Integer carsPerPage, boolean sortByYear);
    Car findOne(int id);
    List<Car> searchByName(String query);
    void save(Car car);
    void update(int id, Car updatedCar);
    void delete(int id);
    Person getCarTenant(int id);
    void release(int id);
    void assign(int id, Person selectedPerson);
}
