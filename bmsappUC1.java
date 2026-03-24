import java.util.*;

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

// ----------- INVENTORY SERVICE -----------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Updated Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// ----------- BOOKING QUEUE -----------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ----------- BOOKING SERVICE -----------
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per room type
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing request for " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure Set exists
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                // Add to Set (prevents duplicates automatically)
                allocatedRooms.get(roomType).add(roomId);

                // Reduce inventory immediately
                inventory.reduceAvailability(roomType);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest   : " + request.getGuestName());
                System.out.println("Room    : " + roomType);
                System.out.println("Room ID : " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + roomType);
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}

public class bmsappUC1 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   BOOK MY STAY - BOOKING ALLOCATION     ");
        System.out.println("=========================================");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();
        BookingService service = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Ram", "Single Room"));
        queue.addRequest(new Reservation("Arjun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room")); // should fail
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        // Process bookings
        service.processBookings(queue);

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\n=========================================");
        System.out.println("All bookings processed.");
    }
}