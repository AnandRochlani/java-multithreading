static final Object LOCK = new Object();

void main() throws InterruptedException {
    IO.println("sleep(1000): main got in after " + trial(false) + " ms");
    IO.println("wait(1000) : main got in after " + trial(true) + " ms");
}

static long trial(boolean useWait) throws InterruptedException {
    Thread holder = new Thread(() -> {
        synchronized (LOCK) {
            try {
                if (useWait) LOCK.wait(1000); else Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }, "holder");
    holder.start();
    Thread.sleep(100);                        // let the holder take the lock first

    long start = System.currentTimeMillis();
    long waited;
    synchronized (LOCK) {                     // main tries to get in
        waited = System.currentTimeMillis() - start;
        LOCK.notifyAll();                     // let the holder go
    }
    holder.join();                            // outside the lock, or we would never let it finish
    return waited;
}
