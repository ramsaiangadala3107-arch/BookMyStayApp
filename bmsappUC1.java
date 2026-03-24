import java.util.*;

// ----------- RESERVATION -----------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType +
                " | RoomID: " + roomId);
    }
}

// ----------- INVENTORY -----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// ----------- BOOKING HISTORY -----------
class BookingHistory {
    private Map<String, Reservation> history = new HashMap<>();

    public void addReservation(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return history.get(id);
    }

    public void removeReservation(String id) {
        history.remove(id);
    }
}

// ----------- CANCELLATION SERVICE -----------
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancel(String reservationId, BookingHistory history, RoomInventory inventory) {

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        rollbackStack.push(r.getRoomId());

        inventory.increaseAvailability(r.getRoomType());

        history.removeReservation(reservationId);

        System.out.println("\nCancellation successful for Reservation: " + reservationId);
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

// ----------- MAIN APPLICATION -----------
public class bmsappUC1 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("RES101", "Ram", "Single Room", "SI-101");
        history.addReservation(r1);

        CancellationService service = new CancellationService();

        System.out.println("Before Cancellation:");
        inventory.displayInventory();

        service.cancel("RES101", history, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        service.cancel("RES999", history, inventory);
    }
}