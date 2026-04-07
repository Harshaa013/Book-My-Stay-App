/**
 * Book My Stay App
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, polymorphism,
 * and simple availability representation.
 *
 * @author Ashish
 * @version 2.1
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}

/* Single Room Class */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 80);
    }
}

/* Double Room Class */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 150);
    }
}

/* Suite Room Class */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 300);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("        Book My Stay Application       ");
        System.out.println("      Hotel Booking System v2.1        ");
        System.out.println("=======================================\n");

        // Static availability variables
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        // Polymorphism: referencing objects using Room type
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Room Details:\n");

        single.displayDetails();
        System.out.println("Available: " + singleRoomAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteRoomAvailable + "\n");

        System.out.println("Application finished execution.");
    }
}
