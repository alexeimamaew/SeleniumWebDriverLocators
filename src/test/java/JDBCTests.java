import Database.*;
import Database.Jdbc.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class JDBCTests {

    @BeforeAll
    static void init() {
        System.out.println("JDBCTests: Starting @BeforeAll initialization.");
        try {
            DatabaseConnection.createConnection();
            DatabaseUtils.createData();
            System.out.println("JDBCTests: Database initialization complete.");
        } catch (Exception e) {
            System.err.println("JDBCTests: Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @AfterAll
    static void tearDown() {
        System.out.println("JDBCTests: Starting @AfterAll cleanup.");
        DatabaseConnection.closeConnection();
        System.out.println("JDBCTests: Database connection closed.");
    }

    @Test
    void insertZooDataTest() throws SQLException {
        int initialZooCount = CRUDUtils.getZooCountRow();
        int newZooId = 4;
        String newZooName = "Зоопарк 'Южный'";
        Zoo newZoo = new Zoo(newZooId, newZooName);
        boolean insertSuccessful = CRUDUtils.insertZooData(newZoo);
        assertTrue(insertSuccessful, "Ошибка при добавлении нового зоопарка.");
        System.out.println("ID добавленной записи зоопарка: " + newZoo.getId());
        assertEquals(initialZooCount + 1, CRUDUtils.getZooCountRow(), "Количество зоопарков должно увеличиться на 1.");
        List<String> zooNames = CRUDUtils.getZooNameData();
        assertThat(zooNames, containsInAnyOrder("Центральный", "Северный", "Западный", newZooName));
}
    @Test
    void updateZooData() throws SQLException {
        int zooIdToUpdate = 1;
        String newName = "Зоопарк 'ЦентральныйПарк";
        Zoo zooToUpdate = new Zoo(zooIdToUpdate, newName);
        boolean updateResult = CRUDUtils.updateZooData(zooToUpdate);
        assertTrue(updateResult, "Failed to update zoo data");
    }

    @Test
    void insertPlacesCountRow() throws SQLException {
        Places place = new Places(6, 1, 185, "Загон 1");
        CRUDUtils.getPlacesCountRow();
        System.out.println(CRUDUtils.insertPlacesData(place));
        Assertions.assertEquals(6, CRUDUtils.getPlacesCountRow());
    }

   @Test
    void checkRowCountForAnimal() throws SQLException {
        int actualAnimalCountRow = CRUDUtils.getRowCountByTable("animal");
        Assertions.assertEquals(10, actualAnimalCountRow);
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

    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        int countBefore = CRUDUtils.getAnimalCountRow();
        Throwable exception = assertThrows(Exception.class, () -> CRUDUtils.insertAnimalData(animal));
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("PRIMARY KEY ON PUBLIC.ANIMAL(ID)");
        int countAfter = CRUDUtils.getAnimalCountRow();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void deleteAnimalDataTest() throws SQLException {
        int animalIdToDelete = 111;
        int zooId = 1;
        Animal animalToDelete = new Animal(animalIdToDelete, "Зверек", 2, 1, 1, zooId);
        int initialCount = CRUDUtils.getAnimalCountRow();
        CRUDUtils.insertAnimalData(animalToDelete);
        assertEquals(initialCount + 1, CRUDUtils.getAnimalCountRow(), "Животное не было добавлено.");
        boolean deleteResult = CRUDUtils.deleteAnimalData(animalIdToDelete);
        assertTrue(deleteResult, "Failed to delete animal data with ID: " + animalIdToDelete);
        assertEquals(initialCount, CRUDUtils.getAnimalCountRow(), "Количество животных не уменьшилось после удаления.");
        assertFalse(CRUDUtils.deleteAnimalData(1100), "Удаление несуществующего животного должно вернуть false.");
    }

    @Test
    void insertNullToWorkman() {
        Assertions.assertFalse(CRUDUtils.insertWorkmanData());
        System.out.println(CRUDUtils.insertWorkmanData());
    }

    @Test
    void countRowZoo() throws SQLException {
        List<String> expectedNames = Arrays.asList("Центральный", "Северный", "Западный");
        int actualZooCountRow = CRUDUtils.getRowCountByTable("zoo");
        Assertions.assertEquals(3, actualZooCountRow);
        List<String> actualNames = CRUDUtils.getZooNameData();
        assertThat(actualNames, containsInAnyOrder(expectedNames.toArray()));
    }
}