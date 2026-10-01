static long total = 0;                                        // guarded by the lock on the class
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

static long run(int clerks) {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(clerks)) {
        for (int i = 1; i <= 200; i++) {
            long seed = i;
            desk.execute(() -> card(seed));                   // 200 computing cards
        }
    }
    return (System.nanoTime() - start) / 1_000_000;
}

void main() {
    IO.println("cores on this machine: " + Runtime.getRuntime().availableProcessors());
    run(10);                                                  // warm-up round, not printed
    for (int clerks : new int[] {1, 4, 10, 200}) {
        IO.println(String.format("pool of %3d: %5d ms", clerks, run(clerks)));
    }
    IO.println("checksum " + total);
}
