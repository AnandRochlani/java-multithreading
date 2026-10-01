// One JVM: the three correct counters take turns for 12 rounds, so the later rounds are warm (JIT-compiled).
static final int CLERKS = 10;
static final int PER_CLERK = 100_000;
static int locked = 0;
static final AtomicInteger atomic = new AtomicInteger();
static final LongAdder adder = new LongAdder();

static synchronized void sellLocked() { locked++; }

void main() throws InterruptedException {
    for (int round = 1; round <= 12; round++) {
        locked = 0; atomic.set(0); adder.reset();
        long s = time(() -> sellLocked());
        long a = time(() -> atomic.incrementAndGet());
        long d = time(() -> adder.increment());
        IO.println(String.format("round %2d: synchronized %3d ms (%d), AtomicInteger %3d ms (%d), LongAdder %3d ms (%d)",
                round, s, locked, a, atomic.get(), d, adder.sum()));
    }
}

static long time(Runnable sell) throws InterruptedException {
    long start = System.nanoTime();
    Thread[] clerks = new Thread[CLERKS];
    for (int i = 0; i < CLERKS; i++) {
        clerks[i] = new Thread(() -> { for (int t = 0; t < PER_CLERK; t++) sell.run(); });
        clerks[i].start();
    }
    for (Thread c : clerks) c.join();
    return (System.nanoTime() - start) / 1_000_000;
}
