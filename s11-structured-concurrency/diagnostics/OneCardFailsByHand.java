// OneCardFails with the cancel written by hand: the seat's slip is cancelled in a finally, as in Don't Wait Forever.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        Future<String> seat = desk.submit(() -> {
            try {
                Thread.sleep(1_000);                                     // head office takes a second to find a seat
            } catch (InterruptedException e) {
                IO.println("seat lookup interrupted after " + ms(start) + " ms");
                throw e;
            }
            return "S7-42";
        });
        Future<Integer> fare = desk.submit(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("fare service is down");
        });
        try {
            IO.println("fare " + fare.get() + ", seat " + seat.get());
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        } finally {
            seat.cancel(true);                                           // the line you have to remember
        }
    }
    IO.println("desk closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
