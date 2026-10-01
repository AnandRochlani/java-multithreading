// Don't pool them; limit the scarce thing instead. 200 cards on virtual threads, but only 10 at the server at once.
static final Semaphore server = new Semaphore(10);           // ten permits: the server takes ten callers at a time
static final AtomicInteger atServer = new AtomicInteger();
static final AtomicInteger most = new AtomicInteger();

void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {   // still a thread per card
        for (int i = 1; i <= 200; i++) {
            desk.execute(() -> {
                server.acquireUninterruptibly();              // take a permit, or wait (unmounted) for one
                try {
                    most.accumulateAndGet(atServer.incrementAndGet(), Math::max);
                    nap(100);
                    atServer.decrementAndGet();
                } finally {
                    server.release();                         // give the permit back
                }
            });
        }
    }
    IO.println("200 cards, at most " + most.get() + " at the server at once, took "
            + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
