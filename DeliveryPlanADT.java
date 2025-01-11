import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeliveryPlanADT {

    public Map<String, DeliveryRequest> createDeliveryPlan(String date, DriverScheduleADT driverSchedule, DeliveryRequestADT deliveryRequestADT) {
        Map<String, DeliveryRequest> plan = new HashMap<>();
        List<DeliveryRequest> requests = deliveryRequestADT.getRequestsForDate(date);
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
