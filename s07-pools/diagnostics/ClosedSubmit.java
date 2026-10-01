void main() {
    ExecutorService desk = Executors.newFixedThreadPool(4);
    desk.submit(() -> IO.println("card 1 served"));
    desk.shutdown();
    desk.submit(() -> IO.println("card 2 served"));
    IO.println("never printed");
}
