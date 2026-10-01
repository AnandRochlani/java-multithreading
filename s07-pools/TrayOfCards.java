import java.lang.management.*;

static int served = 0;                                        // guarded by the lock on the class
static synchronized void stamp() { served++; }
static synchronized int servedSoFar() { return served; }

void main() throws InterruptedException {
    ThreadPoolExecutor desk = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);   // ten clerks
    for (int p = 1; p <= 5_000; p++) {
        desk.execute(() -> {                                  // the same card: a second at the window
            nap(1_000);
            stamp();
        });
    }
    Thread.sleep(2_500);
    var threads = ManagementFactory.getThreadMXBean();
    IO.println("after 2.5 seconds: " + servedSoFar() + " passengers served");
    IO.println("clerks on the roster: " + desk.getPoolSize());
    IO.println("cards waiting in the tray: " + desk.getQueue().size());
    IO.println("the tray is a " + desk.getQueue().getClass().getSimpleName());
    IO.println("threads in this JVM: " + threads.getThreadCount() + " (peak " + threads.getPeakThreadCount() + ")");
    List<Runnable> neverStarted = desk.shutdownNow();         // close the station at once
    IO.println("sent home with " + neverStarted.size() + " cards never started");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
