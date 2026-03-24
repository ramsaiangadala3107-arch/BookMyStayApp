import java.util.LinkedList;
import java.util.Queue;


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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// ----------- BOOKING QUEUE (FIFO STRUCTURE) -----------
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\n--- Booking Requests (FIFO Order) ---");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

public class bmsappUC1 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   BOOK MY STAY - BOOKING REQUEST QUEUE  ");
        System.out.println("=========================================");

        // Initialize booking queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulating guest booking requests
        Reservation r1 = new Reservation("Ram", "Single Room");
        Reservation r2 = new Reservation("Arjun", "Suite Room");
        Reservation r3 = new Reservation("Priya", "Double Room");

        // Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // View queued requests
        bookingQueue.viewRequests();

        System.out.println("\n=========================================");
        System.out.println("Requests stored. Awaiting allocation...");
    }
}