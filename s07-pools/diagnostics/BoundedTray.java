void main() {
    ThreadPoolExecutor desk = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(3));                     // two clerks, a tray that holds three
    try {
        for (int n = 1; n <= 6; n++) {
            desk.execute(() -> nap(500));
            IO.println("card " + n + " accepted");
        }
    } catch (RejectedExecutionException e) {
        IO.println(e.getMessage());
    } finally {
        desk.shutdown();
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
