import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProductReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Uses JFileChooser to let the user pick the file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a product data file");

        int result = fileChooser.showOpenDialog(null); // Open file dialog

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();// Gets the file path
            System.out.println("Selected file: " + filePath);

            // Uses SafeInput to confirm loading the file
            boolean confirm = SafeInput.getYNConfirm(scanner, "Do you want to load this file?");
            if (!confirm) {
                System.out.println("File loading cancelled.");
                scanner.close();
                return; // Exits program
            }

            // Prints the column headers
            String header = String.format("\n%-8s %-12s %-30s %s", "ID", "Name", "Description", "Cost");
            System.out.println(header);
            System.out.println("======================================================");

            // Reads and displays the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length == 4) {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        String description = parts[2].trim();
                        double cost = Double.parseDouble(parts[3].trim());

                        // Format and display each product line
                        String formattedLine = String.format("%-8s %-12s %-30s %.1f", id, name, description, cost);
                        System.out.println(formattedLine);
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }

            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid cost format: " + e.getMessage());
            }

        } else {
            System.out.println("No file was selected.");
        }

        scanner.close();
    }
}
