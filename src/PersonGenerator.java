import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonGenerator {

    // Person class to hold the person data
    static class Person {
        final String id;
        final String firstName;
        final String lastName;
        final String title;
        final int yearOfBirth;

        public Person(String id, String firstName, String lastName, String title, int yearOfBirth) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.title = title;
            this.yearOfBirth = yearOfBirth;
    }

        @Override
        public String toString() {

            // Formats: id, firstName, lastName, title, yearOfBirth
            return id + ", " + firstName + ", " + lastName + ", " + title + ", " + yearOfBirth;
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Person> people = new ArrayList<>();

        System.out.println("Person Generator - Enter person data");

        boolean addMore = true;

        while (addMore) {

            // Gets inputs with validation
            String id = SafeInput.getNonZeroLenString(scanner, "ID");
            String firstName = SafeInput.getNonZeroLenString(scanner, "First Name");
            String lastName = SafeInput.getNonZeroLenString(scanner, "Last Name");

            // Gets title, allowing only a few options
            String title = SafeInput.getRegExString(scanner, "Title (Mr., Mrs., Ms., Dr., Esq.)",
                    "^(Mr\\.|Mrs\\.|Ms\\.|Dr\\.|Esq\\.)$");

            // Gets year of birth allowing a reasonable range, e.g. 900 to 2025
            int yearOfBirth = SafeInput.getRangedInt(scanner, "Year of Birth", 900, 2025);

            // Creates and adds Person object
            Person p = new Person(id, firstName, lastName, title, yearOfBirth);
            people.add(p);

            addMore = SafeInput.getYNConfirm(scanner, "Add another person?");
        }

        // Asks for filename to save
        String filename = SafeInput.getNonZeroLenString(scanner, "Enter filename to save:");

        // Saves to file
        Path file = Path.of(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Person p : people) {
                writer.write(p.toString());
                writer.newLine();
            }
            System.out.println("\nData saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        scanner.close();
    }
}