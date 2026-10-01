static final Map<String, List<String>> charts = new ConcurrentHashMap<>();   // thread-safe: every call on it is safe

static List<String> buildChart(String coach) {
    IO.println(Thread.currentThread().getName() + ": building the seat chart for coach " + coach);
    nap(100);                                                     // fetching it takes a tenth of a second
    return List.of(coach + "-41", coach + "-42", coach + "-43");
}

void main() throws Exception {
    List<Callable<List<String>>> clerks = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
        clerks.add(() -> charts.computeIfAbsent("S7", coach -> buildChart(coach)));   // check and build as ONE call
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
