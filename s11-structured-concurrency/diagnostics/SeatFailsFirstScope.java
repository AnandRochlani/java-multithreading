// The same swap in a structured scope (java --enable-preview): join wakes on the first failure, whichever card it is.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (var booking = StructuredTaskScope.open()) {
        var fare = booking.fork(() -> {
            try {
                Thread.sleep(1_000);                                     // the fare service takes a second today
            } catch (InterruptedException e) {
                IO.println("fare lookup interrupted after " + ms(start) + " ms");
                throw e;
            }
            return 1250;
        });
        var seat = booking.fork(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("no seat on this train");
        });
        try {
            booking.join();
            IO.println("fare " + fare.get() + ", seat " + seat.get());
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        }
    }
    IO.println("scope closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
