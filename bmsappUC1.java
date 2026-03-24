import java.io.*;
import java.util.*;

// ----------- RESERVATION -----------
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

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// ----------- INVENTORY -----------
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void display() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// ----------- STATE WRAPPER -----------
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> bookings;
    private Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookings, Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public List<Reservation> getBookings() {
        return bookings;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

// ----------- PERSISTENCE SERVICE -----------
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    public SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) in.readObject();
            System.out.println("State loaded successfully.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

// ----------- MAIN APPLICATION -----------
public class bmsappUC1 {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        SystemState loadedState = persistence.load();

        RoomInventory inventory;
        List<Reservation> bookings;

        if (loadedState != null) {
            inventory = new RoomInventory();
            inventory.setInventory(loadedState.getInventory());
            bookings = loadedState.getBookings();
        } else {
            inventory = new RoomInventory();
            bookings = new ArrayList<>();
        }

        bookings.add(new Reservation("RES201", "Ram", "Single Room"));
        bookings.add(new Reservation("RES202", "Arjun", "Suite Room"));

        System.out.println("\n--- Booking History ---");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        inventory.display();

        SystemState newState = new SystemState(bookings, inventory.getInventory());
        persistence.save(newState);
    }
}