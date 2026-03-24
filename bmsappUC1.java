import java.util.*;

class Reservation {
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nAdd-On Services for Reservation " + reservationId);
        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getPrice());
        }
    }

    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }
        return total;
    }
}

public class bmsappUC1 {
    public static void main(String[] args) {

        Reservation r1 = new Reservation("RES101", "Ram", "Single Room");

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("RES101", new AddOnService("Breakfast", 200));
        manager.addService("RES101", new AddOnService("Airport Pickup", 500));
        manager.addService("RES101", new AddOnService("Extra Bed", 300));

        manager.displayServices("RES101");

        double total = manager.calculateTotalCost("RES101");
        System.out.println("\nTotal Add-On Cost: ₹" + total);
    }
}