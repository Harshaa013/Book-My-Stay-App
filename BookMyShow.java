import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void incrementRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return bookings.get(id);
    }

    public void markCancelled(String id) {
        Reservation r = bookings.get(id);
        if (r != null) {
            r.cancel();
        }
    }
}

// Cancellation Service
class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              InventoryManager inventory) {

        // Step 1: Validate reservation
        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Error: Reservation does not exist.");
            return;
        }

        if (r.isCancelled()) {
            System.out.println("Error: Reservation already cancelled.");
            return;
        }

        // Step 2: Record for rollback (LIFO)
        rollbackStack.push(r.getRoomId());

        // Step 3: Restore inventory
        inventory.incrementRoom(r.getRoomType());

        // Step 4: Update booking history
        history.markCancelled(reservationId);

        // Step 5: Confirmation
        System.out.println("Cancellation successful for ID: " + reservationId);
        System.out.println("Rolled back Room ID: " + rollbackStack.pop());
    }
}

// Main class
public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();
        BookingHistory history = new BookingHistory();
        CancellationService service = new CancellationService();

        // Setup inventory
        inventory.addRoomType("Deluxe", 2);

        // Create booking
        Reservation r1 = new Reservation("RES401", "Deluxe", "D201");
        history.addReservation(r1);

        System.out.println("Before Cancellation:");
        inventory.displayInventory();

        // Perform cancellation
        service.cancelBooking("RES401", history, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        // Invalid cases
        service.cancelBooking("RES999", history, inventory); // non-existent
        service.cancelBooking("RES401", history, inventory); // duplicate
    }
}
