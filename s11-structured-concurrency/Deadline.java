// One deadline for the whole booking (java --enable-preview): the scope gives both cards 200 ms, then cancels them.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (var booking = StructuredTaskScope.open(cf -> cf.withTimeout(Duration.ofMillis(200)))) {
        var seat = booking.fork(() -> lookup("seat lookup", 1_000, start));  // head office is slow today
        var fare = booking.fork(() -> lookup("fare lookup", 300, start));
        try {
            booking.join();
            IO.println("booked " + seat.get() + ", fare " + fare.get());
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause());
        }
    }
    IO.println("scope closed after " + ms(start) + " ms");
}

static String lookup(String name, long napMs, long start) throws InterruptedException {
    try {
        Thread.sleep(napMs);
    } catch (InterruptedException e) {
        IO.println(name + " interrupted after " + ms(start) + " ms");
        throw e;
    }
    return name + " done";
}

static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
