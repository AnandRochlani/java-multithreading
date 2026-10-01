void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(1);
    long start = System.nanoTime();
    desk.execute(() -> {
        long end = System.nanoTime() + 1_000_000_000L;
        while (System.nanoTime() < end) { }                   // a second of work that never checks for a tap
        IO.println("deaf card finished after " + (System.nanoTime() - start) / 1_000_000 + " ms, tapped? "
                + Thread.currentThread().isInterrupted());
    });
    Thread.sleep(100);
    IO.println("shutdownNow handed back " + desk.shutdownNow().size() + " cards");
    boolean done = desk.awaitTermination(2, TimeUnit.SECONDS);
    IO.println("all clerks gone home? " + done + ", after " + (System.nanoTime() - start) / 1_000_000 + " ms");
}
