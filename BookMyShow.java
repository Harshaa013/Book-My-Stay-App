import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailableRooms(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void allocateRoom(String type) throws InvalidBookingException {
        int count = getAvailableRooms(type);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.put(type, count - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Validator class
class InvalidBookingValidator {

    public void validate(String reservationId, String roomType, InventoryManager inventory)
            throws InvalidBookingException {

        // Check null or empty inputs
        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("Rooms not available for type: " + roomType);
        }
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();
        InvalidBookingValidator validator = new InvalidBookingValidator();

        // Setup inventory
        inventory.addRoomType("Deluxe", 1);
        inventory.addRoomType("Suite", 0);

        // Test cases
        String[][] testBookings = {
                {"RES301", "Deluxe"},     // valid
                {"", "Deluxe"},           // invalid ID
                {"RES302", "Premium"},    // invalid type
                {"RES303", "Suite"}       // no availability
        };

        for (String[] booking : testBookings) {
            String id = booking[0];
            String type = booking[1];

            try {
                // Validation (Fail-Fast)
                validator.validate(id, type, inventory);

                // If valid → proceed
                inventory.allocateRoom(type);
                Reservation r = new Reservation(id, type);

                System.out.println("Booking successful: " + r.getReservationId());

            } catch (InvalidBookingException e) {
                // Graceful error handling
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        // Final inventory state
        inventory.displayInventory();
    }
}