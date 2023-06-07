package ru.fabrichnaya.rentalcarsapp.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.repositories.CarsRepository;
import ru.fabrichnaya.rentalcarsapp.services.impL.CarsServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {
    @InjectMocks
    private CarsServiceImpl carsService;
    @Mock
    private CarsRepository carsRepository;
    @Mock
    private Date dateOfBirth = new Date(05/11/1987);
    private Car car;
    @Spy
    private Car car2 = new Car("Citroen", "чёрный", 2022);;

    @BeforeEach
    public void setUp() {
        car = new Car("BMW", "чёрный", 2022);

    }

    @Test
    @DisplayName("JUnit test for findAll method")
    public void findAll_falseSort_returnListOfCars() {
        Car car3 = new Car("kia", "белая", 2010);

        given(carsRepository.findAll()).willReturn(List.of(car, car3));
        List<Car> carsList = carsService.findAll(false);
        assertThat(carsList).isNotNull();
        assertThat(carsList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for findWithPagination")
    public void findAll_falseSortAndWithPages_returnListOfCars() {
        Car car2 = new Car("kia", "белая", 2010);
        Car car3 = new Car("toyota", "белая", 2010);
        Car car4 = new Car("toyota", "красная", 2014);
        final PageRequest pageRequest = mock(PageRequest.class);
        when(carsRepository.findAll(PageRequest.of(2,2)).getContent()).thenReturn(List.of(
                car, car2));

        List<Car> carsList = carsService.findWithPagination(2,
                2, false);
        assertThat(carsList).isNotNull();
        assertThat(carsList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for findOne method")
    public void findOne_whenGiveId_returnCarObject() {
        given(carsRepository.findById(anyInt())).willReturn(Optional.of(car));
        Car savedCar = carsService.findOne(car.getId());
        assertThat(savedCar).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for findOne method")
    public void searchByName_whenGivenQuery_returnListOfCars() {
        Car car2 = new Car("BMW", "белая", 2010);
        given(carsRepository.findByNameStartingWith(anyString())).willReturn(List.of(car, car2));
        List<Car> cars = carsService.searchByName("BM");
        assertThat(cars).isNotNull();
        assertThat(cars.size()).isEqualTo(2);

    }

    @DisplayName("JUnit test for update method")
    @Test
    public void givenCarObjectAndId_whenUpdateCar_thenNothing(){
        given(carsRepository.findById(anyInt()).get()).willReturn(car);
        willDoNothing().given(carsRepository).save(car);
        car2.setId(1);
        car2.setTenant(new Person("Alex", "Sur", dateOfBirth,
                "al@spring.ru", "M, bo, 189667"));
       carsService.update(1, car);

       verify(carsRepository, times(1)).save(car);
    }

    @DisplayName("JUnit test for save method")
    @Test
    public void givenCarObject_whenSaveCar_thenNothing() {
        willDoNothing().given(carsRepository).save(car);
        carsService.save(car);
        verify(carsRepository, times(1)).save(car);

    }

    @DisplayName("JUnit test for delete method")
    @Test
    public void givenCarId_whenDeleteCar_thenNothing(){
        int carId = 1;
        willDoNothing().given(carsRepository).deleteById(carId);
        carsService.delete(carId);
        verify(carsRepository, times(1)).deleteById(carId);
    }

    @DisplayName("JUnit test getCarTenant method")
    @Test
    public void givenCarId_whenFindById_thenReturnPersonTenant() {
        given(carsRepository.findById(anyInt())).willReturn(Optional.of(car2));
        given(car2.getTenant()).willReturn(new Person("Alex", "Sur",
               dateOfBirth, "al@spring.ru", "Russia, Moscow, 1989098"));
        Person tenant = carsService.getCarTenant(1);
        assertThat(tenant).isNotNull();
    }

    @DisplayName("JUnit test for realise method")
    @Test
    public void givenCarId_whenFindByIdSetTenantNullSetTakenAtNull_thenDoNothing() {
        given(carsRepository.findById(1)).willReturn(Optional.of(car2));
        willDoNothing().given(car2).setTenant(null);
        willDoNothing().given(car2).setTakenAt(null);
        carsService.release(1);
        verify((carsRepository), times(1)).findById(1);
        verify((car2), times(1)).setTenant(null);
        verify((car2), times(1)).setTakenAt(null);
    }









}
