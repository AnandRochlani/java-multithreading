static class Window9 extends Thread {                         // a whole clerk, card sewn on
    Window9() { super("window-9"); }
    @Override public void run() {
        IO.println("getName() says " + getName() + ", but I run on " + Thread.currentThread().getName());
    }
}

void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(2);
    Window9 clerk = new Window9();
    desk.execute(clerk);                                      // a Thread is also a Runnable
    Thread.sleep(200);
    IO.println("window-9 is " + clerk.getState() + ", alive: " + clerk.isAlive());
    desk.shutdown();
}
