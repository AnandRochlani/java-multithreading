void main() throws Exception {                              // let every checked exception out
    List<Future<?>> slips = new ArrayList<>();
    try (ExecutorService desk = Executors.newFixedThreadPool(1)) {   // a roster of one clerk
        for (int n = 1; n <= 4; n++) {
            int card = n;
            slips.add(desk.submit(() -> {
                nap(50);                                          // a moment at the window
                if (card == 2) throw new IllegalStateException("card 2 is torn");
                IO.println("card " + card + " served by " + Thread.currentThread().getName());
            }));
        }
        for (int n = 1; n <= 4; n++) {
            try {
                Object answer = slips.get(n - 1).get();           // present the slip, and wait if you must
                IO.println("slip " + n + ": get returned " + answer);
            } catch (IllegalStateException e) {                   // catch what the card throws?
                IO.println("slip " + n + ": caught " + e);
            }
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
