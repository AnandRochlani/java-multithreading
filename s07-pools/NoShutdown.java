void main() {
    ExecutorService desk = Executors.newFixedThreadPool(4);
    desk.execute(() -> IO.println("card ran on " + Thread.currentThread().getName()));
    IO.println("main is finished");
}
