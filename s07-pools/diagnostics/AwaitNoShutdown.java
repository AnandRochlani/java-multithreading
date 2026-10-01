void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(2);
    desk.execute(() -> IO.println("card served"));
    long start = System.nanoTime();
    boolean done = desk.awaitTermination(2, TimeUnit.SECONDS);   // forgot shutdown() first
    IO.println("awaitTermination returned " + done + " after " + (System.nanoTime() - start) / 1_000_000 + " ms");
    desk.shutdown();
}
