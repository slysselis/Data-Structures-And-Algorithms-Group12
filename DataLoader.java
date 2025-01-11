import java.io.*;


public class DataLoader {

    // Load locations into CityMapADT
    public static void loadLocations(String fileName, CityMapADT cityMap) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cityMap.addLocation(line.trim());
            }
        }
    }

    // Load distances into CityMapADT
    public static void loadDistances(String fileName, CityMapADT cityMap) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String location1 = parts[0].trim();
                String location2 = parts[1].trim();
                int distance = Integer.parseInt(parts[2].trim());
                cityMap.addDistance(location1, location2, distance);
            }
        }
    }

    // Load drivers into DriverScheduleADT
    public static void loadDrivers(String fileName, DriverScheduleADT driverSchedule) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                driverSchedule.addDriver(line.trim());
            }
        }
    }

    // Load delivery requests into DeliveryRequestADT
    public static void loadRequests(String fileName, DeliveryRequestADT deliveryRequests) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String pickup = parts[0].trim();
                String dropoff = parts[1].trim();
                int priority = Integer.parseInt(parts[2].trim());
                deliveryRequests.addRequest(pickup, dropoff, priority);
            }
        }
    }
}