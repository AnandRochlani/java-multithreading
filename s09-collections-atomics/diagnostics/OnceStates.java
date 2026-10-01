// Two Safe Calls Make a Race, extra experiment: SeatChartOnce's ten computeIfAbsent cards; 50 ms in, main counts the pool clerks' states.
// Expect {BLOCKED=9, TIMED_WAITING=1}: nine wait at the reserved pigeonhole's lock (ConcurrentHashMap.java:1753), one naps inside the build.
static final Map<String, List<String>> charts = new ConcurrentHashMap<>();
static List<String> buildChart(String coach) {
    try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    return List.of(coach + "-41");
}
void main() throws Exception {
    List<Callable<List<String>>> clerks = new ArrayList<>();
    for (int i = 1; i <= 10; i++) clerks.add(() -> charts.computeIfAbsent("S7", c -> buildChart(c)));
    try (ExecutorService desk = Executors.newFixedThreadPool(10)) {
        List<Future<List<String>>> slips = new ArrayList<>();
        for (var c : clerks) slips.add(desk.submit(c));
        Thread.sleep(50);
        Map<Thread.State, Integer> states = new TreeMap<>();
        for (Thread t : Thread.getAllStackTraces().keySet())
            if (t.getName().startsWith("pool-1-thread-")) states.merge(t.getState(), 1, Integer::sum);
        IO.println("at 50 ms: " + states);
        for (var s : slips) s.get();
    }
}
