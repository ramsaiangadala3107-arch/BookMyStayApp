import java.util.*;

// ----------- CUSTOM EXCEPTIONS -----------
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InsufficientAvailabilityException extends Exception {
    public InsufficientAvailabilityException(String message) {
        super(message);
    }
}

// ----------- RESERVATION -----------
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

// ----------- INVENTORY -----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InsufficientAvailabilityException {
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InsufficientAvailabilityException("No rooms available for " + roomType);
        }
        inventory.put(roomType, available - 1);
    }
}

// ----------- VALIDATOR -----------
class BookingValidator {

    private Set<String> validRoomTypes;

    public BookingValidator(Set<String> validRoomTypes) {
        this.validRoomTypes = validRoomTypes;
    }

    public void validate(Reservation r, RoomInventory inventory)
            throws InvalidRoomTypeException, InsufficientAvailabilityException {

        if (!validRoomTypes.contains(r.getRoomType())) {
            throw new InvalidRoomTypeException("Invalid room type: " + r.getRoomType());
        }

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InsufficientAvailabilityException("No availability for " + r.getRoomType());
        }
    }
}

// ----------- MAIN APPLICATION -----------
public class bmsappUC1 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Set<String> validTypes = new HashSet<>();
        validTypes.add("Single Room");
        validTypes.add("Double Room");
        validTypes.add("Suite Room");

        BookingValidator validator = new BookingValidator(validTypes);

        List<Reservation> requests = Arrays.asList(
                new Reservation("Ram", "Single Room"),
                new Reservation("Arjun", "Luxury Room"),   // invalid
                new Reservation("Priya", "Suite Room"),
                new Reservation("Kiran", "Suite Room")     // may fail if no availability
        );

        for (Reservation r : requests) {
            try {
                System.out.println("\nProcessing booking for " + r.getGuestName());

                validator.validate(r, inventory);
                inventory.reduceAvailability(r.getRoomType());

                System.out.println("Booking successful for " + r.getGuestName());

            } catch (InvalidRoomTypeException | InsufficientAvailabilityException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        System.out.println("\nSystem continues running safely.");
    }
}