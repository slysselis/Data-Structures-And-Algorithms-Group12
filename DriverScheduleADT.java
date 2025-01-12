import java.util.*;

public class DriverScheduleADT {
    private Map<String, List<String>> driverRoutes;
    private Set<String> availableDrivers;

    /*public DriverScheduleADT() {
        this.driverRoutes = new HashMap<>();
        this.availableDrivers = new HashSet<>();
    }*/

    public void addDriver(String driverID) {
        availableDrivers.add(driverID);
    }

    public void assignDriverRoute(String driverID, List<String> route) {
        if (availableDrivers.contains(driverID)) {
            driverRoutes.put(driverID, route);
            availableDrivers.remove(driverID);
        }
    }

    public boolean isAvailable(String driverID) {
        return availableDrivers.contains(driverID);
    }

    public Set<String> getAvailableDrivers() {
        return new HashSet<>(availableDrivers);
    }

    public List<String> getAssignedRoute(String driverID) {
        return driverRoutes.getOrDefault(driverID, null);
    }

    public void removeDriver(String driverID) {
        availableDrivers.remove(driverID);
        driverRoutes.remove(driverID);
    }
    private Map<String, Set<String>> driverAvailability; // Map of driver to available dates
    
    public DriverScheduleADT() {
        this.driverRoutes = new HashMap<>();
        this.availableDrivers = new HashSet<>();
        this.driverAvailability = new HashMap<>();
    }

    

    
    public void addDriverAvailability(String driverID, String date) {
        driverAvailability.putIfAbsent(driverID, new HashSet<>());
        driverAvailability.get(driverID).add(date);
    }
    
    public void removeDriverAvailability(String driverID, String date) {
        if (driverAvailability.containsKey(driverID)) {
            driverAvailability.get(driverID).remove(date);
        }
    }
    
    public boolean isDriverAvailableOn(String driverID, String date) {
        return driverAvailability.containsKey(driverID) && driverAvailability.get(driverID).contains(date);
    }

    public Map<String, DeliveryRequest> createDeliveryPlan(String string, DriverScheduleADT driverSchedule, DeliveryRequestADT deliveryRequestADT) {
        Map<String, DeliveryRequest> plan = new HashMap<>();
        
                List<DeliveryRequest> requests = deliveryRequestADT.getRequestsForDate(string);
        Set<String> availableDrivers = new HashSet<>(driverSchedule.getAvailableDrivers());

        for (DeliveryRequest request : requests) {
            if (!availableDrivers.isEmpty()) {
                String driver = availableDrivers.iterator().next(); // Assign a driver
                plan.put(driver, request);
                availableDrivers.remove(driver); // Driver is no longer available
            }
        }
        return plan;
    }

    

   

}
