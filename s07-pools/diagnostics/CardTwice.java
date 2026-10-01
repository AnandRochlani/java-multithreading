static class Window9 extends Thread {
    Window9() { super("window-9"); }
    @Override public void run() { IO.println("window-9's card read by " + Thread.currentThread().getName()); }
}

void main() throws InterruptedException {
    Window9 clerk = new Window9();
    try (ExecutorService desk = Executors.newFixedThreadPool(2)) {
        desk.execute(clerk);
        desk.execute(clerk);                                  // the same clerk, handed in twice
    }
    IO.println("window-9 is " + clerk.getState());
    clerk.start();                                            // and he can still be started once
    clerk.join();
    IO.println("window-9 is " + clerk.getState());
}
