
static final Object CASH   = new Object();
static final Object LEDGER = new Object();

static void sell(Object first, Object second) {
    synchronized (first) {
        nap(100);
        synchronized (second) {
            IO.println(Thread.currentThread().getName() + ": sold a ticket");
        }
    }
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sell(CASH, LEDGER), "Meena");
    Thread tom   = new Thread(() -> sell(LEDGER, CASH), "Tom");
    meena.setDaemon(true);                                    // so the station can still close tonight
    tom.setDaemon(true);
    meena.start();
    tom.start();
    Thread.sleep(500);

    ThreadMXBean threads = ManagementFactory.getThreadMXBean();
    long[] stuck = threads.findDeadlockedThreads();           // null when there is no circle
    if (stuck == null) {
        IO.println("no deadlock");
        return;
    }
    for (ThreadInfo t : threads.getThreadInfo(stuck)) {
        IO.println(t.getThreadName() + " is " + t.getThreadState()
                + ", waiting for a lock held by " + t.getLockOwnerName());
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
