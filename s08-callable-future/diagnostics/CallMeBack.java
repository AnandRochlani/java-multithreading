// The claim under test: "A Future cannot tell you when it is done ... the class for that is called CompletableFuture."
// Checked on 27: a callback runs on the clerk's thread, and with no executor it uses the common pool (daemon clerks).
void main() throws Exception {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {
        CompletableFuture<Void> done = CompletableFuture
                .supplyAsync(() -> { nap(300); return "S7-42"; }, desk)
                .thenAccept(berth -> IO.println(String.format("%4d ms  callback on %s: berth %s",
                        (System.nanoTime() - start) / 1_000_000, Thread.currentThread().getName(), berth)));
        IO.println(String.format("%4d ms  main: not waiting, not polling", (System.nanoTime() - start) / 1_000_000));
        done.join();
    }
    CompletableFuture.supplyAsync(() -> { nap(300); return "S7-42"; })        // no executor: the common pool
            .thenAccept(berth -> IO.println("common-pool callback: berth " + berth));
    IO.println("main: returning without waiting for the common-pool card, daemon? "
            + CompletableFuture.supplyAsync(() -> Thread.currentThread().isDaemon()).join());
}

static void nap(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}
