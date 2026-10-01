void main() {
    ExecutorService desk = Executors.newCachedThreadPool();  // no ceiling on the roster
    int handedIn = 0;
    try {
        for (int p = 1; p <= 5_000; p++) {
            desk.execute(() -> nap(1_000));                   // the cold open's card: a second each
            handedIn++;
        }
        IO.println("handed in " + handedIn + " cards");
    } catch (OutOfMemoryError e) {
        IO.println("handed in " + handedIn + " cards, then card " + (handedIn + 1) + " got:");
        IO.println(e);
    } finally {
        desk.shutdown();
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
