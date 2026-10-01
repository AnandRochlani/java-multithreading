static final Object TRAY = new Object();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        synchronized (TRAY) {
            try { TRAY.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }, "Meena");
    Thread ravi = new Thread(() -> {
        synchronized (TRAY) {
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }, "Ravi");
    Thread tom = new Thread(() -> {
        synchronized (TRAY) { }
    }, "Tom");

    meena.start();  Thread.sleep(100);        // Meena waits, and gives the key back
    ravi.start();   Thread.sleep(100);        // Ravi takes the key and sleeps holding it
    tom.start();    Thread.sleep(100);        // Tom wants the key

    IO.println("Meena (called wait)          : " + meena.getState());
    IO.println("Ravi  (sleeping with the key): " + ravi.getState());
    IO.println("Tom   (wants the key)        : " + tom.getState());

    ravi.join();
    tom.join();
    synchronized (TRAY) { TRAY.notifyAll(); }
    meena.join();
}
