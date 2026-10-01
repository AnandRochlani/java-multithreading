// The classic pinning test: 1,000 virtual threads, each naps 100 ms INSIDE synchronized, each on its own private key
static final int PASSENGERS = 1_000;

void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < PASSENGERS; i++) {
            Object key = new Object();                        // nobody else ever wants this key
            desk.execute(() -> {
                synchronized (key) {
                    nap(100);                                 // a nap while holding the brass key
                }
            });
        }
    }
    IO.println(PASSENGERS + " naps of 100 ms inside synchronized took " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
