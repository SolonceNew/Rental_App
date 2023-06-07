package ru.fabrichnaya.rentalcarsapp.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.repositories.PeopleRepository;
import ru.fabrichnaya.rentalcarsapp.services.impL.PeopleServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PeopleServiceImpl peopleService;
    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private Date date = new Date(05/11/1987);

    private Person person;
    @Spy
    private Person updatedPerson = new Person( "name", "surname",date, "n@spring.ru", "Russia, Moscow, 196788");

    @BeforeEach
    public void setUp() {
        person = new Person("Alex", "Sur",date, "al@spring.ru", "Russia, Moscow, 196788");

    }


    @Test
    @DisplayName("JUnit test for findAll method")
    public void findAll_returnListOfCars() {
        Person person2 = new Person("Anna", "Vur",date, "an@spring.ru", "Russia, Moscow, 196788");

        given(peopleRepository.findAll()).willReturn(List.of(person, person2));
        List<Person> personList = peopleService.findAll();
        assertThat(personList).isNotNull();
        assertThat(personList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for findOne method")
    public void findOne_whenGiveId_returnPersonObject() {
        given(peopleRepository.findById(anyInt())).willReturn(Optional.of(person));
        Person savedPerson = peopleService.findOne(person.getId());
        assertThat(savedPerson).isNotNull();
    }

    @DisplayName("JUnit test for save method")
    @Test
    public void givenPersonObject_whenSavePerson_thenNothing() {
        willDoNothing().given(peopleRepository).save(person);
        peopleService.save(person);
        verify(peopleRepository, times(1)).save(person);

    }



    @DisplayName("JUnit test for delete method")
    @Test
    public void givenPersonId_whenDeletePerson_thenNothing(){
        int personId = 1;
        willDoNothing().given(peopleRepository).deleteById(personId);
        peopleService.delete(personId);
        verify(peopleRepository, times(1)).deleteById(personId);
    }











}
