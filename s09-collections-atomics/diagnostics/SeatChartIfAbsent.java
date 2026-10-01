// Two Safe Calls Make a Race, extra experiment: SeatChart with putIfAbsent. The value is an argument, so every clerk builds first;
// only the first put sticks and the other nine hand back the chart that was already there. Expect 10 builds, 1 chart.
static final Map<String, List<String>> charts = new ConcurrentHashMap<>();   // thread-safe: every call on it is safe

static List<String> buildChart(String coach) {
    IO.println(Thread.currentThread().getName() + ": building the seat chart for coach " + coach);
    nap(100);                                                     // fetching it takes a tenth of a second
    return List.of(coach + "-41", coach + "-42", coach + "-43");
}

void main() throws Exception {
    List<Callable<List<String>>> clerks = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
        clerks.add(() -> {
            List<String> mine = buildChart("S7");                 // build first: putIfAbsent needs the value
            List<String> there = charts.putIfAbsent("S7", mine);  // put only if absent, hand back what was there
            return there == null ? mine : there;
        });
    }
    Set<List<String>> handedOut = Collections.newSetFromMap(new IdentityHashMap<>());
    try (ExecutorService desk = Executors.newFixedThreadPool(10)) {
        for (Future<List<String>> slip : desk.invokeAll(clerks)) {
            handedOut.add(slip.get());                            // main alone fills this set
        }
    }
    IO.println("different charts handed out: " + handedOut.size());
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
