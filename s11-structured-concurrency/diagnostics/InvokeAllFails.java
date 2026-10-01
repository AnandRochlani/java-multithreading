// Section 8's invokeAll with the booking's two cards: the fare fails fast, and invokeAll still waits for every card.
void main() throws InterruptedException {
    long start = System.nanoTime();
    List<Callable<Object>> cards = List.of(
            () -> {
                Thread.sleep(1_000);                                     // head office takes a second to find a seat
                IO.println("seat lookup finished after " + ms(start) + " ms, for a booking that already failed");
                return "S7-42";
            },
            () -> {
                Thread.sleep(50);
                throw new IllegalStateException("fare service is down");
            });
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        List<Future<Object>> slips = desk.invokeAll(cards);              // hand in both, wait for both
        IO.println("invokeAll returned after " + ms(start) + " ms, fare slip: " + slips.get(1).state());
    }
    IO.println("desk closed after " + ms(start) + " ms");
}
static long ms(long start) { return (System.nanoTime() - start) / 1_000_000; }
