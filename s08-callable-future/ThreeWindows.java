void main() throws Exception {
    long start = System.nanoTime();
    List<Callable<String>> enquiries = List.of(
            lookup("berth S7-42", 300, start),                    // the slowest, handed in first
            lookup("fare 1250", 250, start),
            lookup("platform 3", 200, start));                    // the fastest, handed in last
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        List<Future<String>> slips = desk.invokeAll(enquiries);   // hand in all three, wait for all three
        IO.println(at(start) + "invokeAll returned " + slips.size() + " slips");
        for (Future<String> slip : slips) {
            IO.println(at(start) + "  " + slip.get());            // every get is instant now
        }
    }
}

static Callable<String> lookup(String answer, long ms, long start) {
    return () -> {
        Thread.sleep(ms);
        IO.println(at(start) + "a clerk finished: " + answer);
        return answer;
    };
}

static String at(long start) {
    return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000);
}
