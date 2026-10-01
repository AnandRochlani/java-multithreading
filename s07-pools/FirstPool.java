void main() {
    ExecutorService desk = Executors.newFixedThreadPool(3);  // a roster of three clerks
    for (int n = 1; n <= 6; n++) {
        int card = n;
        desk.execute(() -> {                                  // hand over the card, not a clerk
            nap(100);
            IO.println("card " + card + " served by " + Thread.currentThread().getName());
        });
    }
    IO.println("main: all six cards handed in");
    desk.shutdown();                                          // the station closes after these cards
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
