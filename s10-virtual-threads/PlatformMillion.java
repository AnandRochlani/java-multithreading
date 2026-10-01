// RushHour's million with the threads Section 2 taught you to build: new Thread, then start
void main() {
    long start = System.nanoTime();
    int hired = 0;
    try {
        for (int p = 0; p < 1_000_000; p++) {
            new Thread(() -> nap(1_000)).start();             // a platform thread per passenger, a second each
            hired++;
        }
    } catch (OutOfMemoryError e) {
        IO.println("hired " + hired + " platform threads in " + (System.nanoTime() - start) / 1_000_000 + " ms, then:");
        IO.println(e);
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
