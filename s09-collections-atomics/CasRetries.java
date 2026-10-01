static final AtomicInteger ticketsSold = new AtomicInteger();
static final int CLERKS = 10;
static final int PER_CLERK = 100_000;

static int sellOne() {                                            // one sale, the long way round
    int misses = 0;
    while (true) {
        int seen = ticketsSold.get();                             // read the turnstile
        if (ticketsSold.compareAndSet(seen, seen + 1)) {          // still 'seen'? then make it seen + 1, as ONE step
            return misses;
        }
        misses++;                                                 // somebody clicked first: read again, try again
    }
}

void main() throws Exception {
    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        clerks.add(() -> {
            int misses = 0;
            for (int t = 0; t < PER_CLERK; t++) {
                misses += sellOne();
            }
            return misses;                                        // each clerk hands back his own count
        });
    }
    int misses = 0;
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        for (Future<Integer> slip : desk.invokeAll(clerks)) {
            misses += slip.get();
        }
    }
    IO.println("register        : " + ticketsSold.get());
    IO.println("compareAndSet said no " + misses + " times, and each time the clerk read again and retried");
}
