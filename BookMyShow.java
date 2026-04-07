import java.util.*;

/**
 * Book My Stay App
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe room allocation while preventing
 * double booking using Set and HashMap.
 *
 * @version 6.0
 */

/* ---------------- RESERVATION ---------------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/* ---------------- INVENTORY SERVICE ---------------- */

class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}


/* ---------------- BOOKING SERVICE ---------------- */

class BookingService {

    private Queue<Reservation> requestQueue;
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService() {

        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Request received from " + reservation.getGuestName());
    }

    // Process requests
    public void processBookings(InventoryService inventory) {

        System.out.println("\nProcessing booking requests...\n");

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();

            String roomType = reservation.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = roomType.replace(" ", "").toUpperCase()
                        + "-" + UUID.randomUUID().toString().substring(0, 5);

                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Set ensures uniqueness
                allocatedRooms.get(roomType).add(roomId);

                // Update inventory
                inventory.decrement(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("-----------------------------");

            } else {

                System.out.println("Reservation Failed for "
                        + reservation.getGuestName()
                        + " (No rooms available)");
            }
        }
    }

    // Display allocated rooms
    public void displayAllocations() {

        System.out.println("\nAllocated Rooms:");

        for (String roomType : allocatedRooms.keySet()) {

            System.out.println(roomType + " → " + allocatedRooms.get(roomType));
        }
    }
}


/* ---------------- APPLICATION ENTRY ---------------- */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("        Book My Stay Application       ");
        System.out.println("     Hotel Booking System Version 6.0  ");
        System.out.println("=======================================");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        // Booking requests
        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingService.addRequest(new Reservation("David", "Suite Room")); // Should fail

        // Process bookings
        bookingService.processBookings(inventory);

        // Show results
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nRoom allocation completed.");
    }
}
