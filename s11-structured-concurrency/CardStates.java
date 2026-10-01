// What each card's handle says after the scope is cancelled (java --enable-preview). allUntil: cancel at the first failure.
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

void main() throws Exception {
    try (var windows = StructuredTaskScope.open(
            Joiner.<String>allUntil(card -> card.state() == Subtask.State.FAILED))) {   // cancel at the first failure
        windows.fork(() -> { Thread.sleep(100); return "berth S7-42"; });               // answers first
        windows.fork(() -> { Thread.sleep(200); throw new IllegalStateException("fare service is down"); });
        windows.fork(() -> { Thread.sleep(1_000); return "platform 3"; });              // still asleep at the cancel
        windows.fork(() -> {
            try { Thread.sleep(1_000); } catch (InterruptedException e) { }             // ignores the tap...
            return "meal: vegetarian";                                                   // ...and answers anyway
        });
        List<Subtask<String>> cards = windows.join();                                    // every card, in fork order
        for (Subtask<String> card : cards) {
            switch (card.state()) {
                case SUCCESS     -> IO.println("SUCCESS      " + card.get());
                case FAILED      -> IO.println("FAILED       " + card.exception());
                case UNAVAILABLE -> IO.println("UNAVAILABLE");
            }
        }
    }
}
