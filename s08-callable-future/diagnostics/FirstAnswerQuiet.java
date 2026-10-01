// FirstAnswer prints "invokeAny returned" ~15 ms after "south server: answered". Is that invokeAny, or the first
// use of the clerk's print line? Same three mirrors, but nobody prints until invokeAny has returned.
static volatile long answeredAt, stoppedAt;

void main() throws Exception {
    long start = System.nanoTime();
    String berth;
    long returnedAt;
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        berth = desk.invokeAny(List.of(mirror("north", 300), mirror("south", 200), mirror("east", 250)));
        returnedAt = System.nanoTime();
    }
    long closedAt = System.nanoTime();
    IO.println(String.format("answered %.1f ms, invokeAny returned %.1f ms, a loser stopped %.1f ms, closed %.1f ms: %s",
            (answeredAt - start) / 1e6, (returnedAt - start) / 1e6, (stoppedAt - start) / 1e6, (closedAt - start) / 1e6, berth));
}

static Callable<String> mirror(String server, long ms) {
    return () -> {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            stoppedAt = System.nanoTime();
            throw e;
        }
        answeredAt = System.nanoTime();
        return server;
    };
}
