// The by-hand version with the roles swapped: the SEAT fails fast, the fare takes a second, and main asks the fare's slip first.
void main() throws InterruptedException {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        Future<Integer> fare = desk.submit(() -> {
            try {
                Thread.sleep(1_000);                                     // the fare service takes a second today
            } catch (InterruptedException e) {
                IO.println("fare lookup interrupted after " + ms(start) + " ms");
                throw e;
            }
            return 1250;
        });
        Future<String> seat = desk.submit(() -> {
            Thread.sleep(50);
            throw new IllegalStateException("no seat on this train");
        });
        try {
            IO.println("fare " + fare.get() + ", seat " + seat.get());   // waits on the fare's slip first
        } catch (ExecutionException e) {
            IO.println("booking failed after " + ms(start) + " ms: " + e.getCause().getMessage());
        } finally {
            fare.cancel(true);
            seat.cancel(true);
        }
    }
    IO.println("desk closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
