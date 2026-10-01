void main() {
    ExecutorService desk = Executors.newFixedThreadPool(4);
    desk.execute(() -> IO.println("card ran on " + Thread.currentThread().getName()));
    desk.shutdown();                                          // the shutters come down
    IO.println("main is finished");
}
