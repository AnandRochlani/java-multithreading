void main() {
    long start = System.nanoTime();
    ExecutorService desk = Executors.newCachedThreadPool();  // no shutdown anywhere
    desk.execute(() -> IO.println("card ran on " + Thread.currentThread().getName()));
    Runtime.getRuntime().addShutdownHook(new Thread(() ->
            IO.println("JVM exiting after " + (System.nanoTime() - start) / 1_000_000 + " ms")));
    IO.println("main is finished");
}
