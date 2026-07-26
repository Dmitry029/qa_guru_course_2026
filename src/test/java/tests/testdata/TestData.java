package tests.testdata;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestData {

    private final Faker faker = new Faker();

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getEmail() {
        return faker.internet().emailAddress();
    }

    public String getGender() {
        return faker.options().option("Male", "Female", "Other");
    }

    public String getMobile() {
        return faker.number().digits(10);
    }

    public String getDateOfBirth() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = faker.date().birthday();
        return formatter.format(date);
    }

    public String getSubject() {
        return faker.options().option("Maths", "Biology", "Computer Science", "Commerce", "Accounting", "Economics"
            , "Social Studies", "History", "Physics");
    }

    public String getHobby() {
        return faker.options().option("Sports", "Reading", "Music");
    }

    public String getFile() {
        return faker.options().option("smile1.jpg", "smile2.jpg", "smile3.jpg");
    }

    public String getAddress() {
        return faker.address().streetAddress();
    }

    public String getState() {
        return faker.options().option("NCR", "Uttar Pradesh", "Haryana", "Rajasthan");
    }

    public String getCity(String state) {
        List<String> cities = switch (state) {
            case "Rajasthan" -> List.of("Jaipur", "Jaiselmer");
            case "Haryana" -> List.of("Karnal", "Panipat");
            case "Uttar Pradesh" -> List.of("Agra", "Lucknow", "Merrut");
            case "NCR" -> List.of("Delhi", "Gurgaon", "Noida");
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
        int index = faker.number().numberBetween(0, cities.size());
        return cities.get(index);
    }

}
