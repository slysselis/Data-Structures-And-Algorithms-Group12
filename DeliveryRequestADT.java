import java.util.*;

class DeliveryRequest {
    private String pickup;
    private String dropoff;
    private int priority;

    public DeliveryRequest(String pickup, String dropoff, int priority) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.priority = priority;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public int getPriority() {
        return priority;
    }
}

class DeliveryRequestADT {
    private List<DeliveryRequest> requestList;
    private Map<String, List<DeliveryRequest>> requestsByDate;

    public DeliveryRequestADT() {
        this.requestList = new ArrayList<>(); // Initialize the list
        this.requestsByDate = new HashMap<>(); // Initialize the map
    }

    public void addRequest(String pickup, String dropoff, int priority) {
        requestList.add(new DeliveryRequest(pickup, dropoff, priority));
    }

    

    public List<DeliveryRequest> getPendingRequests() {
        return new ArrayList<>(requestList);
    }

    public void markCompleted(DeliveryRequest request) {
        requestList.remove(request);
    }
    
    
    public void addRequest(String pickup, String dropoff, int priority, String date) {
        requestsByDate.putIfAbsent(date, new ArrayList<>());
        requestsByDate.get(date).add(new DeliveryRequest(pickup, dropoff, priority));
    }
    
    public List<DeliveryRequest> getRequestsForDate(String date) {
        return requestsByDate.getOrDefault(date, new ArrayList<>());
    } 

}
