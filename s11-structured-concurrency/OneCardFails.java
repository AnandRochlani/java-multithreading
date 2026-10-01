// Sophie's booking on the virtual desk, Section 8's way: two cards, two slips. The fare fails; nobody stops the seat lookup.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        Future<String> seat = desk.submit(() -> {
            Thread.sleep(1_000);                                     // head office takes a second to find a seat
            IO.println("seat lookup finished after " + ms(start) + " ms, for a booking that already failed");
            return "S7-42";
        });
        Future<Integer> fare = desk.submit(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("fare service is down");
        });
        try {
            IO.println("fare " + fare.get());
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        }
    }
    IO.println("desk closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
