// Cancelling is a tap on the shoulder (java --enable-preview): a seat card that ignores the tap holds up the closing brace.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (var booking = StructuredTaskScope.open()) {
        booking.fork(() -> {
            for (int i = 0; i < 10; i++) {                                   // a second of work: ten naps of 100 ms
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    IO.println("seat lookup was tapped after " + ms(start) + " ms, and kept going");   // swallows the tap
                }
            }
            IO.println("seat lookup finished after " + ms(start) + " ms");
            return "S7-42";
        });
        booking.fork(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("fare service is down");
        });
        try {
            booking.join();
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        }
    }
    IO.println("scope closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
