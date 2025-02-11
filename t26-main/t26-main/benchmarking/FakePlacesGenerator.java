import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner; // Import the Scanner class for user input

public class FakePlacesGenerator {

    // Function to generate fake places
    public static List<String> generateFakePlaces(int numPlaces) {
        List<String> fakePlaces = new ArrayList<>();
        Random random = new Random();

        // Sample data for generating random names, municipalities, and regions
        String[] names = {
                "Dreamland Brewery", "Mystic Ales", "Whimsical Wines", "Fantasy Brew Co",
                "Magic Elixir", "Enchanted Spirits", "Fairy Tale Brewing", "Neverland Suds",
                "Wishing Well Brewing", "Fable Fermentations"
        };

        String[] municipalities = {
                "Happytown", "Wonderland", "Frolicking Fields", "Joyville",
                "Dreamsville", "Magic Meadows", "Fairy Hollow", "Fantasia",
                "Adventure Bay", "Sunshine City"
        };

        String[] regions = {
                "Imaginary", "Fictional", "Fantasyland", "Magical Realm",
                "Dreamworld", "Utopia", "Elysium", "Neverland",
                "Wonderscape", "Mystical Lands"
        };

        // Generate fake places
        for (int i = 0; i < numPlaces; i++) {
            String place = String.format("{\n" +
                    "  \"name\": \"%s\",\n" +
                    "  \"municipality\": \"%s\",\n" +
                    "  \"region\": \"%s\",\n" +
                    "  \"country\": \"Neverland\",\n" + // Include index in country
                    "  \"latitude\": \"%s\",\n" + // Quotation marks for coordinates
                    "  \"longitude\": \"%s\"\n" + // Quotation marks for coordinates
                    "}",
                    names[random.nextInt(names.length)],
                    municipalities[random.nextInt(municipalities.length)],
                    regions[random.nextInt(regions.length)],
                    String.format("%.6f", random.nextDouble() * 180 - 90), // Valid latitude from -90 to 90
                    String.format("%.6f", random.nextDouble() * 360 - 180) // Valid longitude from -180 to 180
            );
            fakePlaces.add(place);
        }

        return fakePlaces;
    }

    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for input

        System.out.print("Enter the number of fake places to generate: "); // Prompt for input
        int numEntries = scanner.nextInt(); // Read user input

        // Generate the specified number of fake places
        List<String> fakePlaces = generateFakePlaces(numEntries);

        // Prepare the final output format
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("{");
        outputBuilder.append("\"earthRadius\": 6378.0,\n");
        outputBuilder.append("\"requestType\": \"tour\",\n");
        outputBuilder.append("\"response\": 1.0,\n");
        outputBuilder.append("\"places\": [\n");
        
        // Append each place to the output
        for (String place : fakePlaces) {
            outputBuilder.append("  ").append(place).append(",\n");
        }
        
        // Remove the last comma and newline
        if (fakePlaces.size() > 0) {
            outputBuilder.setLength(outputBuilder.length() - 2); // Remove last comma and newline
        }
        
        outputBuilder.append("\n]\n}");

        // Save to a text file
        String outputFilePath = "fake_places_neverland.txt";
        try (FileWriter file = new FileWriter(outputFilePath)) {
            file.write(outputBuilder.toString());
            System.out.println("Output file created: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
}
