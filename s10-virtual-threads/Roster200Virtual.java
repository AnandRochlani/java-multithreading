static final int PASSENGERS = 200;

static void card(int i) {
    nap(100);                                                 // a tenth of a second: waiting on the server
    if (i % 50 == 0) IO.println("card " + i + " served by " + Thread.currentThread().getName());
}

void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {   // was newFixedThreadPool(4)
        for (int i = 1; i <= PASSENGERS; i++) {
            int n = i;
            desk.execute(() -> card(n));
        }
    }                                                         // close(): shut down, then wait
    IO.println(PASSENGERS + " cards on virtual threads took " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
