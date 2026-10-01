// Roster200 again, on a pool of FOUR virtual threads: the pool's ceiling, rebuilt out of cheap threads
static final int PASSENGERS = 200;

static void card(int i) {
    nap(100);
}

void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(4, Thread.ofVirtual().factory())) {   // four virtual clerks
        for (int i = 1; i <= PASSENGERS; i++) {
            int n = i;
            desk.execute(() -> card(n));
        }
    }
    IO.println(PASSENGERS + " cards on a pool of 4 virtual threads took " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
