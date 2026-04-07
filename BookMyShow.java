import java.util.*;

// Class representing an Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class to handle mapping between reservation and services
class AddOnServiceManager {
    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add services to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation ID: " + reservationId);
        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        System.out.println("Total Additional Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation ID
        String reservationId = "RES123";

        // Creating add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa Access", 1200);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, airportPickup);

        // Display selected services and total cost
        manager.displayServices(reservationId);
    }
}
