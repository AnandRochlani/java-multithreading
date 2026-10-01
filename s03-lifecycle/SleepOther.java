void main() throws InterruptedException {
    Thread tom = new Thread(() -> IO.println("Tom is selling tickets"), "Tom");
    long start = System.currentTimeMillis();
    tom.sleep(1000);                                              // looks like it puts Tom to sleep
    IO.println(Thread.currentThread().getName() + " slept for " + (System.currentTimeMillis() - start) + " ms; Tom is " + tom.getState());
}
