void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {   // close() runs at the closing brace
        for (int n = 1; n <= 6; n++) {
            int card = n;
            desk.execute(() -> {
                nap(200);
                IO.println("card " + card + " served by " + Thread.currentThread().getName());
            });
        }
        IO.println("all six handed in after " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }
    IO.println("the try block is over after " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
