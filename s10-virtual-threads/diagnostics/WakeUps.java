// RushHour's million, with a record of WHEN each card woke up (per half second) and which threads burned the CPU.
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

static final int PASSENGERS = 1_000_000;
static final AtomicIntegerArray wokeIn = new AtomicIntegerArray(40);   // half-second buckets from the start
static long start;

void main() {
    start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < PASSENGERS; i++) {
            desk.execute(() -> {
                nap(1_000);
                int bucket = (int) ((System.nanoTime() - start) / 500_000_000L);
                wokeIn.incrementAndGet(Math.min(bucket, 39));
            });
        }
    }
    IO.println("all served after " + (System.nanoTime() - start) / 1_000_000 + " ms; cards woken in each half second:");
    for (int b = 0; b < 40; b++) {
        if (wokeIn.get(b) > 0) IO.println(String.format("  %4.1f-%4.1f s: %,9d", b / 2.0, (b + 1) / 2.0, wokeIn.get(b)));
    }
    ThreadMXBean mx = ManagementFactory.getThreadMXBean();
    long workers = 0, scheduler = 0; int carriers = 0;
    for (long id : mx.getAllThreadIds()) {
        var info = mx.getThreadInfo(id);
        if (info == null) continue;
        long cpu = mx.getThreadCpuTime(id) / 1_000_000;
        if (info.getThreadName().endsWith("delayScheduler")) scheduler += cpu;
        else if (info.getThreadName().startsWith("ForkJoinPool-1-worker")) { workers += cpu; carriers++; }
    }
    IO.println("CPU: " + carriers + " carrier threads " + workers + " ms in total, the delay scheduler thread " + scheduler + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
