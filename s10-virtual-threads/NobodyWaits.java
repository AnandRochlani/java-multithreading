void main() {
    Thread.ofVirtual().start(() -> {
        nap(100);                                             // a tenth of a second at the window
        IO.println("the virtual clerk served the card");
    });
    IO.println("main is finished");                           // no join, no close
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
