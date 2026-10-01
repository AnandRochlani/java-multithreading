void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(2);
    for (int n = 1; n <= 4; n++) {
        int card = n;
        desk.execute(() -> {
            nap(300);
            IO.println("card " + card + " served");
        });
    }
    desk.shutdown();                                          // no new cards; the four still get served
    boolean done = desk.awaitTermination(100, TimeUnit.MILLISECONDS);
    IO.println("after 100 ms, all clerks gone home? " + done);
    done = desk.awaitTermination(5, TimeUnit.SECONDS);
    IO.println("after waiting up to 5 s more, all clerks gone home? " + done);
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
