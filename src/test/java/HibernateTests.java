import Database.DatabaseUtils;
import Database.Hibernate.DBHibernateService;
import Database.Hibernate.Models.Animal;
import Database.Hibernate.Models.Places;
import Database.Hibernate.Models.Workman;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.sql.SQLException;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HibernateTests {
    DBHibernateService dbHibernateService = new DBHibernateService();

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        DatabaseUtils.dropData();
        DatabaseUtils.createData();
        dbHibernateService = new DBHibernateService();
    }

    @Test
    void countRowAnimal() {
        Assertions.assertEquals(10, dbHibernateService.getAnimalCount());
    }

    static Stream<Animal> animalProvider() {
        List<Animal> animals = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setName("Sharik");
            animal.setAge(10);
            animal.setType(1);
            animal.setSex(1);
            animal.setPlace(1);
            animals.add(animal);
        }
        return animals.stream();
    }

    @Test
    void insertAnimal() {
        int sizeBefore = dbHibernateService.getAnimalCount();
       Animal animal = new Animal();
       animal.setName("Борис");
       animal.setAge(7);
       animal.setType(1);
       animal.setSex(1);
       animal.setPlace(1);
       dbHibernateService.insertAnimal(animal);
       Assertions.assertEquals(sizeBefore + 1, dbHibernateService.getAnimalCount());
    }

    @Test
    void updateAnimal() {
        Animal animalToUpdate = dbHibernateService.getAnimalByName("Бусинка"); // ID 1
        Assertions.assertNotNull(animalToUpdate);
        Assertions.assertEquals(2, animalToUpdate.getAge());
        animalToUpdate.setName("Бусинка Обновленная");
        animalToUpdate.setAge(3);
        animalToUpdate.setPlace(2);
        dbHibernateService.updateAnimal(animalToUpdate);
        Animal updatedAnimal = dbHibernateService.getAnimalByName("Бусинка Обновленная");
        Assertions.assertNotNull(updatedAnimal);
        Assertions.assertEquals(animalToUpdate.getId(), updatedAnimal.getId());
        Assertions.assertEquals("Бусинка Обновленная", updatedAnimal.getName());
        Assertions.assertEquals(3, updatedAnimal.getAge());
        Assertions.assertEquals(2, updatedAnimal.getPlace());
        Assertions.assertThrows(NoResultException.class, () -> dbHibernateService.getAnimalByName("Бусинка"));
    }

    @Test
    void deleteAnimalById() {
        int initialCount = dbHibernateService.getAnimalCount();
        Animal newAnimal = new Animal();
        newAnimal.setName("Временный_для_удаления");
        newAnimal.setAge(1);
        newAnimal.setType(1);
        newAnimal.setSex(1);
        newAnimal.setPlace(1);
        dbHibernateService.insertAnimal(newAnimal);
        Assertions.assertEquals(initialCount + 1, dbHibernateService.getAnimalCount());
        Animal animalToDelete = dbHibernateService.getAnimalByName("Временный_для_удаления");
        Assertions.assertNotNull(animalToDelete);
        int idToDelete = animalToDelete.getId();
        dbHibernateService.deleteAnimalById(idToDelete);
        Assertions.assertEquals(initialCount, dbHibernateService.getAnimalCount());
        Assertions.assertThrows(NoResultException.class, () -> dbHibernateService.getAnimalByName("Временный_для_удаления"));
        dbHibernateService.deleteAnimalById(99999);
        Assertions.assertEquals(initialCount, dbHibernateService.getAnimalCount());
    }

    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertAnimal(animal));
    }

    @Test
    void insertNullToWorkman() {
        Workman workman = new Workman();
        workman.setId(88);
        workman.setName(null);
        workman.setAge(12);
        workman.setPosition(1);
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertWorkman(workman));
    }

    @Test
    void insertPlacesCountRow() {
        int sizeBefore = dbHibernateService.getPlacesCount();
        Places places = new Places();
        places.setId(6);
        places.setRow(1);
        places.setPlace_num(185);
        places.setName("Загон 1");
        dbHibernateService.insertPlaces(places);
        Assertions.assertEquals(sizeBefore + 1, dbHibernateService.getPlacesCount());
    }

    @Test
    void countRowZoo() {
        List<String> expectedNames = Arrays.asList("Центральный", "Северный", "Западный");

        int actualZooCountRow = dbHibernateService.getZooCount();
        Assertions.assertEquals(3, actualZooCountRow);

        List<String> actualNames = dbHibernateService.getZooNameData();
        assertThat(actualNames, containsInAnyOrder(expectedNames.toArray()));
    }

    @Test
    void deleteWorkmanByIdTest() {
        int workmanId = 1;

        assertThrows(ConstraintViolationException.class, () -> dbHibernateService.deleteWorkmanById(workmanId));
    }
}
