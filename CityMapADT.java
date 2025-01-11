import java.util.*;

class CityMapADT {
    private Map<String, Map<String, Integer>> adjacencyList;

    public CityMapADT() {
        this.adjacencyList = new HashMap<>();
    }

    public void addLocation(String location) {
        adjacencyList.putIfAbsent(location, new HashMap<>());
    }

    public void addDistance(String location1, String location2, int distance) {
        adjacencyList.get(location1).put(location2, distance);
        adjacencyList.get(location2).put(location1, distance); // Assuming undirected graph
    }

    public Map<String, Map<String, Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public String getDistance(String location1, String location2) {
        if (adjacencyList.containsKey(location1) && adjacencyList.get(location1).containsKey(location2)) {
            return adjacencyList.get(location1).get(location2) + " km";
        }
        return "No route available";
    }
}
