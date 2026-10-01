// Ten thousand virtual threads each nap twice. Which carrier did each sit on before and after its first nap?
static final int PASSENGERS = 10_000;
static final Set<String> carriers = ConcurrentHashMap.newKeySet();
static final AtomicInteger moved = new AtomicInteger();

static String carrier() {                                     // "VirtualThread[#85]/runnable@ForkJoinPool-1-worker-9"
    String me = Thread.currentThread().toString();
    return me.substring(me.indexOf('@') + 1);                 // -> "ForkJoinPool-1-worker-9"
}

void main() {
    IO.println("cores: " + Runtime.getRuntime().availableProcessors());
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < PASSENGERS; i++) {
            desk.execute(() -> {
                String before = carrier();
                nap(100);                                     // stand up from the chair...
                String after = carrier();                     // ...and sit down wherever one is free
                carriers.add(before);
                carriers.add(after);
                if (!before.equals(after)) moved.incrementAndGet();
                nap(100);
            });
        }
    }
    IO.println(PASSENGERS + " virtual threads ran on " + carriers.size() + " carrier threads");
    IO.println("woke up on a different carrier than they fell asleep on: " + moved.get() + " of " + PASSENGERS);
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
