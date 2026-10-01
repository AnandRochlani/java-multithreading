// invokeAll with a time limit: what happens to the lookups still running when it expires?
void main() throws Exception {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        List<Future<String>> slips = desk.invokeAll(List.of(
                () -> { Thread.sleep(300); return "berth S7-42"; },
                () -> { Thread.sleep(250); return "fare 1250"; },
                () -> { Thread.sleep(200); return "platform 3"; }), 260, TimeUnit.MILLISECONDS);
        IO.println(String.format("%4d ms  invokeAll(260 ms) returned", (System.nanoTime() - start) / 1_000_000));
        for (Future<String> slip : slips) {
            IO.println("         " + slip.state() + (slip.state() == Future.State.SUCCESS ? "  " + slip.resultNow() : ""));
        }
    }
}
