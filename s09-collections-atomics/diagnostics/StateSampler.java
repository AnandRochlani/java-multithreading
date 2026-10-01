// Run as:  java StateSampler.java sync   |   java StateSampler.java atomic
static final int CLERKS = 10;
static final int PER_CLERK = 2_000_000;                          // long enough to watch
static int plain = 0;
static final Object KEY = new Object();
static final AtomicInteger turnstile = new AtomicInteger();

void main(String[] args) throws InterruptedException {
    boolean sync = args.length > 0 && args[0].equals("sync");
    List<Thread> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        clerks.add(Thread.ofPlatform().name("window-" + i).unstarted(() -> {
            for (int t = 0; t < PER_CLERK; t++) {
                if (sync) {
                    synchronized (KEY) { plain++; }
                } else {
                    turnstile.incrementAndGet();
                }
            }
        }));
    }
    Map<Thread.State, Integer> seen = new EnumMap<>(Thread.State.class);
    long start = System.nanoTime();
    clerks.forEach(Thread::start);
    boolean anyAlive = true;
    while (anyAlive) {                                            // main looks at every clerk, over and over
        anyAlive = false;
        for (Thread c : clerks) {
            Thread.State s = c.getState();
            if (s != Thread.State.TERMINATED && s != Thread.State.NEW) {
                seen.merge(s, 1, Integer::sum);
                anyAlive = true;
            } else if (s == Thread.State.NEW) {
                anyAlive = true;
            }
        }
    }
    long ms = (System.nanoTime() - start) / 1_000_000;
    int total = seen.values().stream().mapToInt(Integer::intValue).sum();
    IO.println((sync ? "synchronized " : "AtomicInteger") + ": count " + (sync ? plain : turnstile.get()) + " in " + ms + " ms; "
            + total + " looks at a live clerk: " + seen);
}
