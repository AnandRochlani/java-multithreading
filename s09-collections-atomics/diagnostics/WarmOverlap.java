// WarmCounters' method, plus (a) a FRESH LongAdder every round (WarmCounters reuses one: reset() keeps its cells), and
// (b) for each counter, how long the ten clerks' counting loops really overlapped. Each cell prints wall/overlap/most:
// microseconds from the first start to the last join / microseconds with two or more clerks counting / most at once.
static final int CLERKS = 10;
static final int PER_CLERK = 100_000;
static int locked = 0;
static final AtomicInteger atomic = new AtomicInteger();
static LongAdder adder = new LongAdder();
static final LongAdder kept = new LongAdder();

static synchronized void sellLocked() { locked++; }

void main() throws InterruptedException {
    for (int round = 1; round <= 12; round++) {
        locked = 0; atomic.set(0); kept.reset(); adder = new LongAdder();
        String s = time(() -> sellLocked());
        String a = time(() -> atomic.incrementAndGet());
        String k = time(() -> kept.increment());
        LongAdder fresh = adder;
        String d = time(() -> fresh.increment());
        IO.println(String.format("round %2d: sync %s | atomic %s | adderKept %s | adderFresh %s | counts %d %d %d %d",
                round, s, a, k, d, locked, atomic.get(), kept.sum(), fresh.sum()));
    }
}

// returns "wall_us/overlap_us/maxAtOnce"
static String time(Runnable sell) throws InterruptedException {
    long[] st = new long[CLERKS], en = new long[CLERKS];
    long start = System.nanoTime();
    Thread[] clerks = new Thread[CLERKS];
    for (int i = 0; i < CLERKS; i++) {
        int me = i;
        clerks[i] = new Thread(() -> { st[me] = System.nanoTime(); for (int t = 0; t < PER_CLERK; t++) sell.run(); en[me] = System.nanoTime(); });
        clerks[i].start();
    }
    for (Thread c : clerks) c.join();
    long wall = (System.nanoTime() - start) / 1000;
    // time during which 2 or more clerks were inside their loops, and the most at once
    List<long[]> ev = new ArrayList<>();
    for (int i = 0; i < CLERKS; i++) { ev.add(new long[]{st[i], 1}); ev.add(new long[]{en[i], -1}); }
    ev.sort((x, y) -> x[0] != y[0] ? Long.compare(x[0], y[0]) : Long.compare(x[1], y[1]));
    int now = 0, most = 0; long overlap = 0, prev = 0;
    for (long[] e : ev) { if (now >= 2) overlap += e[0] - prev; now += (int) e[1]; most = Math.max(most, now); prev = e[0]; }
    return wall + "/" + overlap / 1000 + "/" + most;
}
