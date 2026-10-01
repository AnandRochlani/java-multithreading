// The happy path (java --enable-preview): seat 300 ms, fare 200 ms, forked together; then the same two calls one after the other.
void main() throws Exception {
    long start = System.nanoTime();
    try (var booking = StructuredTaskScope.open()) {
        var seat = booking.fork(() -> seatFinder());
        var fare = booking.fork(() -> fareQuote());
        booking.join();                                                  // both succeeded: join returns (null)
        IO.println("forked:     seat " + seat.get() + ", fare $" + fare.get() + ", after " + ms(start) + " ms");
    }
    start = System.nanoTime();
    String berth = seatFinder();
    int price = fareQuote();
    IO.println("one by one: seat " + berth + ", fare $" + price + ", after " + ms(start) + " ms");
}
static String seatFinder() throws InterruptedException { Thread.sleep(300); return "S7-42"; }
static int fareQuote() throws InterruptedException { Thread.sleep(200); return 1250; }
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
