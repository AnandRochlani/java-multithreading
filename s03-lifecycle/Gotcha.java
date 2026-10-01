void main() throws InterruptedException {
    Thread window9 = new Thread(() -> nap(3000), "window-9");     // never started

    long start = System.currentTimeMillis();
    window9.join();
    IO.println("join returned after " + (System.currentTimeMillis() - start) + " ms");
    IO.println("window-9 is " + window9.getState());
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
