// A ThreadLocal "cache" of a 1 KB buffer, the classic trick on a pool. Usage: java ThreadLocalCost.java pool | virtual
import java.lang.management.ManagementFactory;

static final AtomicInteger buffersMade = new AtomicInteger();
static final ThreadLocal<byte[]> BUFFER = ThreadLocal.withInitial(() -> {
    buffersMade.incrementAndGet();
    return new byte[1024];                                    // one scratch buffer per thread, reused by its cards
});

void main(String[] args) throws Exception {
    boolean virtual = args[0].equals("virtual");
    long start = System.nanoTime();
    try (ExecutorService desk = virtual ? Executors.newVirtualThreadPerTaskExecutor() : Executors.newFixedThreadPool(10)) {
        for (int i = 0; i < 1_000_000; i++) {
            desk.execute(() -> {
                BUFFER.get()[0]++;                            // use this thread's buffer
                if (virtual) nap(2_000);                      // the virtual cards stay alive, asleep, for a while
            });
        }
        if (virtual) {
            Thread.sleep(1_000);                              // every virtual card is now asleep, buffer in hand
            System.gc();
            IO.println("while the cards sleep, heap in use after a GC: "
                    + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() >> 20) + " MB");
        }
    }
    IO.println((virtual ? "virtual threads" : "pool of 10") + ": 1000000 cards, buffers made: " + buffersMade.get()
            + ", " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
