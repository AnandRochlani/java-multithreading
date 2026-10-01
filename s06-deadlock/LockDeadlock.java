import java.lang.management.*;

static final ReentrantLock CASH   = new ReentrantLock();
static final ReentrantLock LEDGER = new ReentrantLock();

static void sell(ReentrantLock first, ReentrantLock second) {
    first.lock();
    try {
        nap(100);
        second.lock();                                        // plain lock(): no time limit
        try {
            IO.println(Thread.currentThread().getName() + ": sold a ticket");
        } finally {
            second.unlock();
        }
    } finally {
        first.unlock();
    }
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sell(CASH, LEDGER), "Meena");
    Thread tom   = new Thread(() -> sell(LEDGER, CASH), "Tom");
    meena.start();
    tom.start();
    Thread.sleep(500);
    IO.println("Meena: " + meena.getState() + ", Tom: " + tom.getState());

    ThreadMXBean threads = ManagementFactory.getThreadMXBean();
    IO.println("findMonitorDeadlockedThreads: " + (threads.findMonitorDeadlockedThreads() == null ? "none" : "found"));
    long[] stuck = threads.findDeadlockedThreads();
    IO.println("findDeadlockedThreads: " + (stuck == null ? "none" : stuck.length + " threads"));
    meena.join();
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
