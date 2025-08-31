import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PersonReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Uses JFileChooser to let the user pick the file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a person data file");

        int result = fileChooser.showOpenDialog(null); // Shows the file picker

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath(); // Gets the file path
            System.out.println("Selected file: " + filePath);

            // Uses SafeInput to confirm loading the file
            boolean proceed = SafeInput.getYNConfirm(scanner, "Do you want to load this file");
            if (!proceed) {
                System.out.println("File loading cancelled.");
                scanner.close();
                return; // Exits program
            }

            // Prints the column headers
            String header = String.format("%-7s %-11s %-11s %-8s %-4s", "ID#", "First Name", "Last Name", "Title", "YOB");
            System.out.println(header);
            System.out.println("======================================================");

            // Reads and displays file
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length == 5) {
                        String id = parts[0].trim();
                        String firstName = parts[1].trim();
                        String lastName = parts[2].trim();
                        String title = parts[3].trim();
                        int yearOfBirth = Integer.parseInt(parts[4].trim());

                        System.out.printf("%-7s %-11s %-11s %-8s %-4d\n", id, firstName, lastName, title, yearOfBirth);
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format in file: " + e.getMessage());
            }

        } else {
            System.out.println("No file was selected.");
        }

        scanner.close();
    }
}