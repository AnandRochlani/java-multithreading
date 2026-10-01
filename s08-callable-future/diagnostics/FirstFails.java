// invokeAny when the fastest server fails, and when every server fails.
void main() throws Exception {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        String berth = desk.invokeAny(List.of(
                mirror("north", 300, false, start), mirror("south", 200, true, start), mirror("east", 250, false, start)));
        IO.println(at(start) + "invokeAny returned: " + berth);
        try {
            desk.invokeAny(List.of(
                    mirror("north", 300, true, start), mirror("south", 200, true, start), mirror("east", 250, true, start)));
        } catch (ExecutionException e) {
            IO.println(at(start) + "every server failed: " + e);
        }
    }
}

static Callable<String> mirror(String server, long ms, boolean fails, long start) {
    return () -> {
        Thread.sleep(ms);
        if (fails) throw new IllegalStateException(server + " server is down");
        return "S7-42 from the " + server + " server";
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
