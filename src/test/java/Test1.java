import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Car;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test1 {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String PATH = "src/test/resources/";

    @Test
    void createJsonFileFromObject() throws IOException {
        Car car = new Car("Blue", "Vehicle");
        objectMapper.writeValue(new File(PATH + "example.json"), car);
    }

    @Test
    void readJsonFromStringToObject() throws IOException {
        String jsonString = "{\n" +
                "  \"color\": \"Blue\",\n" +
                "  \"type\": \"Vehicle\"\n" +
                "}";
        Car car = objectMapper.readValue(jsonString, Car.class);
        assertEquals("Blue", car.getColor());
        assertEquals("Vehicle", car.getType());
    }

    @Test
    void readJsonFromFileToObject() throws IOException {
        Car car = objectMapper.readValue(new File(PATH + "example.json"), Car.class);
        assertEquals("Blue", car.getColor());
        assertEquals("Vehicle", car.getType());
    }

    @Test
    void readAJsonArrayAnCreateAList() throws IOException {
        List<Car> cars = objectMapper.readValue(new File(PATH+"listOfUsers.json"), new TypeReference<List<Car>>(){});
        assertEquals(2, cars.size());
    }

    @Test
    void avoidExceptionWhenPropertyDoesNotExistsInObject() throws IOException {
        String jsonString = "{\n" +
                "  \"color\": \"Blue\",\n" +
                "  \"Wheels\": \"4\",\n" +
                "  \"type\": \"Vehicle\"\n" +
                "}";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = objectMapper.readValue(jsonString, Car.class);
        assertEquals("Blue", car.getColor());
        assertEquals("Vehicle", car.getType());
    }
}
