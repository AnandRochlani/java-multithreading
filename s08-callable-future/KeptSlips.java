void main() {
    List<Future<?>> slips = new ArrayList<>();                    // somewhere to keep the slips
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {   // a roster of one clerk
        for (int n = 1; n <= 4; n++) {
            int card = n;
            slips.add(desk.submit(() -> {                         // keep what submit hands back
                nap(50);                                          // a moment at the window
                if (card == 2) throw new IllegalStateException("card 2 is torn");
                IO.println("card " + card + " served by " + Thread.currentThread().getName());
            }));
        }
    }                                                             // close() waits for all four cards
    for (int n = 1; n <= 4; n++) {
        IO.println("slip " + n + ": " + slips.get(n - 1));        // what is written on each slip?
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
