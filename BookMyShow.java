import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State (Inventory + Booking History)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting with safe defaults.");
        }

        // Return safe default state
        return new SystemState(new HashMap<>(), new ArrayList<>());
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // STEP 1: Load previous state
        SystemState state = service.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> bookings = state.bookings;

        // If first run, initialize data
        if (inventory.isEmpty()) {
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);
        }

        // Simulate new booking
        Reservation r1 = new Reservation("RES501", "Ashish", "Deluxe");
        bookings.add(r1);
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        // Display current state
        System.out.println("\n--- Current Bookings ---");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        System.out.println("\n--- Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }

        // STEP 2: Save state before shutdown
        service.save(new SystemState(inventory, bookings));
    }
}