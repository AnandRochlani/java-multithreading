void main() throws Exception {
    long start = System.nanoTime();
    List<Callable<String>> mirrors = List.of(                     // one question, three servers
            mirror("north", 300, start),
            mirror("south", 200, start),
            mirror("east", 250, start));
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        String berth = desk.invokeAny(mirrors);                   // the first good answer wins
        IO.println(at(start) + "invokeAny returned: " + berth);
    }
    IO.println(at(start) + "the try block is over");
}

static Callable<String> mirror(String server, long ms, long start) {
    return () -> {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            IO.println(at(start) + server + " server: told to stop");
            throw e;
        }
        IO.println(at(start) + server + " server: answered");
        return "S7-42 from the " + server + " server";
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
