void main() throws InterruptedException {
    Object tray = new Object();
    synchronized (tray) {
        tray.wait();                    // now we own the key, so we may wait
    }
}
