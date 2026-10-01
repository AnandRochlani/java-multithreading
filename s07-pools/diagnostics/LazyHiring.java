void main() throws InterruptedException {
    ThreadPoolExecutor desk = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    IO.println("before any card: pool size " + desk.getPoolSize());
    desk.execute(() -> IO.println("card A on " + Thread.currentThread().getName()));
    Thread.sleep(200);                                        // card A is long finished; its clerk is idle
    IO.println("after card A: pool size " + desk.getPoolSize() + ", active " + desk.getActiveCount());
    desk.execute(() -> IO.println("card B on " + Thread.currentThread().getName()));
    Thread.sleep(200);
    IO.println("after card B: pool size " + desk.getPoolSize());
    for (int i = 0; i < 10; i++) desk.execute(() -> {});
    Thread.sleep(200);
    IO.println("after ten more: pool size " + desk.getPoolSize());
    desk.shutdown();
}
