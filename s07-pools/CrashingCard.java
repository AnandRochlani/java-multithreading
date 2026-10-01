void main() {
    ExecutorService desk = Executors.newFixedThreadPool(1);  // a roster of one clerk
    for (int n = 1; n <= 4; n++) {
        int card = n;
        desk.execute(() -> {
            nap(50);                                          // a moment at the window
            if (card == 2) throw new IllegalStateException("card 2 is torn");
            IO.println("card " + card + " served by " + Thread.currentThread().getName());
        });
    }
    desk.shutdown();
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
