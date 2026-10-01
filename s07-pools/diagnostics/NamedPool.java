void main() {
    ThreadFactory hire = Thread.ofPlatform().name("window-", 1).factory();   // the builder from Section 2
    try (ExecutorService desk = Executors.newFixedThreadPool(3, hire)) {
        for (int n = 1; n <= 6; n++) {
            int card = n;
            desk.execute(() -> {
                nap(100);
                IO.println("card " + card + " served by " + Thread.currentThread().getName());
            });
        }
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
