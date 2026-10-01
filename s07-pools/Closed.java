void main() {
    ExecutorService desk = Executors.newFixedThreadPool(4);  // a roster of four
    desk.execute(() -> {
        nap(100);
        IO.println("card 1 served");
    });
    desk.shutdown();
    desk.execute(() -> IO.println("card 2 served"));          // handed in after the shutters came down
    IO.println("never printed");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
