package tests.testdata;


import net.datafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestData {

    private final Faker faker = new Faker();

    public final String firstName = faker.name().firstName();
    public final String lastName = faker.name().lastName();
    public final String email = faker.internet().emailAddress();
    public final String gender = faker.options().option("Male", "Female", "Other");
    public final String mobile = faker.number().digits(10);
    public final String dateOfBirth = getDateOfBirth();
    public final String subject = faker.options().option("Maths", "Biology", "Computer Science", "Commerce", "Accounting", "Economics"
        , "Social Studies", "History", "Physics");
    public final String hobby = faker.options().option("Sports", "Reading", "Music");
    public final String fileName = faker.options().option("smile1.jpg", "smile2.jpg", "smile3.jpg");
    public final String address = faker.address().streetAddress();
    public final String state = faker.options().option("NCR", "Uttar Pradesh", "Haryana", "Rajasthan");
    public final String city = getCity(state);

    private String getDateOfBirth() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = faker.date().birthday();
        return formatter.format(date);
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
