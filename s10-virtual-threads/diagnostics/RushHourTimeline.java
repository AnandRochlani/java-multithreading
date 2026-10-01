// RushHour with three clocks: when did the last card fall asleep, when did the first and the last wake up?
static final int PASSENGERS = 1_000_000;
static final AtomicInteger served = new AtomicInteger();
static final AtomicLong lastAsleep = new AtomicLong();       // nanoTime at which the last card began its nap
static final AtomicLong firstAwake = new AtomicLong(Long.MAX_VALUE);
static final AtomicLong lastAwake = new AtomicLong();

void main(String[] args) {
    int passengers = args.length > 0 ? Integer.parseInt(args[0]) : PASSENGERS;
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < passengers; i++) {
            desk.execute(() -> {
                lastAsleep.accumulateAndGet(System.nanoTime(), Math::max);
                nap(1_000);
                long now = System.nanoTime();
                firstAwake.accumulateAndGet(now, Math::min);
                lastAwake.accumulateAndGet(now, Math::max);
                served.incrementAndGet();
            });
        }
        IO.println("handed in " + passengers + " cards after " + ms(start, System.nanoTime()) + " ms");
    }
    long end = System.nanoTime();
    IO.println("last card fell asleep at " + ms(start, lastAsleep.get()) + " ms");
    IO.println("first card woke up at    " + ms(start, firstAwake.get()) + " ms");
    IO.println("last card woke up at     " + ms(start, lastAwake.get()) + " ms");
    IO.println(served.get() + " passengers served after " + ms(start, end) + " ms");
}

static long ms(long from, long to) {
    return (to - from) / 1_000_000;
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
