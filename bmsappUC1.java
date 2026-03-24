import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Application
public class bmsappUC1 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   BOOK MY STAY - INVENTORY MANAGEMENT   ");
        System.out.println("=========================================");

        // Initialize inventory (Single Source of Truth)
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Check availability
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single Room"));

        // Update inventory (simulate booking)
        System.out.println("\nBooking 1 Single Room...");
        int current = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", current - 1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\n=========================================");
        System.out.println("System terminated.");
    }
}