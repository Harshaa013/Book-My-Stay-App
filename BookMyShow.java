import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are collected and stored
 * using a Queue to maintain arrival order.
 *
 * @version 5.0
 */

/* ---------------- RESERVATION OBJECT ---------------- */

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

    public void displayRequest() {
        System.out.println("Guest: " + guestName +
                " | Requested Room: " + roomType);
    }
}


/* ---------------- BOOKING QUEUE ---------------- */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Booking request added for "
                + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}


/* ---------------- APPLICATION ENTRY ---------------- */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("        Book My Stay Application       ");
        System.out.println("     Hotel Booking System Version 5.0  ");
        System.out.println("=======================================");

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO)
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue
        queue.displayQueue();

        System.out.println("\nRequests stored in FIFO order.");
        System.out.println("No inventory updates performed.");
    }
}
