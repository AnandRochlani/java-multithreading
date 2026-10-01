void main() {
    IO.println("cores on this machine: " + Runtime.getRuntime().availableProcessors());
    for (int clerks : new int[] {4, 10, 25, 50, 200}) {
        long start = System.nanoTime();
        try (ExecutorService desk = Executors.newFixedThreadPool(clerks)) {
            for (int i = 1; i <= 200; i++) desk.execute(() -> nap(100));   // 200 waiting cards
        }
        IO.println(String.format("pool of %3d: %5d ms", clerks, (System.nanoTime() - start) / 1_000_000));
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
