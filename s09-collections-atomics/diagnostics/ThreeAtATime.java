static final Semaphore windows = new Semaphore(3);                // three open windows, ten passengers
static final AtomicInteger atWindows = new AtomicInteger();
static final AtomicInteger most = new AtomicInteger();

void main() throws InterruptedException {
    try (ExecutorService queue = Executors.newFixedThreadPool(10)) {
        for (int p = 1; p <= 10; p++) {
            queue.execute(() -> {
                try {
                    windows.acquire();                            // wait for a free window
                    try {
                        int now = atWindows.incrementAndGet();
                        most.accumulateAndGet(now, Math::max);
                        Thread.sleep(100);                        // being served
                        atWindows.decrementAndGet();
                    } finally {
                        windows.release();                        // give the window back
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
    IO.println("ten passengers served, never more than " + most.get() + " at the windows at once");
}
