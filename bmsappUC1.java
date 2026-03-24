import java.util.HashMap;
import java.util.Map;

/**
 * UseCase4HotelBookingApp
 *
 * Demonstrates room search functionality using read-only access.
 * Only available rooms are displayed without modifying inventory.
 *
 * @author Ram
 * @version 1.0
 */

// ----------- DOMAIN MODEL (Room) -----------
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
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// ----------- INVENTORY (STATE HOLDER) -----------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose full inventory (read-only usage)
    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

// ----------- SEARCH SERVICE (READ-ONLY LOGIC) -----------
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("-----------------------------");
            }
        }
    }
}

// ----------- MAIN APPLICATION -----------
public class bmsappUC1 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY - ROOM SEARCH         ");
        System.out.println("=========================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (Domain Model)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service (Read-only)
        SearchService searchService = new SearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\n=========================================");
        System.out.println("Search completed (No data modified).");
    }
}