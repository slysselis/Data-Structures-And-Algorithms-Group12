import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class DeliverySystem {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(DeliverySystem.class.getName());

    public static void main(String[] args) {
        // Check if data files exist; if not, generate data
        ensureDataFilesExist();

        // Load data from files
        CityMapADT cityMap = setupCityMap();
        DriverScheduleADT driverSchedule = setupDriverSchedule();
        DeliveryRequestADT deliveryRequests = setupDeliveryRequests();

        while (true) {
            displayMenu();
            int choice = getValidMenuChoice(1, 4);
            switch (choice) {
                case 1:
                    viewPendingRequests(cityMap, deliveryRequests);
                    break;
                case 2:
                    viewDrivers(driverSchedule);
                    break;
                case 3:
                    viewCityMap(cityMap);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    
    // Ensure data files exist, generating them if necessary
    private static void ensureDataFilesExist() {
        if (!new File("citymap.txt").exists() || 
            !new File("drivers.txt").exists() || 
            !new File("requests.txt").exists()) {
            generateAndStoreData();
        }
    }

    

    // Generate and store semi-random data
    private static void generateAndStoreData() {
        int numLocations = 10; // Number of locations
        int numDrivers = 5;    // Number of drivers
        int numRequests = 10;  // Number of delivery requests
        Random rand = new Random();
    
        // Generate and store city map
        try (FileWriter cityMapWriter = new FileWriter("citymap.txt")) {
            // Write locations including the "Warehouse"
            cityMapWriter.write("Warehouse\n");
            for (int i = 1; i <= numLocations; i++) {
                cityMapWriter.write("Location" + i + "\n");
            }
    
            
    
            // Generate distances from the Warehouse to each Location
            for (int i = 1; i <= numLocations; i++) {
                int distance = rand.nextInt(20) + 1; // Distance between 1 and 20
                cityMapWriter.write("Warehouse Location" + i + " " + distance + "\n");
            }
    
            // Generate distances between other locations
            for (int i = 1; i <= numLocations; i++) {
                for (int j = i + 1; j <= numLocations; j++) {
                    int distance = rand.nextInt(20) + 1; // Distance between 1 and 20
                    cityMapWriter.write("Location" + i + " Location" + j + " " + distance + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to citymap.txt: " + e.getMessage());
        }
    
        // Generate and store driver schedule
        try (FileWriter driversWriter = new FileWriter("drivers.txt")) {
            for (int i = 1; i <= numDrivers; i++) {
                driversWriter.write("Driver" + i + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to drivers.txt: " + e.getMessage());
        }
    
        // Generate and store delivery requests
        try (FileWriter requestsWriter = new FileWriter("requests.txt")) {
            for (int i = 0; i < numRequests; i++) {
                String pickup = "Warehouse";
                String dropoff = "Location" + (rand.nextInt(numLocations) + 1);
                int priority = rand.nextInt(3) + 1; // Priority between 1 and 3
                requestsWriter.write(pickup + " " + dropoff + " " + priority + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to requests.txt: " + e.getMessage());
        }
    }

    // File-based setup methods (unchanged)
    private static CityMapADT setupCityMap() {
        return loadCityMapFromFile("citymap.txt");
    }

    private static DriverScheduleADT setupDriverSchedule() {
        return loadDriversFromFile("drivers.txt");
    }

    private static DeliveryRequestADT setupDeliveryRequests() {
        return loadRequestsFromFile("requests.txt");
    }

    // Other methods (unchanged)
    private static CityMapADT loadCityMapFromFile(String fileName) {
        CityMapADT cityMap = new CityMapADT();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("\\w+ \\w+ \\d+")) { // Matches distances (e.g., "Warehouse Customer1 5")
                    String[] parts = line.split(" ");
                    cityMap.addDistance(parts[0], parts[1], Integer.parseInt(parts[2]));
                } else { // Locations
                    cityMap.addLocation(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("City map file not found: " + fileName);
        }
        return cityMap;
    }

    private static DriverScheduleADT loadDriversFromFile(String fileName) {
        DriverScheduleADT driverSchedule = new DriverScheduleADT();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                driverSchedule.addDriver(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Driver file not found: " + fileName);
        }
        return driverSchedule;
    }

    private static DeliveryRequestADT loadRequestsFromFile(String fileName) {
        DeliveryRequestADT deliveryRequests = new DeliveryRequestADT();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                String pickup = parts[0];
                String dropoff = parts[1];
                int priority = Integer.parseInt(parts[2]);
                deliveryRequests.addRequest(pickup, dropoff, priority);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Requests file not found: " + fileName);
        }
        return deliveryRequests;
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. View Pending Requests");
        System.out.println("2. View Available Drivers");
        System.out.println("3. View City Map");
        System.out.println("4. Exit");
    }

    private static int getValidMenuChoice(int min, int max) {
        while (true) {
            try {
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static void viewPendingRequests(CityMapADT cityMap, DeliveryRequestADT deliveryRequests) {
        List<DeliveryRequest> requests = deliveryRequests.getPendingRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        System.out.println("\nPending Requests:");
        for (int i = 0; i < requests.size(); i++) {
            DeliveryRequest req = requests.get(i);
            System.out.println((i + 1) + ". " + req.getDropoff() + " (" +
                    cityMap.getDistance(req.getPickup(), req.getDropoff()) + " from Warehouse)");
        }
    }

    private static void viewDrivers(DriverScheduleADT driverSchedule) {
        System.out.println("\nAvailable Drivers:");
        for (String driver : driverSchedule.getAvailableDrivers()) {
            System.out.println(driver);
        }
    }

    private static void viewCityMap(CityMapADT cityMap) {
        System.out.println("\nCity Map (Distances):");
        Map<String, Map<String, Integer>> adjacencyList = cityMap.getAdjacencyList();
        for (String location : adjacencyList.keySet()) {
            for (Map.Entry<String, Integer> entry : adjacencyList.get(location).entrySet()) {
                System.out.println(location + " -> " + entry.getKey() + ": " + entry.getValue() + " km");
            }
        }
    }

    @SuppressWarnings("unused")
    private static void processRequests(CityMapADT cityMap, DeliveryRequestADT deliveryRequests, DriverScheduleADT driverSchedule) {
        if (!checkDriverAvailability(driverSchedule, "Driver1")) {
            System.out.println("No available drivers to process deliveries.");
            return;
        }

        List<DeliveryRequest> requests = deliveryRequests.getPendingRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending requests to process.");
            return;
        }

        int choice = getValidRequestChoice(requests);
        DeliveryRequest selected = requests.get(choice - 1);
        System.out.println("Processing delivery to: " + selected.getDropoff());
        System.out.println("Route: " + selected.getPickup() + " -> " + selected.getDropoff());
        System.out.println("Distance: " + cityMap.getDistance(selected.getPickup(), selected.getDropoff()));
        deliveryRequests.markCompleted(selected);
        logAction("Processed delivery to " + selected.getDropoff());
    }

    private static boolean checkDriverAvailability(DriverScheduleADT driverSchedule, String driverID) {
        if (!driverSchedule.isAvailable(driverID)) {
            return false;
        }
        System.out.println("Driver " + driverID + " is available.");
        return true;
    }

    private static int getValidRequestChoice(List<DeliveryRequest> requests) {
        while (true) {
            try {
                System.out.print("Select a request to process (1 to " + requests.size() + "): ");
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= requests.size()) {
                    return choice;
                }
                System.out.println("Invalid choice. Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static void logAction(String action) {
        LOGGER.info(action);
    }
}