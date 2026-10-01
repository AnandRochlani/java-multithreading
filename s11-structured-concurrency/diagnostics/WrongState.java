// Subtask.get() is only for a card that succeeded, exception() only for one that failed (java --enable-preview).
import java.util.concurrent.StructuredTaskScope.Joiner;

void main() throws Exception {
    try (var windows = StructuredTaskScope.open(Joiner.<String>allUntil(_ -> false))) {   // never cancels, never throws
        var seat = windows.fork(() -> "S7-42");
        var fare = windows.fork(() -> { throw new IllegalStateException("fare service is down"); });
        windows.join();
        IO.println("seat: " + seat.state() + ", fare: " + fare.state());
        try {
            fare.get();
        } catch (IllegalStateException e) {
            IO.println("fare.get() threw: " + e);
        }
        try {
            seat.exception();
        } catch (IllegalStateException e) {
            IO.println("seat.exception() threw: " + e);
        }
    }
}
