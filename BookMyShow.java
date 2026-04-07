import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how a HashMap can be used to centralize and manage
 * room availability across the system.
 *
 * @author Ashish
 * @version 3.1
 */

/* Inventory Class */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display inventory
    public void displayInventory() {

        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(
                    entry.getKey() + " → Available: " + entry.getValue()
            );
        }
    }
}

/* Application Entry Class */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("        Book My Stay Application       ");
        System.out.println("     Hotel Booking System Version 3.1  ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Check availability
        System.out.println("\nChecking availability for Single Room:");
        System.out.println("Available: " +
                inventory.getAvailability("Single Room"));

        // Update inventory
        System.out.println("\nUpdating inventory after booking...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication finished execution.");
    }
}
