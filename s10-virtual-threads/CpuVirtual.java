// Section 7's CpuBound cards: 200 computing cards on a pool of ten, then on a virtual thread each
static long total = 0;
static synchronized void add(long x) { total += x; }         // so the JIT cannot throw the work away

static void card(long seed) {
    long x = seed;
    for (int i = 0; i < 20_000_000; i++) {                    // pure computation: nothing to wait for
        x ^= x << 13;
        x ^= x >>> 7;
        x ^= x << 17;
    }
    add(x);
}

static long run(ExecutorService desk) {
    long start = System.nanoTime();
    try (desk) {
        for (int i = 1; i <= 200; i++) {
            long seed = i;
            desk.execute(() -> card(seed));                   // 200 computing cards
        }
    }
    return (System.nanoTime() - start) / 1_000_000;
}

void main() {
    run(Executors.newFixedThreadPool(10));                    // warm-up round, not printed
    for (int round = 1; round <= 3; round++) {
        long pool = run(Executors.newFixedThreadPool(10));
        long virtual = run(Executors.newVirtualThreadPerTaskExecutor());
        IO.println("round " + round + ": pool of 10 " + pool + " ms, virtual threads " + virtual + " ms");
    }
    IO.println("checksum " + total);
}
