void main() {                                                 // the original shape: card 1 does not nap
    ExecutorService desk = Executors.newFixedThreadPool(4);
    desk.execute(() -> IO.println("card 1 served"));
    desk.shutdown();
    desk.execute(() -> IO.println("card 2 served"));          // handed in after the shutters came down
    IO.println("never printed");
}
