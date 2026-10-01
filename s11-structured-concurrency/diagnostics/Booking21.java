// WhenOneCardFails in the Java 21 shape of this preview API (JEP 453, first preview), in classic syntax so JDK 21 can run it.
import java.util.concurrent.*;

public class Booking21 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        try (var booking = new StructuredTaskScope.ShutdownOnFailure()) {
            var seat = booking.fork(() -> {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    System.out.println("seat lookup interrupted after " + ms(start) + " ms");
                    throw e;
                }
                return "S7-42";
            });
            var fare = booking.fork(() -> {
                Thread.sleep(50);
                throw new IllegalStateException("fare service is down");
            });
            try {
                booking.join().throwIfFailed();
                System.out.println("booked " + seat.get() + ", fare " + fare.get());
            } catch (ExecutionException e) {
                System.out.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
            }
        }
        System.out.println("scope closed after " + ms(start) + " ms");
    }
    static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
}
