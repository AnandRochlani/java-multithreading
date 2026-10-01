// cancel(false) on a card that has already started: no tap on the shoulder.
void main() throws Exception {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        Future<String> seat = desk.submit(() -> {
            Thread.sleep(300);
            IO.println(String.format("%4d ms  clerk: finished the lookup anyway", (System.nanoTime() - start) / 1_000_000));
            return "S7-42";
        });
        Thread.sleep(100);
        boolean torn = seat.cancel(false);                        // false: do not interrupt a running clerk
        IO.println(String.format("%4d ms  main: cancel(false) returned %b, state %s", (System.nanoTime() - start) / 1_000_000, torn, seat.state()));
        try { seat.get(); } catch (CancellationException e) { IO.println("         main: get threw " + e); }
    }
    IO.println(String.format("%4d ms  main: the try block is over", (System.nanoTime() - start) / 1_000_000));
}
