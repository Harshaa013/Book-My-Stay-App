import java.util.*;

// Booking Request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Shared Inventory Manager (Thread-safe)
class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Deluxe", 2); // limited rooms
    }

    // Critical Section
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private InventoryManager inventory;

    public BookingProcessor(BookingQueue queue, InventoryManager inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // synchronized retrieval
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            // Critical section for allocation
            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking SUCCESS for " + request.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking FAILED for " + request.getGuestName());
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryManager inventory = new InventoryManager();

        // Simulate multiple guests
        queue.addRequest(new BookingRequest("Ashish", "Deluxe"));
        queue.addRequest(new BookingRequest("Rahul", "Deluxe"));
        queue.addRequest(new BookingRequest("Sneha", "Deluxe"));
        queue.addRequest(new BookingRequest("Priya", "Deluxe"));

        // Create multiple threads
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.displayInventory();
    }
}
