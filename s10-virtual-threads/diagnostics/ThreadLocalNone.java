// ThreadLocalCost's virtual run WITHOUT the ThreadLocal: the same million sleeping cards, for the heap baseline
import java.lang.management.ManagementFactory;

void main() throws Exception {
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < 1_000_000; i++) {
            desk.execute(() -> nap(2_000));
        }
        Thread.sleep(1_000);
        System.gc();
        IO.println("no ThreadLocal: while the cards sleep, heap in use after a GC: "
                + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() >> 20) + " MB");
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
