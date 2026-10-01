static final Object tray = new Object();

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        synchronized (tray) {
            try { tray.wait(); } catch (InterruptedException e) { return; }
        }
    }, "printer");
    ravi.start();
    Thread.sleep(100);                                   // let Ravi sit down
    IO.println("before the knock  : " + ravi.getState());
    synchronized (tray) {                                // Meena takes the key
        tray.notifyAll();                                // knocks
        Thread.sleep(100);                               // and keeps the key a moment
        IO.println("knocked, key held : " + ravi.getState());
    }
    ravi.join();
    IO.println("after Meena left  : " + ravi.getState());
}
