void main() {
    ThreadFactory hire = Thread.ofPlatform().daemon().name("window-", 1).factory();   // daemon clerks
    ExecutorService desk = Executors.newFixedThreadPool(3, hire);
    for (int n = 1; n <= 6; n++) {
        int card = n;
        desk.execute(() -> {
            nap(100);
            IO.println("card " + card + " served by " + Thread.currentThread().getName());
        });
    }
    IO.println("main is finished");                           // no shutdown, and no waiting either
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
