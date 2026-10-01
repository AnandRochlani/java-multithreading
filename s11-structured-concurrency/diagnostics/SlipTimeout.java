// Section 8's deadline on a slip: get with a timeout stops the waiting, not the card.
void main() throws Exception {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        Future<String> seat = desk.submit(() -> {
            Thread.sleep(1_000);                                         // head office is slow today
            IO.println("seat lookup finished after " + ms(start) + " ms, long after the deadline");
            return "S7-42";
        });
        try {
            seat.get(200, TimeUnit.MILLISECONDS);                        // a deadline on the slip
        } catch (TimeoutException e) {
            IO.println("stopped waiting after " + ms(start) + " ms: " + e);
        }
    }
    IO.println("desk closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
