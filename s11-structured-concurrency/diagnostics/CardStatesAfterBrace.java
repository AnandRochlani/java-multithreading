// CardStates, with the fourth card saying when it is tapped, and every state read again after the brace (java --enable-preview).
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

void main() throws Exception {
    long start = System.nanoTime();
    List<Subtask<String>> cards;
    try (var windows = StructuredTaskScope.open(
            Joiner.<String>allUntil(card -> card.state() == Subtask.State.FAILED))) {   // cancel at the first failure
        windows.fork(() -> { Thread.sleep(100); return "berth S7-42"; });
        windows.fork(() -> { Thread.sleep(200); throw new IllegalStateException("fare service is down"); });
        windows.fork(() -> { Thread.sleep(1_000); return "platform 3"; });
        windows.fork(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {                                           // ignores the tap...
                IO.println("meal card tapped after " + (System.nanoTime() - start) / 1_000_000 + " ms, answering anyway");
            }
            return "meal: vegetarian";                                                   // ...and answers anyway
        });
        cards = windows.join();
        IO.println("after join:  " + cards.stream().map(Subtask::state).toList());
    }
    IO.println("after brace: " + cards.stream().map(Subtask::state).toList());   // every card has finished by now
}
