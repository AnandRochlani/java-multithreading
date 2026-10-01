void main() {
    long start = System.nanoTime();
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        for (int n = 1; n <= 3; n++) {
            int card = n;
            desk.execute(() -> {
                nap(200);
                IO.println("card " + card + " served after " + (System.nanoTime() - start) / 1_000_000 + " ms");
            });
        }
        throw new IllegalStateException("main tripped inside the try");
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
