// A deadline that keeps whatever arrived in time (java --enable-preview): allUntil never throws, not even at the deadline.
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

void main() throws Exception {
    long start = System.nanoTime();
    try (var windows = StructuredTaskScope.open(Joiner.<String>allUntil(_ -> false),     // never cancel for a result
                                                cf -> cf.withTimeout(Duration.ofMillis(250)))) {
        windows.fork(() -> { Thread.sleep(100); return "berth S7-42"; });
        windows.fork(() -> { Thread.sleep(200); return "fare 1250"; });
        windows.fork(() -> { Thread.sleep(400); return "platform 3"; });               // misses the deadline
        List<Subtask<String>> cards = windows.join();
        IO.println("join returned after " + ms(start) + " ms, cancelled? " + windows.isCancelled());
        for (Subtask<String> card : cards) {
            IO.println("  " + card.state() + (card.state() == Subtask.State.SUCCESS ? "  " + card.get() : ""));
        }
    }
}

static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
