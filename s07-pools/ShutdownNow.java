void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(2);
    long start = System.nanoTime();
    for (int n = 1; n <= 6; n++) {
        int card = n;
        desk.execute(() -> {
            try {
                Thread.sleep(500);                            // half a second at the window
                IO.println("card " + card + " served");
            } catch (InterruptedException e) {
                IO.println("card " + card + " interrupted after " + (System.nanoTime() - start) / 1_000_000 + " ms");
            }
        });
    }
    Thread.sleep(100);
    List<Runnable> neverStarted = desk.shutdownNow();         // the building is on fire
    IO.println("shutdownNow handed back " + neverStarted.size() + " cards that never started");
    IO.println("all clerks gone home? " + desk.awaitTermination(1, TimeUnit.SECONDS));
}
