// The source lesson's break five, re-timed (java --enable-preview): seat 300 ms, fare fails at 200 ms, Sophie bound around it all.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() throws Exception {
    long start = System.nanoTime();
    ScopedValue.where(PASSENGER, "Sophie").call(() -> {
        try (var booking = StructuredTaskScope.open()) {
            booking.fork(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    IO.println("seat lookup told to stop after " + ms(start) + " ms, still reads " + PASSENGER.get());
                    throw e;
                }
                return "S7-42 for " + PASSENGER.get();
            });
            booking.fork(() -> {
                Thread.sleep(200);
                throw new IllegalStateException("fare service is down");
            });
            booking.join();
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms, real reason: " + e.getCause());
        }
        return null;
    });
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
