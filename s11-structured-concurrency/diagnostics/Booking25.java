// WhenOneCardFails in the Java 25 shape of this preview API (JEP 505, fifth preview): join threw StructuredTaskScope.FailedException.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (var booking = StructuredTaskScope.open()) {                     // one scope for the whole booking
        var seat = booking.fork(() -> {
            try {
                Thread.sleep(1_000);                                     // head office takes a second to find a seat
            } catch (InterruptedException e) {
                IO.println("seat lookup interrupted after " + ms(start) + " ms");
                throw e;
            }
            return "S7-42";
        });
        var fare = booking.fork(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("fare service is down");
        });
        try {
            booking.join();                                              // wait for both cards, or for the first to fail
            IO.println("booked " + seat.get() + ", fare " + fare.get());
        } catch (StructuredTaskScope.FailedException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        }
    }
    IO.println("scope closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
