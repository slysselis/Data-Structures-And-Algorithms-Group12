import java.util.*;

public class ADTTest {

    public static void testCityMapADT() {
        CityMapADT cityMap = new CityMapADT();


        // Test 1: Add location
        cityMap.addLocation("A");
        cityMap.addLocation("B");
        System.out.println("Test 1. Add location: " + (cityMap.getAdjacencyList().containsKey("A") ? "PASS" : "FAIL"));


        // Test 2: Add distance
        cityMap.addDistance("A", "B", 5);
        System.out.println("Test 2. Add distance: " + (cityMap.getDistance("A", "B").equals("5 km") ? "PASS" : "FAIL"));


        // Test 3: Get adjacency list
        Map<String, Map<String, Integer>> adjacencyList = cityMap.getAdjacencyList();
        System.out.println("Test 3. Get adjacency list: " + (!adjacencyList.isEmpty() && adjacencyList.get("A").get("B") == 5 ? "PASS" : "FAIL"));


        // Test 4: Get distance
        String distance = cityMap.getDistance("A", "B");
        System.out.println("Test 4. Get adjacency list: " + (distance.equals("5 km") ? "PASS" : "FAIL"));


        // Test 5: Get distance (missing)
        distance = cityMap.getDistance("A", "C");
        System.out.println("Test 5. Get distance (missing): " + (distance.equals("No route available") ? "PASS" : "FAIL"));
    }

    public static void testDriverScheduleADT() {
        DriverScheduleADT driverSchedule = new DriverScheduleADT();


        // Test 1: Add driver
        driverSchedule.addDriver("Driver1");
        System.out.println("Test 1. Add driver: " + (driverSchedule.isAvailable("Driver1") ? "PASS" : "FAIL"));


        // Test 2: Assign driver to route
        driverSchedule.assignDriverRoute("Driver1", Arrays.asList("LocationA", "LocationB"));
        System.out.println("Test 2. Assign driver to route: " + (!driverSchedule.isAvailable("Driver1") ? "PASS" : "FAIL"));


        // Test 3: Check availability
        System.out.println("Test 3. Check availability: " + (!driverSchedule.isAvailable("Driver1") ? "PASS" : "FAIL"));


        // Test 4: Get assigned route
        List<String> route = driverSchedule.getAssignedRoute("Driver1");
        System.out.println("Test 4. Get assigned route: " + (route != null && route.contains("LocationA") ? "PASS" : "FAIL"));


        // Test 5: Remove driver
        driverSchedule.removeDriver("Driver1");
        System.out.println("Test 5. Remove driver: " + (driverSchedule.getAssignedRoute("Driver1") == null && !driverSchedule.isAvailable("Driver1") ? "PASS" : "FAIL"));
    }

    public static void testDeliveryRequestADT() {
        DeliveryRequestADT deliveryRequests = new DeliveryRequestADT();


        // Test 1: Add request
        deliveryRequests.addRequest("Pickup1", "Dropoff1", 1);
        List<DeliveryRequest> pending = deliveryRequests.getPendingRequests();
        System.out.println("Test 1. Add request: " + (!pending.isEmpty() && pending.get(0).getPickup().equals("Pickup1") ? "PASS" : "FAIL"));


        // Test 2: Get pending requests
        System.out.println("Test 2. Get pending requests: " + (pending.size() == 1 ? "PASS" : "FAIL"));


        // Test 3: Mark completed
        DeliveryRequest completedRequest = pending.get(0);
        deliveryRequests.markCompleted(completedRequest);
        System.out.println("Test 3. Mark completed: " + (deliveryRequests.getPendingRequests().isEmpty() ? "PASS" : "FAIL"));
    }

    public static void testDeliveryPlanCreation() {
        DriverScheduleADT driverSchedule = new DriverScheduleADT();
        DeliveryRequestADT deliveryRequestADT = new DeliveryRequestADT();

        // Add sample drivers and requests
        driverSchedule.addDriver("Driver1");
        driverSchedule.addDriver("Driver2");

        deliveryRequestADT.addRequest("Pickup1", "Dropoff1", 1, "2025-01-12");
        deliveryRequestADT.addRequest("Pickup2", "Dropoff2", 2, "2025-01-12");

        // Create a Delivery Plan
        Map<String, DeliveryRequest> plan = driverSchedule.createDeliveryPlan("2025-01-12", driverSchedule, deliveryRequestADT);

        // Test the plan generation
        System.out.println("Test 1. Delivery Plan Creation: " + (plan.size() == 2 ? "PASS" : "FAIL"));
    }

    public static void main(String[] args) {
        System.out.println("Testing CityMapADT...");
        testCityMapADT();


        System.out.println("\nTesting DriverScheduleADT...");
        testDriverScheduleADT();


        System.out.println("\nTesting DeliveryRequestADT...");
        testDeliveryRequestADT();

        System.out.println("\nTesting Delivery Plan Creation...");
        testDeliveryPlanCreation();
    }
}

