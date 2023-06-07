package ru.fabrichnaya.rentalcarsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fabrichnaya.rentalcarsapp.models.Car;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {
    List<Car> findByNameStartingWith(String query);
}
