static final int PASSENGERS = 10_000;                         // ten thousand passengers: RushHour with one number changed
static final AtomicInteger served = new AtomicInteger();

void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {   // a new virtual thread for every card
        for (int i = 0; i < PASSENGERS; i++) {
            desk.execute(() -> {
                nap(1_000);                                   // every card waits one second on the server
                served.incrementAndGet();
            });
        }
        IO.println("handed in " + PASSENGERS + " cards after " + ms(start) + " ms");
    }                                                         // close(): wait for every card
    IO.println(served.get() + " passengers served after " + ms(start) + " ms");
}

static long ms(long start) {
    return (System.nanoTime() - start) / 1_000_000;
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
