void main() {
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {
        desk.execute(() -> nap(60_000));                      // one card that takes a whole minute
        IO.println("card handed in");
    }
    IO.println("the try block is over");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
