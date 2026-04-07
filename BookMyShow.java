import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates safe read-only access to inventory while displaying
 * room details and availability to the user.
 *
 * @version 4.0
 */

/* ---------------- ROOM DOMAIN MODEL ---------------- */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: $" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 80);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 150);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300);
    }
}


/* ---------------- INVENTORY ---------------- */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);   // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}


/* ---------------- SEARCH SERVICE ---------------- */

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive programming check
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("-----------------------------");
            }
        }
    }
}


/* ---------------- APPLICATION ENTRY ---------------- */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("        Book My Stay Application       ");
        System.out.println("     Hotel Booking System Version 4.0  ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Guest performs search
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. System state unchanged.");
    }
}
